package com.mou.cloudmusic_md3t.ui.screen.main.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mou.cloudmusic_md3t.R

@Composable
fun HomeScreen() {
    SuggestZone()
}

@Composable
fun SuggestCard() {

}

@Composable
fun PrivateRadioCard(){
    Card(modifier = Modifier.size(width = 180.dp, height = 100.dp)) {
    }
}

@Composable
fun TodaySuggest(){
    Card(modifier = Modifier.size(width = 140.dp, height = 100.dp)) {
    }
}

@Composable
fun SuggestZone(){
    Column(Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.suggest_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PrivateRadioCard()
            TodaySuggest()
        }
    }

}


@Preview(backgroundColor = 0xFFFFFF)
@Composable
fun CardPreview(){
    Row (horizontalArrangement = Arrangement.spacedBy(32.dp)){
        PrivateRadioCard()
        TodaySuggest()
    }
}

@Preview(showBackground = true,backgroundColor = 0xFFFFFF)
@Composable
fun SuggestZonePreview(){
    SuggestZone()
}