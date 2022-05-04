/*
 * Copyright (c) 2021
 * Fernando Enrique Araoz Morales.
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program;
 * if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package scanning.scanner;

import scanning.Scanner;
import scanning.Token;
import scanning.TokenType;

/**
 * Scans a double or integer. The specification is found in
 * the spec web page under /primitives/number.
 *
 * This class assumes that the first character conforms to the spec,
 * so it doesn't handle whitespace or indentation.
 */
public class NumberScanner extends AbstractScanner {
    public NumberScanner(Scanner scanner) {
        super(scanner);
    }

    private boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }

    private boolean isHexDigit(char c) {
        return 'a' <= c && c <= 'f' || 'A' <= c && c <= 'F';
    }

    private Token scanFloatingPoint() {
        // Precondition: currentValue contains an integer
        //               next() will return '.'

        // If there's no more input return the scanned integer
        if (!hasNext2()) return create(TokenType.Integer);

        // Contains the char after '.'
        char current = peek2();

        if (!isDigit(current)) {
            // Something like  '2.x' , return only 2 without consuming the dot and x
            return create(TokenType.Integer);
        }

        // Consume and append the 2 characters: '.' and the next number
        append(next());
        append(next());

        char c;
        while (hasNext()) {
            c = peek1();
            if (isDigit(c)) {
                append(next());
            }
            // TODO: catch 'e' for exponents
            else {
                break;
            }
        }

        return create(TokenType.Floating);
    }

    private Token scanScientificNotation() {
        // Precondition: currentValue contains an integer or floating point
        //               next() returns 'e'

        // Should contain + or -
        char p2 = peek2();
        if (p2 != '+' && p2 != '-') {
            // TODO: Report an error of scientific notation, instead of generic error?
            // TODO: What to return? TokenType.Floating or Integer?

            return create(TokenType.Floating);
        }

        // Should contain a digit
        char p3 = peek3();
        if (!isDigit(p3)) {
            // TODO: What to return? TokenType.Floating or Integer?
            return create(TokenType.Floating);
        }

        // Consume the 3 peeked chars: e, +/- and digit
        append(next());
        append(next());
        append(next());

        while (isDigit(peek1())) append(next());

        return create(TokenType.Floating);
    }

    private Token scanDecimal() {
        char c;
        while (hasNext()) {
            c = peek1();
            if (isDigit(c)) {
                append(next());
            }
            // TODO: Catch '.' or 'e' for floating point
            else if (c == '.') {
                return scanFloatingPoint();
            }
            else if (c == 'e') {
                return scanScientificNotation();
            }
            else break;
        }
        return create(TokenType.Integer);
    }

    // Assumes the next call to next() will return the first character
    private Token scanHexadecimalInteger() {
        // Precondition: currentValue is '0'
        //               peek() returns 'x'

        // Test the next character
        char c = peek2();
        if (isDigit(c) || isHexDigit(c)) {
            // If the hex is valid, consume and append the 2 chars
            // Consume and append 'x'
            append(next());
            // Consume and returns the next digit
            append(next());

            while (hasNext()) {
                c = peek1();
                if (isDigit(c) || isHexDigit(c)) {
                    append(next());
                }
            }
        } else {
            // 0x was detected, but then no other hexadecimal value. Only return 0.
            return create(TokenType.Integer, "0");
        }

        return create(TokenType.Integer);
    }

    /**
     * Attempts to scan a number (double or integer). It returns the corresponding token.
     * Assumes the next char is valid.
     * @return A number token, or null if not found
     */
    public Token scan() {
        if (!hasNext()) return null;

        // DecimalInteger
        char c = next();
        append(c);

        if (c == '0') {
            c = peek1();
            // HEX
            if (c == 'x' || c == 'X') {
                return scanHexadecimalInteger();
            } else if (c == '.') {
                return scanFloatingPoint();
            } else if (isDigit(c)) {
                return scanDecimal();
            } else if (c == 'e') {
                return scanScientificNotation();
            } else {
                // It's not decimal nor hex, return only 0
                return create(TokenType.Integer);
            }
        } else if (isDigit(c)) {
            return scanDecimal();
        }

        return null;
    }

}
