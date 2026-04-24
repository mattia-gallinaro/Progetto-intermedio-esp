package com.example.simon

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.sp

private val tag = listOf("ButtonClick", "Activity", "Value", "IndexGrid")

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

    Log.d(tag[1], "MainScreen has been called")

    //per salvare e gestire la sequenza attuale e quella dello storico
    var currentSeq by rememberSaveable { mutableStateOf("") }
    var history by rememberSaveable { mutableStateOf(listOf<String>()) }

    //per verificare che il contenuto delle variabili rimanga salvato anche dopo
    // cambio attività o cambio orientamento dello schermo
    Log.d(tag[1], "currentSeq value : $currentSeq")
    Log.d(tag[1], "history value : $history")

    //per avere il context per l'intent
    val context = LocalContext.current

    //funzione lambda per inserire una lettera nella sequenza corrente
    val onColorButtonClick : (String) -> Unit= {color ->

        Log.d(tag[0], "Button $color clicked")

        if(currentSeq.compareTo("") == 0) currentSeq = color
        else currentSeq += ", $color"

        Log.d(tag[0], "Sequence after button clicked $currentSeq")
    }

    //funzione lambda per convertire la lista di stringhe in un'unica stringa composta da sequenze separate dal carattere ';'
    //se la lista non e' vuota senno' ritorna una stringa vuota
    val convertListToString : (historySeq : List<String>) -> String = { historySeq ->

        var sequenceHistory = ""

        if(historySeq.size != 0){
            for(i in 0..(historySeq.size - 2))sequenceHistory = sequenceHistory + historySeq[i] + ";"
            sequenceHistory += historySeq[history.size - 1]
        }

        sequenceHistory
    }

    //controllo l'orientamento dello schermo del dispositivo
    if(LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT){

        Log.d(tag[1], "Layout for portrait orientation")

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ){

            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(2f)// in modo che la griglia dei bottoni occupi 2/4 dello schermo in proporzione al text e la row che contiene i bottoni
            ){
                ButtonGrid(onColorButtonClick)
            }

            Box(modifier = Modifier
                .weight(1f)
                .padding(10.dp)
                .border(
                    1.dp,
                    if(isSystemInDarkTheme())SolidColor(Color.White)
                    else SolidColor(Color.Black),//isSystemDarkTheme serve per controllare se il dispositivo
                                                        //ha attivato la modalità notte o meno . In base a quello modifica
                                                        //la linea per delimitare il testo
                    shape = RoundedCornerShape(4.dp)
                )
            ){
                Text(
                    text = currentSeq,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(5.dp)
                        .verticalScroll(rememberScrollState()),
                    fontSize = 20.sp
                )
            }

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

                        Log.d(tag[0], "End game clicked")

                        //aggiungo una sequenza alla lista solo se la sequenza corrente non e' vuota
                        if(currentSeq.compareTo("") != 0)history = history.plus(currentSeq)

                        Log.d(tag[2], "New list of sequences : $history")

                        val historySequence = convertListToString(history)
                        currentSeq = ""

                        Log.d(tag[2],"String ready to be sent for other activity : $historySequence")

                        //intent per far startare l'attivita' successiva
                        context.startActivity(Intent(context, SequenceHistory::class.java).putExtra("History", historySequence))
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
                        Log.d(tag[0], "End game clicked")

                        Log.d(tag[2], "Old value of currentSeq: $currentSeq")

                        currentSeq = ""

                        Log.d(tag[2], "New value of currentSeq: $currentSeq")
                    }
                ){
                    Text(stringResource(R.string.end_sequence))
                }
            }
        }
    }else{

        Log.d(tag[1], "Layout for portrait orientation")

        Row(modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
        ){
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
            ) {
                ButtonGrid(onColorButtonClick)
            }
            Column(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
            ) {

                Box(modifier = Modifier
                    .weight(1f)
                    .border(
                        1.dp,
                        if(isSystemInDarkTheme())SolidColor(Color.White)
                        else SolidColor(Color.Black),
                        shape = RoundedCornerShape(4.dp)
                    )
                ){
                    Text(
                        text = currentSeq,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(5.dp)
                            .verticalScroll(rememberScrollState()),
                        fontSize = 20.sp
                    )
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.5f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center

                ){
                    Button(
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp)
                            .weight(1f)
                        ,
                        onClick = {
                            Log.d(tag[0], "End game clicked")

                            //aggiungo una sequenza alla lista solo se la sequenza corrente non e' vuota
                            if(currentSeq.compareTo("") != 0)history = history.plus(currentSeq)

                            Log.d(tag[2], "New list of sequences : $history")

                            val historySequence = convertListToString(history)
                            currentSeq = ""

                            Log.d(tag[2],"String ready to be sent for other activity : $historySequence")

                            //intent per far startare l'attivita' successiva
                            context.startActivity(Intent(context, SequenceHistory::class.java).putExtra("History", historySequence))

                        }
                    ){
                        Text(stringResource(R.string.end_game))
                    }

                    Button(
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp)
                            .weight(1f)
                        ,
                        onClick = {
                            Log.d(tag[0], "End game clicked")

                            Log.d(tag[2], "Old value of currentSeq: $currentSeq")

                            currentSeq = ""

                            Log.d(tag[2], "New value of currentSeq: $currentSeq")
                        }
                    ){
                        Text(stringResource(R.string.end_sequence))
                    }
                }
            }
        }
    }
}

//per definire una classe che genera in automatico getter e setter e mi permette di avere
data class ColorButton(val color : Color, val name : String)

@Composable
fun ButtonGrid(onColorButtonClick : (String) -> Unit) {

    //Lista di coppie di colore + prima lettera del colore

    Log.d(tag[1], "Creation of ButtonGrid")

    val colorList = listOf(ColorButton(Color.Red, "R"),
        ColorButton(Color.Blue, "B"),
        ColorButton(Color.Green, "G"),
        ColorButton(Color.Cyan, "C"),
        ColorButton(Color.Yellow, "Y"),
        ColorButton(Color.Magenta, "M")).shuffled()// in modo che i colori dei bottoni vengano mescolati
                                                          // ad ogni chiamata di button grid
                                                          // e non rimangano nella stessa posizione

    Column(modifier = Modifier
            .fillMaxSize()
        ) {
            repeat(3) { row ->
                Row(
                    modifier = Modifier
                        .weight(1f)//affinchè le 3 righe abbiano lo stesso spazio
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(2) { col ->

                        val index = row * 2 + col //piuttosto di usare una variabile i, l'indice viene calcolato ad ogni iterazione sfruttando il numero della row e il numero della colonna
                                                  // in modo da seguire la seguente logica
                                                  /* index            row               col
                                                  *   0                0                 0
                                                  *   1                0                 1
                                                  *   2                1                 0
                                                  *   3                1                 1
                                                  *   4                2                 0
                                                  *   5                2                 1
                                                  *   in questo modo gli indici possono essere ricalcolati ad ogni chiamata della funzione senza incombere in errori di IndexOutOfBoundsException
                                                  *   come avveniva precedentemente con l'uso di una variabile i la quale non veniva reinizializzata ad ogni chiamata della funzione ButtonGrid
                                                  * */

                        Log.d(tag[3], "Index value is : $index")

                        Button(
                            modifier = Modifier
                                .weight(1f)//in modo che i bottoni possano occupare metà riga ciascuno
                                .fillMaxHeight()
                                .padding(10.dp),
                            onClick = { onColorButtonClick(colorList[index].name) },
                            colors = ButtonDefaults.buttonColors(containerColor = colorList[index].color)

                        ) {}

                    }
                }
            }
        }

    Log.d(tag[1], "ButtonGrid created ")
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimonTheme {
        MainScreen()
    }
}