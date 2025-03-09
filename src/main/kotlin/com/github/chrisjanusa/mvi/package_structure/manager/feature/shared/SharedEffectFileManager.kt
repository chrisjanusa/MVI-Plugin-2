package com.github.chrisjanusa.mvi.package_structure.manager.feature.shared

import com.github.chrisjanusa.mvi.package_structure.manager.base.EffectFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.plugin.PluginSliceFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.helper.ISharedFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.helper.SharedFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.shared.helper.SharedFileNameProvider
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureChild
import com.intellij.openapi.vfs.VirtualFile

class SharedEffectFileManager(
    file: VirtualFile,
    sharedFileManager: ISharedFileManager = SharedFileManager(file)
) : EffectFileManager(file), ISharedFileManager by sharedFileManager, FeatureChild {
    override val baseName: String
        get() = featureName
    override val actionName = SharedActionFileManager.getFileName(featureName)
    override val typeAliasPath: String
        get() = generatedPackage?.packagePath ?: ""

    override val fileName: String by lazy {
        getFileName(featureName)
    }
    override val actionEffect: String by lazy {
        getActionName(featureName)
    }
    override val stateEffect: String by lazy {
        getStateName(featureName)
    }
    override val stateSliceEffect: String by lazy {
        getStateSliceName(featureName)
    }
    override val navEffect: String by lazy {
        getNavName(featureName)
    }
    override val state by lazy {
        SharedStateFileManager.getFileName(featureName)
    }
    override val slice by lazy {
        "NoSlice"
    }
    override val hasState = true
    override val hasSlice = false

    override fun getEffectFullName(effectName: String): String =
        "$effectName${SharedFileNameProvider.SHARED_SUFFIX}$SUFFIX"

    override fun addEffectToViewModel(effectName: String) {
        sharedPackage.viewModel?.addEffect(effectName, "${packagePathExcludingFile}.$effectName")
    }

    fun addSliceUpdateEffectToViewModel(effectName: String) {
        sharedPackage.viewModel?.addSliceUpdateEffect(effectName, "${packagePathExcludingFile}.$effectName")
    }

    fun addSliceUpdateEffect(slice: PluginSliceFileManager) {
        addImport("$typeAliasPath.${getSliceUpdateName(featureName)}")
        addImport("${slice.pluginPackage.sliceUpdate?.packagePath}")
        addImport(slice.packagePath)
        rootPackage.foundationPackage?.slice?.packagePathExcludingFile?.let { addImport("$it.NoSlice") }
        val effectName = "Monitor${slice.pluginPackage.sliceUpdate}Effect"
        addToBottom(
            "internal object $effectName : ${getSliceUpdateName(featureName)}() {\n" +
            "    override fun stateUpdateToChildSliceUpdate(state: ${sharedPackage.state}, slice: NoSlice) = ${slice.pluginPackage.sliceUpdate}(\n" +
            "        $slice(\n" +
            "        )\n" +
            "    )\n" +
            "}"
        )
        addSliceUpdateEffectToViewModel(effectName)
        writeToDisk()
    }

    companion object : SharedFileNameProvider(SUFFIX) {
        override fun createInstance(virtualFile: VirtualFile) = SharedEffectFileManager(virtualFile)
        fun getActionName(pluginName: String) = pluginName + SHARED_SUFFIX + ACTION_SUFFIX
        fun getStateName(pluginName: String) = pluginName + SHARED_SUFFIX + STATE_SUFFIX
        fun getStateSliceName(pluginName: String) = pluginName + SHARED_SUFFIX + STATE_SLICE_SUFFIX
        fun getSliceUpdateName(pluginName: String) = pluginName + SHARED_SUFFIX + SLICE_UPDATE_SUFFIX
        fun getNavName(pluginName: String) = pluginName + SHARED_SUFFIX + NAV_SUFFIX
        fun createNewInstance(insertionPackage: SharedPackage): SharedEffectFileManager? {
            val fileName = getFileName(insertionPackage.featureName)
            return insertionPackage.createNewFile(
                fileName,
                SharedEffectTemplate(insertionPackage, fileName)
                    .createContent()
            )?.let { SharedEffectFileManager(it) }
        }
    }
}