package com.eh.clho

import com.eh.clho.boarditems.*
import com.eh.clho.game.Step
import java.lang.StringBuilder

class Board(val settings: GameSettings) {

    companion object {
        val SPACE = "          "

        fun markOnBoard(it: BoardItem, board: Array<Array<BoardItem>>) {
            for (i in 0 until it.length) {
                // revert x and y for readability
                if (it.direction == 0) {
                    board[it.cordinate.y][it.cordinate.x + i] = it
                } else {
                    board[it.cordinate.y + i][it.cordinate.x] = it
                }
            }
        }

        fun emptyOnBoard(cordinate: Coordinate, board: Array<Array<BoardItem>>, holes: List<Hole>) {
            board[cordinate.y][cordinate.x] = getEmptyOrHole(cordinate, holes)
        }


        fun getEmptyOrHole(coordinate: Coordinate, holes: List<Hole>): BoardItem {
            return if (holes.any { it.cordinate.sameCoordinate(coordinate) }) Hole(coordinate) else Empty(coordinate)
        }
    }

    val historyImage = mutableSetOf<String>()
    val movableItems = mutableListOf<MovableItem>()
    val holes = mutableListOf<Hole>()
    val rabbits = mutableListOf<Rabbit>()
    val foxes = mutableListOf<Fox>()

    var board: Array<Array<BoardItem>>

    init {
        val column = mutableListOf<Array<BoardItem>>()
        for (i in 0 until 5) {
            val row = mutableListOf<BoardItem>()
            for (j in 0 until 5) {
                row.add(Empty(Coordinate(j, i)) as BoardItem)
            }
            column.add(row.toTypedArray())
        }
        board = column.toTypedArray()
        drawBoard()
    }

    private fun drawBoard() {
        settings.items.forEach {
            addToBoard(it)
            placeItem(it)
        }
    }

    fun addToBoard(it: BoardItem) {
        when (it) {
            is Rabbit -> {
                rabbits.add(it)
                movableItems.add(it)
            }
            is Fox -> {
                foxes.add(it)
                movableItems.add(it)
            }
            is Hole -> {
                holes.add(it)
            }
            is MovableItem -> {
                movableItems.add(it)
            }
        }
    }

    fun placeItem(item: BoardItem) {
        settings.items.forEach {
            markOnBoard(it, board)
        }
        markOnBoard(item, board)
    }

    fun print() {
        board.forEach { horizontalLine ->
            println("| ${horizontalLine.map { (it.name + SPACE).substring(0, 10) + it.cordinate.toString() }.joinToString(" |")} |\n")
        }
        println("\n\n")
    }

    fun setEmpty(coordinate: Coordinate) {
        val empyType = getEmptyOrHole(coordinate, holes)
        val invertedCoord = coordinate.getInvertedCoord()
        board[invertedCoord.x][invertedCoord.y] = empyType
    }


    fun isCoordinateEmpty(coordinate: Coordinate): Boolean {
        val invert = coordinate.getInvertedCoord()
        val dest = board.getOrNull(invert.x)?.getOrNull(invert.y)
        return dest is Empty || dest is Hole
    }

    fun getAdjacentsItems(cordinate: Coordinate, direction: Coordinate.Direction? = null): Array<BoardItem> {
        val result = mutableListOf<BoardItem>()
//        val yLength = if (movableItem.direction == 1) movableItem.length else 1
//        val xLength = if (movableItem.direction == 0) movableItem.length else 1
        val yLength = 1
        val xLength = 1
        val left = get(cordinate.copy(x = cordinate.x - xLength))?.also {
            direction?.also { _ -> if (direction == Coordinate.Direction.LEFT) result.add(it) } ?: result.add(it)
        }
        val right = get(cordinate.copy(x = cordinate.x + xLength))?.also {
            direction?.also { _ -> if (direction == Coordinate.Direction.RIGHT) result.add(it) } ?: result.add(it)
        }
        val down = get(cordinate.copy(y = cordinate.y + yLength))?.also {
            direction?.also { _ -> if (direction == Coordinate.Direction.DOWN) result.add(it) } ?: result.add(it)
        }
        val up = get(cordinate.copy(y = cordinate.y - yLength))?.also {
            direction?.also { _ -> if (direction == Coordinate.Direction.UP) result.add(it) } ?: result.add(it)
        }

        return result.toTypedArray()
    }

    fun get(coordinate: Coordinate): BoardItem? {
        if (!coordinate.getInvertedCoord().validCoordinate()) {
//            println("Invalid Coordinate in Board#get $coordinate")
            return null
        }

        return board[coordinate.getInvertedCoord().x][coordinate.getInvertedCoord().y]
    }


    fun getBoardCoordinateById(name: String): Coordinate? {
        board.forEach { row ->
            row.firstOrNull { it.name == name }?.let {
                return it.cordinate
            }
        }
        return null
    }


    fun getMovableCoordinate(item: MovableItem): Array<Coordinate> {
        val coords = mutableListOf<Coordinate>()
        getAdjacentsItems(item.cordinate).forEach { adj ->
            if (adj !is Hole && adj !is Empty) {
                when (item.cordinate.compareDirection(adj.cordinate)) {
                    Coordinate.Direction.UP -> {
                        val moveSpace = if (adj.direction == 1) adj.length else 1
                        val newCoord = adj.cordinate.copy(y = adj.cordinate.y - moveSpace)
                        if (newCoord.validCoordinate()) {
                            coords.add(newCoord)
                        }
                    }

                    Coordinate.Direction.DOWN -> {
                        val moveSpace = if (adj.direction == 1) adj.length else 1
                        val newCoord = adj.cordinate.copy(y = adj.cordinate.y + moveSpace)
                        if (newCoord.validCoordinate()) {
                            coords.add(newCoord)
                        }

                    }
                    Coordinate.Direction.LEFT -> {
                        val moveSpace = if (adj.direction == 0) adj.length else 1
                        val newCoord = adj.cordinate.copy(x = adj.cordinate.x - moveSpace)
                        if (newCoord.validCoordinate()) {
                            coords.add(newCoord)
                        }

                    }
                    Coordinate.Direction.RIGHT -> {
                        val moveSpace = if (adj.direction == 0) adj.length else 1
                        val newCoord = adj.cordinate.copy(x = adj.cordinate.x + moveSpace)
                        if (newCoord.validCoordinate()) {
                            coords.add(newCoord)
                        }
                    }
                    else -> {
                    }
                }
            }

        }
        return coords.toTypedArray()
    }

    fun record() {
        historyImage.add(board.customHashCode())
    }


}

fun Array<Array<BoardItem>>.customHashCode(): String {
    val stringBuilder = StringBuilder()
    this.forEach { row ->
        stringBuilder.append(row.map { it.name + it.cordinate }.joinToString(""))
    }
    return stringBuilder.toString()
}