package com.github.chrisjanusa.mvi.helper.ui

import com.intellij.ui.CollectionListModel
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.Panel

fun Panel.stringListInputPanel(typeName: String, bindingList: MutableList<String>, fileSuffix: String = "") {
    val stringListModel = CollectionListModel<String>()
    stringListModel.addListDataListener(object : javax.swing.event.ListDataListener {
        override fun intervalAdded(e: javax.swing.event.ListDataEvent?) {
            bindingList.clear()
            bindingList.addAll(stringListModel.items.map { if (fileSuffix.isNotEmpty()) it.substringBefore(fileSuffix) else it})
        }

        override fun intervalRemoved(e: javax.swing.event.ListDataEvent?) {
            bindingList.clear()
            bindingList.addAll(stringListModel.items.map { if (fileSuffix.isNotEmpty()) it.substringBefore(fileSuffix) else it})
        }

        override fun contentsChanged(e: javax.swing.event.ListDataEvent?) {
            bindingList.clear()
            bindingList.addAll(stringListModel.items.map { if (fileSuffix.isNotEmpty()) it.substringBefore(fileSuffix) else it})
        }
    })
    val stringList = JBList(stringListModel)
    lateinit var inputTextField: JBTextField

    row("Add a(n) $typeName") {
        inputTextField = textField().component
        button("Add") {
            val newItem = inputTextField.text + fileSuffix
            if (newItem.isNotEmpty()) {
                stringListModel.add(newItem)
                inputTextField.text = ""
            }
        }
    }
    row("List of $typeName to be created") {
        cell(stringList)
    }
    row {
        button("Remove") {
            val selectedIndex = stringList.selectedIndex
            if (selectedIndex != -1) {
                stringListModel.remove(selectedIndex)
            }
        }
    }
}