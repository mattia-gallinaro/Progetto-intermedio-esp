package com.example.simon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.simon.ui.theme.SimonTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.layout.Row
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme

//funzione lambda usata per dividere la singola sequenza in una lista di singole
//lettere che indicano il colore
private val convertSequenceToList : (seq : String) -> List<String> = { seq ->
    val seqPolished = seq.replace(" ", "")
    seqPolished.split(",")
}
private val tag = listOf("Activity", "Value")
class SequenceHistory : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val seqHistory : String?  = intent.extras?.getString("History")

        Log.d(tag[0] ,"Value received from the intent is : $seqHistory")

        setContent {
            SimonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ShowHistory(
                        modifier = Modifier.padding(innerPadding),
                        seqHistory ?: ""
                    )
                }
            }
        }
    }
}

@Composable
fun ShowHistory(modifier: Modifier = Modifier , sequences : String) {

    Log.d(tag[0], "ShowHistory called")

    Log.d(tag[1], "Value received from main for sequences : $sequences")

    val history = sequences.split(";")

    Log.d(tag[1], "List of sequences splitted : $history")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.history),
            fontSize = 30.sp
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
        )

        //controllo se ho passato delle sequenze o meno
        //se non ne ho mostro un messaggio a schermo per indicare che non sono presenti delle sequenze
        if(history[0] != ""){
            //se ho degli elementi invece sfrutto una lazy column dato che crea e mostra gli oggetti correntemente visibili
            LazyColumn{
                //scorro la lista di sequenze ricevuta dalla MainActivity tramite intent
                items(history.size){
                        i -> SingleSequence(history[i])//creo un oggetto composable per rappresentare singolarmente le sequeunze
                }
            }
        }else{
            Column(modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.Center,           // per poter porre il testo
                horizontalAlignment = Alignment.CenterHorizontally) // al centro dello schermo
            {
                Text(
                    text = stringResource(R.string.emptyHistory),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,// fa in modo che il testo venga scritto al centro
                    fontSize = 25.sp
                )
            }
        }
    }
}

//
@Composable
fun SingleSequence(sequence : String){

    Log.d(tag[0], "Creating a new row for a sequence")

    Log.d(tag[1], "Sequence not splitted : $sequence")

    val buttonsSequence = convertSequenceToList(sequence)

    Log.d(tag[1], "Sequnence splitted : $buttonsSequence")

    Row(
        modifier = Modifier
        .fillMaxWidth()
        .height(90.dp)
        .padding(8.dp)
        .border(
            1.dp,
            if(isSystemInDarkTheme())SolidColor(Color.White) else SolidColor(Color.Black),
            shape = RoundedCornerShape(4.dp)
        ),
    ){
        Text(
            text = buttonsSequence.size.toString(),
            fontSize = 25.sp,
            modifier = Modifier.fillMaxWidth().weight(3f),
            maxLines = 1,
            textAlign = TextAlign.Center
        )

        //Per dividere i 2 testi
        Spacer(modifier = Modifier.padding(horizontal = 10.dp).fillMaxHeight())

        //Per rappresentare la lista dei bottoni premuti
        Text(
            text = buttonsSequence.toString().replace("[", "").replace("]",""),
            fontSize = 25.sp,
            modifier = Modifier.fillMaxWidth().weight(8f),
            maxLines = 1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis//per sostituire la parte di testo che andrebbe in una seconda
                                            // riga o al di fuori della box con dei puntini
        )
    }

    Log.d(tag[0], "New row for a sequence has been created")
}


