package com.eh.clho.rules

import com.eh.clho.Board
import com.eh.clho.Coordinate
import com.eh.clho.boarditems.BoardItem
import com.eh.clho.boarditems.Empty
import com.eh.clho.boarditems.Rabbit

fun Rabbit.getMovableSpots(board: Board): Array<Coordinate> {
    val list = mutableListOf<Coordinate>()
    val adjsCoords = this.cordinate.getAdjCoordinates()
    adjsCoords.forEach { firstAdj ->
        // first adj is not movable
        if (this.adjcentItemIsNotEmptyToJump(board.get(firstAdj))) {
            addRBMovableRecur(list, this, firstAdj, board)
        }
    }
    return TestRules.arrangeMovableOrder(list, lastCoordinate).toTypedArray()
}

// Skip 1 space and check Coordinates 1 space apart
private fun addRBMovableRecur(list: MutableList<Coordinate>, rabbit: Rabbit, adjCoord: Coordinate, board: Board) {
    val direction = rabbit.cordinate.compareDirection(adjCoord)
    val newCoord = adjCoord.copy().apply { translate(direction, 1) }
    if (newCoord.validCoordinate()) {
        addRBMovableRecurlv2(list, rabbit, newCoord, board)
    }
}

private fun addRBMovableRecurlv2(list: MutableList<Coordinate>, rabbit: Rabbit, newCoord: Coordinate, board: Board) {
    board.get(newCoord)?.also {
        // not not moving back ***
        if (board.isCoordinateEmpty(it.cordinate)) {
            list.add(it.cordinate)
        } else {
            addRBMovableRecur(list, rabbit, newCoord, board)
        }
    }
}

fun Rabbit.adjcentItemIsNotEmptyToJump(boardItem: BoardItem?): Boolean {
    return boardItem !is Empty
}


