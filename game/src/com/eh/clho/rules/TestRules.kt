package com.eh.clho.rules

import com.eh.clho.Board
import com.eh.clho.Coordinate
import com.eh.clho.boarditems.Empty
import com.eh.clho.boarditems.Hole
import com.eh.clho.boarditems.MovableItem

object TestRules {
    fun arrangeMovableOrder(coords: MutableList<Coordinate>, lastCoordinate: Coordinate): MutableList<Coordinate> {
        coords.find { it.sameCoordinate(lastCoordinate) }?.also {
            coords.remove(it)
            coords.add(it)
        }

        return coords
    }

    fun testRepeatedBoardImg(item: MovableItem, spot: Coordinate, board: Board): Boolean {
        val clone = board.board.clone()
        val itemClone = item.copy() as MovableItem
        val invertSpot = spot.getInvertedCoord()
        itemClone.lastCoordinate = itemClone.cordinate
        itemClone.cordinate = spot

        clone[invertSpot.x][invertSpot.y] = itemClone
        for (i in 0 until itemClone.length) {
            val iCoordInverted = item.cordinate.getInvertedCoord()
            val holesCoords = board.holes.map { it.cordinate }
            if (item.direction == 0) {
                clone[iCoordInverted.x + i][iCoordInverted.y] = if (holesCoords.contains(item.cordinate)) Hole(item.cordinate) else Empty(item.cordinate)
            } else {
                clone[iCoordInverted.x][iCoordInverted.y + i] = if (holesCoords.contains(item.cordinate)) Hole(item.cordinate) else Empty(item.cordinate)
            }
        }

        val hashCode = clone.contentDeepHashCode()
        println("hash code $hashCode ${board.historyImage.contains(hashCode)}")
        println(board.historyImage.joinToString(", ") { it.toString() })
        return board.historyImage.contains(hashCode)
    }
}