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

package scanning;

import java.util.ArrayList;

public class Scanner {

    private final String input;
    // Tracks the line number
    private int lineNumber = 0;
    // Tracks the position of the lexer
    private int position = 0;
    // Tracks whether the scanner is at the start of the line
    private boolean isLineStart = true;
    // Tracks the indentation level of the line
    private int indentationLevel = 0;

    public Scanner(String input) {
        this.input = input;
    }

    public ArrayList<Token> tokens() {
        ArrayList<Token> tokens = new ArrayList<>();
        int inputSize = input.length();

        if (position >= input.length()) {
            tokens.add(new Token(TokenType.EOF, "", lineNumber, position));
            return tokens;
        }

        // While there's still input
        while (position < inputSize) {
            Token token = nextToken();
            tokens.add(token);
        }

        return tokens;
    }

    /**
     * Consumes and returns the next token.
     *
     * @return The next token
     */
    protected Token nextToken() {
        Token nextToken;

        // TODO: check current char and decide which scanner to use
        if (position >= input.length()) {
            return new Token(TokenType.EOF, "", lineNumber, position);
        }
        char nextChar = peek();

        // If whitespace is found at the start of the line, update the indentation level
        if (nextChar == ' ' && isLineStart) {
            indentationLevel++;
            position++;
            return nextToken();
        } else if (nextChar == '\n') {
            position++;
            isLineStart = true;
            lineNumber++;
            return nextToken();
        }

        return null;
    }

    /**
     * Returns the char at the current position without consuming it.
     * Assumes EOF hasn't been reached.
     *
     * @return char at current position
     */
    protected char peek() {
        return input.charAt(position);
    }

}
