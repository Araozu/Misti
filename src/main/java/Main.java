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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {

    private final static InputStreamReader input = new InputStreamReader(System.in);
    private final static BufferedReader stdin = new BufferedReader(input);
    private final static String version = "0.0.1";

    private static void printCopyright() {
        System.out.println("Misti " + version + "\nCopyright (c) 2021 Fernando Enrique Araoz Morales.");
    }

    public static void main(String[] args) {
        printCopyright();
        new Repl(stdin).run();
    }
}
