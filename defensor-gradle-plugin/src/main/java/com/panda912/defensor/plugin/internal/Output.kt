package com.panda912.defensor.plugin.internal

/**
 * Created by panda on 2021/9/14 16:16
 */
data class Output(
  val className: String,
  val bytes: ByteArray
) {

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Output

    if (!bytes.contentEquals(other.bytes)) return false

    return true
  }

  override fun hashCode(): Int {
    return bytes.contentHashCode()
  }
}