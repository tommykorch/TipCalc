package com.example.tipcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalc.ui.theme.TipCalcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalcTheme {
                Scaffold { innerPadding ->
                    DemoScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun DemoText(message: String, fontSize: Float) {
    Text(
        text = message,
        fontSize = fontSize.sp,
        fontWeight = FontWeight.Bold,

    )
}
@Composable
fun TextFieldDemo() {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFFFF0F5),
            unfocusedContainerColor = Color(0xFFFFF0F5),
        ),
        placeholder = {
            Text("Введите текст...", color = Color.Gray)
        }
    )
}
@Preview(showSystemUi = true)
@Composable
fun DemoTextPreview() {
    TipCalcTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DemoScreen(modifier = Modifier.padding(innerPadding))
        }
    }

}
@Composable
fun DemoSlider(sliderPosition: Float, onPositionChange: (Float) -> Unit) {
    Slider(
        modifier = Modifier.padding(10.dp),
        valueRange = 0f..25f,
        value = sliderPosition,
        onValueChange = { onPositionChange(it) }
    )
}
@Composable
fun DemoScreen(modifier: Modifier = Modifier) {
    var sliderPosition by remember { mutableFloatStateOf(0f)
    }
    var selectedOption by remember { mutableStateOf("5%") }
    val handlePositionChange = { position: Float ->
        sliderPosition = position
    }
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize().padding(top = 25.dp,start=10.dp)
    )
    {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(all=5.dp)

        ) {
            DemoText(message = "Сумма заказа:", fontSize = 20f)
            TextFieldDemo()

        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(all=5.dp)

        ) {
            DemoText(message = "Количество блюд:", fontSize = 20f)
            TextFieldDemo()

        }

        Spacer(modifier = Modifier.height(50.dp))
        DemoText(message = "Чаевые:", fontSize = 20f)
        DemoSlider(
            sliderPosition = sliderPosition,
            onPositionChange = handlePositionChange
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(all=15.dp)

        ) {
            Text(
                style = MaterialTheme.typography.headlineMedium,
                text = sliderPosition.toInt().toString()
            )
            Text(
                style = MaterialTheme.typography.headlineMedium,
                text = "25"
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding()
        ) {
            Text(
                style = MaterialTheme.typography.headlineSmall,
                text = "Скидка:"
            )

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
                    .padding(top = 30.dp),
            ) {
                listOf("3%", "5%", "7%", "10%").forEach { option ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RadioButton(

                            selected = selectedOption == option,
                            onClick = { selectedOption = option }
                        )
                        Text(
                            text = option,
                            modifier = Modifier.padding(top = 2.dp),
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }

    }
}

