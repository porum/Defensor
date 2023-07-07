package com.panda912.defensor.sample

import android.app.Activity
import android.os.Bundle
import android.widget.Toast

class MainActivity : Activity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    Toast.makeText(this.applicationContext, "Hello defensor!", Toast.LENGTH_SHORT).show()
  }
}