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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import scanning.MainScanner;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StringScannerTest {

    private String valueOf(String s) {
        return new StringScanner(new MainScanner(s)).scan().value;
    }

    @Test
    @DisplayName("should scan an empty string")
    void t1() {
        assertEquals("", valueOf("\"\""));
    }

    @Test
    @DisplayName("should scan a string with contents")
    void t2() {
        assertEquals("Hello, world!", valueOf("\"Hello, world!\""));
    }

    @Test
    @DisplayName("should not scan a newline. should return the string up until the newline, and set an error")
    void t3() {
        var mainScanner = new MainScanner("\"Hello,\nworld!");
        var stringScanner = new StringScanner(mainScanner);
        var result = stringScanner.scan();

        assertEquals("Hello,", result.value);
        var error = mainScanner.getErrorList().get(0);
        assertEquals("Unexpected new line inside a string.", error.reason);
        // next position should be 8, the index of 'w'
        var nextPosition = stringScanner.getPosition();
        assertEquals(8, nextPosition);
    }
}
