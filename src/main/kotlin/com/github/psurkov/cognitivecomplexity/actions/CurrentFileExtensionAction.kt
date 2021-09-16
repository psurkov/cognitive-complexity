package com.github.psurkov.cognitivecomplexity.actions

import com.github.psurkov.cognitivecomplexity.PluginBundle
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager

class CurrentFileExtensionAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val openFile = FileEditorManager.getInstance(project).openFiles.firstOrNull() ?: return
        val notificationText = openFile.extension?.let { extension ->
            PluginBundle.message("action.current.file.extension", extension)
        } ?: PluginBundle.message("action.current.file.extension.missing")
        NotificationGroupManager.getInstance().getNotificationGroup("File Extension Notification Group")
            .createNotification(notificationText, NotificationType.INFORMATION)
            .notify(project)
    }

    override fun update(event: AnActionEvent) {
        event.presentation.isEnabled = event.project?.let {
            FileEditorManager.getInstance(it).openFiles.isNotEmpty()
        } ?: false
    }
}