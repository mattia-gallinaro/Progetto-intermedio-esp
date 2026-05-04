package com.example.simon

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.simon.ui.theme.SimonTheme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

//deve ricevere le liste di bottoni corretti e bottoni errati tramite intent ( metodo più semplice ) oppure sfrutta il db e basta passare l'id per
class GameDetail : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    val list = listOf<String>()

    val type = if(LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) Column(){} else Row(){}

    Column(modifier = modifier.
        padding(16.dp)
        ){
        if(LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT){
            Text(text = "")

            LazyColumn(modifier = Modifier.weight(8f).verticalScroll(rememberScrollState())) {
                items(items = list) { row ->
                    ShowSingleButtons(Color.Red, row)
                }
            }

            Text(text = "")

            LazyColumn(modifier = Modifier.weight(8f).verticalScroll(rememberScrollState())) {
                items(items = list) { row ->
                    ShowSingleButtons(Color.Red, row)
                }
            }
        }else{
            Column(modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
            ) {

                Text(modifier = Modifier.weight(2f), text = "")

                LazyColumn(modifier = Modifier.weight(8f).verticalScroll(rememberScrollState())) {
                    items(items = list) { row ->
                        ShowSingleButtons(Color.Red, row)
                    }
                }
            }
        }
    }
}

@Composable
fun ShowSingleButtons(colorButton: Color , colorName : String){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)
        .border(width = 2.dp,
                brush = SolidColor(colorButton),
                shape = RoundedCornerShape(1.dp)
        ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            fontSize = 4.sp,
            text = colorName
        )
    }
}

//da rinominare ovviamente
@Composable
fun PartOfUi(sequence : List<String>, color: Color){

    Column(modifier = Modifier){
        Text(modifier = Modifier.weight(2f), text = "")

        LazyColumn(modifier = Modifier.weight(8f).verticalScroll(rememberScrollState())) {
            items(items = sequence) { row ->
                ShowSingleButtons(Color.Red, row)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimonTheme {
        Greeting("Android")
    }
}
