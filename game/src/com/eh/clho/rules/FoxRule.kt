package com.eh.clho.rules

import com.eh.clho.Board
import com.eh.clho.Coordinate
import com.eh.clho.boarditems.BoardItem
import com.eh.clho.boarditems.Fox

//Not allow moving back
fun Fox.getMovableSpots(board: Board): Array<Coordinate> {
    val list = mutableListOf<Coordinate>()
    val adjs = board.getAdjacentsItems(cordinate)
//    val tailAdjs = board.getAdjacentsItems(tail)
    getFoxMovableSpots(this, adjs, board, list)
//    getFoxMovableSpots(this, tailAdjs, board, list)
    return TestRules.arrangeMovableOrder(list, lastCoordinate).toTypedArray()
}

fun getFoxMovableSpots(fox: Fox, adjs: Array<BoardItem>, board: Board, list: MutableList<Coordinate>) {
    adjs.forEach { firstAdj ->
        //skip tail
        val direction = fox.cordinate.compareDirection(firstAdj.cordinate)
        //match direction
        if (fox.direction == 1 && (direction == Coordinate.Direction.DOWN || direction == Coordinate.Direction.UP)) {
            getFoxMovableSpotsWithAlignedSpots(board, fox, firstAdj, Coordinate.Direction.RIGHT, list)
        }
        //match direction
        if (fox.direction == 0 && (direction == Coordinate.Direction.LEFT || direction == Coordinate.Direction.RIGHT)) {
            getFoxMovableSpotsWithAlignedSpots(board, fox, firstAdj, Coordinate.Direction.DOWN, list)
        }

        if (direction == Coordinate.Direction.SAME) {
            getFoxMovableSpotsWithAlignedSpots(board, fox, firstAdj, Coordinate.Direction.SAME, list)
        }
    }

}

fun getFoxMovableSpotsWithAlignedSpots(board: Board, fox: Fox, firstAdj: BoardItem, d: Coordinate.Direction, list: MutableList<Coordinate>) {
    if (board.isCoordinateEmpty(firstAdj.cordinate)) {
        list.add(firstAdj.cordinate)
    } else if (d == Coordinate.Direction.SAME) {
        val direction = if (fox.direction == 0) Coordinate.Direction.RIGHT else Coordinate.Direction.DOWN
        board.getAdjacentsItems(fox.tail, direction).firstOrNull()?.also { boardItem ->
            if (board.isCoordinateEmpty(boardItem.cordinate)) {
                list.add(fox.cordinate.copy().apply { translate(direction, 1) })
            }
        }
    }
}


