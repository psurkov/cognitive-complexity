package testData.currentKotlinMethodAction

import java.util.function.Consumer

// Method name func, method line count 3
fun func(): Consumer<Int> {
    return Consumer { <caret> }
}