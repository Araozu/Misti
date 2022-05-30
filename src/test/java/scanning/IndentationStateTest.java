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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IndentationStateTest {

    @Test
    @DisplayName("should return 0 as first indentation level")
    void t1() {
        var state = new IndentationState();
        assertEquals(0, state.get());
    }

    @Test
    @DisplayName("should increase the indentation value")
    void t2() {
        var state = new IndentationState();
        state.increaseTo(4);
        assertEquals(4, state.get());
    }

    @Test
    @DisplayName("should not allow to increase to a level inferior to the current one")
    void t3() {
        var state = new IndentationState();
        try {
            state.increaseTo(4);
            state.increaseTo(2);
            Assertions.fail("Did not throw");
        } catch (IllegalArgumentException e) {
            assertEquals("Attempted to add a lower indentation level", e.getMessage());
        }
    }

    @Test
    @DisplayName("should decrease the level and return the number of decreased levels")
    void t4() {
        var state = new IndentationState();
        state.increaseTo(4);
        state.increaseTo(8);
        var levelsDecreased = state.decreaseTo(4);
        assertEquals(1, levelsDecreased);
    }

    @Test
    @DisplayName("should decrease multiple levels")
    void t5() {
        var state = new IndentationState();
        state.increaseTo(4);
        state.increaseTo(8);
        state.increaseTo(12);
        var levelsDecreased = state.decreaseTo(4);
        assertEquals(2, levelsDecreased);

        state = new IndentationState();
        state.increaseTo(4);
        state.increaseTo(8);
        state.increaseTo(12);
        levelsDecreased = state.decreaseTo(0);
        assertEquals(3, levelsDecreased);
    }

    @Test
    @DisplayName("should throw when trying to decrease to a negative number")
    void t6() {
        var state = new IndentationState();
        try {
            state.increaseTo(8);
            state.decreaseTo(-10);
            Assertions.fail("Did not throw");
        } catch (IllegalArgumentException e) {
            assertEquals("Attempted to decrease to a negative number", e.getMessage());
        }
    }

    @Test
    @DisplayName("should throw when trying to decrease to a value greater than or equal to the current level")
    void t7() {
        var state = new IndentationState();
        try {
            state.increaseTo(4);
            state.decreaseTo(5);
            Assertions.fail("Did not throw");
        } catch (IllegalArgumentException e) {
            assertEquals("Attempted to decrease to a value greater than or equal to the current level", e.getMessage());
        }
    }

    @Test
    @DisplayName("should return -1 if the decreased amount doesn't match with some level")
    void t8() {
        var state = new IndentationState();
        state.increaseTo(4);
        state.increaseTo(8);
        var levelsDecreased = state.decreaseTo(6);
        assertEquals(-1, levelsDecreased);
    }

    @Test
    @DisplayName("after decreasing to a level, it should remain")
    void t9() {
        var state = new IndentationState();
        state.increaseTo(4);
        state.increaseTo(8);
        var levelsDecreased = state.decreaseTo(4);
        assertEquals(4, state.get());
    }

    // TODO: What happens if indentation is decreased to an illegal state?
}
