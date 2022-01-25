package com.eh.clho.game

import com.eh.clho.Board
import com.eh.clho.Coordinate
import com.eh.clho.boarditems.MovableItem


data class Step(val item: MovableItem, val from: Coordinate, val to: Coordinate, val lastCoordinate: Coordinate = item.lastCoordinate.copy()) {

    fun revertMove(): Step {
        return Step(item, to, from)
    }

    fun isReverseStep(other: Step?): Boolean {
        if (other == null) {
            return false
        }
        val reverse = other.revertMove()
        val isReversed = reverse.item.name == item.name && reverse.from == from && reverse.to == to
        if (isReversed) {
            //println("Reversed step is not allowed $other")
        }
        return isReversed
    }

    fun execute(board: Board): Boolean {
        return item.move(to, board)
    }

    fun executeReverse(board: Board) {
        println(this)
        item.move(from, board)
        item.lastCoordinate = lastCoordinate
    }
}