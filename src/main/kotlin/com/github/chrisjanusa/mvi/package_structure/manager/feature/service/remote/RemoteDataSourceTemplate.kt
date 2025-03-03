package com.github.chrisjanusa.mvi.package_structure.manager.feature.service.remote

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class RemoteDataSourceTemplate(
    packageManager: Manager,
    fileName: String,
    private val dataSourceName: String,
    private val baseUrl: String,
    private val endpoint: String,
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {

    override fun createContent(): String {
        return "import ${rootPackage?.commonPackage?.servicePackage?.dataPackage?.dataError?.packagePath}\n" +
        "import ${rootPackage?.commonPackage?.servicePackage?.packagePath}.data.Result\n" +
        "import ${rootPackage?.commonPackage?.servicePackage?.packagePath}.remote.safeCall\n" +
        "import io.ktor.client.HttpClient\n" +
        "import io.ktor.client.request.get\n" +
        "import io.ktor.client.request.parameter\n" +
        "\n" +
        "class $fileName(\n" +
        "    private val httpClient: HttpClient\n" +
        ") {\n" +
        "    suspend fun WHAT_IS_THIS_DATA_SOURCE_DOING() : Result<${dataSourceName}ResponseDTO, DataError.Remote> {\n" +
        "        return safeCall {\n" +
        "            httpClient.get(\n" +
        "                urlString = \"\${BASE_URL}$endpoint\"\n" +
        "            ) {\n" +
        "                // example query parameter: parameter(\"q\", query)\n" +
        "            }\n" +
        "        }\n" +
        "    }\n" +
        "\n" +
        "    companion object {\n" +
        "        const val BASE_URL = \"$baseUrl\"\n" +
        "    }\n" +
        "}\n"
    }
}