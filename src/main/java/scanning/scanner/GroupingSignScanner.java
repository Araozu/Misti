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

import scanning.MainScanner;
import scanning.Token;
import scanning.TokenType;

public class GroupingSignScanner extends AbstractScanner {
    public GroupingSignScanner(MainScanner mainScanner) {
        super(mainScanner);
    }

    /**
     * Assumes the next character is an opening grouping sign
     *
     * @return
     */
    @Override
    public Token scan() {
        switch (peek1()) {
            case '(': {
                // Consume open parenthesis
                next();
                // Consume possible whitespace
                while (peek1() == ' ') next();

                if (peek1() == ')') {
                    // consume closing parenthesis, create unit token
                    append('(');
                    append(next());
                    return create(TokenType.Unit);
                } else {
                    append('(');
                    return create(TokenType.ParenOpen);
                }
            }
            case ')': {
                append(next());
                return create(TokenType.ParenClosed);
            }
            case '[': {
                append(next());
                return create(TokenType.BracketOpen);
            }
            case ']': {
                append(next());
                return create(TokenType.BracketClosed);
            }
            case '{': {
                append(next());
                return create(TokenType.BraceOpen);
            }
            case '}': {
                append(next());
                return create(TokenType.BraceClosed);
            }
        }
        // Should never happen
        throw new RuntimeException("Illegal Scanner state: tried to scan a grouping sign " +
                "but none was found");
    }
}
