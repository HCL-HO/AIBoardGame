package com.eh.clho

import com.eh.clho.boarditems.Fox
import com.eh.clho.boarditems.Mushroom
import com.eh.clho.boarditems.Rabbit

object GameSettingCollection {
    val setting1 = GameSettings(mutableListOf(
            Rabbit("rabbit1", Coordinate(0, 2)),
            Fox("fox1", Coordinate(0, 1), 0)
    ))
    val setting2 = GameSettings(mutableListOf(
            Rabbit("rabbit1", Coordinate(0, 2)),
            Fox("fox1", Coordinate(0, 1), 0),
            Fox("fox2", Coordinate(0, 3), 0)
    ))
    val setting3 = GameSettings(mutableListOf(
            Rabbit("rabbit1", Coordinate(0, 2)),
            Fox("fox1", Coordinate(0, 1), 0),
            Fox("fox2", Coordinate(0, 3), 0),
            Mushroom(Coordinate(2, 3)),
            Mushroom(Coordinate(1, 2)))
    )

    val masterSettings = GameSettings(
            mutableListOf(
                    Rabbit("rabbit1", Coordinate(3, 1)),
                    Rabbit("rabbit2", Coordinate(4, 2)),
                    Rabbit("rabbit3", Coordinate(3, 4)),
                    Fox("fox1", Coordinate(0,1), 0),
                    Mushroom(Coordinate(0,3)),
                    Mushroom(Coordinate(2,2)),
                    Mushroom(Coordinate(3,0))
            )
    )
}