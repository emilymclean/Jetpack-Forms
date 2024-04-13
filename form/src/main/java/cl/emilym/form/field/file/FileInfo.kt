package cl.emilym.form.field.file

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns

/**
 * Represents the state of a file operation.
 *
 * @param T The type of file information associated with this state.
 */
sealed class FileState<T: FileInfo> {
    abstract val file: T

    /**
     * Represents a completed file operation state.
     */
    data class Complete<T: FileInfo>(override val file: T): FileState<T>()

    /**
     * Represents a file operation in progress state.
     *
     * @param progress The progress of the file operation.
     */
    data class Progress<T: FileInfo>(override val file: T, val progress: Double): FileState<T>()

    /**
     * Represents a failed file operation state.
     *
     * @param e The exception associated with the failure.
     */
    data class Failed<T: FileInfo>(override val file: T, val e: Exception): FileState<T>()

    /**
     * Represents an invalid file operation state.
     *
     * @param reason The reason for the invalid state.
     */
    data class Invalid<T: FileInfo>(override val file: T, val reason: String): FileState<T>()

    /**
     * Represents a waiting file operation state.
     */
    data class Waiting<T: FileInfo>(override val file: T): FileState<T>()
}

/**
 * Represents information about a file.
 */
interface FileInfo {
    val uri: Uri
    val name: String
    val mimeType: String

    /**
     * The size of the file in bytes.
     */
    val size: Long
}

/**
 * Represents information about a locally stored file.
 *
 * @param uri The URI of the file.
 * @param name The name of the file.
 * @param mimeType The MIME type of the file.
 * @param size The size of the file in bytes.
 */
data class LocalFileInfo(
    override val uri: Uri,
    override val name: String,
    override val mimeType: String,
    override val size: Long
): FileInfo {

    companion object {

        /**
         * Converts a URI and content resolver into a LocalFileInfo instance.
         *
         * @param uri The URI of the file.
         * @param contentResolver An instance of ContentResolver
         *
         * @return The local file info
         */
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

/**
 * Represents information about a remotely stored file.
 *
 * @param uri The URI of the file.
 * @param name The name of the file.
 * @param mimeType The MIME type of the file.
 * @param size The size of the file in bytes.
 * @param remoteReference The remote reference URI of the file.
 */
data class RemoteFileInfo(
    override val uri: Uri,
    override val name: String,
    override val mimeType: String,
    override val size: Long,
    val remoteReference: Uri?
): FileInfo {

    companion object {

        /**
         * Converts a URI and content resolver into a RemoteFileInfo instance.
         *
         * @param uri The URI of the file.
         * @param contentResolver An instance of ContentResolver
         *
         * @return The local file info
         */
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