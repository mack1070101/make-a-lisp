package com.mackenziebligh

/**
 * Reads input from the console and converts it into a [MalType].
 */
fun readStr(input: String) = readForm(Reader(tokenize(input)))

/**
 * Converts input from [readStr] and turns it into a list of strings (tokens).
 */
private fun tokenize(input: String) = Reader
    .TOKEN_REGEX
    .findAll(input)
    .map { it.groups[1]?.value as String }
    .filter { it != "" }
    .filter { !it.startsWith(";") }
    .toList()

/**
 * Takes the contents of a [Reader] and turns it into a [MalType].
 */
private fun readForm(reader: Reader) =
    when (reader.peek()) {
        "(" -> readList(reader)
        else -> readAtom(reader)
    }

/**
 * Used by [readForm] to read a list and convert it into a a [MalType].
 */
private fun readList(reader: Reader) = readListHelper(reader, MalList(), reader.next())

/**
 * Helper function for [readList] to recursively process a reader and accumulate values recursively.
 */
private fun readListHelper(reader: Reader, values: MalList, next: String?): MalType {
    when (reader.peek()) {
        null -> return values
        ")" -> { reader.next(); null }
        else -> readForm(reader)
    }?.let { values.add(it) }

    return readListHelper(reader, values, reader.next())
}

/**
 * Converts a token contained in a reader into a [MalType].
 */
private fun readAtom(reader: Reader): MalType {
    val groups = reader.peek()?.let { Reader.ATOM_REGEX.find(it)?.groups }
        ?: throw Exception("MatchGroupConnection Cannot be null. Reader: $reader")

    return when {
        groups[1]?.value != null -> MalLong(groups[1]?.value?.toLong() ?: 0L)
        groups[8]?.value != null -> MalSymbol(groups[8]?.value as String)
        else -> throw Exception("Not parsable")
    }
}

/**
 * Used for parsing user input into [MalType] by the [readStr] function.
 */
private class Reader(private val tokens: List<String> = listOf()) {
    private var position = 0

    companion object {
        /**
         * For parsing a string into a list of strings ("tokens").
         */
        val TOKEN_REGEX = Regex("[\\s,]*(~@|[\\[\\]{}()'`~^@]|\"(?:\\\\.|[^\\\\\"])*\"?|;.*|[^\\s\\[\\]{}('\"`,;)]*)")

        /**
         * For converting a string into a [MalType].
         */
        val ATOM_REGEX = Regex("(^-?[0-9]+$)|(^nil$)|(^true$)|(^false$)|^\"((?:\\\\.|[^\\\\\"])*)\"$|^\"(.*)$|:(.*)|(^[^\"]*$)")
    }

    /**
     * Gets the next token.
     */
    fun next() = peek(position++)

    /**
     * Looks at the current token.
     */
    fun peek(p: Int = position) = if (tokens.isEmpty()) null else if (p < tokens.size) tokens[p] else null
}
