package com.github.chrisjanusa.mvi.package_structure.manager.app.di

import com.github.chrisjanusa.mvi.helper.file_helper.findChildFile
import com.github.chrisjanusa.mvi.helper.file_helper.getAppPackageFile
import com.github.chrisjanusa.mvi.helper.file_helper.toCamelCase
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.vfs.VirtualFile

class KoinModuleFileManager(file: VirtualFile) : FileManager(file), RootChild {
    val diModule by lazy {
        AppDiPackage(file.parent)
    }
    override val rootPackage by lazy {
        diModule.rootPackage
    }
    val koinInit by lazy {
        diModule.koinInit
    }

    fun addPluginViewModel(pluginName: String) {
        addAfterFirst(
            lineToAdd = "    viewModel(\n" +
            "        qualifier = named(getClassName<${pluginName}ViewModel>())\n" +
            "    ) { parameters ->\n" +
            "        ${pluginName}ViewModel(onAppAction = parameters.get(), parentViewModel = parameters.getOrNull())\n" +
            "    }.bind<PluginViewModel<*, *>>()\n"
        ) { line ->
            line.contains("viewModelModule")
        }
    }

    fun addSharedViewModel(featureName: String) {
        addAfterFirst(
            lineToAdd = "    viewModel(\n" +
            "        qualifier = named(getClassName<${featureName}ViewModel>())\n" +
            "    ) { parameters ->\n" +
            "        ${featureName}ViewModel(onAppAction = parameters.get(), parentViewModel = parameters.getOrNull())\n" +
            "    }.bind<SharedViewModel<*, *>>()\n"
        ) { line ->
            line.contains("viewModelModule")
        }
    }

    fun addDatabase(databaseName: String) {
        val moduleName = "databaseModule"

        val databaseDefinition =
            "    single {\n" +
            "        Room.databaseBuilder(\n" +
            "            androidApplication(),\n" +
            "            ${databaseName}Database::class.java,\n" +
            "            ${databaseName}Database.DB_NAME\n" +
            "        ).build()\n" +
            "    }\n" +
            "    single { get<${databaseName}Database>().${databaseName.toCamelCase()}Dao }\n"
        val inserted = addAfterFirst(
            lineToAdd = databaseDefinition
        ) { line ->
            line.contains(moduleName)
        }
        if (!inserted) {
            addToBottom(
                text = "internal val $moduleName = module {\n" +
                databaseDefinition +
                "}"
            )
            koinInit?.addModule("databaseModule")
        }
    }

    fun addRemote(dataSourceName: String) {
        val moduleName = "remoteModule"
        val dataSourceDefinition = "singleOf(::RemoteBookDataSource)\n"

        val okHttpFactoryCreated = addAfterFirst(
            lineToAdd = dataSourceDefinition
        ) { line ->
            line.contains("single { OkHttp.create() }")
        }
        if (!okHttpFactoryCreated) {
            val okHttpDefinition =
                "    single {\n" +
                "        HttpClientFactory.create(get())\n" +
                "    }\n" +
                "    single { OkHttp.create() }"
            val remoteModuleExists = addAfterFirst(
                lineToAdd = okHttpDefinition
            ) { line ->
                line.contains(moduleName)
            }
            if (!remoteModuleExists) {
                addToBottom(
                    text =
                    "internal val $moduleName = module {\n" +
                    okHttpDefinition +
                    "\n}"
                )
                koinInit?.addModule(moduleName)
            }
            addAfterFirst(
                lineToAdd = dataSourceDefinition
            ) { line ->
                line.contains("single { OkHttp.create() }")
            }
        }
    }

    fun addRepository(repositoryName: String) {
        val moduleName = "repositoryModule"
        val repositoryDefinition = "singleOf(::${repositoryName}Repository).bind<I${repositoryName}Repository>()\n"

        val moduleExists = addAfterFirst(
            lineToAdd = repositoryDefinition
        ) { line ->
            line.contains(moduleName)
        }
        if (!moduleExists) {
            addToBottom(
                text =
                "internal val $moduleName = module {\n" +
                repositoryDefinition +
                "}"
            )
            koinInit?.addModule(moduleName)
        }
    }

    companion object : StaticChildInstanceCompanion("KoinModule", AppDiPackage) {
        override fun createInstance(virtualFile: VirtualFile) = KoinModuleFileManager(virtualFile)
        fun createNewInstance(insertionPackage: AppDiPackage): KoinModuleFileManager? {
            return insertionPackage.createNewFile(
                NAME,
                KoinModuleTemplate(insertionPackage, NAME)
                    .createContent()
            )?.let { KoinModuleFileManager(it) }
        }
    }
}

private fun AnActionEvent.getKoinModule(): VirtualFile? {
    val projectBaseDir = getAppPackageFile() ?: return null
    val diDirFile = projectBaseDir.findChild("di") ?: return null
    return diDirFile.findChildFile("KoinModule")
}

fun AnActionEvent.getKoinModuleManager(): KoinModuleFileManager? {
    val koinModule = getKoinModule() ?: return null
    return KoinModuleFileManager(koinModule)
}