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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mou.cloudmusic_md3t.R
import com.mou.cloudmusic_md3t.data.music.PlayableSong
import com.mou.cloudmusic_md3t.data.music.Song

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
    Card(modifier = Modifier.size(width = 180.dp, height = 100.dp)) {
    }
}

@Composable
fun TodaySuggest(
    playTodayList:(Song)->Unit
){
    Card(modifier = Modifier.size(width = 140.dp, height = 100.dp)) {
        Column(Modifier.padding(8.dp)) {
            Text(
                text = "今日歌单推荐",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxHeight(0.4f)
            ) {
                Text(
                    text = "1",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "2",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Row (horizontalArrangement = Arrangement.End){
                // Circle Button to Play
                IconButton(
                    onClick = {
                              playTodayList(PlayableSong(1,"https://gitee.com/xiaomouz/xiaomouz/raw/master/upload/images/06bcb167ff840.jpg", "test", 1000))
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                    ),
                    modifier = Modifier.clip(CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PlayCircleOutline,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun SuggestZone(startPlayList:(Song)->Unit){
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