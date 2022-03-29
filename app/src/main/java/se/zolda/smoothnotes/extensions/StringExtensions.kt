package se.zolda.smoothnotes.extensions

fun String.isLastCharNewLine() = this.last().toString() == "\n"
fun Char.isNewLine() = this.toString() == "\n"