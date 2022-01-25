//package com.eh.clho
//
//import com.eh.clho.boarditems.Fox
//import com.eh.clho.boarditems.MovableItem
//import com.eh.clho.boarditems.Rabbit
//import com.eh.clho.game.Step
//import com.eh.clho.game.StepList
//import com.eh.clho.rules.TestRules
//import com.eh.clho.rules.getMovableSpots
//import kotlin.system.exitProcess
//
//class Game2(val board: Board) {
//
//    val stepList = StepList()
//
//    fun start() {
////        unitTest()
//        //println("Start")
//        startGameLoop(board)
//    }
//
//    /**
//     *  1. all movable items
//     *  2. for each get adjMovable spots, x = steplist size
//     *  3. back to x step
//     *  4. addmove to steplist
//     *  5. item move
//     *  6. if not repeated image, got to step2 else end
//     */
//    private fun startGameLoop(board: Board) {
////        val snapShot = board.copy()
//        if (!checkWinStatus(board)) {
//            // all movableItems move lv1
//            val allMovables = board.movableItems
////            allMovables.shuffle()
//            allMovables.forEach { item ->
//                //println("calculating ${item.name} at lv ${stepList.size}")
//                when (item) {
//                    is Rabbit -> {
//                        val movableSpots = item.getMovableSpots(board)
//                        proceedMovable(movableSpots, stepList.size, item)
//                    }
//                    is Fox -> {
//                        val movableSpots = item.getMovableSpots(board)
//                        proceedMovable(movableSpots, stepList.size, item)
//                    }
//                }
//            }
//        } else {
//            //println("End Steps: ${stepList.size}")
//            exitProcess(0)
//        }
//    }
//
//    private fun proceedMovable(movableSpots: Array<Coordinate>, step: Int, item: MovableItem) {
//        movableSpots.forEach { spot ->
//            stepList.backToStep(step - 1, board)
//            if (stepList.add(Step(item, item.cordinate, spot), board.customHashCode())) {
//                item.move(spot, board)
//                board.record()
//                if (!TestRules.testRepeatedBoardImg(board.customHashCode(), stepList)) {
//                    startGameLoop(board)
//                } else {
//                    //println("repeated board image ${board.customHashCode()}")
//                }
//            }
//        }
//    }
//
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
//        //println("hash1 $hash1")
//        //println("hash2 $hash2")
//        //println(hash1 == hash2)
////        TestRules.testRepeatedBoardImg(r, c, board)
//    }
//
//
//    fun checkWinStatus(board: Board): Boolean {
//        board.rabbits.forEach { r ->
//            if (!board.holes.any { it.cordinate.sameCoordinate(r.cordinate) }) {
//                return false
//            }
//        }
//        return true
//    }
//
//}