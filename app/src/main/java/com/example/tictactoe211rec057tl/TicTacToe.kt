package com.example.tictactoe211rec057tl

import kotlin.random.Random

fun Boolean?.toMark(): String = when (this) {
    true -> "X"
    false -> "O"
    null -> "-"
}

interface Game {
    val isFinished: Boolean
    val winner: Boolean?
    val field: Field
    fun act(row: Int, col: Int): Boolean
}

interface Field {
    val size: Int
    fun get(row: Int, col: Int): Boolean?
}

interface MutableField : Field {
    fun set(row: Int, col: Int, value: Boolean)
}

class GameImp(private val isPvC: Boolean) : Game {

    override var isFinished: Boolean = false
    override var winner: Boolean? = null
    override val field: MutableField = ArrayField(3)

    private var currentTurn: Boolean = true
    private val userMark = true

    init {
        if (isPvC && !userMark) actAi()
    }

    override fun act(row: Int, col: Int): Boolean {
        if (field.get(row, col) != null || isFinished) return false

        field.set(row, col, currentTurn)
        checkEnd()

        if (!isFinished && isPvC) {
            actAi()
            checkEnd()
        } else {
            currentTurn = !currentTurn
        }

        return true
    }

    private fun actAi() {
        for (r in 0 until field.size) {
            for (c in 0 until field.size) {
                if (field.get(r, c) == null) {
                    field.set(r, c, !userMark)
                    return
                }
            }
        }
    }

    private fun checkEnd() {
        val lines = mutableListOf<List<Boolean?>>()

        // rows, cols
        for (i in 0 until field.size) {
            lines.add((0 until field.size).map { field.get(i, it) })
            lines.add((0 until field.size).map { field.get(it, i) })
        }

        // diagonals
        lines.add((0 until field.size).map { field.get(it, it) })
        lines.add((0 until field.size).map { field.get(it, field.size - 1 - it) })

        for (line in lines) {
            if (line.all { it == true }) {
                winner = true
                isFinished = true
                return
            }
            if (line.all { it == false }) {
                winner = false
                isFinished = true
                return
            }
        }

        // tie
        if ((0 until field.size).all { r ->
                (0 until field.size).all { c ->
                    field.get(r, c) != null
                }
            }) {
            isFinished = true
        }
    }
}

class ArrayField(override val size: Int) : MutableField {

    private val points: Array<Array<Boolean?>> = Array(size) { arrayOfNulls(size) }

    override fun set(row: Int, col: Int, value: Boolean) {
        points[row][col] = value
    }

    override fun get(row: Int, col: Int): Boolean? = points[row][col]
}

inline fun Field.forEach(action: (row: Int, col: Int, value: Boolean?) -> Unit) {
    repeat(size) { row ->
        repeat(size) { col ->
            action(row, col, get(row, col))
        }
    }
}