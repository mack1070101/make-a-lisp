package com.mackenziebligh

fun main(args: Array<String>) {
    fun read(input: String?) = input
    fun eval(input: String?) = input
    fun print(input: String?) = input
    fun rep(input: String?) = print(read(eval(input)))
    fun loop(rep: (input: String?) -> String?) {
        while (true) {
            print(">")
            val line = readLine()
            rep.invoke(line)
        }
    }

    loop(::rep)
}
