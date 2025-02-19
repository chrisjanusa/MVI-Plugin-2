package com.github.chrisjanusa.mvi.document_management

import com.intellij.openapi.editor.Document

class ViewModelDocumentManager(document: Document) : DocumentManager(document) {
    fun addEffect(effectName: String) {
        addAfterFirst("${effectName}Effect,") { line ->
            line.contains("val effectList")
        }
    }

    fun removeEffect(effectName: String) {
        removeFirst { line ->
            line.contains(effectName)
        }
    }
}