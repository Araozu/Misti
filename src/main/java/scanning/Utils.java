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

package scanning;

public class Utils {
    public static boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }

    public static boolean isLowercase(char c) {
        return (c >= 'a' && c <= 'z');
    }

    public static boolean isUppercase(char c) {
        return (c >= 'A' && c <= 'Z');
    }

    public static boolean isHexDigit(char c) {
        return isDigit(c) || 'a' <= c && c <= 'f' || 'A' <= c && c <= 'F';
    }

    public static boolean isOperatorChar(char c) {
        return (c == '+' || c == '-' || c == '=' || c == '*' || c == '!' || c == '\\' || c == '/' || c == '|'
                || c == '@' || c == '#' || c == '$' || c == '~' || c == '%' || c == '&' || c == '?' || c == '<'
                || c == '>' || c == '^' || c == '.' || c == ':');
    }
}
