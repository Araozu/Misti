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

import error.ScannerError;
import scanning.MainScanner;
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
    private final StringBuilder currentValue = new StringBuilder();
    private MainScanner mainScanner;

    protected AbstractScanner(MainScanner mainScanner) {
        // If mainScanner didn't throw, these values are valid
        input = mainScanner.getInput();
        position = mainScanner.getPosition();
        lineNumber = mainScanner.getLineNumber();

        startPosition = position;
        inputSize = input.length();
        this.mainScanner = mainScanner;
    }

    public int getPosition() {
        return position;
    }

    protected String getCurrentValue() {
        return currentValue.toString();
    }

    protected void addError(ScannerError error) {
        mainScanner.addError(error);
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
     * @return The next character without consuming it, or \0 if EOF is reached
     */
    protected char peek1() {
        if (!hasNext()) return '\0';
        return input.charAt(position);
    }

    /**
     * @return The next's next character without consuming it, or \0 if EOF is reached
     */
    protected char peek2() {
        if (!hasNext2()) return '\0';
        return input.charAt(position + 1);
    }

    /**
     * @return The next's next's next character without consuming it, or \0 if EOF is reached
     */
    protected char peek3() {
        if (!hasNext3()) return '\0';
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

    /**
     * @return Whether there is at least 3 chars remaining
     */
    protected boolean hasNext3() {
        return position + 2 < inputSize;
    }

    protected void append(char c) {
        currentValue.append(c);
    }

    /**
     * @param type The type of token
     * @param value The string value
     * @return A token with these values
     */
    private Token create(TokenType type, String value) {
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
