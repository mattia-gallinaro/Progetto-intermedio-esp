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
import androidx.compose.ui.tooling.preview.Preview
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

//funzione lambda usata per dividere la singola sequenza in una lista di singole
//lettere che indicano il colore
private val convertSequenceToList : (seq : String) -> List<String> = { seq ->
    val seqPolished = seq.replace(" ", "")
    seqPolished.split(",")
}
private const val tag = "History"
class SequenceHistory : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val seqHistory : String?  = intent.extras?.getString("History")

        Log.d(tag ,"Value received from the intent is : $seqHistory")

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

    val history = sequences.split(";")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.history)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
        )

        //controllo se ho passato delle sequenze o meno
        //se non ne ho mostro un messaggio a schermo per indicare che non sono presenti delle sequenze
        if(history[0] != ""){
            LazyColumn() {
                //scorro la lista di sequenze ricevuta dalla MainActivity tramite intent
                items(history.size){
                        i -> SingleSequence(history[i])//creo un oggetto composable per rappresentare singolarmente le sequeunze
                }
            }
        }else{
            Column(modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.emptyHistory),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
            }
        }
    }
}

//
@Composable
fun SingleSequence(sequence : String){

    val buttonsSequence = convertSequenceToList(sequence)

    Row(
        modifier = Modifier
        .fillMaxWidth()
        .height(90.dp)
        .padding(8.dp)
        .border(
            1.dp,
            SolidColor(Color.White),
            shape = RoundedCornerShape(4.dp)
        ),
    ){
        Text(
            text = buttonsSequence.size.toString(),
            fontSize = 30.sp,
            modifier = Modifier.fillMaxWidth().weight(2f),
            maxLines = 1,
            textAlign = TextAlign.Center
        )

        //Per dividere i 2 testi
        Spacer(modifier = Modifier.padding(horizontal = 10.dp).fillMaxHeight())

        //Ra
        Text(
            text = buttonsSequence.toString().replace("[", "").replace("]",""),
            fontSize = 30.sp,
            modifier = Modifier.fillMaxWidth().weight(8f),
            maxLines = 1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis//per sostituire la parte di testo che andrebbe in una seconda riga o al di fuori della box con un
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    SimonTheme {
        ShowHistory(sequences = "")
    }
}

