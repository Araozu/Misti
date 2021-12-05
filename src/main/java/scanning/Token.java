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

public class Token {

    // Indicates the type of token
    final TokenType type;
    // Contains the token as a raw string
    final String value;
    // The line number where this token appears
    final int lineNumber;
    // The absolute position of the start of this token
    final int position;

    /**
     * @param type       Indicates the type of token
     * @param value      Contains the token as a raw string
     * @param lineNumber The line number where this token appears
     * @param position   The absolute position of the start of this token
     */
    public Token(TokenType type, String value, int lineNumber, int position) {
        this.type = type;
        this.value = value;
        this.lineNumber = lineNumber;
        this.position = position;
    }

    public int length() {
        return value.length();
    }

}
