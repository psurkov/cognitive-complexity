package testData.currentKotlinMethodAction

import java.util.function.Consumer

fun func(): Consumer<Int> {
    return Consumer { <caret> }
}