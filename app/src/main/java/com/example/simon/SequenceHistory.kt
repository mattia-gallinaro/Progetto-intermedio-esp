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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color

val convertSequenceToList : (seq : String) -> List<String> = { seq ->
    val seqPolished = seq.replace(" ", "")
    seqPolished.split(",")
}

class SequenceHistory : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val seqHistory : String?  = intent.extras?.getString("History")

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
            .fillMaxSize(),
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

        if(history[0] != ""){
            LazyColumn() {
                items(history.size){
                        i -> SingleSequence(history[i])
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
                    fontSize = 30.sp
                )
            }
        }
    }
}

@Composable
fun SingleSequence(sequence : String){

    val buttonsSequence = convertSequenceToList(sequence)

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(90.dp)
        .border(
            1.dp,
            SolidColor(Color.White),
            shape = RoundedCornerShape(4.dp)
        )
    ){
        Text(
            text = buttonsSequence.size.toString(),
            modifier = Modifier
        )
        Text(
            text = buttonsSequence.toString()
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

