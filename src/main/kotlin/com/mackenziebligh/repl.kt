package com.mackenziebligh

/**
 * Singleton that maps [String] to a corresponding [MalFunction].
 */
private object REPL {
    val env = mapOf<String, MalFunction> ()
}

/**
 * Reads input from the console and converts it into a [MalType]. This is the first function called, and the
 * result is passed off to [eval].
 *
 * TODO Step 0: Implement this function using code from this repository and without declaring types
 */
fun read(input: String) = TODO() as MalType

/**
 * Evaluates a [MalType] to it's resulting type. This function is called after [read], and the result is passed off to
 * [printer].
 */
fun eval(ast: MalType, replEnv: Map<String, MalFunction> = REPL.env): MalType = ast

/**
 * Converts a [MalType] into a printable string. Called after [eval] and before [loop].
 *
 * TODO Step 0: Implement this function using code from this repository and without declaring types
 */
fun printer(input: MalType) = TODO() as String

/**
 * Creates an infinite loop via magic.
 *
 * TODO Step 1: Implement a function that loops infinitely and calls param "rep" once per loop
 */
fun loop(rep: () -> Any?) {
    print("> ")
    TODO("Implement the rest of loop here. readLine() may be helpful")
}

/**
 * Runs a "ReadEvaluatePrint" (rep) function infinitely via [loop].
 *
 * TODO Step 2: Implement a REPL here
 */
fun main(args: Array<String>) = loop {
        try {
            TODO("Implement your read eval printer step here")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
