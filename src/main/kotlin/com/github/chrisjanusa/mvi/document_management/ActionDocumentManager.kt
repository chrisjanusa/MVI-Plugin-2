package com.github.chrisjanusa.mvi.document_management

import com.github.chrisjanusa.mvi.foundation.addIf
import com.intellij.openapi.editor.Document

class ActionDocumentManager(actionDocument: Document) : DocumentManager(actionDocument) {
    fun getAllRegularActions(): List<String> {
        return getAllActionsIfLineIsValid { line -> !line.contains("NavAction") }
    }

    fun getAllNavActions(): List<String> {
        return getAllActionsIfLineIsValid { line -> line.contains("NavAction") }
    }

    fun addAction(actionName: String, isReducible: Boolean, hasParameters: Boolean) {
        val sealedClassLine = getFirstLine { line ->
            line.contains("sealed") && line.contains("ReducibleAction")
        } ?: return
        val stateName = sealedClassLine.substringAfter("<").substringBefore(",")
        val sliceName = sealedClassLine.substringBefore(">").substringAfter(", ")
        val actionClassName = sealedClassLine.substringAfter("class ").substringBefore(":").substringBefore(" ")
        val type = if (hasParameters) "class" else "object"
        val parameters = if (hasParameters) "()" else ""
        val lineToAdd = "    data $type $actionName$parameters: $actionClassName()" + " {".addIf { isReducible } + "\n" +
                        "        override fun reduce(state: $stateName, slice: $sliceName) = state.copy(\n".addIf { isReducible } +
                        "        )\n".addIf { isReducible } +
                        "    }\n".addIf { isReducible }
        addAfterFirst(lineToAdd) { line ->
            line.contains("sealed") && line.contains("ReducibleAction")
        }
    }

    fun addNavAction(actionName: String) {
        val navActionName = addNavActionTypeIfNotPresent() ?: return
        val lineToAdd = "    data object $actionName : $navActionName()\n"
        addAfterFirst(lineToAdd) { line ->
            line.contains("sealed") && line.contains("NavAction")
        }
    }

    private fun addNavActionTypeIfNotPresent(): String? {
        val existingClassLine = getFirstLine { line ->
            line.contains("sealed") && line.contains("NavAction")
        }
        if (existingClassLine == null) {
            val sealedClassLine = getFirstLine { line ->
                line.contains("sealed") && line.contains("ReducibleAction")
            } ?: return null
            val actionClassName = sealedClassLine.substringAfter("class ").substringBefore(":").substringBefore(" ")
            val navActionName = actionClassName.replace("Action", "NavAction")
            val reducibleActionImport = getFirstLine { line ->
                line.contains("import") && line.contains("ReducibleAction")
            } ?: return null
            val navActionDep = reducibleActionImport.replace("ReducibleAction", "NavAction").substringAfter("import ")
            addImport(navActionDep)
            addToBottom(
                text = "sealed class $navActionName : NavAction {\n" +
                       "}\n"
            )
            return navActionName
        } else {
            return existingClassLine.substringAfter("class ").substringBefore(":").substringBefore(" ")
        }
    }

    private fun getAllActionsIfLineIsValid(lineValidator: (String) -> Boolean): List<String> {
        val actionNames = mutableListOf<String>()
        documentLines.forEach { line ->
            if (line.startsWith("    data") && lineValidator(line)) {
                val words = line.substringAfter("    ").split(" ")
                //Skip "data" and "class"/"object"
                actionNames.add(words[2].substringBefore(":").substringBefore("("))
            }
        }
        return actionNames
    }
}