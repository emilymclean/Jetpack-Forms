package cl.emilym.form

import kotlinx.coroutines.flow.Flow

/**
 * An interface representing an object that can be checked for validity.
 */
interface Verifiable {

    /**
     * A flow representing whether the object is currently valid or not.
     */
    val isValid: Flow<Boolean>

    /**
     * Validates the object.
     *
     * @param silent If true, validation will be performed without emitting validation errors.
     * @return True if the object is valid, false otherwise.
     */
    suspend fun validate(silent: Boolean): Boolean

}