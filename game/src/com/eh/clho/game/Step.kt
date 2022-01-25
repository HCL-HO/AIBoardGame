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
        return reverse.item.name == item.name && reverse.from == from && reverse.to == to
    }

    fun execute(board: Board) {
        item.move(to, board)
    }

    fun executeReverse(board: Board) {
        item.move(from, board)
        item.lastCoordinate = lastCoordinate
    }
}