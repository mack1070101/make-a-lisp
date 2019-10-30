package com.mackenziebligh

abstract class MalType

/**
 * Class for containing sequential data.
 */
data class MalList(val values: MutableList<MalType> = mutableListOf()) : MalType() {
    fun add(obj: MalType) {
        this.values.add(obj)
    }
}

/**
 * Class for representing [Int] and [Long]
 */
data class MalInt(val value: Long) : MalType()

/**
 * Class for representing symbols (e.g. +, -, /, *)
 */
data class MalSymbol(val value: String) : MalType()
