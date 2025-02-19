package com.github.chrisjanusa.mvi.document_management

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Document

open class DocumentManager(
    private val document: Document,
) {
    internal var documentText = document.text
    internal var documentLines : List<String>
        get() = documentText.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toMutableList()
        set(lines) {
            documentText = lines.joinToString(separator = "\n").dropLastWhile { it == '\n' }
        }

    fun addToBottom(text: String) {
        documentText = "$documentText\n\n$text"
    }

    fun addAfterFirst(lineToAdd: String, predicate: (String) -> Boolean) {
        performActionAtFirst(
            predicate
        ) { newDocumentLines, index ->
                newDocumentLines.add(index + 1, lineToAdd)
        }
    }

    fun addBeforeFirst(lineToAdd: String, predicate: (String) -> Boolean) {
        performActionAtFirst(
            predicate
        ) { newDocumentLines, index ->
            newDocumentLines.add(index, lineToAdd)
        }
    }

    fun removeFirst(predicate: (String) -> Boolean) {
        performActionAtFirst(
            predicate
        ) { newDocumentLines, index ->
            newDocumentLines.removeAt(index)
        }
    }

    fun findAndReplaceIfNotExists(oldValue: String, newValue: String, existStringCheck: String = newValue) {
        if (!documentText.contains(existStringCheck)) {
            findAndReplace(oldValue, newValue)
        }
    }

    fun findAndReplace(oldValue: String, newValue: String) {
        documentText.replace(oldValue, newValue)
    }

    fun removeAllOccurrences(textToRemove: String) {
        findAndReplace(textToRemove, "")
    }

    fun addImport(dep: String) {
        if (!documentText.contains(dep)) {
            addBeforeFirst("import $dep") { line ->
                line.contains("import ")
            }
        }
    }

    fun writeToDisk() {
        val application = ApplicationManager.getApplication()
        application.invokeLater {
            application.runWriteAction {
                document.setText(documentText)
            }
        }
    }

    private fun performActionAtFirst(predicate: (String) -> Boolean, action: (MutableList<String>, Int) -> Unit) {
        val lineIndex = findFirstLineIndex(predicate = predicate)
        val newDocumentLines = documentLines.toMutableList()
        if (lineIndex >= 0) {
            action(newDocumentLines, lineIndex)
            documentLines = newDocumentLines
        }
    }

    private fun findFirstLineIndex(predicate: (String) -> Boolean): Int {
        documentLines.forEachIndexed { index, line ->
            if (predicate(line)) {
                return index
            }
        }
        return -1
    }
}