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
import scanning.Token;
import scanning.TokenType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroupingSignScannerTest {

    private static Token tokenOf(String s) {
        return new GroupingSignScanner(new MainScanner(s)).scan();
    }

    @Test
    @DisplayName("should scan unit ()")
    void t1() {
        var token = tokenOf("()");
        assertEquals("()", token.value);
        assertEquals(TokenType.Unit, token.type);
    }
}
