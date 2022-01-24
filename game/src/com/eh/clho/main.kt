package com.eh.clho

class Main {
    companion object {
        @JvmStatic()
        fun main(args: Array<String>) {
            Game(Board(GameSettingCollection.masterSettings)).start()
        }
    }
}
