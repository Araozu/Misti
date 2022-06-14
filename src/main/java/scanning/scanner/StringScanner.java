/*
 * Copyright (c) 2022
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

public class StringScanner extends AbstractScanner {
    static final String UNEXPECTED_NEW_LINE_MSG = "Unexpected new line inside a string.";

    public StringScanner(MainScanner mainScanner) {
        super(mainScanner);
    }

    /**
     * Returns an adequate escape character.
     * If next is not a escape character, it returns \0
     *
     * @param next The character after the backslash \
     * @return The escaped character, or \0
     */
    private char handleEscapeChar(char next) {
        switch (next) {
            case 'n': {
                return '\n';
            }
            case '"': {
                return '"';
            }
            case 'r': {
                return '\r';
            }
            case '\\': {
                return '\\';
            }
            case 'f': {
                return '\f';
            }
            case 'b': {
                return '\b';
            }
            case 't': {
                return '\t';
            }
            default: {
                return '\0';
            }
        }
    }

    /**
     * Scans a string.
     * Assumes the next char will be a double quote "
     *
     * @return A String token
     */
    @Override
    public Token scan() {
        // Consume opening quote
        next();

        while (true) {
            char c = peek1();

            if (c == '"') {
                // Consume closing quote
                next();
                return create(TokenType.String);
            } else if (c == '\n') {
                addError(new ScannerError(UNEXPECTED_NEW_LINE_MSG));
                // Consume new line
                next();
                return create(TokenType.String);
            } else if (c == '\\') {
                char result = handleEscapeChar(peek2());
                if (result != '\0') {
                    // Consume the backslash and escape char, and continue
                    next();
                    next();
                    // Add the escape character
                    append(result);
                    continue;
                } else {
                    // Consume and ignore the backslash
                    next();
                    continue;
                }
            }

            append(next());
        }
    }
}
