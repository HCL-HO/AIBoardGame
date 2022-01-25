package com.eh.clho.game

import com.eh.clho.Board
import com.eh.clho.boarditems.BoardItem


class StepTree : StepNode(null, null, 0)

open class StepNode(val parent: StepNode?, val step: Step?, val number: Int) {

    init {
        //println("step $number")
    }

    private val _children: MutableList<StepNode> = mutableListOf()

    fun children(): Array<StepNode> {
        return _children.toTypedArray()
    }

    fun addChildren(step: Step) {
        this.step?.let { thisStep ->
            if (!thisStep.isReverseStep(step)) {
                _children.add(StepNode(this, step, parent?.number?.plus(1) ?: 0))
            }
        } ?: _children.add(StepNode(this, step, parent?.number?.plus(1) ?: 0))
    }

    fun printInverted(board: Board) {
        parent?.let {
            it.step?.executeReverse(board)
            board.print()
            it.printInverted(board)
        }
    }
}

