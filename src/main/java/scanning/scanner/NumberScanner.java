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

    private Token scanDecimalInteger(String start) {
        StringBuilder builder = new StringBuilder();
        builder.append(start);
        char c;
        while (hasNext()) {
            c = next();
            if ('0' <= c && c < '9') builder.append(c);
            else break;
        }
        return create(TokenType.Integer, builder.toString());
    }

    private Token scanHexadecimalInteger(String start) {
        if (!hasNext()) return null;

        StringBuilder builder = new StringBuilder();
        builder.append(start);

        char c = next();
        if ('0' <= c && c < '9' || 'a' <= c && c <= 'f' || 'A' <= c && c <= 'F') {
            builder.append(c);
            while (hasNext()) {
                c = next();
                if ('0' <= c && c < '9' || 'a' <= c && c <= 'f' || 'A' <= c && c <= 'F') {
                    builder.append(c);
                }
            }
        } else {
            // 0x was detected, but then no other hexadecimal value. Only return 0.
            return create(TokenType.Integer, "0");
        }

        return create(TokenType.Integer, builder.toString());
    }

    /**
     * Attempts to scan a number (double or integer). It returns the corresponding token.
     * @return A number token, or null if not found
     */
    public Token scan() {
        if (!hasNext()) return null;

        // DecimalInteger
        char c = next();
        if (c == '0') {
            c = next();
            // HEX
            if (c == 'x' || c == 'X') {
                return scanHexadecimalInteger("0" + c);
            } else if ('0' <= c && c < '9') {
                return scanDecimalInteger("0" + c);
            } else {
                // It's not decimal nor hex, return only 0
                return create(TokenType.Integer, "0");
            }
        } else if ('1' <= c && c < '9') {
            return scanDecimalInteger(c + "");
        }

        return null;
    }

}
