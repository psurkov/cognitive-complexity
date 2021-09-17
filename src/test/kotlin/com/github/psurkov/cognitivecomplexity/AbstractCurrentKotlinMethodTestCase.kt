package com.github.psurkov.cognitivecomplexity

import com.intellij.ide.DataManager
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.testFramework.LightJavaCodeInsightTestCase
import java.util.concurrent.TimeUnit

/**
 * A TestCase for testing CurrentKotlinMethodAction
 */
abstract class AbstractCurrentKotlinMethodTestCase : LightJavaCodeInsightTestCase() {

    /**
     * Perform CurrentKotlinMethodAction test using file before and after action perform.
     * Use &lt;caret&gt; marker where caret should be
     * placed when file is loaded in editor.
     * @param filePathBefore source file's relative path from [getTestDataPath]
     * @param filePathAfter expected file's relative path from [getTestDataPath]
     */
    protected open fun doFileTest(filePathBefore: String, filePathAfter: String) {
        configureByFile(filePathBefore)
        invokeAction()
        checkResultByFile(null, filePathAfter, false)
    }

    override fun getTestDataPath(): String {
        return "src/test/resources/testData/currentKotlinMethodAction/"
    }

    private fun invokeAction() {
        val actionId = "com.github.psurkov.cognitivecomplexity.actions.CurrentKotlinMethodAction"
        val action = ActionManager.getInstance().getAction(actionId)
        assertNotNull("Can find registered action with id=$actionId", action)
        val existingDataContext = DataManager.getInstance()
            .dataContextFromFocusAsync
            .blockingGet(1, TimeUnit.SECONDS)!!
        action.actionPerformed(
            AnActionEvent.createFromAnAction(action, null, "") { key ->
                true.takeIf { key == "addCommentSilent" } ?: existingDataContext.getData(key)
            }
        )
    }
}