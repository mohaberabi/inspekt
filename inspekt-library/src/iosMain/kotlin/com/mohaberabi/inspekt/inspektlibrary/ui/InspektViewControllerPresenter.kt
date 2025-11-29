package com.mohaberabi.inspekt.inspektlibrary.ui

import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIApplication
import platform.UIKit.UISceneActivationStateForegroundActive
import platform.UIKit.UIScreen
import platform.UIKit.UIViewController
import platform.UIKit.UIWindow
import platform.UIKit.UIWindowLevelAlert
import platform.UIKit.UIWindowScene

internal object InspektViewControllerPresenter {
    private var inspektWindow: UIWindow? = null


    @OptIn(ExperimentalForeignApi::class)
    fun show(
        targetViewController: UIViewController,
    ) {
        if (inspektWindow != null) return

        val app = UIApplication.sharedApplication()

        val windowScene = app.connectedScenes
            .firstOrNull {
                (it as? UIWindowScene)?.activationState == UISceneActivationStateForegroundActive
            } as? UIWindowScene

        val window = if (windowScene != null) {
            UIWindow(windowScene = windowScene)
        } else {
            UIWindow(frame = UIScreen.mainScreen.bounds)
        }

        window.rootViewController = targetViewController
        window.windowLevel = UIWindowLevelAlert + 1.0
        window.makeKeyAndVisible()
        inspektWindow = window
    }

    fun dismiss() {
        inspektWindow?.hidden = true
        inspektWindow = null
    }
}