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

import java.util.Stack;

public class IndentationState {
    private final Stack<Integer> levels;

    public IndentationState() {
        levels = new Stack<>();
        levels.push(0);
    }

    /**
     * Returns the current indentation level
     * @return
     */
    public int get() {
        return levels.peek();
    }

    /**
     * Increases the current indentation level
     * @param level new indentationLevel
     * @throws IllegalArgumentException if the new value is lower or equal to the current
     */
    public void increaseTo(int level) {
        int current = get();
        if (level <= current) {
            throw new IllegalArgumentException("Attempted to add a lower indentation level");
        }
        levels.push(level);
    }

    /**
     *
     * @param amount number of whitespace to decrease
     * @return The number of levels decreased, or -1
     */
    public int decreaseTo(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Attempted to decrease to a negative number");
        }
        if (amount >= get()) {
            throw new IllegalArgumentException("Attempted to decrease to a value greater than or equal to the current level");
        }

        // As there's always a level 0, just return the size -1 and clear the stack
        if (amount == 0) {
            int size = levels.size() - 1;
            levels.clear();
            levels.push(0);
            return size;
        }

        int counter = 0;
        levels.pop();
        while (levels.size() > 1) {
            int nextLower = get();

            counter++;
            if (nextLower == amount) {
                return counter;
            }
            else if (nextLower < amount) {
                break;
            }
            levels.pop();
        }

        return -1;
    }

}
