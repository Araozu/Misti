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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainScannerTest {

    @Test
    @DisplayName("tokens should not return null")
    public void testTokensNotNull() {
        var lexer = new MainScanner("");
        Assertions.assertNotNull(lexer.tokens());
    }

    @Test
    @DisplayName("tokens should return an EOF token if the input has no tokens")
    public void testTokensEmptyInput() {
        MainScanner mainScanner;
        ArrayList<Token> tokens;

        mainScanner = new MainScanner("");
        tokens = mainScanner.tokens();
        Assertions.assertEquals(1, tokens.size());
        Assertions.assertEquals(TokenType.EOF, tokens.get(0).type);

        mainScanner = new MainScanner(" ");
        tokens = mainScanner.tokens();
        Assertions.assertEquals(1, tokens.size());
        Assertions.assertEquals(TokenType.EOF, tokens.get(0).type);

        mainScanner = new MainScanner("  ");
        tokens = mainScanner.tokens();
        Assertions.assertEquals(1, tokens.size());
        Assertions.assertEquals(TokenType.EOF, tokens.get(0).type);

        mainScanner = new MainScanner("\n");
        tokens = mainScanner.tokens();
        Assertions.assertEquals(1, tokens.size());
        Assertions.assertEquals(TokenType.EOF, tokens.get(0).type);
    }

    @Test
    @DisplayName("should scan numbers")
    void testNextInteger() {
        MainScanner mainScanner;
        ArrayList<Token> tokens;

        mainScanner = new MainScanner("126 278.98 0.282398 1798e+1 239.3298e-103");
        tokens = mainScanner.tokens();
        assertEquals("126", tokens.get(0).value);
        assertEquals("278.98", tokens.get(1).value);
        assertEquals("0.282398", tokens.get(2).value);
        assertEquals("1798e+1", tokens.get(3).value);
        assertEquals("239.3298e-103", tokens.get(4).value);
        assertEquals(TokenType.EOF, tokens.get(5).type);
    }

    @Test
    @DisplayName("should scan identifiers")
    void t4() {
        MainScanner mainScanner;
        ArrayList<Token> tokens;

        mainScanner = new MainScanner("_ i j number number2 _ignored camelCase sneak_case_10");
        tokens = mainScanner.tokens();
        assertEquals("_", tokens.get(0).value);
        assertEquals("i", tokens.get(1).value);
        assertEquals("j", tokens.get(2).value);
        assertEquals("number", tokens.get(3).value);
        assertEquals("number2", tokens.get(4).value);
        assertEquals("_ignored", tokens.get(5).value);
        assertEquals("camelCase", tokens.get(6).value);
        assertEquals("sneak_case_10", tokens.get(7).value);
        assertEquals(TokenType.EOF, tokens.get(8).type);
    }

    @Test
    @DisplayName("should scan operators")
    void t5() {
        MainScanner mainScanner;
        ArrayList<Token> tokens;

        mainScanner = new MainScanner("+ - * / % += -= *= /= ** *** <- <= >= => -> <$>");
        tokens = mainScanner.tokens();
        assertEquals("+", tokens.get(0).value);
        assertEquals("-", tokens.get(1).value);
        assertEquals("*", tokens.get(2).value);
        assertEquals("/", tokens.get(3).value);
        assertEquals("%", tokens.get(4).value);
        assertEquals("+=", tokens.get(5).value);
        assertEquals("-=", tokens.get(6).value);
        assertEquals("*=", tokens.get(7).value);
        assertEquals("/=", tokens.get(8).value);
        assertEquals("**", tokens.get(9).value);
        assertEquals("***", tokens.get(10).value);
        assertEquals("<-", tokens.get(11).value);
        assertEquals("<=", tokens.get(12).value);
        assertEquals(">=", tokens.get(13).value);
        assertEquals("=>", tokens.get(14).value);
        assertEquals("->", tokens.get(15).value);
        assertEquals("<$>", tokens.get(16).value);
        assertEquals(TokenType.EOF, tokens.get(17).type);
    }

}
