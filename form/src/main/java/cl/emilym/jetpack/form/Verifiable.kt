package cl.emilym.jetpack.form

import kotlinx.coroutines.flow.Flow

interface Verifiable {

    val isValid: Flow<Boolean>
    suspend fun validate(silent: Boolean): Boolean

}