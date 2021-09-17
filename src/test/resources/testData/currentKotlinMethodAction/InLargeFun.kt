package testData.currentKotlinMethodAction

fun <T> largeFun(list: List<T>): List<List<T>> =
    if (list.isEmpty()) {
        listOf(emptyList())
    } else largeFun(list.drop(1)).let {
        it + it.map { it <caret>+ list.first() }
    }