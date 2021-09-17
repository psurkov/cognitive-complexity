package com.github.psurkov.cognitivecomplexity.actions

import com.github.psurkov.cognitivecomplexity.PluginBundle
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKey
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.ui.MessageDialogBuilder
import com.intellij.psi.PsiManager
import com.intellij.psi.util.parentOfType
import org.jetbrains.kotlin.idea.KotlinFileType
import org.jetbrains.kotlin.idea.util.getLineCount
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtPsiFactory

class CurrentKotlinMethodAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val addCommentSilent = event.getData(DataKey.create("addCommentSilent")) ?: false
        val caret = event.getData(PlatformDataKeys.CARET)?.caretModel ?: return
        val editor = caret.primaryCaret.editor
        val project = editor.project ?: return
        val file = FileDocumentManager.getInstance().getFile(editor.document) ?: return
        val method = PsiManager.getInstance(project).findFile(file)
            ?.findElementAt(caret.primaryCaret.offset)
            ?.parentOfType<KtNamedFunction>()
            ?: return NotificationGroupManager.getInstance()
                .getNotificationGroup("Current Kotlin Method Notification Group")
                .createNotification(
                    PluginBundle.message("action.current.kotlin.method.outside.of.it.error"),
                    NotificationType.ERROR
                )
                .notify(project)
        val name = method.name.orEmpty()
        val lineCount = method.getLineCount()
        val addComment = addCommentSilent ||
                MessageDialogBuilder.okCancel(
                    PluginBundle.message("action.current.kotlin.method.dialog.title", name),
                    PluginBundle.message("action.current.kotlin.method.dialog.message", name, lineCount)
                ).yesText(
                    PluginBundle.message("action.current.kotlin.method.dialog.yesText", name, lineCount)
                ).ask(project)
        if (!addComment) {
            return
        }
        val comment = KtPsiFactory(project, true).createComment(
            PluginBundle.message("action.current.kotlin.method.comment", name, lineCount)
        )
        runWriteAction {
            WriteCommandAction.runWriteCommandAction(project) {
                method.addBefore(comment, method.funKeyword)
            }
        }
    }

    /**
     * We don't check if caret is inside the Kotlin function because checking is slow while [update] should work fast.
     */
    override fun update(event: AnActionEvent) {
        event.presentation.isEnabled = event.getData(PlatformDataKeys.CARET)?.caretModel?.let {
            val document = it.primaryCaret.editor.document
            FileDocumentManager.getInstance().getFile(document)?.fileType is KotlinFileType
        } ?: false
    }
}