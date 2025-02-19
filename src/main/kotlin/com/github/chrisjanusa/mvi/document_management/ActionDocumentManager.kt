package com.github.chrisjanusa.mvi.document_management

import com.intellij.openapi.editor.Document

class ActionDocumentManager(actionDocument: Document) : DocumentManager(actionDocument) {
    fun getAllRegularActions(): List<String> {
        return getAllActionsIfLineIsValid { line -> !line.contains("NavAction") }
    }

    fun getAllNavActions(): List<String> {
        return getAllActionsIfLineIsValid { line -> line.contains("NavAction") }
    }

    private fun getAllActionsIfLineIsValid(lineValidator: (String) -> Boolean): List<String> {
        val actionNames = mutableListOf<String>()
        documentLines.forEach { line ->
            if (line.startsWith("    data") && lineValidator(line)) {
                val words = line.split(" ")
                //Skip "Data" and "class"/"object"
                actionNames.add(words[2].substringBefore(":").substringBefore("("))
            }
        }
        return actionNames
    }
}