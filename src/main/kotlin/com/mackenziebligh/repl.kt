package com.mackenziebligh

fun main(args: Array<String>) {
    fun read(input: String) = readStr(input)
    fun eval(input: MalType) = input
    fun print(input: MalType) = prStr(input)
    fun loop(rep: (input: String) -> String) {
        kotlin.io.print("> ")
        println(rep.invoke(readLine() ?: ""))
            loop(rep)
    }
    fun rep(input: String) = print(eval(read(input)))

    loop(::rep)
}
