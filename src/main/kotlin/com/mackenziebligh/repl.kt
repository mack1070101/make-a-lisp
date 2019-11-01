package com.mackenziebligh

/**
 * Singleton that maps [String] to a corresponding [MalFunction].
 */
private object REPL {
    val env = mapOf(
        "+" to MalFunction { values: List<MalType> -> values.reduceRight { x, y -> (x as MalLong) + (y as MalLong) } },
        "-" to MalFunction { values: List<MalType> -> values.reduceRight { x, y -> (x as MalLong) - (y as MalLong) } },
        "/" to MalFunction { values: List<MalType> -> values.reduceRight { x, y -> (x as MalLong) / (y as MalLong) } },
        "*" to MalFunction { values: List<MalType> -> values.reduceRight { x, y -> (x as MalLong) * (y as MalLong) } })
}

/**
 * Reads input from the console and converts it into a [MalType]. This is the first function called, and the
 * result is passed off to [eval].
 */
fun read(input: String) = readStr(input)

/**
 * Evaluates a [MalType] to it's resulting type. This function is called after [read], and the result is passed off to
 * [printer].
 */
fun eval(ast: MalType, replEnv: Map<String, MalFunction> = REPL.env): MalType =
    if (ast is MalList && ast.values.isNotEmpty()) {
        val evaluated = evalAst(ast, replEnv) as MalList
        if (evaluated.first() !is MalFunction) {
            evaluated
        } else  {
            (evaluated.first() as MalFunction).invoke(evaluated.rest())
        }
    }  else {
        evalAst(ast, replEnv)
    }

/**
 * Helper function to evaluate an abstract syntax tree. Called by [eval].
 */
fun evalAst(malAst: MalType, replEnv: Map<String, MalFunction>) =
    when (malAst) {
        is MalSymbol -> replEnv[malAst.value] ?: throw Exception("${malAst.value} not found")
        is MalList -> malAst.values.fold(MalList(), { a, b -> a.add(eval(b, replEnv)); a })
        else -> malAst
    }

/**
 * Converts a [MalType] into a printable string. Called after [eval] and before [loop].
 */
fun printer(input: MalType) = prStr(input)

/**
 * Loops infinitely via recursion. Invokes a "ReadEvaluatePrint" (rep) function with associated command line UI.
 */
fun loop(rep: () -> Unit) {
    print("> ")
    println(rep.invoke())
    loop(rep)
}

/**
 * Runs a "ReadEvaluatePrint" (rep) function infinitely via [loop].
 */
fun main(args: Array<String>) = loop {
    try {
        printer(eval(read(readLine() ?: ""), REPL.env))
    }  catch (e: Exception)  {
        e.printStackTrace()
    }
}
