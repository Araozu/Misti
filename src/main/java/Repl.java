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

import scanning.MainScanner;
import scanning.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Repl {

    private final BufferedReader stdin;

    public Repl(BufferedReader stdin) {
        this.stdin = stdin;
    }

    public void run() {
        System.out.println("REPL: Enter expressions to evaluate them. Type Ctrl-D to exit.");
        try {
            while (true) {
                System.out.print("> ");
                String input = stdin.readLine();
                if (input == null) {
                    break;
                }
                MainScanner sc = new MainScanner(input);
                ArrayList<Token> tokens = sc.tokens();
                for (Token t: tokens) {
                    System.out.println("[" + t.value + "]");
                }
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println();
        }
    }

}
