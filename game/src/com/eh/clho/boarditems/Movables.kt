package com.eh.clho.boarditems

import com.eh.clho.Board
import com.eh.clho.Coordinate


abstract class MovableItem : BoardItem() {
    abstract var lastCoordinate: Coordinate
    protected abstract fun _move(newCoordinate: Coordinate, board: Board): Boolean
    abstract fun isValidMove(newCoordinate: Coordinate, board: Board): Boolean
    fun move(newCoordinate: Coordinate, board: Board): Boolean {
        return if (!newCoordinate.sameCoordinate(cordinate)) {
            val coordinateCopy = cordinate.copy()
            val r = _move(newCoordinate, board)
            if (r) {
                lastCoordinate = coordinateCopy
            }
            r
        } else {
            false
        }
    }

}


class Rabbit(override val name: String, override var cordinate: Coordinate) : MovableItem() {

    override fun copy(): BoardItem {
        val r = Rabbit(name, cordinate)
        r.lastCoordinate = lastCoordinate
        return r
    }

    override var lastCoordinate: Coordinate = cordinate

    override fun _move(newCoordinate: Coordinate, board: Board): Boolean {
        return if (isValidMove(newCoordinate, board)) {
            println("rabbit move from ${cordinate} to ${newCoordinate}")
            board.setEmpty(cordinate)
            this.cordinate = newCoordinate
            board.placeItem(this)
            println("rabbit now in ${newCoordinate}")
            true
        } else {
            false
        }
    }

    override fun isValidMove(newCoordinate: Coordinate, board: Board): Boolean {
        return board.isCoordinateEmpty(newCoordinate)
    }

    override val length: Int
        get() = 1
}

class Fox(override val name: String, override var cordinate: Coordinate, override val direction: Int) : MovableItem() {

    override fun isValidMove(newCoordinate: Coordinate, board: Board): Boolean {
        return direction == 1 && newCoordinate.x == cordinate.x || direction == 0 && newCoordinate.y == cordinate.y
    }

    override fun copy(): BoardItem {
        val f = Fox(name, cordinate, direction)
        f.lastCoordinate = lastCoordinate
        return f
    }

    override var lastCoordinate: Coordinate = cordinate

    val tail: Coordinate
        get() {
            return if (direction == 0) {
                cordinate.copy(x = cordinate.x + 1)
            } else {
                cordinate.copy(y = cordinate.y + 1)
            }
        }

    override fun _move(newCoordinate: Coordinate, board: Board): Boolean {

        if (isValidMove(newCoordinate, board)) {
            if (moveVertical(newCoordinate, board)) {
                return true
            }

            if (moveHorizontal(newCoordinate, board)) {
                return true
            }
        }

        return false
    }

    private fun moveVertical(newCoordinate: Coordinate, board: Board): Boolean {
        if (direction == 1 && newCoordinate.x == cordinate.x) {
            println("fox move from ${cordinate} to ${newCoordinate}")
            val moveDown = cordinate.y < newCoordinate.y
            val tailNewCoord: Coordinate
            val headNewcoord: Coordinate
            val clearCoord: Coordinate

            if (moveDown) {
                headNewcoord = tail
                tailNewCoord = newCoordinate
                clearCoord = cordinate
            } else {
                headNewcoord = newCoordinate
                tailNewCoord = headNewcoord
                clearCoord = tail
            }

            if (validMove(headNewcoord, board) && validMove(tailNewCoord, board)) {
                // move up or down
                board.setEmpty(clearCoord)
                cordinate = headNewcoord
                board.placeItem(this)
                println("fox now in ${newCoordinate}")
                return true
            }
        }
        return false
    }

    private fun moveHorizontal(newCoordinate: Coordinate, board: Board): Boolean {
        if (direction == 0 && newCoordinate.y == cordinate.y) {
            val moveToRight = cordinate.x < newCoordinate.x
            val tailNewCoord: Coordinate
            val headNewcoord: Coordinate
            val clearCoord: Coordinate
            if (moveToRight) {
                clearCoord = cordinate
                tailNewCoord = newCoordinate
                headNewcoord = tail
            } else {
                clearCoord = tail
                tailNewCoord = cordinate
                headNewcoord = newCoordinate
            }

            if (validMove(headNewcoord, board) && validMove(tailNewCoord, board)) {
                println("fox move from ${cordinate} to ${headNewcoord}")
                board.setEmpty(clearCoord)
                cordinate = headNewcoord
                board.placeItem(this)
                println("fox now in ${cordinate}")
                return true
            }
        }
        return false
    }

    private fun validMove(coordinate: Coordinate, board: Board): Boolean {
        return ((isSelf(coordinate, board) || board.isCoordinateEmpty(coordinate)) && coordinate.validCoordinate())
    }

    private fun isSelf(cordinate: Coordinate, board: Board): Boolean {
        return board.get(cordinate)?.name == name
    }

    override val length: Int
        get() = 2
}