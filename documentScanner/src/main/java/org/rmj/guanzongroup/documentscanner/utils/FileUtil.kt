package org.rmj.guanzongroup.documentscanner.utils

import android.os.Environment
import androidx.activity.ComponentActivity
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
    val storageDir: File? = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(TransNox + "_" + EntryNox + "_" + FileCode, ".png", storageDir)
  }
}
