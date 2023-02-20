package org.rmj.guanzongroup.documentscanner.utils

import android.util.Log
import androidx.activity.ComponentActivity
import org.rmj.g3appdriver.etc.ImageFileCreator
import org.rmj.guanzongroup.documentscanner.xxxImageStatic.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * This class contains a helper function creating temporary files
 *
 * @constructor creates file util
 */
class FileUtil {
  val TAG = FileUtil::class.java.simpleName
  /**
   * create a temporary file
   *
   * @param activity the current activity
   * @param pageNumber the current document page number
   */
  @Throws(IOException::class)
  fun createImageFile(activity: ComponentActivity, pageNumber: Int): File {
    // use current time to make file name more unique
    val dateTime: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())

    // create file in pictures directory
    val lsRoot: String = activity.getExternalFilesDir(null).toString()
    val storageDir = File(lsRoot + "/COAD" + "/")
    if (!storageDir.exists()) {
      storageDir.mkdirs()
    }
    val lsFileNm = TransNox + "_" + EntryNox + "_" + FileCode + ".png"
    val loImgFle = File(storageDir, lsFileNm)
    Log.d(TAG, "File Path: " + loImgFle.absolutePath)
    return loImgFle
  }
}
