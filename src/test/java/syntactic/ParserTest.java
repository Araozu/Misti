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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import scanning.MainScanner;
import scanning.TokenType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {
    public static Expr getExpression(String input) {
        MainScanner mainScanner = new MainScanner(input);
        Parser parser = new Parser(mainScanner);
        return parser.parse();
    }

    @Test
    @DisplayName("should parse an integer")
    void t1() {
        Expr result = getExpression("200");

        if (!(result instanceof Expr.IntegerExpr)) {
            fail("Result not instance of IntegerExpr");
        }

        Expr.IntegerExpr numberExpr = (Expr.IntegerExpr) result;
        assertEquals("200", numberExpr.token.value);
        assertEquals(TokenType.Integer, numberExpr.token.type);
    }

    @Test
    @DisplayName("should parse a floating point number")
    void t2() {
        Expr result = getExpression("199.34");

        if (!(result instanceof Expr.FloatingExpr)) {
            fail("Result not instance of FloatingExpr");
        }

        Expr.FloatingExpr numberExpr = (Expr.FloatingExpr) result;
        assertEquals("199.34", numberExpr.token.value);
        assertEquals(TokenType.Floating, numberExpr.token.type);
    }

    @Test
    @DisplayName("should parse a string")
    void t3() {
        Expr result = getExpression("\"Hello, world!\"");

        if (!(result instanceof Expr.StringExpr)) {
            fail("Result not instance of StringExpr");
        }

        Expr.StringExpr numberExpr = (Expr.StringExpr) result;
        assertEquals("Hello, world!", numberExpr.token.value);
        assertEquals(TokenType.String, numberExpr.token.type);
    }
}
