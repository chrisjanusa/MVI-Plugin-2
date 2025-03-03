package com.github.chrisjanusa.mvi.package_structure.manager.common.service.data

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class CommonServiceResultTemplate(
    packageManager: Manager,
    fileName: String,
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {
    override fun createContent(): String =
        "sealed interface Result<out D, out E: Error> {\n" +
        "    data class Success<out D>(val data: D): Result<D, Nothing>\n" +
        "    data class Error<out E: com.example.bookpediaandroid.common.service.data.Error>(val error: E):\n" +
        "        Result<Nothing, E>\n" +
        "}\n" +
        "\n" +
        "inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {\n" +
        "    return when(this) {\n" +
        "        is Result.Error -> Result.Error(error)\n" +
        "        is Result.Success -> Result.Success(map(data))\n" +
        "    }\n" +
        "}\n" +
        "\n" +
        "inline fun <T, E: Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {\n" +
        "    return when(this) {\n" +
        "        is Result.Error -> this\n" +
        "        is Result.Success -> {\n" +
        "            action(data)\n" +
        "            this\n" +
        "        }\n" +
        "    }\n" +
        "}\n" +
        "inline fun <T, E: Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {\n" +
        "    return when(this) {\n" +
        "        is Result.Error -> {\n" +
        "            action(error)\n" +
        "            this\n" +
        "        }\n" +
        "        is Result.Success -> this\n" +
        "    }\n" +
        "}\n" +
        "\n" +
        "typealias EmptyResult<E> = Result<Unit, E>\n"
}