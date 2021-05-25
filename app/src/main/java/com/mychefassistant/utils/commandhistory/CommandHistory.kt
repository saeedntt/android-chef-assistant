package com.mychefassistant.utils.commandhistory

class CommandHistory {
    private val topList = mutableListOf<Command>()
    private val bottomList = mutableListOf<Command>()

    fun run(command: Command) {
        topList.add(command)
        command.execute()
    }

    fun undo() {
        if (topList.isNotEmpty()) {
            val last = topList.last()
            topList.remove(last)
            bottomList.add(last)
            last.unExecute()
        }
    }

    fun redo() {
        if (bottomList.isNotEmpty()) {
            val last = bottomList.last()
            bottomList.remove(last)
            topList.add(last)
            last.execute()
        }
    }

    fun retry() {
        if (topList.isNotEmpty()) {
            topList.last().execute()
        }
    }
}