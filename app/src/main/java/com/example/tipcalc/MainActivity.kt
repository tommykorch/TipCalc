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
fun NumberTextField(value: String, onValueChange: (String) -> Unit, placeholder: String) {
    TextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.all { it.isDigit() } || newValue.isEmpty()) {
                onValueChange(newValue)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFFFF0F5),
            unfocusedContainerColor = Color(0xFFFFF0F5),
        ),
        placeholder = {
            Text(placeholder, color = Color.Gray)
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
    //var selectedOption by remember { mutableStateOf("5%") }
    val handlePositionChange = { position: Float ->
        sliderPosition = position
    }
    var orderAmount by remember { mutableStateOf("") }
    var dishCount by remember { mutableStateOf("") }

    val discountPercentage = remember(dishCount) {
        when {
            dishCount.isEmpty() -> 0
            (dishCount.toIntOrNull() ?: 0) <= 2 -> 3
            (dishCount.toIntOrNull() ?: 0) <= 5 -> 5
            (dishCount.toIntOrNull() ?: 0) <= 10 -> 7
            else -> 10
        }
    }
    val totalAmount = remember(orderAmount, sliderPosition, discountPercentage) {
        val amount = orderAmount.toDoubleOrNull() ?: 0.0
        val tip = amount * (sliderPosition / 100)
        val discount = amount * (discountPercentage / 100.0)
        amount + tip - discount
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
            NumberTextField(
                value = orderAmount,
                onValueChange = { orderAmount = it },
                placeholder = "Введите сумму..."
            )

        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(all=5.dp)

        ) {
            DemoText(message = "Количество блюд:", fontSize = 20f)
            NumberTextField(
                value = dishCount,
                onValueChange = { dishCount = it },
                placeholder = "Введите количество..."
            )

        }

        Spacer(modifier = Modifier.height(50.dp))
        DemoText(message = "Чаевые:", fontSize = 20f)
        DemoSlider(
            sliderPosition = sliderPosition,
            onPositionChange = { sliderPosition = it }
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(all=15.dp)

        ) {
            Text(
                style = MaterialTheme.typography.headlineMedium,
                text = sliderPosition.toInt().toString()+"%"
            )
            Text(
                style = MaterialTheme.typography.headlineMedium,
                text = "25%"
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
                            selected = discountPercentage.toString() == option.removeSuffix("%"),
                            onClick = {}
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
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)
        ) {
            Text(
                style = MaterialTheme.typography.headlineSmall,
                text = "Итого:"
            )
            Text(
                style = MaterialTheme.typography.headlineMedium,
                text = "%.2f".format(totalAmount),
                color = Color.Red
            )
        }

    }
}

