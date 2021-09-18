package com.panda912.defensor.sample

import android.app.Application
import android.util.Log

/**
 * Created by panda on 2021/9/13 15:52
 */
class SampleApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    Log.i("SampleApplication", packageName)
  }
}