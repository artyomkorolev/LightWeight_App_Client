package com.example.lightweight.AuthReg

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle

class AppLifecycleTracker : Application.ActivityLifecycleCallbacks {

    private var activityReferences = 0
    private var isActivityChangingConfigurations = false

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {
        if (++activityReferences == 1 && !isActivityChangingConfigurations) {
            // App enters foreground
        }
    }

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {
        isActivityChangingConfigurations = activity.isChangingConfigurations
        if (--activityReferences == 0 && !isActivityChangingConfigurations) {
            // App enters background
            clearGuestPreferences(activity)
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}

    private fun clearGuestPreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences("guestEatings", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
        val sharedPreferences1 = context.getSharedPreferences("guestTrainings", Context.MODE_PRIVATE)
        sharedPreferences1.edit().clear().apply()
        val sharedPreferences2 = context.getSharedPreferences("guestPhoto",Context.MODE_PRIVATE)
        sharedPreferences2.edit().clear().apply()
    }
}