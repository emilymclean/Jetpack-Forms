package cl.emilym.jetpack.form.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.emilym.form.compose.FormFieldLockup
import cl.emilym.form.compose.field.SingleCheckboxFormFieldWidget
import cl.emilym.form.compose.field.DateFormFieldWidget
import cl.emilym.form.compose.field.NumberFormFieldWidget
import cl.emilym.form.compose.field.TextFormFieldWidget
import cl.emilym.form.field.CheckboxFormField
import cl.emilym.form.field.DateFormField
import cl.emilym.form.field.NumberFormField
import cl.emilym.form.field.TextFormField
import cl.emilym.form.validator.CharacterMaximumValidator
import cl.emilym.form.validator.RequiredValidator
import java.text.DateFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textFormField = TextFormField("Test", listOf(CharacterMaximumValidator(100)))
        val checkboxFormField = CheckboxFormField("Test Checkbox", listOf(RequiredValidator()))
        val dateFormField = DateFormField("Test Date", listOf(), DateFormat.getDateInstance())
        val numberFormField = NumberFormField<Int>("Test Number", listOf())

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
            }
        }
    }

}