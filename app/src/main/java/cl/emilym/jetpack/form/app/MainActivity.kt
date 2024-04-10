package cl.emilym.jetpack.form.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.emilym.bytes.HumanReadableBytes
import cl.emilym.form.compose.FormFieldLockup
import cl.emilym.form.compose.field.SingleCheckboxFormFieldWidget
import cl.emilym.form.compose.field.DateFormFieldWidget
import cl.emilym.form.compose.field.FileFormFieldWidget
import cl.emilym.form.compose.field.MultipleCheckboxFormFieldWidget
import cl.emilym.form.compose.field.NumberFormFieldWidget
import cl.emilym.form.compose.field.RadioFormFieldWidget
import cl.emilym.form.compose.field.TextFormFieldWidget
import cl.emilym.form.compose.model.SelectionOption
import cl.emilym.form.field.CheckboxFormField
import cl.emilym.form.field.DateFormField
import cl.emilym.form.field.MultipleSelectionFormField
import cl.emilym.form.field.NumberFormField
import cl.emilym.form.field.SingleSelectionFormField
import cl.emilym.form.field.TextFormField
import cl.emilym.form.field.file.LazyFileFormField
import cl.emilym.form.form.SimpleForm
import cl.emilym.form.validator.BlankValidator
import cl.emilym.form.validator.CharacterMaximumValidator
import cl.emilym.form.validator.EnforceValueValidator
import cl.emilym.form.validator.RequiredValidator
import cl.emilym.form.validator.file.FileCountValidator
import cl.emilym.form.validator.file.FileMimeTypeValidator
import cl.emilym.form.validator.file.FileSizeValidator
import kotlinx.coroutines.runBlocking
import java.text.DateFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val options = listOf(
            SelectionOption(1, "One"),
            SelectionOption(2, "Two"),
            SelectionOption(3, "Three"),
        )

        val fileSize = 1000000L

        val textFormField = TextFormField("testText", listOf(
            CharacterMaximumValidator(100),
            RequiredValidator(),
            BlankValidator(),
            EnforceValueValidator("Must enter \"pog\"", "pog")
        ))
        val checkboxFormField = CheckboxFormField("testCheckbox", listOf(RequiredValidator()))
        val dateFormField = DateFormField("testDate", listOf(), DateFormat.getDateInstance())
        val numberFormField = NumberFormField<Int>("testNumber", listOf())
        val multipleSelectionFormField = MultipleSelectionFormField<Int>("testMultiple", listOf())
        val singleSelectionFormField = SingleSelectionFormField<Int>("testSingle", listOf(
            EnforceValueValidator("Must select \"One\"", 1)
        ))
        val fileFormField = LazyFileFormField(
            "testFiles",
            listOf(FileMimeTypeValidator(listOf("image/jpeg", "image/png")), FileSizeValidator(fileSize)),
            listOf(FileCountValidator(1, 5, "Must have between 1 and 5 files")),
        )

        val form = SimpleForm(
            listOf(
                textFormField,
                checkboxFormField,
                dateFormField,
                numberFormField,
                multipleSelectionFormField,
                singleSelectionFormField,
                fileFormField
            )
        )


        setContent {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FormFieldLockup(
                    textFormField.name,
                    "This is a test lockup",
                    false
                ) {
                    TextFormFieldWidget(
                        textFormField
                    )
                }

                FormFieldLockup(
                    checkboxFormField.name,
                    "This is a checkbox lockup test",
                    true
                ) {
                    SingleCheckboxFormFieldWidget(
                        checkboxFormField
                    ) {
                        Text("Something")
                    }
                }

                FormFieldLockup(
                    dateFormField.name,
                    "This is a date lockup test",
                    true
                ) {
                    DateFormFieldWidget(
                        dateFormField
                    )
                }

                FormFieldLockup(
                    numberFormField.name,
                    "This is a number form field test"
                ) {
                    NumberFormFieldWidget(
                        numberFormField
                    )
                }

                FormFieldLockup(multipleSelectionFormField.name) {
                    MultipleCheckboxFormFieldWidget(
                        multipleSelectionFormField,
                        options
                    ) {
                        Text(it)
                    }
                }

                FormFieldLockup(singleSelectionFormField.name) {
                    RadioFormFieldWidget(
                        singleSelectionFormField,
                        options
                    ) {
                        Text(it)
                    }
                }

                FormFieldLockup(fileFormField.name) {
                    FileFormFieldWidget(
                        fileFormField,
                        uploadInstructionContent =
                            "Your files must be a JPG, GIF, PDF or PNG under ${HumanReadableBytes.si(fileSize)}"
                    )
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        runBlocking {
                            form.validate(false)
                        }
                    }
                ) {
                    Text("Submit")
                }
            }
        }
    }

}