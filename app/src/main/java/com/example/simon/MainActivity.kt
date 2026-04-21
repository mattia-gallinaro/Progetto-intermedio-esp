package com.example.simon

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.simon.ui.theme.SimonTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    //per salvare e gestire la sequenza attuale e quella dello storico
    var currentSeq by rememberSaveable { mutableStateOf("") }
    var history by rememberSaveable { mutableStateOf(ArrayList<String>()) }

    val context = LocalContext.current

    //per sapere l'orientamento dello schermo attuale
    val orientation = LocalConfiguration.current.orientation

    val onColorButtonClick : (String) -> Unit= {color ->
        if(currentSeq.compareTo("") == 0) currentSeq = "$color, "
        else currentSeq = currentSeq + ""
    }

    if(orientation == Configuration.ORIENTATION_PORTRAIT){
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ){

            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(2f)// in modo che la griglia dei bottoni occupi 2/4 dello schermo in proporzione al resto
            ){
                ButtonGrid(onColorButtonClick)
            }

            Text(
                text = currentSeq,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .weight(1f)
            ){
                Button(
                    modifier = Modifier
                        .height(40.dp)
                        .width(80.dp)
                        .weight(1f)
                    ,
                    onClick = {

                        history.add(currentSeq)
                        currentSeq = ""

                        //funziona ma si può spostare in classActivity per avere meno problemi prob
                        context.startActivity(Intent(context, SequenceHistory::class.java))
                    }
                ){
                    Text(stringResource(R.string.end_game))
                }

                Button(
                    modifier = Modifier
                        .height(40.dp)
                        .width(80.dp)
                        .weight(1f)
                    ,
                    onClick = {
                        currentSeq = ""
                    }
                ){
                    Text("Cancella")
                }
            }
        }
    }else{
        Row(modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
        ){
            ButtonGrid(onColorButtonClick)
            Column(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .fillMaxWidth()
            ) {
                Text(
                    text =  "Test "
                )
            }
        }
    }
}

//per definire una classe automatica
data class ColorButton(val color : Color, val name : String)

@Composable
fun ButtonGrid(onColorButtonClick : (String) -> Unit) {

    //arraylist che contiene colore + stringa che rappresenta la prima lettera del colore in inglese

    val colorList: ArrayList<ColorButton> = ArrayList<ColorButton>()
    colorList.add(ColorButton(Color.Red, "R"))
    colorList.add(ColorButton(Color.Blue, "B"))
    colorList.add(ColorButton(Color.Green, "G"))
    colorList.add(ColorButton(Color.Cyan, "C"))
    colorList.add(ColorButton(Color.Yellow, "Y"))
    colorList.add(ColorButton(Color.Magenta, "M"))

    colorList.shuffle()//mescolo i colori in modo che così i colori dei bottoni a schermo vengono cambiati e non rimangono in una posizione fissa

    var i = 0;

        Column(modifier = Modifier
            .fillMaxSize()
        ) {
            repeat(3) {
                Row(
                    modifier = Modifier
                        .weight(1f)//affinchè le 3 righe abbiano lo stesso spazio
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(2) {

                        Button(
                            modifier = Modifier
                                .weight(1f)//in modo che i bottoni possano occupare metà riga
                                .fillMaxHeight()
                                .padding(10.dp),
                            onClick = { onColorButtonClick(colorList[i].name) },
                            colors = ButtonDefaults.buttonColors(containerColor = colorList[i].color)
                        ) {}

                        i++;
                    }
                }
            }
        }

    }


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimonTheme {
        MainScreen()
    }
}