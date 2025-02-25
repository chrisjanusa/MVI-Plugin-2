package com.github.chrisjanusa.mvi.package_structure.manager.base

import com.intellij.openapi.vfs.VirtualFile

open class ActionFileManager(actionFile: VirtualFile) : FileManager(actionFile) {
    fun getAllRegularActions(): List<String> {
        return getAllActionsIfLineIsValid { line -> !line.contains(NAV_SUFFIX) }
    }

    fun getAllNavActions(): List<String> {
        return getAllActionsIfLineIsValid { line -> line.contains(NAV_SUFFIX) }
    }

    fun addAction(actionName: String, isReducible: Boolean, hasParameters: Boolean) {
        val sealedClassLine = getFirstLine { line ->
            line.isReducibleActionDefinition()
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
            line.isReducibleActionDefinition()
        }
        writeToDisk()
    }

    fun addNavAction(actionName: String) {
        val navActionName = addNavActionTypeIfNotPresent() ?: return
        val lineToAdd = "    data object $actionName : $navActionName()\n"
        addAfterFirst(lineToAdd) { line ->
            line.isNavActionDefinition()
        }
        writeToDisk()
    }

    private fun addNavActionTypeIfNotPresent(): String? {
        val existingClassLine = getFirstLine { line ->
            line.isNavActionDefinition()
        }
        if (existingClassLine == null) {
            val sealedClassLine = getFirstLine { line ->
                line.isReducibleActionDefinition()
            } ?: return null
            val actionClassName = sealedClassLine.substringAfter("class ").substringBefore(":").substringBefore(" ")
            val navActionName = actionClassName.replace(SUFFIX, NAV_SUFFIX)
            val reducibleActionImport = getFirstLine { line ->
                line.isReducibleActionDefinition()
            } ?: return null
            val navActionDep = reducibleActionImport.replace(REDUCIBLE_SUFFIX, NAV_SUFFIX).substringAfter("import ")
            addImport(navActionDep)
            addToBottom(
                text = "sealed class $navActionName : $NAV_SUFFIX {\n" +
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

    private fun String.isNavActionDefinition() = contains("sealed") && contains(NAV_SUFFIX)
    private fun String.isReducibleActionDefinition() = contains("sealed") && contains(REDUCIBLE_SUFFIX)

    companion object {
        fun getFileName(baseName: String, isShared: Boolean = false) = baseName + SHARED_SUFFIX.addIf { isShared } + SUFFIX

        const val SUFFIX = "Action"
        const val NAV_SUFFIX = "NavAction"
        const val REDUCIBLE_SUFFIX = "ReducibleAction"
        private const val SHARED_SUFFIX = "Shared"
    }
}