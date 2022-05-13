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

import scanning.MainScanner;
import scanning.Token;
import scanning.TokenType;

import static scanning.Utils.isOperatorChar;

public class OperatorScanner extends AbstractScanner {

    public OperatorScanner(MainScanner mainScanner) {
        super(mainScanner);
    }

    @Override
    public Token scan() {
        while (isOperatorChar(peek1())) append(next());

        return create(TokenType.Operator);
    }
}
