package com.github.psurkov.cognitivecomplexity.actions

import com.github.psurkov.cognitivecomplexity.PluginBundle
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.ui.Messages

class CurrentFileInfoAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val openFile = FileEditorManager.getInstance(project).openFiles.firstOrNull() ?: return
        Messages.showMessageDialog(
            PluginBundle.message("action.current.file.info.message", openFile.name),
            PluginBundle.message("action.current.file.info.title", project.name),
            Messages.getInformationIcon()
        )
    }

    override fun update(event: AnActionEvent) {
        event.presentation.isEnabled = event.project?.let {
            FileEditorManager.getInstance(it).openFiles.isNotEmpty()
        } ?: false
    }
}