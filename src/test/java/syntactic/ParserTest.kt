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

package syntactic

import scanning.MainScanner
import scanning.TokenType
import syntactic.Expr.Floating
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.fail

object ParserTest {
    private fun getExpression(input: String): Expr? {
        val mainScanner = MainScanner(input)
        val parser = Parser(mainScanner)
        return parser.nextExpr()
    }

    private fun typeError(type: String): String {
        return "Result not instance of Expr.$type"
    }

    @Test
    fun `should parse an integer`() {
        val result = getExpression("200")
        if (result !is Expr.Integer) {
            fail(typeError("Integer"))
        }
        assertEquals("200", result.token.value)
        assertEquals(TokenType.Integer, result.token.type)
    }

    @Test
    fun `should parse a floating point number`() {
        val result = getExpression("199.34")
        if (result !is Floating) {
            fail(typeError("Floating"))
        }
        assertEquals("199.34", result.token.value)
        assertEquals(TokenType.Floating, result.token.type)
    }

    @Test
    fun `should parse a string`() {
        val result = getExpression("\"Hello, world!\"")
        if (result !is Expr.String) {
            fail(typeError("String"))
        }
        assertEquals("Hello, world!", result.token.value)
        assertEquals(TokenType.String, result.token.type)
    }

    @Test
    fun `should parse an identifier`() {
        val result = getExpression("name")
        if (result !is Expr.Identifier) {
            fail(typeError("Identifier"))
        }
        assertEquals("name", result.token.value)
        assertEquals(TokenType.Identifier, result.token.type)
    }

    @Test
    fun `should parse unit`() {
        val result = getExpression("()")
        if (result !is Expr.Unit) {
            fail(typeError("Unit"))
        }
        assertEquals("()", result.token.value)
        assertEquals(TokenType.Unit, result.token.type)
    }

    @Test
    fun `should parse a simple expression inside parenthesis`() {
        val result = getExpression("(10)")
        if (result !is Expr.Integer) {
            fail(typeError("Integer"))
        }

        assertEquals("10", result.token.value)
    }

    @Test
    fun `should not parse a expression if there is no closing paren`() {
        val result = getExpression("(10")
        assertNull(result, "Result is not null")
        // TODO: should report an error
    }
}
