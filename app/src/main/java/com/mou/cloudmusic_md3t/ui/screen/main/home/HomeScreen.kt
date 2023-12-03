package com.mou.cloudmusic_md3t.ui.screen.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mou.cloudmusic_md3t.R
import com.mou.cloudmusic_md3t.data.entities.PlayableSong
import com.mou.cloudmusic_md3t.data.entities.Song

@Composable
fun HomeScreen(startPlayList: (Song) -> Unit) {
    val viewModel:HomeViewModel = viewModel()
    SuggestZone(startPlayList)
}

@Composable
fun SuggestCard() {

}

@Composable
fun PrivateRadioCard(){
    Card(modifier = Modifier
        .height(height = 120.dp)
        .fillMaxWidth(0.6f)
    ) {
    }
}

@Composable
fun TodaySuggest(
    playTodayList:(Song)->Unit
){
    Card(modifier = Modifier
        .height(120.dp)
        .fillMaxWidth(1f)
    ) {
        Column(Modifier.padding(8.dp)) {
            Text(
                text = stringResource(id = R.string.suggest_today_title),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxHeight(0.4f)
            ) {
            }
            Row (
                modifier = Modifier.fillMaxWidth(1f),
                horizontalArrangement = Arrangement.End
            ){
                // Circle Button to Play
                IconButton(
                    onClick = {
                              playTodayList(
                                  PlayableSong(
                                  1,
                                  "https://gitee.com/xiaomouz/xiaomouz/raw/master/upload/images/06bcb167ff840.jpg",
                                  "test",
                                  1000)
                              )
                    },
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PlayCircleOutline,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}

@Composable
fun SuggestZone(startPlayList:(Song)->Unit){
    Column(
        Modifier
            .fillMaxWidth()
            .padding(24.dp)) {
        Text(
            text = stringResource(id = R.string.listen_right_now_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PrivateRadioCard()
            TodaySuggest(startPlayList)
        }
    }

}


@Preview(backgroundColor = 0xFFFFFF)
@Composable
fun CardPreview(){
    Row (horizontalArrangement = Arrangement.spacedBy(32.dp)){
        PrivateRadioCard()
        TodaySuggest({})
    }
}

@Preview(showBackground = true,backgroundColor = 0xFFFFFF)
@Composable
fun SuggestZonePreview(){
    SuggestZone({})
}