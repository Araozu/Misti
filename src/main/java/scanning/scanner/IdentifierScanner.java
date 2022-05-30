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
import scanning.Utils;

import java.util.HashMap;

public class IdentifierScanner extends AbstractScanner {
    public IdentifierScanner(MainScanner mainScanner) {
        super(mainScanner);
    }

    public static final HashMap<String, TokenType> keywords = new HashMap<>();

    static {
        keywords.put("var", TokenType.VAR);
        keywords.put("val", TokenType.VAL);
    }

    /**
     * @param c Char to test
     * @return Whether c is a letter, number or underscore
     */
    private boolean isIdentifierChar(char c) {
        return Utils.isLowercase(c) || Utils.isUppercase(c) || (c == '_') || Utils.isDigit(c);
    }

    /**
     * Scans an identifier
     * Assumes the next char is a lowercase char or an underscore.
     *
     * @return An Identifier token
     */
    @Override
    public Token scan() {
        append(next());

        while (isIdentifierChar(peek1())) append(next());

        String currentValue = getCurrentValue();
        // Try to get a keyword
        TokenType type = keywords.get(currentValue);
        if (type == null) type = TokenType.Identifier;

        return create(type);
    }
}
