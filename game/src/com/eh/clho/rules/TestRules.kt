package com.eh.clho.rules

import com.eh.clho.Board
import com.eh.clho.Coordinate
import com.eh.clho.customHashCode
import com.eh.clho.game.Step

object TestRules {
    fun arrangeMovableOrder(coords: MutableList<Coordinate>, lastCoordinate: Coordinate): MutableList<Coordinate> {
        coords.find { it.sameCoordinate(lastCoordinate) }?.also {
            coords.remove(it)
            coords.add(it)
        }

        return coords
    }

    fun testRepeatedBoardImg(board: Board): Boolean {
        return board.historyImage.contains(board.board.customHashCode())
    }

    fun testIfStepIsRepeated(step: Step?, board: Board): Boolean {
        if (step == null) {
            return false
        }
        val cloneBord = board.board.map { it.clone() }.toTypedArray()
        val cloneItem = step.item.copy()
        return if (step.item.isValidMove(step.to, board)) {
            cloneItem.cordinate = step.to
            Board.emptyOnBoard(step.item.cordinate, cloneBord, board.holes)
            Board.markOnBoard(cloneItem, cloneBord)
            println(" check duplicate")
            println(board.board.customHashCode())
            println(cloneBord.customHashCode())
            board.historyImage.contains(cloneBord.customHashCode())
        } else {
            true
        }
    }
}