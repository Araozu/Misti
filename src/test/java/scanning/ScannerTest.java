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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ScannerTest {

    @Test
    @DisplayName("tokens should not return null")
    public void testTokensNotNull() {
        var lexer = new Scanner("");
        Assertions.assertNotNull(lexer.tokens());
    }

    @Test
    @DisplayName("tokens should return an EOF token if the input has no tokens")
    public void testTokensEmptyInput() {
        Scanner scanner;
        ArrayList<Token> tokens;

        scanner = new Scanner("");
        tokens = scanner.tokens();
        Assertions.assertEquals(1, tokens.size());
        Assertions.assertEquals(TokenType.EOF, tokens.get(0).type);

        scanner = new Scanner(" ");
        tokens = scanner.tokens();
        Assertions.assertEquals(1, tokens.size());
        Assertions.assertEquals(TokenType.EOF, tokens.get(0).type);

        scanner = new Scanner("  ");
        tokens = scanner.tokens();
        Assertions.assertEquals(1, tokens.size());
        Assertions.assertEquals(TokenType.EOF, tokens.get(0).type);

        scanner = new Scanner("\n");
        tokens = scanner.tokens();
        Assertions.assertEquals(1, tokens.size());
        Assertions.assertEquals(TokenType.EOF, tokens.get(0).type);
    }

    @Test
    @DisplayName("next should recognize an integer")
    public void testNextInteger() {

    }

}
