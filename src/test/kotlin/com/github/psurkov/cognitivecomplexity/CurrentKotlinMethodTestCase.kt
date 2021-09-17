package com.github.psurkov.cognitivecomplexity

import com.intellij.testFramework.TestDataPath

@TestDataPath("\$CONTENT_ROOT/resources/testData/currentKotlinMethodAction/")
class CurrentKotlinMethodTestCase : AbstractCurrentKotlinMethodTestCase() {

    fun testInAnonymousFun() {
        doFileTest("InAnonymousFun.kt", "InAnonymousFunResult.kt")
    }

    fun testInDeepFun() {
        doFileTest("InDeepFun.kt", "InDeepFunResult.kt")
    }

    fun testInFunComment() {
        doFileTest("InFunComment.kt", "InFunCommentResult.kt")
    }

    fun testInFunKeyword() {
        doFileTest("InFunKeyword.kt", "InFunKeywordResult.kt")
    }

    fun testInJavaFunc() {
        doFileTest("InJavaFunc.java", "InJavaFuncResult.java")
    }

    fun testInLargeFun() {
        doFileTest("InLargeFun.kt", "InLargeFunResult.kt")
    }

    fun testInMediumFun() {
        doFileTest("InMediumFun.kt", "InMediumFunResult.kt")
    }

    fun testInMicroFun() {
        doFileTest("InMicroFun.kt", "InMicroFunResult.kt")
    }

    fun testInMiniFun() {
        doFileTest("InMiniFun.kt", "InMiniFunResult.kt")
    }

    fun testInOneOfTwoFunc() {
        doFileTest("InOneOfTwoFunc.kt", "InOneOfTwoFuncResult.kt")
    }

    fun testOutsideOfFun() {
        doFileTest("OutsideOfFun.kt", "OutsideOfFunResult.kt")
    }
}