package cl.emilym.form

import kotlinx.coroutines.flow.Flow

interface Verifiable {

    val isValid: Flow<Boolean>
    suspend fun validate(silent: Boolean): Boolean

}