package com.github.chrisjanusa.mvi.package_structure.manager

import com.github.chrisjanusa.mvi.package_structure.Manager
import com.github.chrisjanusa.mvi.package_structure.instance_companion.InstanceCompanion
import com.github.chrisjanusa.mvi.package_structure.manager.app.AppActivityFileManager
import com.github.chrisjanusa.mvi.package_structure.parent_provider.RootChild

object ManagerProvider {
    val pathToManagerMap = hashMapOf<InstanceCompanion, HashMap<String, Manager>>()

    fun setAppActivity(appActivity: AppActivityFileManager) {
        pathToManagerMap[AppActivityFileManager] = hashMapOf(appActivity.packagePath to appActivity)
    }

    fun getAppActivity(rootPackageChild: RootChild): AppActivityFileManager? {
        val storedActivityMap = pathToManagerMap[AppActivityFileManager]
        val storedActivity = storedActivityMap?.values?.firstOrNull() as? AppActivityFileManager
        if (storedActivity == null) {
            val newActivity = rootPackageChild.rootPackage.appPackage?.activity
            newActivity?.let { setAppActivity(it) }
            return newActivity
        } else {
            return storedActivity
        }
    }
}