package com.mackenziebligh

/**
 * Wrapper class for all types that can be handled by our repl.kt file.
 */
abstract class MalType

/**
 * Class for containing sequential data.
 */
data class MalList(val values: MutableList<MalType> = mutableListOf()) : MalType() {

    /**
     * Adds an item to the internal list [values]. Exists for convenience and brevity.
     */
    fun add(obj: MalType) = this.values.add(obj)

    /**
     * Pulls the first item off the internal list [values] if it exists. Corresponds to "car".
     */
    fun first() = if (this.values.isNotEmpty()) values.first() else null

    /**
     * Pulls all items off the internal list [values] except the first one. Corresponds to "cdr"
     */
    fun rest() = if (this.values.isNotEmpty()) values.subList(1, values.size) else listOf<MalType>()
}

/**
 * Class for representing [Int] and [Long].
 */
data class MalLong(val value: Long) : MalType() {
    // Provide inline operators to make code that handles math look nice
    operator fun plus(operand: MalLong) = MalLong(value + operand.value)
    operator fun minus(operand: MalLong) = MalLong(value - operand.value)
    operator fun div(operand: MalLong) = MalLong(value / operand.value)
    operator fun times(operand: MalLong) = MalLong(value * operand.value)
}

/**
 * Class for representing symbols (e.g. +, -, /, *).
 */
data class MalSymbol(val value: String) : MalType()

/**
 * Class for containing [MalType] to be evaluated.
 */
data class MalFunction(private val func: (sequence: List<MalType>) -> MalType) : MalType() {

    /**
     * Invokes the [MalFunction].
     */
    fun invoke(sequence: List<MalType>) = func.invoke(sequence)
}
