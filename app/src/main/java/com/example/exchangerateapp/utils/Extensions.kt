package com.example.exchangerateapp.utils

fun String.isValid(): Boolean {
    var hasSeparator = false
    var digitsAfterSeparator = 0

    this.forEachIndexed { index, c ->
        when {
            index == 0 -> if (c.isSeparator()) return false
            !hasSeparator -> {
                if (c.isSeparator()) { hasSeparator = true }
            }
            else -> {
                if (c.isSeparator()) return false else {
                    digitsAfterSeparator++
                }
            }
        }
    }

    return digitsAfterSeparator <= 2
}

fun String.setCorrectSeparator(): String =
    this.replace(',', '.')

private fun Char.isSeparator() : Boolean =
    this == ',' || this == '.'