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

import scanning.scanner.AbstractScanner;
import scanning.scanner.IdentifierScanner;
import scanning.scanner.NumberScanner;
import scanning.scanner.OperatorScanner;

import java.util.ArrayList;

public class MainScanner {

    private final String input;
    private final int inputSize;
    // Tracks the line number
    private int lineNumber = 0;
    // Tracks the position of the lexer
    private int position = 0;
    // Tracks whether the scanner is at the start of the line
    private boolean isLineStart = true;
    // Tracks the indentation level of the line
    private int indentationLevel = 0;

    public MainScanner(String input) {
        this.input = input;
        if (input == null) {
            throw new RuntimeException("NumberScanner: Input is null");
        }

        this.inputSize = input.length();
    }

    public String getInput() {
        return input;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getPosition() {
        return position;
    }

    private boolean hasNext() {
        return position < inputSize;
    }

    public ArrayList<Token> tokens() {
        ArrayList<Token> tokens = new ArrayList<>();

        // While there's still input
        while (hasNext()) {
            Token token = nextToken();
            // If an unknown character is found
            if (token == null) continue;
            tokens.add(token);
        }

        // Add EOF
        tokens.add(new Token(TokenType.EOF, "", lineNumber, position));

        return tokens;
    }

    /**
     * Consumes and returns the next token.
     *
     * @return The next token
     */
    protected Token nextToken() {
        // Check if there's input, because this method is recursive.
        if (!hasNext()) {
            return null;
        }
        char nextChar = peek();

        // TODO: check current char and decide which scanner to use

        // Check for number - integer of float
        if (Utils.isDigit(nextChar)) {
            AbstractScanner sc = new NumberScanner(this);
            Token t = sc.scan();
            this.position = sc.getPosition();
            return t;
        }
        // Check for identifier/keyword
        else if (Utils.isLowercase(nextChar) || nextChar == '_') {
            AbstractScanner sc = new IdentifierScanner(this);
            Token t = sc.scan();
            this.position = sc.getPosition();
            return t;
        }
        // Check for operators
        else if (Utils.isOperatorChar(nextChar)) {
            OperatorScanner op = new OperatorScanner(this);
            Token t = op.scan();
            this.position = op.getPosition();
            return t;
        }
        // If whitespace is found at the start of the line, update the indentation level
        // TODO: Emit INDENT and DEDENT tokens
        else if (nextChar == ' ' && isLineStart) {
            indentationLevel++;
            position++;
            return nextToken();
        }
        else if (nextChar == '\n') {
            position++;
            isLineStart = true;
            lineNumber++;
            return nextToken();
        }
        // No adequate scanner found, or implemented.
        else {
            // TODO: Error handling
            System.err.println("unrecognized character: " + nextChar);
            position++;
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
        if (!hasNext()) return '\0';
        return input.charAt(position);
    }

}