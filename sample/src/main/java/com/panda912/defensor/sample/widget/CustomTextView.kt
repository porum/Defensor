package com.panda912.defensor.sample.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class CustomTextView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = android.R.attr.textViewStyle
) : TextView(context, attrs, defStyleAttr) {

}