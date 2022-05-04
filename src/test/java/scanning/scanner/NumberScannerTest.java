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

    private static String valueOf(String s) {
        return getTokenOf(s).value;
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

    @Test
    @DisplayName("should not consume an unneeded character in an invalid hex")
    void t1() {
        var scanner = new NumberScanner(new Scanner("0x 1"));
        Token result = scanner.scan();
        Assertions.assertNotNull(result);
        assertEquals("0", result.value);
        assertEquals(1, scanner.position);
    }

    @Test
    @DisplayName("should not consume an unneeded char after scanning 0")
    void t3() {
        var sc = new NumberScanner(new Scanner("0 a"));
        var result = sc.scan();
        assertEquals("0", result.value);
        assertEquals(1, sc.position);
    }

    @Test
    @DisplayName("should scan a double using a dot")
    public void t2() {
        Token result = getTokenOf("0.1");
        assertEquals("0.1", result.value);
    }

    @Test
    @DisplayName("should not scan an incomplete fp")
    void t22() {
        assertEquals("15", valueOf("15.toUpperCase"));
    }

    @Test
    @DisplayName("should scan a fp with many digits")
    void t4() {
        assertEquals("10234.4897234", getTokenOf("10234.4897234").value);
    }

    @Test
    @DisplayName("should scan a fp without decimal part, with exponent")
    void t5() {
        assertEquals("1e+0", getTokenOf("1e+0").value);
        assertEquals("1e-0", getTokenOf("1e-0").value);
        assertEquals("1e+10", getTokenOf("1e+10").value);
        assertEquals("0e+0", valueOf("0e+0"));
        assertEquals("123498790e+12349870", valueOf("123498790e+12349870"));
        assertEquals("1e-123498702397", valueOf("1e-123498702397"));
    }

    @Test
    @DisplayName("should scan a single digit")
    void t6() {
        assertEquals("1", valueOf("1"));
    }

    @Test
    @DisplayName("should scan a fp with decimal part")
    void t7() {
        assertEquals("1.24e+1", valueOf("1.24e+1"));
        assertEquals("1.24234609e+1123498", valueOf("1.24234609e+1123498"));
        assertEquals("0.00000000000001e+1", valueOf("0.00000000000001e+1"));
    }

    @Test
    @DisplayName("should not scan an incomplete fp with exponent")
    void t8() {
        assertEquals("100", valueOf("100e"));
        assertEquals("100", valueOf("100e+"));
        assertEquals("100", valueOf("100e-"));
        assertEquals("100", valueOf("100ex"));
    }
}
