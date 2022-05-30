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

import error.AbstractError;
import error.ErrorList;
import scanning.scanner.AbstractScanner;
import scanning.scanner.IdentifierScanner;
import scanning.scanner.NumberScanner;
import scanning.scanner.OperatorScanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private final IndentationState indentationLevel = new IndentationState();
    private ErrorList errorList;

    public MainScanner(String input, ErrorList errorList) {
        this.input = input;
        if (input == null) {
            throw new RuntimeException("NumberScanner: Input is null");
        }

        this.inputSize = input.length();
        this.errorList = errorList;
    }

    public MainScanner(String input) {
        this(input, new ErrorList());
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

    public List<AbstractError> getErrorList() {
        return errorList.getErrors();
    }

    public void addError(AbstractError error) {
        errorList.addError(error);
    }

    private boolean hasNext() {
        return position < inputSize;
    }

    public ArrayList<Token> tokens() {
        ArrayList<Token> tokens = new ArrayList<>();

        // While there's still input
        while (hasNext()) {
            Token[] nextTokens = nextToken();
            // If an unknown character is found
            if (nextTokens == null) continue;
            Collections.addAll(tokens, nextTokens);
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
    protected Token[] nextToken() {
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
        else if (isLineStart && (nextChar == ' ' || indentationLevel.get() > 0)) {

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
            if (newIndentationLevel > indentationLevel.get()) {
                indentationLevel.increaseTo(newIndentationLevel);
                isLineStart = false;

                Token[] tokenArr = new Token[1];
                tokenArr[0] = new Token(TokenType.Indent, "", lineNumber, position);
                return tokenArr;
            }
            // If it is lower, emit a DEDENT token
            else if (newIndentationLevel < indentationLevel.get()) {
                // TODO: Emit an error if the indentation is incorrect
                int levelsDecreased = indentationLevel.decreaseTo(newIndentationLevel);

                Token[] tokenArr = new Token[levelsDecreased];
                for (int i = 0; i < levelsDecreased; i++) {
                    tokenArr[i] = new Token(TokenType.Dedent, "", lineNumber, position);
                }

                isLineStart = false;
                return tokenArr;
            }
            // If it is the same, do nothing
            else {
                return nextToken();
            }
        }
        else if (nextChar == '\n') {
            while (peek() == '\n') {
                position++;
                lineNumber++;
            }
            isLineStart = true;
            return nextToken();
        }

        // At this point, it is safe to assume it is not the start of a line
        isLineStart = false;

        // Check for number - integer of float
        if (Utils.isDigit(nextChar)) {
            AbstractScanner sc = new NumberScanner(this);
            Token t = sc.scan();
            this.position = sc.getPosition();

            Token[] tokenArr = new Token[1];
            tokenArr[0] = t;
            return tokenArr;
        }
        // Check for identifier/keyword
        else if (Utils.isLowercase(nextChar) || nextChar == '_') {
            AbstractScanner sc = new IdentifierScanner(this);
            Token t = sc.scan();
            this.position = sc.getPosition();

            Token[] tokenArr = new Token[1];
            tokenArr[0] = t;
            return tokenArr;
        }
        // Check for operators
        else if (Utils.isOperatorChar(nextChar)) {
            OperatorScanner op = new OperatorScanner(this);
            Token t = op.scan();
            this.position = op.getPosition();

            Token[] tokenArr = new Token[1];
            tokenArr[0] = t;
            return tokenArr;
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
