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

    protected StringScanner(MainScanner mainScanner) {
        super(mainScanner);
    }

    private boolean peekIsNotStringEnd() {
        char p = peek1();
        return p != '"' && p != '\n';
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

        // TODO: escape characters

        while (peekIsNotStringEnd()) {
            append(next());
        }

        // When finding a new line inside
        if (peek1() == '\n') {
            addError(new ScannerError(UNEXPECTED_NEW_LINE_MSG));
            // Consume new line
            next();
            return create(TokenType.String);
        }

        // Consume closing quote
        next();

        return create(TokenType.String);
    }
}
