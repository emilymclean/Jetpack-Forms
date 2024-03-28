package cl.emilym.form.field.file

import cl.emilym.form.FormField
import cl.emilym.form.MappedOutputFormField

interface FileFormField<T: FileInfo>: FormField<List<FileState<T>>> {

    

}