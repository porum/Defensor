package com.panda912.defensor.sample

import android.app.Application
import android.util.Log
import com.panda912.defensor.CrashDefensor

/**
 * Created by panda on 2021/9/13 15:52
 */
class SampleApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    CrashDefensor.init(
      CrashDefensor.Config()
        .setApplicationId(BuildConfig.APPLICATION_ID)
        .setEnableThrow(false)
    )
    CrashDefensor.setCrashCaughtListener { code, msg, th ->
      Log.i("CrashDefensor", "[$code] $msg $th")
    }
  }
}