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
import scanning.Scanner;
import scanning.Token;
import scanning.TokenType;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentifierScannerTest {

    private Token tokenOf(String s) {
        return new IdentifierScanner(new Scanner(s)).scan();
    }

    private String valueOf(String s) {
        return new IdentifierScanner(new Scanner(s)).scan().value;
    }

    @Test
    @DisplayName("should scan a single valid char")
    void t1() {
        assertEquals("_", valueOf("_"));
        assertEquals("a", valueOf("a"));
        assertEquals("i", valueOf("i"));
    }

    @Test
    @DisplayName("should scan a valid 2 lenght identifier")
    void t2() {
        assertEquals("_a", valueOf("_a"));
        assertEquals("_z", valueOf("_z"));
        assertEquals("_A", valueOf("_A"));
        assertEquals("_Z", valueOf("_Z"));
        assertEquals("__", valueOf("__"));
        assertEquals("_0", valueOf("_0"));
        assertEquals("_9", valueOf("_9"));
        assertEquals("aa", valueOf("aa"));
        assertEquals("az", valueOf("az"));
        assertEquals("aA", valueOf("aA"));
        assertEquals("aZ", valueOf("aZ"));
        assertEquals("a_", valueOf("a_"));
        assertEquals("a0", valueOf("a0"));
        assertEquals("a9", valueOf("a9"));
        assertEquals("za", valueOf("za"));
        assertEquals("zz", valueOf("zz"));
        assertEquals("zA", valueOf("zA"));
        assertEquals("zZ", valueOf("zZ"));
        assertEquals("z_", valueOf("z_"));
        assertEquals("z0", valueOf("z0"));
        assertEquals("z9", valueOf("z9"));
    }

    @Test
    @DisplayName("should scan longer identifiers")
    void t3() {
        assertEquals("_validIdentifier", valueOf("_validIdentifier"));
        assertEquals("iterationCount", valueOf("iterationCount"));
        assertEquals("buffer", valueOf("buffer"));
        assertEquals(
                "aVeryLongIdentifier2WithSome5Numbers67InBetween1",
                valueOf("aVeryLongIdentifier2WithSome5Numbers67InBetween1")
        );
    }

    @Test
    @DisplayName("should scan a keyword")
    void t4() {
        var returnToken = tokenOf("var");
        assertEquals(TokenType.VAR, returnToken.type);
    }

    @Test
    @DisplayName("should scan all keywords")
    void t5() {
        for (Map.Entry<String, TokenType> entry : IdentifierScanner.keywords.entrySet()) {
            var returnToken = tokenOf(entry.getKey());
            assertEquals(entry.getValue(), returnToken.type);
        }
    }
}
