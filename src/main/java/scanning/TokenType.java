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

package scanning;

public enum TokenType {
    Indentation,
    NewLine,
    Identifier,
    Comment,
    Integer,
    Floating,
    String,
    Unit,
    Operator,
    // Parentheses
    LeftParen,
    RightParen,
    // Brackets
    LeftBracket,
    RightBracket,
    // Braces
    LeftBrace,
    RightBrace,
    EOF,
    // Indicates an increase in indentation
    Indent,
    // Indicates a decrease in indentation
    Dedent,
    // Keywords
    VAR,
    VAL,
}
