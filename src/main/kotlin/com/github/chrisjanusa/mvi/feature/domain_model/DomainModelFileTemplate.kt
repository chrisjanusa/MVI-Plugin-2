package com.github.chrisjanusa.mvi.feature.domain_model

import com.github.chrisjanusa.mvi.foundation.FileTemplate

class DomainModelFileTemplate(private val domainModelName: String): FileTemplate(domainModelName) {
    override fun createContent(rootPackage: String): String =
                "data class $domainModelName(\n" +
                        "    \n" +
                        ")\n"
}