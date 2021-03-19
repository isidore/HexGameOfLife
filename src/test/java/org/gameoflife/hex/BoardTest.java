package org.gameoflife.hex;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void oddsGoTogether() {
        assertTrue(Board.isValidCoordinate(1, 1));
        assertTrue(Board.isValidCoordinate(2, 2));
        assertFalse(Board.isValidCoordinate(2, 1));
        assertFalse(Board.isValidCoordinate(1, 2));
    }
}