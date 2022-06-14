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

import scanning.MainScanner;
import scanning.Token;

public class Parser {

    private final MainScanner mainScanner;

    public Parser(MainScanner mainScanner) {
        this.mainScanner = mainScanner;
    }

    public Expr parse() {

        for (Token nextToken: mainScanner.tokens()) {
            switch (nextToken.type) {
                case Integer: {
                    return new Expr.Integer(nextToken);
                }
                case Floating: {
                    return new Expr.Floating(nextToken);
                }
                case String: {
                    return new Expr.String(nextToken);
                }
                case Identifier: {
                    return new Expr.Identifier(nextToken);
                }
                default: {
                    return null;
                }
            }
        }
        return null;
    }
}
