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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperatorScannerTest {

    private String valueOf(String s) {
        return new OperatorScanner(new Scanner(s)).scan().value;
    }

    @Test
    @DisplayName("should scan an operator of lenght 1")
    void t1() {
        ArrayList<String> arr = new ArrayList<>();
        arr.add("+");
        arr.add("-");
        arr.add("=");
        arr.add("*");
        arr.add("!");
        arr.add("\\");
        arr.add("/");
        arr.add("|");
        arr.add("@");
        arr.add("#");
        arr.add("$");
        arr.add("~");
        arr.add("%");
        arr.add("&");
        arr.add("?");
        arr.add("<");
        arr.add(">");
        arr.add("^");
        arr.add(".");
        arr.add(":");

        for (String op: arr) {
            assertEquals(op, valueOf(op));
        }
    }

    @Test
    @DisplayName("should scan length 2 operators")
    void t2() {
        assertEquals("<<", valueOf("<<"));
        assertEquals(">>", valueOf(">>"));
        assertEquals("<|", valueOf("<|"));
        assertEquals("|>", valueOf("|>"));
        assertEquals("+>", valueOf("+>"));
        assertEquals("<+", valueOf("<+"));
        assertEquals("+=", valueOf("+="));
        assertEquals("-=", valueOf("-="));
        assertEquals("?.", valueOf("?."));
        assertEquals("??", valueOf("??"));
        assertEquals("?:", valueOf("?:"));
        assertEquals("*=", valueOf("*="));
        assertEquals("/=", valueOf("/="));
        assertEquals("==", valueOf("=="));
        assertEquals("!=", valueOf("!="));
    }

    @Test
    @DisplayName("should scan length 3 operators")
    void t3() {
        assertEquals("<$>", valueOf("<$>"));
        assertEquals("<->", valueOf("<->"));
        assertEquals("...", valueOf("..."));
        assertEquals(".<.", valueOf(".<."));
    }
}
