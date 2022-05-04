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

public abstract class AbstractScanner {
    protected final String input;
    protected int position;
    protected final int startPosition;
    protected final int lineNumber;
    private final int inputSize;
    /**
     * Stores the value of the token currently being scanned
     */
    private StringBuilder currentValue = new StringBuilder();

    protected AbstractScanner(Scanner scanner) {
        input = scanner.getInput();
        position = scanner.getPosition();
        lineNumber = scanner.getLineNumber();

        if (input == null) {
            throw new RuntimeException("AbstractScanner: Input is null");
        }
        if (position < 0) {
            throw new RuntimeException("AbstractScanner: Start position is negative");
        }
        if (lineNumber < 0) {
            throw new RuntimeException("AbstractScanner: Line number is negative");
        }

        startPosition = position;
        inputSize = input.length();
    }

    /**
     * Attempts to scan a token.
     *
     * @return A token, if found, or null
     */
    public abstract Token scan();

    /**
     * Consumes and returns the next character
     *
     * @return the next character
     */
    protected char next() {
        char c = input.charAt(position);
        position++;
        return c;
    }

    /**
     * Returns the next character, without consuming it.
     *
     * @return the next character
     */
    protected char peek1() {
        if (!hasNext()) return '\0';
        return input.charAt(position);
    }

    /**
     * Returns the character after the next, without consuming it.
     * Assumes that character exists
     * @return
     */
    protected char peek2() {
        return input.charAt(position + 1);
    }

    protected char peek3() {
        return input.charAt(position + 2);
    }

    /**
     * @return Whether there is input remaining
     */
    protected boolean hasNext() {
        return position < inputSize;
    }

    /**
     * @return Whether there is at least 2 chars remaining
     */
    protected boolean hasNext2() {
        return position + 1 < inputSize;
    }

    protected void append(char c) {
        currentValue.append(c);
    }

    protected void append(String s) {
        currentValue.append(s);
    }

    /**
     * Creates a token with the specified value
     * @param type
     * @param value
     * @return
     */
    protected Token create(TokenType type, String value) {
        return new Token(
                type,
                value,
                lineNumber,
                startPosition
        );
    }

    /**
     * Creates a token with the value currently stored
     * @param type The type of token
     * @return A token
     */
    protected Token create(TokenType type) {
        return create(type, currentValue.toString());
    }
}
