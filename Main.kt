package tictactoe

import kotlin.math.absoluteValue

fun analyze(state: CharArray): String? {

    val xcount = state.filter { it == 'X' }.count()
    val ocount = state.filter { it == 'O' }.count()
    val dif = xcount - ocount

    val normal = arrayOf(state.sliceArray(0..2), state.sliceArray(3..5), state.sliceArray(6..8))
    val trspose = transpose(normal)
    val diagonal = arrayOf(charArrayOf(normal[0][0], normal[1][1], normal[2][2]),
        charArrayOf(normal[0][2], normal[1][1], normal[2][0]))
    val results = normal + trspose + diagonal
    val flat = results.flatMap { it.toList() }.toCharArray()
    val joined = results.map {it.joinToString("")}

    var end: String? = null
    when {
        dif.absoluteValue > 1 || "XXX" in joined && "OOO" in joined -> end = "Impossible"
        "XXX" in joined -> end =  "X wins"
        "OOO" in joined -> end = "O wins"
        ' ' !in flat -> end = "Draw"
    }
    return end
}


fun transpose(data: Array<CharArray>): Array<CharArray> {
    val matrix = Array(3) {CharArray(3)}
    for (i in 0..2) {
        for (j in 0..2){
            matrix[j][i] = data[i][j]
        }
    }
    return matrix
}

fun display(data: CharArray): Unit{
    println("---------")
    println("| ${data[0]} ${data[1]} ${data[2]} |")
    println("| ${data[3]} ${data[4]} ${data[5]} |")
    println("| ${data[6]} ${data[7]} ${data[8]} |")
    println("---------")
}

fun isnumber(data: String): Boolean {
    val str = data.filter { !it.isWhitespace() }
    val result = str.toList().filter { !it.isDigit() }
    return result.isEmpty()
}

fun check(data: String, info: CharArray, sign: Char): CharArray {
    val coordinates = mapOf("1 1" to 0, "1 2" to 1, "1 3" to 2,
        "2 1" to 3, "2 2" to 4, "2 3" to 5,
        "3 1" to 6, "3 2" to 7, "3 3" to 8)

    var strdata = data
    var checking = true
    while (checking) {
        val isnum = isnumber((strdata))
        if (!isnum) {
            println("You should enter numbers!")
            print("Enter coordinates: ")
            strdata = readLine()!!
        } else if ( !coordinates.containsKey(strdata)) {
            println("Coordinates should be from 1 to 3!")
            print("Enter coordinates: ")
            strdata = readLine()!!
        } else if (info[coordinates[strdata]!!] == 'O' || info[coordinates[strdata]!!] == 'X') {
            println("This cell is occupied! Choose another one!")
            print("Enter coordinates: ")
            strdata = readLine()!!
        } else {
            checking = false
        }
    }
    info[coordinates[strdata]!!] = sign
    return info

}

fun input(data: CharArray, sign: Char): CharArray {
    print("Enter the coordinates: ")
    val cd = readLine()!!
    return check(cd, data, sign)
}

fun main() {
    var bdata = CharArray(9) {' '}
    display(bdata)

    val playing = true
    while (playing) {
        bdata = input(bdata, 'X')
        display(bdata)
        val result = analyze(bdata)
        if (result != null) {
            println(result)
            break
        }
        bdata = input(bdata, 'O')
        display(bdata)
        val state = analyze(bdata)
        if (state != null) {
            println(state)
            break
        }
    }
}