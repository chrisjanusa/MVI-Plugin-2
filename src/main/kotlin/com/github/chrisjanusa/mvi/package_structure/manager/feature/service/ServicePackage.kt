package com.github.chrisjanusa.mvi.package_structure.manager.feature.service

import com.github.chrisjanusa.mvi.helper.file_helper.toSnakeCase
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.instance_companion.StaticInstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.PackageManager
import com.github.chrisjanusa.mvi.package_structure.manager.base.ModelFileManager
import com.github.chrisjanusa.mvi.package_structure.manager.feature.FeaturePackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.database.DatabasePackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.database.DatabaseWrapperPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.mapper.FeatureMapperPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.remote.FeatureRemotePackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.remote.FeatureRemoteWrapperPackage
import com.github.chrisjanusa.mvi.package_structure.manager.feature.service.repository.FeatureRepositoryPackage
import com.github.chrisjanusa.mvi.package_structure.parent_provider.FeatureDirectChild
import com.intellij.openapi.vfs.VirtualFile

class ServicePackage(file: VirtualFile): PackageManager(file), FeatureDirectChild {

    val repositoryPackage by lazy {
        file.findChild(FeatureRepositoryPackage.NAME)?.let { FeatureRepositoryPackage(it) }
    }
    val repositories by lazy {
        repositoryPackage?.repositories ?: emptyList()
    }

    override val featurePackage by lazy {
        FeaturePackage(file.parent)
    }
    val featureName by lazy {
        featurePackage.featureName
    }
    override val rootPackage by lazy {
        featurePackage.rootPackage
    }
    fun createRepositoryPackage() = FeatureRepositoryPackage.createNewInstance(this)

    val databaseWrapperPackage by lazy {
        file.findChild(DatabaseWrapperPackage.NAME)?.let { DatabaseWrapperPackage(it) }
    }
    val databases by lazy {
        databaseWrapperPackage?.databases
    }
    val allEntities by lazy {
        databaseWrapperPackage?.allEntities
    }
    fun createDatabase(databaseName: String, entityNames: List<String>): DatabasePackage? {
        val databaseWrapper = DatabaseWrapperPackage.createNewInstance(
            insertionPackage = this,
        )
        return databaseWrapper?.createDatabase(databaseName.toSnakeCase(), entityNames)
    }

    val remoteWrapperPackage by lazy {
        file.findChild(FeatureRemoteWrapperPackage.NAME)?.let { FeatureRemoteWrapperPackage(it) }
    }
    val remoteDataSources by lazy {
        remoteWrapperPackage?.dataSources
    }
    val allDTOs by lazy {
        remoteWrapperPackage?.allDTOs
    }
    fun createRemoteDataSource(dataSourceName: String, baseUrl: String, endpoint: String): FeatureRemotePackage? {
        val remoteWrapperPackage = FeatureRemoteWrapperPackage.createNewInstance(
            insertionPackage = this,
        )
        return remoteWrapperPackage?.createDataSource(dataSourceName, baseUrl, endpoint)
    }

    val mapperPackage by lazy {
        file.findChild(FeatureMapperPackage.NAME)?.let { FeatureMapperPackage(it) }
    }
    val mappers by lazy {
        mapperPackage?.mappers
    }
    fun addMapper(from: ModelFileManager, to: ModelFileManager) {
        val mapperPackage = FeatureMapperPackage.createNewInstance(this)
        mapperPackage?.addMapper(from, to)
    }

    companion object : StaticInstanceCompanion("service") {
        override fun createInstance(virtualFile: VirtualFile) = ServicePackage(virtualFile)

        override val allChildrenInstanceCompanions: List<InstanceCompanion>
            get() = listOf(DatabaseWrapperPackage, FeatureRepositoryPackage, FeatureRemoteWrapperPackage, FeatureMapperPackage)

        fun createNewInstance(insertionPackage: FeaturePackage): ServicePackage? {
            return insertionPackage.createNewDirectory(NAME)?.let { ServicePackage(it) }
        }
    }
}