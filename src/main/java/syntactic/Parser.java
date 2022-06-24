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

import error.ErrorList;
import error.SyntaxError;
import scanning.MainScanner;
import scanning.Token;
import scanning.TokenType;

import java.util.ArrayList;

public class Parser {

    private int position = 0;
    private final ArrayList<Token> tokens;
    private final int tokenAmount;
    private final ErrorList errorList;

    public Parser(MainScanner mainScanner, ErrorList errorList) {
        tokens = mainScanner.tokens();
        tokenAmount = tokens.size();
        this.errorList = errorList;
    }

    public Parser(MainScanner mainScanner) {
        this(mainScanner, new ErrorList());
    }

    /**
     * @return Token at current position, or null if there are no tokens left
     */
    private Token next() {
        if (position >= tokenAmount) {
            return null;
        } else {
            Token t = tokens.get(position);
            position += 1;
            return t;
        }
    }

    private Token peek() {
        return tokens.get(position);
    }

    private boolean check(TokenType type) {
        if (position >= tokenAmount) return false;
        return peek().type == type;
    }

    /**
     * Checks if the current token is any of types. If it is,
     * the token is consumed and true is returned.
     * Otherwise, false is returned
     *
     * @param types Array of types to check
     * @return Whether any of the types was found
     */
    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                next();
                return true;
            }
        }

        return false;
    }

    private boolean expect(TokenType type, Token t) {
        if (t == null) return false;
        return t.type == type;
    }

    /**
     * @return The next expression, or null if there are no expressions left
     */
    public Expr expression() {
        Token nextToken = peek();
        if (nextToken == null) {
            return null;
        }

        return primary();
    }

    private Expr primary() {
        if (check(TokenType.Integer)) return new Expr.Integer(next());
        if (check(TokenType.Floating)) return new Expr.Floating(next());
        if (check(TokenType.String)) return new Expr.String(next());
        if (check(TokenType.Identifier)) return new Expr.Identifier(next());
        if (check(TokenType.Unit)) return new Expr.Unit(next());

        if (check(TokenType.LeftParen)) {
            // Consume left paren
            next();
            Expr next = expression();
            if (check(TokenType.RightParen)) {
                return next;
            } else {
                errorList.addError(new SyntaxError("Missing closing paren"));
                return null;
            }
        }

        // TODO: Error handling?
        return null;
    }
}
