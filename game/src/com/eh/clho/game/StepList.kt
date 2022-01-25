package com.eh.clho.game

import com.eh.clho.Board
import kotlin.system.exitProcess


class StepList() {
    val historyImages = mutableSetOf<String>()
    val steps = mutableListOf<Step>()
    val size: Int
        get() {
            return steps.size
        }

    fun add(step: Step, hashImage: String): Boolean {
        if (steps.size > 2500) {
            exitProcess(0)
        }
        // Not allow move back immediately
        if (!step.isReverseStep(steps.lastOrNull())) {
            println("add $step")
            steps.add(step)
            historyImages.add(hashImage)
            return true
        } else {
            println("Reverse Step not allowed: $step")
        }

        return false

    }

    fun backToStep(i: Int, board: Board) {
        if (i > 0 && i < steps.size) {
            println("Reverse move back to step $i")
            for (x in steps.size - 1 downTo i) {
                steps.removeAt(x).revertMove().also {
                    it.item.move(it.to, board)
                    it.item.lastCoordinate = it.lastCoordinate
                }
            }
        }
    }

    fun isRepeatedImage(hash: String): Boolean {
        return historyImages.contains(hash)
    }
}