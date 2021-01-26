package com.example.pesel

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pesel.ui.theme.PeselTheme


// Zrobil Artem Kovalenko 251334


class MainActivity : AppCompatActivity() {
    private var pesel = ""
    val WEIGHTS = intArrayOf(1, 3, 7, 9, 1, 3, 7, 9, 1, 3, 1)
    var isValid = false
    var gender = ""
    var BirthDate = ""
    var message = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PeselTheme {
                PeselForm()
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PeselFormPreview() {
        PeselTheme {
            PeselForm()
        }
    }

    @Composable
    fun PeselForm() {
        Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
            Column(Modifier.align(Alignment.Center)) {
                val modifier = Modifier.padding(vertical = 4.dp)
                val peselState = remember { mutableStateOf(TextFieldValue()) }
                TextField(
                        value = peselState.value,
                        onValueChange = { peselState.value = it },
                        modifier = modifier,
                        placeholder = { Text("Write PESEL") }

                )
                Button(
                        onClick = {
                            pesel = peselState.value.text
                            ValidatePesel(Pesel = pesel)
                        })
                {
                    Text("Verify")
                }

                Text(message)
            }

        }
    }


    fun ValidatePesel(Pesel: String) {
        PeselUtil(Pesel)
        if (isValid == true) {
            message = "${"Pesel is correct"}, ${BirthDate}, ${gender}"
        } else {
            message = "Pesel is NOT correct."
        }
    }


    fun PeselUtil(Pesel: String) {
        val sum = (0 until Pesel.length).sumBy { Pesel[it].toInt() * WEIGHTS[it] }
        if (sum % 10 == 0) {
            isValid = true
        }

        isValid = !(Pesel.isBlank() || Pesel.length != 11)
        isValid = Pesel.matches(Regex("^\\d{11}$"))

        if (isValid == true) {
            if (Pesel[9].toInt() % 2 != 0) gender = "Male" else gender = "Female"
        }

        if (isValid == true) {
            var year = Integer.parseInt(Pesel.substring(0, 2), 10)
            var month = Integer.parseInt(Pesel.substring(2, 4), 10)
            val day = Integer.parseInt(Pesel.substring(4, 6), 10)

            when {
                month > 80 -> {
                    year += 1800
                    month -= 80
                }
                month > 60 -> {
                    year += 2200
                    month -= 60
                }
                month > 40 -> {
                    year += 2100
                    month -= 40
                }
                month > 20 -> {
                    year += 2000
                    month -= 20
                }
                else -> year += 1900
            }

            BirthDate = year.toString() + "-" + String.format("%02d",
                    month
            ) + "-" + String.format("%02d", day)
        }
    }

}








