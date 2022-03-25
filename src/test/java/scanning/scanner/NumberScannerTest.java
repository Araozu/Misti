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
import scanning.Token;
import scanning.TokenType;
import scanning.scanner.NumberScanner;

public class NumberScannerTest {

    @Test
    @DisplayName("should throw if input is null")
    public void shouldThrowIfInputIsNull() {
        try {
            new NumberScanner(null, 0, 0);
            Assertions.fail("scan did not throw with null input");
        } catch (RuntimeException e) {
            Assertions.assertEquals("NumberScanner: Input is null", e.getMessage());
        }
    }

    @Test
    @DisplayName("should throw if startPosition is less than 0")
    public void shouldThrowIfStartIsLessThan0() {
        try {
            new NumberScanner("", -1, 0);
            Assertions.fail("scan did not throw with negative start position");
        } catch (RuntimeException e) {
            Assertions.assertEquals("NumberScanner: Start position is negative", e.getMessage());
        }
    }

    @Test
    @DisplayName("should throw if lineNumber is less than 0")
    public void shouldThrowIfLineNumberIsLessThan0() {
        try {
            new NumberScanner("", 0, -1);
            Assertions.fail("scan did not with negative line number");
        } catch (RuntimeException e) {
            Assertions.assertEquals("NumberScanner: Line number is negative", e.getMessage());
        }
    }

    @Test
    @DisplayName("should return null if there is no number")
    public void shouldReturnNullIfTheresNoNumber() {
        Token result = new NumberScanner("", 0, 0).scan();
        Assertions.assertNull(result);
    }

    @Test
    @DisplayName("should return null if does not start with a number")
    public void shouldReturnNullIfDoesntStartWithNumber() {
        Token result = new NumberScanner("abc", 0, 0).scan();
        Assertions.assertNull(result);
    }

    @Test
    @DisplayName("should scan a simple integer")
    public void shouldScanSimpleInteger() {
        Token result = new NumberScanner("123", 0, 0).scan();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(TokenType.Integer, result.type);
        Assertions.assertEquals("123", result.value);
        Assertions.assertEquals(0, result.lineNumber);
        Assertions.assertEquals(0, result.position);

        result = new NumberScanner("  0123", 2, 1).scan();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(TokenType.Integer, result.type);
        Assertions.assertEquals("0123", result.value);
        Assertions.assertEquals(1, result.lineNumber);
        Assertions.assertEquals(2, result.position);

        result = new NumberScanner("  0123 45", 2, 1).scan();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(TokenType.Integer, result.type);
        Assertions.assertEquals("0123", result.value);
        Assertions.assertEquals(1, result.lineNumber);
        Assertions.assertEquals(2, result.position);
    }

    @Test
    @DisplayName("should scan a hex value")
    public void shouldScanAHexValue(){
        Token result = new NumberScanner("0x20", 0, 0).scan();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(TokenType.Integer, result.type);
        Assertions.assertEquals("0x20", result.value);
        Assertions.assertEquals(0, result.lineNumber);
        Assertions.assertEquals(0, result.position);

        result = new NumberScanner("hello 0X20", 6, 0).scan();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(TokenType.Integer, result.type);
        Assertions.assertEquals("0X20", result.value);
        Assertions.assertEquals(0, result.lineNumber);
        Assertions.assertEquals(6, result.position);
    }

    @Test
    @DisplayName("should not scan an incomplete hex value")
    public void shouldNotScanAnIncompleteHexValue(){
        Token result = new NumberScanner("0 x20", 0, 0).scan();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(TokenType.Integer, result.type);
        Assertions.assertEquals("0", result.value);
        Assertions.assertEquals(0, result.lineNumber);
        Assertions.assertEquals(0, result.position);

        result = new NumberScanner("0x 20", 0, 0).scan();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(TokenType.Integer, result.type);
        Assertions.assertEquals("0", result.value);
        Assertions.assertEquals(0, result.lineNumber);
        Assertions.assertEquals(0, result.position);

        result = new NumberScanner("0 x 20", 0, 0).scan();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(TokenType.Integer, result.type);
        Assertions.assertEquals("0", result.value);
        Assertions.assertEquals(0, result.lineNumber);
        Assertions.assertEquals(0, result.position);
    }


}
