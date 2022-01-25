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

    val stepList = StepList()

    fun start() {
//        unitTest()
        println("Start")
        startGameLoop(board)
    }

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
        val tree = StepTree()

        recursiveGameLoop(allMovables, tree)
    }

    private fun recursiveGameLoop(allMovables: MutableList<MovableItem>, node: StepNode) {
        if (checkWinStatus(board)) {
            println(" You have won!!")
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

        node.children().forEach { childnode ->
            if (!TestRules.testIfStepIsRepeated(childnode.step, board)) {
                childnode.step?.execute(board)
                board.print()
                board.record()
                // Continue loop all movable items
                recursiveGameLoop(allMovables, childnode)
            } else {
                println("repeated board image ${board.board.customHashCode()}")
                childnode.step?.executeReverse(board)
            }
        }
    }

//    private fun unitTest() {
//        board.record()
//        val hash1 = board.customHashCode()
//        val c = Coordinate(1, 0)
//        val r = board.rabbits[0]
//        val oldCoord = r.cordinate
//        r.move(c, board)
//        r.move(oldCoord, board)
//        val hash2 = board.customHashCode()
//
//        println("hash1 $hash1")
//        println("hash2 $hash2")
//        println(hash1 == hash2)
////        TestRules.testRepeatedBoardImg(r, c, board)
//    }


    fun checkWinStatus(board: Board): Boolean {
        board.rabbits.forEach { r ->
            if (!board.holes.any { it.cordinate.sameCoordinate(r.cordinate) }) {
                return false
            }
        }
        return true
    }

}