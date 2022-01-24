package com.eh.clho

import com.eh.clho.boarditems.Fox
import com.eh.clho.boarditems.MovableItem
import com.eh.clho.boarditems.Rabbit
import com.eh.clho.rules.TestRules
import com.eh.clho.rules.getMovableSpots
import kotlin.system.exitProcess

class Game(val board: Board) {

    val stepList = StepList()

    fun start() {
//        unitTest()
        println("Start")
        startGameLoop(board, 0)
    }

    private fun startGameLoop(board: Board, step: Int) {
//        val snapShot = board.copy()
        if (!checkWinStatus(board)) {
            // all movableItems move lv1
            val allMovables = board.movableItems
            allMovables.shuffle()
            allMovables.forEach { item ->
                println("calculating ${item.name} at lv $step")
                when (item) {
                    is Rabbit -> {
                        val movableSpots = item.getMovableSpots(board)
                        proceedMovable(movableSpots, step, item)
                    }
                    is Fox -> {
                        val movableSpots = item.getMovableSpots(board)
                        proceedMovable(movableSpots, step, item)
                    }
                }
            }
        } else {
            println("End Steps: $step")
            exitProcess(0)
        }
    }

    private fun proceedMovable(movableSpots: Array<Coordinate>, step: Int, item: MovableItem) {
        movableSpots.forEach { spot ->
            println("starting at lv $step")
            stepList.backToStep(step, board)
            if (stepList.add(Step(item, item.cordinate, spot))) {
                if (!TestRules.testRepeatedBoardImg(item, spot, board)) {
                    item.move(spot, board)
                    board.record()
                    startGameLoop(board, step + 1)
                }
            }
        }
    }

    private fun unitTest() {
        board.record()
        val f = board.foxes[0]
        val spots = f.getMovableSpots(board)
        spots.forEach {
            println(it)
        }
        spots.apply {
            proceedMovable(spots, 0, f)
        }
    }


    fun checkWinStatus(board: Board): Boolean {
        board.rabbits.forEach { r ->
            if (!board.holes.any { it.cordinate.sameCoordinate(r.cordinate) }) {
                return false
            }
        }
        return true
    }

    data class Step(val item: MovableItem, val from: Coordinate, val to: Coordinate) {
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
    }

    class StepList {
        val steps = mutableListOf<Step>()

        fun add(step: Step): Boolean {
            if (steps.size > 100) {
                exitProcess(0)
            }
            // Not allow move back immediately
            if (!step.isReverseStep(steps.lastOrNull())) {
                println("add $step")
                steps.add(step)
                return true
            }

            return false

        }

        fun backToStep(i: Int, board: Board) {
            println("Reverse move back to step $i")
            for (x in steps.size - 1 downTo i) {
                steps.removeAt(x).revertMove().also {
                    board.execute(it)
                }
            }
        }
    }

}