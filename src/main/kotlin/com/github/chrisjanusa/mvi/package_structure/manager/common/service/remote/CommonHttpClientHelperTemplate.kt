package com.github.chrisjanusa.mvi.package_structure.manager.common.service.remote

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.manager.base.Template

internal class CommonHttpClientHelperTemplate(
    packageManager: Manager,
    fileName: String,
) : Template(
    packageManager = packageManager,
    fileName = fileName
) {
    override fun createContent(): String =
        "import ${rootPackage?.commonPackage?.servicePackage?.packagePath}.data.DataError\n" +
        "import ${rootPackage?.commonPackage?.servicePackage?.packagePath}.data.Result\n" +
        "import io.ktor.client.call.NoTransformationFoundException\n" +
        "import io.ktor.client.call.body\n" +
        "import io.ktor.client.statement.HttpResponse\n" +
        "import io.ktor.client.network.sockets.SocketTimeoutException\n" +
        "import io.ktor.util.network.UnresolvedAddressException\n" +
        "import kotlinx.coroutines.ensureActive\n" +
        "import kotlin.coroutines.coroutineContext\n" +
        "\n" +
        "suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): Result<T, DataError.Remote> {\n" +
        "    val response = try {\n" +
        "        execute()\n" +
        "    } catch (e: SocketTimeoutException) {\n" +
        "        return Result.Error(DataError.Remote.REQUEST_TIME_OUT)\n" +
        "    } catch (e: UnresolvedAddressException) {\n" +
        "        return Result.Error(DataError.Remote.NO_INTERNET)\n" +
        "    } catch (e: Exception) {\n" +
        "        coroutineContext.ensureActive()\n" +
        "        return Result.Error(DataError.Remote.UNKNOWN)\n" +
        "    }\n" +
        "    return responseToResult(response)\n" +
        "}\n" +
        "\n" +
        "suspend inline fun <reified T> responseToResult(\n" +
        "    response: HttpResponse\n" +
        "): Result<T, DataError.Remote> {\n" +
        "    return when(response.status.value) {\n" +
        "        in 200..299 -> {\n" +
        "            try {\n" +
        "                Result.Success(response.body<T>())\n" +
        "            } catch (e: NoTransformationFoundException) {\n" +
        "                Result.Error(DataError.Remote.SERIALIZATION)\n" +
        "            }\n" +
        "        }\n" +
        "        408 -> Result.Error(DataError.Remote.REQUEST_TIME_OUT)\n" +
        "        429 -> Result.Error(DataError.Remote.TOO_MANY_REQUESTS)\n" +
        "        in 500..599 -> Result.Error(DataError.Remote.SERVER_ERROR)\n" +
        "        else -> Result.Error(DataError.Remote.UNKNOWN)\n" +
        "    }\n" +
        "}\n"
}