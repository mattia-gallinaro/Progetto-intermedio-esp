package com.example.simon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.simon.ui.theme.SimonTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

//deve ricevere le liste di bottoni corretti e bottoni errati tramite intent ( metodo più semplice ) oppure sfrutta il db e basta passare l'id per
class GameDetail : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ShowDetails(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ShowDetails(modifier: Modifier = Modifier , correctButtons: List<String>? = null, incorrectButtons: List<String>? = null) {

    val list = listOf<String>()

Column(modifier = modifier
.fillMaxSize()
.padding(16.dp)) {

//al posto di xx verrà inserito il numero della partita ottenuto tramite intent o db
Text( modifier = Modifier.weight(2f), text = "List of buttons pressed correctly and incorrectly of game number XX", fontSize = 30.sp)

Box(modifier = Modifier.
weight(8f)){
    PartOfUi( correctButtons , incorrectButtons)//in seguito verranno passate le stringhe ottenute o tramite room/sql db
                                                                    //opppure ottenute tramite intent in modo da non mandare molteplici richieste
}
}
}

//da rinominare
//controllare che vengano mostrati correttamente i bottoni a schermo
@Composable
fun PartOfUi(correctButtons : List<String>?, incorrectButtons: List<String>?){

Column(modifier = Modifier){
Text(buildAnnotatedString {
    withStyle(style = SpanStyle(color = Color.Green)) {
        append(if(!correctButtons.isNullOrEmpty())correctButtons.toString().replace("[", "").replace("]", "") else "")
    }

    withStyle(style = SpanStyle(color = Color.Red)) {
        append(if(!incorrectButtons.isNullOrEmpty())incorrectButtons.toString().replace("[", "").replace("]", "") else "")
    }
}
)
}
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
SimonTheme {
Greeting("Android")
}
}

//val type = if(LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) Column(){} else Row(){}
