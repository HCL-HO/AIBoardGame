package com.eh.clho

import com.eh.clho.boarditems.BoardItem
import com.eh.clho.boarditems.Hole

class GameSettings(val items: MutableList<BoardItem>) {
    init {
        // add to first.. must be drawn on board first
        // other items with same coord can override it
        items.apply {
            add(0, Hole(Coordinate(0, 0)))
            add(0, Hole(Coordinate(4, 4)))
            add(0, Hole(Coordinate(0, 4)))
            add(0, Hole(Coordinate(4, 0)))
        }
    }
}