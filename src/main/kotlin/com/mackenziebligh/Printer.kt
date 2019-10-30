package com.mackenziebligh

/**
 * Converts a [MalType] to a string to display on the terminal.
 */
fun prStr(malType: MalType): String {
    return when (malType) {
        is MalSymbol -> malType.value
        is MalInt -> malType.value.toString()
        is MalList -> malType.values.joinToString(" ", "(", ")") { prStr(it) }
        else -> throw Exception("Cannot print $malType")
    }
}
