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


        // Handle whitespace
        if (!isLineStart && nextChar == ' ') {
            while (peek() == ' ') position++;
            return nextToken();
        }
        // Handle indentation
        // If it is a new line, and the current indentation level is greater than 0,
        //  then there should probably be a dedent
        else if (isLineStart && (nextChar == ' ' || indentationLevel > 0)) {

            int newIndentationLevel = 0;
            while (peek() == ' ') {
                newIndentationLevel++;
                position++;
            }

            // If the next char is \0, then we reached EOF. There is nothing to indent/dedent
            if (peek() == '\0') {
                return nextToken();
            }

            // If:
            // - this is not a new line where indentation drops to 0
            // - the next char is a new line
            // return, as there is nothing to indent/dedent
            if (newIndentationLevel != 0 && peek() == '\n') {
                return nextToken();
            }

            // Check current indentation level. If the new level is higher, emit an INDENT token
            if (newIndentationLevel > indentationLevel) {
                indentationLevel = newIndentationLevel;
                isLineStart = false;
                return new Token(TokenType.Indent, "", lineNumber, position);
            }
            // If it is lower, emit a DEDENT token
            else if (newIndentationLevel < indentationLevel) {
                indentationLevel = newIndentationLevel;
                isLineStart = false;
                return new Token(TokenType.Dedent, "", lineNumber, position);
            }
            // If it is the same, do nothing
            else {
                return nextToken();
            }
        }
        else if (nextChar == '\n') {
            position++;
            isLineStart = true;
            lineNumber++;
            return nextToken();
        }

        // At this point, it is safe to assume it is not the start of a line
        isLineStart = false;

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
