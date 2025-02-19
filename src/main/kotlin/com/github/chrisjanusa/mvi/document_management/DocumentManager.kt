package com.github.chrisjanusa.mvi.document_management

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Document

class DocumentManager(
    private val document: Document,
) {
    private var documentText = document.text

    fun findAndReplaceIfNotExists(oldValue: String, newValue: String, existStringCheck: String = newValue) {
        if (!documentText.contains(existStringCheck)) {
            findAndReplace(oldValue, newValue)
        }
    }

    fun findAndReplace(oldValue: String, newValue: String) {
        documentText.replace(oldValue, newValue)
    }

    fun writeToDisk() {
        val application = ApplicationManager.getApplication()
        application.invokeLater {
            application.runWriteAction {
                document.setText(documentText)
            }
        }
    }
}