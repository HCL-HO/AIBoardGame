package com.eh.clho

import com.eh.clho.boarditems.Fox
import com.eh.clho.boarditems.MovableItem
import com.eh.clho.boarditems.Rabbit
import com.eh.clho.game.Step
import com.eh.clho.game.StepList
import com.eh.clho.game.StepNode
import com.eh.clho.game.StepTree
import com.eh.clho.rules.TestRules
import com.eh.clho.rules.getMovableSpots
import kotlin.system.exitProcess

class Game(val board: Board) {

    var reloadImageRequired = false

    fun start() {
//        unitTest()
        //println("Start")
        startGameLoop(board)
    }

    private lateinit var tree: StepTree

    /**
     *  1. all movable items
     *  2. for each get adjMovable spots, x = steplist size
     *  3. back to x step
     *  4. addmove to steplist
     *  5. item move
     *  6. if not repeated image, got to step2 else end
     */
    private fun startGameLoop(board: Board) {
        val allMovables = board.movableItems
//            allMovables.shuffle()
        tree = StepTree()

        recursiveGameLoop(allMovables, tree)
    }

    private fun recursiveGameLoop(allMovables: MutableList<MovableItem>, node: StepNode) {
        if (checkWinStatus(board)) {
            println(" You have won!!")
            board.print()
            node.printInverted(board)
            exitProcess(0)
        }
        allMovables.forEach { item ->
            when (item) {
                is Rabbit -> {
                    val movableSpots = item.getMovableSpots(board)
                    movableSpots.map { Step(item, item.cordinate, it) }.forEach {
                        node.addChildren(it)
                    }
                }
                is Fox -> {
                    val movableSpots = item.getMovableSpots(board)
                    movableSpots.map { Step(item, item.cordinate, it) }.forEach {
                        node.addChildren(it)
                    }
                }
            }
        }

        val cloneBord = board.board.map { it.clone() }.toTypedArray()
        node.children().forEach { childnode ->
            if (reloadImageRequired) {
                //println("Reloading ..")
                board.loadSave(cloneBord)
                reloadImageRequired = false
            }
            if (!TestRules.testIfStepIsRepeated(childnode.step, board) && childnode.step?.execute(board) == true) {
                board.print()
                board.record()
                // Continue loop all movable items
                recursiveGameLoop(allMovables, childnode)
            } else {
                reloadImageRequired = true
            }
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

}