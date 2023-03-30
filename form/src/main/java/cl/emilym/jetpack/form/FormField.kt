package cl.emilym.jetpack.form

import kotlinx.coroutines.flow.Flow

interface FormField<T> {

    val name: String

    var currentValue: T?
    val liveValue: Flow<T>

    val errorMessage: Flow<T>

}