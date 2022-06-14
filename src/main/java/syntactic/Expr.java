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

package syntactic;

import scanning.Token;

public abstract class Expr {
    private Expr() {
    }

    static class Integer extends Expr {
        final Token token;

        Integer(Token token) {
            this.token = token;
        }
    }

    static class Floating extends Expr {
        final Token token;

        Floating(Token token) {
            this.token = token;
        }
    }

    static class String extends Expr {
        final Token token;

        String(Token token) {
            this.token = token;
        }
    }

    static class Identifier extends Expr {
        final Token token;

        Identifier(Token token) {
            this.token = token;
        }
    }

    static class Unit extends Expr {
        final Token token;

        Unit(Token token) {
            this.token = token;
        }
    }
}
