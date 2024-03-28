package cl.emilym.form.field.file

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns

sealed class FileState<T: FileInfo>(
    val file: T
) {
    class Complete<T: FileInfo>(file: T): FileState<T>(file)
    class Progress<T: FileInfo>(file: T, val progress: Double): FileState<T>(file)
    class Failed<T: FileInfo>(file: T, val e: Exception): FileState<T>(file)
    class Invalid<T: FileInfo>(file: T, val reason: String): FileState<T>(file)
    class Waiting<T: FileInfo>(file: T): FileState<T>(file)
}

interface FileInfo {
    val uri: Uri
    val name: String
    val mimeType: String
    // Size in bytes
    val size: Long
}

data class LocalFileInfo(
    override val uri: Uri,
    override val name: String,
    override val mimeType: String,
    override val size: Long
): FileInfo {

    companion object {

        fun fromUri(uri: Uri, contentResolver: ContentResolver): LocalFileInfo? {
            val type = contentResolver.getType(uri) ?: return null
            return contentResolver.query(uri, null, null, null, null)?.use { c ->
                val nameIdx = c.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val sizeIdx = c.getColumnIndex(OpenableColumns.SIZE)
                c.moveToFirst()
                val name = c.getString(nameIdx)
                val size = c.getLong(sizeIdx)

                LocalFileInfo(
                    uri,
                    name,
                    type,
                    size
                )
            }
        }

    }

}

data class RemoteFileInfo(
    override val uri: Uri,
    override val name: String,
    override val mimeType: String,
    override val size: Long,
    val remoteReference: Uri?
): FileInfo {

    companion object {
        fun fromUri(uri: Uri, contentResolver: ContentResolver): RemoteFileInfo? {
            val descriptor = LocalFileInfo.fromUri(uri, contentResolver) ?: return null
            return RemoteFileInfo(descriptor.uri, descriptor.name, descriptor.mimeType, descriptor.size, null)
        }
    }


}