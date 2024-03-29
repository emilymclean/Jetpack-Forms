package cl.emilym.form.field.file

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns

sealed class FileState<T: FileInfo> {
    abstract val file: T

    data class Complete<T: FileInfo>(override val file: T): FileState<T>()
    data class Progress<T: FileInfo>(override val file: T, val progress: Double): FileState<T>()
    data class Failed<T: FileInfo>(override val file: T, val e: Exception): FileState<T>()
    data class Invalid<T: FileInfo>(override val file: T, val reason: String): FileState<T>()
    data class Waiting<T: FileInfo>(override val file: T): FileState<T>()
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LocalFileInfo

        return uri == other.uri && name == other.name
    }

    override fun hashCode(): Int {
        return uri.hashCode()
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RemoteFileInfo

        return uri == other.uri && name == other.name
    }

    override fun hashCode(): Int {
        return uri.hashCode()
    }


}