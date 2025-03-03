package com.github.chrisjanusa.mvi.package_structure.manager.app.di

import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticChildInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.base.FileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.api.ApiFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.generated.PluginViewModelFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.database.DatabaseClassFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.remote.RemoteDataSourceFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.repository.RepositoryFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.generated.FeatureSharedViewModelFileManager
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild
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

    fun addPluginViewModel(pluginViewModelManager: PluginViewModelFileManager) {
        if (documentText.contains("named(getClassName<$pluginViewModelManager>())")) {
            return
        }
        addAfterFirst(
            lineToAdd = "    viewModel(\n" +
            "        qualifier = named(getClassName<$pluginViewModelManager>())\n" +
            "    ) { parameters ->\n" +
            "        $pluginViewModelManager(onAppAction = parameters.get(), parentViewModel = parameters.getOrNull())\n" +
            "    }.bind<PluginViewModel<*, *>>()\n"
        ) { line ->
            line.contains("viewModelModule")
        }
        addImport(pluginViewModelManager.packagePath)
        pluginViewModelManager.rootPackage.foundationPackage?.pluginViewModel?.packagePath?.let { addImport(it) }
        pluginViewModelManager.rootPackage.commonPackage?.classNameHelper?.packagePath?.let { addImport(it) }
    }

    fun addSharedViewModel(sharedViewModelManager: FeatureSharedViewModelFileManager) {
        if (documentText.contains("named(getClassName<$sharedViewModelManager>())")) {
            return
        }
        addAfterFirst(
            lineToAdd = "    viewModel(\n" +
            "        qualifier = named(getClassName<$sharedViewModelManager>())\n" +
            "    ) { parameters ->\n" +
            "        $sharedViewModelManager(onAppAction = parameters.get(), parentViewModel = parameters.getOrNull())\n" +
            "    }.bind<SharedViewModel<*, *>>()\n"
        ) { line ->
            line.contains("viewModelModule")
        }
        addImport(sharedViewModelManager.packagePath)
        addImport("org.koin.dsl.bind")
        addImport("org.koin.core.qualifier.named")
        sharedViewModelManager.rootPackage.foundationPackage?.sharedViewModel?.packagePath?.let { addImport(it) }
        sharedViewModelManager.rootPackage.commonPackage?.classNameHelper?.packagePath?.let { addImport(it) }
    }

    fun addRepository(repositoryManger: RepositoryFileManager, apiFileManager: ApiFileManager) {
        if (documentText.contains("singleOf(::${repositoryManger})")) {
            return
        }
        val moduleName = "repositoryModule"
        val repositoryDefinition = "singleOf(::${repositoryManger}).bind<I${apiFileManager}>()\n"
        addImport(repositoryManger.packagePath)
        addImport(apiFileManager.packagePath)
        addImport("org.koin.core.module.dsl.singleOf")

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

    fun addDatabase(databaseManger: DatabaseClassFileManager) {
        if (documentText.contains("get<${databaseManger}>")) {
            return
        }
        val moduleName = "databaseModule"
        val databaseDefinition =
        "    single {\n" +
        "        Room.databaseBuilder(\n" +
        "            androidApplication(),\n" +
        "            ${databaseManger}::class.java,\n" +
        "            ${databaseManger}.DB_NAME\n" +
        "        ).build()\n" +
        "    }\n" +
        "    single { get<${databaseManger}>().${databaseManger.daoInstance} }\n"
        addImport(databaseManger.packagePath)
        addImport("org.koin.android.ext.koin.androidApplication")
        addImport("androidx.room.Room")

        val moduleExists = addAfterFirst(
            lineToAdd = databaseDefinition
        ) { line ->
            line.contains(moduleName)
        }
        if (!moduleExists) {
            addToBottom(
                text =
                "internal val $moduleName = module {\n" +
                databaseDefinition +
                "}"
            )
            koinInit?.addModule(moduleName)
        }
    }

    fun addRemoteDataSource(remoteDataSourceFileManager: RemoteDataSourceFileManager) {
        if (documentText.contains("singleOf(::${remoteDataSourceFileManager})")) {
            return
        }
        val moduleName = "remoteModule"
        val remoteDefinition =
            "    singleOf(::${remoteDataSourceFileManager})\n"
        addImport(remoteDataSourceFileManager.packagePath)
        addImport("org.koin.core.module.dsl.singleOf")

        val moduleExists = addAfterFirst(
            lineToAdd = remoteDefinition
        ) { line ->
            line.contains(moduleName)
        }
        if (!moduleExists) {
            addToBottom(
                text =
                "internal val $moduleName = module {\n" +
                "    single {\n" +
                "        HttpClientFactory.create(get())\n" +
                "    }\n" +
                "    single { OkHttp.create() }\n" +
                remoteDefinition +
                "}"
            )
            remoteDataSourceFileManager.rootPackage.commonPackage?.servicePackage?.remotePackage?.httpClientFactory?.packagePath?.let {
                addImport(it)
            }
            addImport("io.ktor.client.engine.okhttp.OkHttp")
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