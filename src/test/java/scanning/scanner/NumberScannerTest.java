/*
 * Copyright (c) 2021-2022
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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import scanning.Scanner;
import scanning.Token;
import scanning.TokenType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberScannerTest {

    private static Token getTokenOf(String s) {
        return new NumberScanner(new Scanner(s)).scan();
    }

    @Test
    @DisplayName("should throw if input is null")
    public void shouldThrowIfInputIsNull() {
        try {
            new NumberScanner(new Scanner(null));
            Assertions.fail("scan did not throw with null input");
        } catch (RuntimeException e) {
            assertEquals("NumberScanner: Input is null", e.getMessage());
        }
    }

    @Test
    @DisplayName("should return null if there is no number")
    public void shouldReturnNullIfTheresNoNumber() {
        Token result = new NumberScanner(new Scanner("")).scan();
        Assertions.assertNull(result);
    }

    @Test
    @DisplayName("should return null if does not start with a number")
    public void shouldReturnNullIfDoesntStartWithNumber() {
        Token result = new NumberScanner(new Scanner("abc")).scan();
        Assertions.assertNull(result);
    }

    @Test
    @DisplayName("should scan a simple integer")
    public void shouldScanSimpleInteger() {
        Token result = new NumberScanner(new Scanner("123")).scan();
        Assertions.assertNotNull(result);
        assertEquals(TokenType.Integer, result.type);
        assertEquals("123", result.value);
        assertEquals(0, result.lineNumber);
        assertEquals(0, result.position);

        result = new NumberScanner(new Scanner("0123")).scan();
        Assertions.assertNotNull(result);
        assertEquals(TokenType.Integer, result.type);
        assertEquals("0123", result.value);
        assertEquals(0, result.lineNumber);
        assertEquals(0, result.position);

        result = new NumberScanner(new Scanner("0123 45")).scan();
        Assertions.assertNotNull(result);
        assertEquals(TokenType.Integer, result.type);
        assertEquals("0123", result.value);
        assertEquals(0, result.lineNumber);
        assertEquals(0, result.position);
    }

    @Test
    @DisplayName("should scan a hex value")
    public void shouldScanAHexValue(){
        Token result = new NumberScanner(new Scanner("0x20")).scan();
        Assertions.assertNotNull(result);
        assertEquals(TokenType.Integer, result.type);
        assertEquals("0x20", result.value);
        assertEquals(0, result.lineNumber);
        assertEquals(0, result.position);

        assertEquals("0xff", getTokenOf("0xff").value);
    }

    @Test
    @DisplayName("should not scan an incomplete hex value")
    public void shouldNotScanAnIncompleteHexValue(){
        Token result = new NumberScanner(new Scanner("0 x20")).scan();
        Assertions.assertNotNull(result);
        assertEquals(TokenType.Integer, result.type);
        assertEquals("0", result.value);
        assertEquals(0, result.lineNumber);
        assertEquals(0, result.position);

        result = new NumberScanner(new Scanner("0x 20")).scan();
        Assertions.assertNotNull(result);
        assertEquals(TokenType.Integer, result.type);
        assertEquals("0", result.value);
        assertEquals(0, result.lineNumber);
        assertEquals(0, result.position);

        result = new NumberScanner(new Scanner("0 x 20")).scan();
        Assertions.assertNotNull(result);
        assertEquals(TokenType.Integer, result.type);
        assertEquals("0", result.value);
        assertEquals(0, result.lineNumber);
        assertEquals(0, result.position);
    }

    @Test
    @DisplayName("should not scan a hex number if it doesn't start with 0")
    public void test() {
        Token result = getTokenOf("1xff");
        assertEquals("1", result.value);
    }

}
