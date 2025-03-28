package com.github.chrisjanusa.mvi.package_structure.manager.base

import com.github.chrisjanusa.mvi.helper.file_helper.pathToPackage
import com.github.chrisjanusa.mvi.package_structure.Manager
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.vfs.VirtualFile

open class FileManager(
    val file: VirtualFile,
): Manager {
    val packagePath by lazy {
        file.path.pathToPackage()
    }
    val packagePathExcludingFile  by lazy {
        file.path.pathToPackage().substringBeforeLast(".")
    }
    val name by lazy {
        file.nameWithoutExtension
    }
    val document by lazy {
        file.let { FileDocumentManager.getInstance().getDocument(it) }
    }

    internal var documentText = document?.text ?: ""
    internal var documentLines : List<String>
        get() = documentText.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toMutableList()
        set(lines) {
            documentText = lines.joinToString(separator = "\n").dropLastWhile { it == '\n' }
        }

    fun addToBottom(text: String) {
        documentLines = documentLines + listOf("",text)
    }

    fun addAfterFirst(lineToAdd: String, predicate: (String) -> Boolean) : Boolean {
        return performActionAtFirst(
            predicate
        ) { newDocumentLines, index ->
                newDocumentLines.add(index + 1, lineToAdd)
        }
    }

    fun addBeforeFirst(lineToAdd: String, predicate: (String) -> Boolean) : Boolean {
        return performActionAtFirst(
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

    fun removeBetween(startPredicate: (String) -> Boolean, endPredicate: (String) -> Boolean) {
        val newLines = documentLines.toMutableList()
        var startIndex: Int? = null
        documentLines.forEachIndexed { index, line  ->
             if (startPredicate(line)) {
                 startIndex = index
             }
            startIndex?.let { newLines.removeAt(it) }
            if (endPredicate(line)) {
                return@forEachIndexed
            }
        }
        documentLines = newLines
    }

    fun findAndReplaceIfNotExists(oldValue: String, newValue: String, existStringCheck: String = newValue) {
        if (!documentText.contains(existStringCheck)) {
            findAndReplace(oldValue, newValue)
        }
    }

    fun findAndReplace(oldValue: String, newValue: String) {
        documentText = documentText.replace(oldValue, newValue)
    }

    fun findNotFollowedByLetterAndReplace(
        oldValue: String,
        newValue: String
    ) {
        val regex = Regex("(${Regex.escape(oldValue)})(?![a-zA-Z])")
        documentText =  regex.replace(documentText, newValue)
    }

    fun removeAllOccurrences(textToRemove: String) {
        findAndReplace(textToRemove, "")
    }

    fun removeAllLinesThatMatch(predicate: (String) -> Boolean) {
        documentLines = documentLines.filter { line ->
            !predicate(line)
        }
    }

    fun addImport(dep: String) {
        if (!documentText.contains("$dep\n")) {
            val addedBefore = addBeforeFirst("import $dep") { line ->
                line.contains("import ")
            }
            if (!addedBefore) {
                addAfterFirst("\nimport $dep") {line ->
                    line.contains("package ")
                }
            }
        }
    }

    fun removeImport(dep: String) {
        removeAllOccurrences("import $dep\n")
    }

    fun writeToDisk() {
        val application = ApplicationManager.getApplication()
        application.invokeLater {
            application.runWriteAction {
                document?.setText(documentText)
            }
        }
    }

    fun getFirstLine(predicate: (String) -> Boolean): String? {
        val lineNum = findFirstLineIndex(predicate)
        return documentLines.getOrNull(lineNum)
    }

    fun performActionAtFirst(predicate: (String) -> Boolean, action: (MutableList<String>, Int) -> Unit) : Boolean {
        val lineIndex = findFirstLineIndex(predicate = predicate)
        val newDocumentLines = documentLines.toMutableList()
        if (lineIndex >= 0) {
            action(newDocumentLines, lineIndex)
            documentLines = newDocumentLines
            return true
        }
        return false
    }

    private fun findFirstLineIndex(predicate: (String) -> Boolean): Int {
        documentLines.forEachIndexed { index, line ->
            if (predicate(line)) {
                return index
            }
        }
        return -1
    }

    override fun toString(): String {
        return name
    }
}