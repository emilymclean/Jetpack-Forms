package cl.emilym.form

import kotlinx.coroutines.flow.Flow

interface FormField<T>: Verifiable {

    val name: String

    var currentValue: T?
    val liveValue: Flow<T?>

    val errorMessage: Flow<String?>

}