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

import scanning.Token;
import scanning.TokenType;

public abstract class AbstractScanner {
    protected final String input;
    protected int position;
    protected final int startPosition;
    protected final int lineNumber;
    private final int inputSize;

    protected AbstractScanner(String input, int position, int lineNumber) {
        if (input == null) {
            throw new RuntimeException("NumberScanner: Input is null");
        }
        if (position < 0) {
            throw new RuntimeException("NumberScanner: Start position is negative");
        }
        if (lineNumber < 0) {
            throw new RuntimeException("NumberScanner: Line number is negative");
        }

        this.input = input;
        this.position = position;
        startPosition = position;
        this.lineNumber = lineNumber;
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
    protected char peek() {
        return input.charAt(position);
    }

    /**
     * @return Whether there is input remaining
     */
    protected boolean hasNext() {
        return position < inputSize;
    }

    protected Token create(TokenType type, String value) {
        return new Token(
                type,
                value,
                lineNumber,
                startPosition
        );
    }
}
