package com.mohaberabi.inspekt

import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationDelegateProtocol
import platform.UIKit.UIApplicationShortcutItem
import platform.UIKit.UIResponder

class RootAppDelegate(
    private val onItemOpenedApp: (item: UIApplicationShortcutItem) -> Unit = {},
) : UIResponder(), UIApplicationDelegateProtocol {

    override fun application(
        application: UIApplication,
        performActionForShortcutItem: UIApplicationShortcutItem,
        completionHandler: (Boolean) -> Unit
    ) {
        val item = performActionForShortcutItem
        onItemOpenedApp(item)
    }


}
