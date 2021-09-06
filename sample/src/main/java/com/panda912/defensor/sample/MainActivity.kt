package com.panda912.defensor.sample

import android.Manifest
import android.app.Dialog
import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.panda912.defensor.sample.widget.CustomTextView
import org.json.JSONArray
import org.json.JSONObject

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

  private val receiver = Receiver()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val context: Context = this
    context.bindService(Intent(), object : ServiceConnection {
      override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
      }

      override fun onServiceDisconnected(name: ComponentName?) {
      }
    }, Context.BIND_IMPORTANT)


    val jsonObject = JSONObject("null")
    val jsonArray = JSONArray("")


    val textView1 = findViewById<CustomTextView>(R.id.tv)
    textView1.text = "haha"
    textView1.visibility = View.INVISIBLE

    val textView2 = findViewById<TextView>(R.id.tv2)
    textView2.text = "haha"
    textView2.visibility = View.INVISIBLE

    val textView3 = findViewById<AppCompatTextView>(R.id.tv3)
    textView3.text = "haha"
    textView3.setText(R.string.app_name)
    textView3.setText(R.string.app_name, TextView.BufferType.SPANNABLE)
    textView3.setText(charArrayOf('p', 'a', 'n', 'd', 'a'), 0, 2)
    textView3.visibility = View.INVISIBLE
    textView3.setHint(R.string.app_name)
    textView3.hint = "hint"

    filesDir.mkdir()
    filesDir.mkdirs()

    val webView = WebView(this)
    webView.loadUrl("")

    View.inflate(this, R.layout.activity_main, null)

    val ids = intent.getIntArrayExtra("ids")
    val strArr = intent.getStringArrayExtra("strArr")
    val longArr = intent.getLongArrayExtra("longArr")
    val floatArr = intent.getFloatArrayExtra("floatArr")
    val doubleArr = intent.getDoubleArrayExtra("doubleArr")
    val shortArr = intent.getShortArrayExtra("shortArr")
    val byteArr = intent.getByteArrayExtra("byteArr")
    val boolArr = intent.getBooleanArrayExtra("boolArr")

    Log.i(TAG, "${ids[2]}")
    Log.i(TAG, strArr[0])
    Log.i(TAG, "${longArr[0]}")
    Log.i(TAG, "${floatArr[0]}")
    Log.i(TAG, "${doubleArr[0]}")
    Log.i(TAG, "${shortArr[0]}")
    Log.i(TAG, "${byteArr[0]}")


    val intList = intent.getIntegerArrayListExtra("intList")
    intList.add(2)
    intList.remove(1)
    intList.removeAt(2)
    intList.get(3)
    intList.size


    "".equals(ids)
    "ahahah".substring(2)
    "adaddadada".subSequence(2, 1)
    " daada ".trim()
    "dadad".toCharArray()

    registerReceiver(receiver, IntentFilter().apply {
      addAction(Intent.ACTION_BATTERY_CHANGED)
    })
    sendBroadcast(Intent())

    val ret = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val result = checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)

  }

  override fun onStop() {
    super.onStop()
    unregisterReceiver(receiver)
  }

  class InnerDialog(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
    }

    override fun dismiss() {
      if (BuildConfig.DEBUG) {
        super.dismiss()
      } else {
        dismissInternal()
      }
    }

    private fun dismissInternal() {
      Log.i(TAG, "dismissInternal")
    }
  }
}