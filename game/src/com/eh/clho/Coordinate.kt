package com.eh.clho

data class Coordinate(var x: Int, var y: Int) {
    fun sameCoordinate(coordinate: Coordinate): Boolean {
        return this.x == coordinate.x && this.y == coordinate.y
    }

    fun getInvertedCoord(): Coordinate {
        return Coordinate(y, x)
    }

    fun translate(direction: Direction, space: Int) {
        when (direction) {
            Direction.UP -> {
                y -= space
            }
            Direction.DOWN -> {
                y += space
            }
            Direction.LEFT -> {
                x -= space
            }
            Direction.RIGHT -> {
                x += space
            }
            else -> {
            }
        }

    }

    // object direction respect to this
    fun compareDirection(cordinate: Coordinate): Direction {
        if (x == cordinate.x && y == cordinate.y) {
            return Direction.SAME
        } else if (x == cordinate.x) {
            if (y < cordinate.y) {
                return Direction.DOWN
            } else if (y > cordinate.y) {
                return Direction.UP
            }
        } else if (y == cordinate.y) {
            if (x < cordinate.x) {
                return Direction.RIGHT
            } else if (x > cordinate.x) {
                return Direction.LEFT
            }
        }

        return Direction.DIAGONAL
    }

    fun validCoordinate(): Boolean {
        if (this.x < 0 || this.x > 4) {
            return false
        }
        if (this.y < 0 || this.y > 4) {
            return false
        }

        return true
    }

    fun getAdjCoordinates(): Array<Coordinate> {
        return mutableListOf<Coordinate>().apply {
            copy().apply { translate(Direction.LEFT, 1) }.also { if (it.validCoordinate()) this.add(it) }
            copy().apply { translate(Direction.RIGHT, 1) }.also { if (it.validCoordinate()) this.add(it) }
            copy().apply { translate(Direction.UP, 1) }.also { if (it.validCoordinate()) this.add(it) }
            copy().apply { translate(Direction.DOWN, 1) }.also { if (it.validCoordinate()) this.add(it) }
        }.toTypedArray()
    }

    enum class Direction {
        UP, DOWN, LEFT, RIGHT, DIAGONAL, SAME
    }
}