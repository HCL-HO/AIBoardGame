package com.eh.clho.boarditems

import com.eh.clho.Coordinate

abstract class BoardItem {
    abstract val name: String
    abstract var cordinate: Coordinate
    abstract val length: Int
    open val direction = 0 // horizontal = 0 vertical = 1

    abstract fun copy(): BoardItem
}


class Mushroom(override var cordinate: Coordinate) : BoardItem() {
    override fun copy(): BoardItem {
        return Mushroom(cordinate)
    }

    override val name: String = "mushroom"
    override val length: Int
        get() = 1
}

class Hole(override var cordinate: Coordinate) : BoardItem() {
    override fun copy(): BoardItem {
        return Hole(cordinate)
    }

    override val name: String = "hole"
    override val length: Int
        get() = 1
}

class Empty(override var cordinate: Coordinate) : BoardItem() {
    override fun copy(): BoardItem {
        return Empty(cordinate)
    }

    //arbitrary
    override val name: String = "empty"
    override val length: Int
        get() = 1
}


