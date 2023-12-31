package com.mou.cloudmusic_md3t.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.MotionPhotosPaused
import androidx.compose.material.icons.filled.PauseCircleOutline
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.mou.cloudmusic_md3t.data.entities.PlayableSong
import com.mou.cloudmusic_md3t.data.entities.Song
import com.mou.cloudmusic_md3t.data.entities.SongStatus
import com.mou.cloudmusic_md3t.ui.screen.main.MusicBarState

@Composable
fun MusicPlayingBar(
    musicBarState: MusicBarState
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(musicBarState.song.songCoverImageUri)
                    .crossfade(true)
                    .build(),
                contentDescription = "Songs Image",
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                val state = painter.state
                if (state is AsyncImagePainter.State.Loading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp)
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    SubcomposeAsyncImageContent(
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .border(
                                BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer),
                                RoundedCornerShape(12.dp)
                            )
                    )
                }
            }
            Text(
                text = musicBarState.song.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth(0.6f)
            )
            Row {
                IconButton(onClick = { musicBarState.song.switch() }) {
                    // switch lang
                    when (musicBarState.song.status) {
                        SongStatus.LOADING -> Icon(Icons.Filled.MotionPhotosPaused, contentDescription = "downloading")
                        SongStatus.PLAYING -> Icon(Icons.Filled.PauseCircleOutline, contentDescription = "playing")
                        SongStatus.PAUSE -> Icon(Icons.Filled.PlayCircle, contentDescription = "pause")
                        SongStatus.STOP -> Icon(Icons.Filled.Replay, contentDescription = "stop")
                        SongStatus.PENDING -> Icon(Icons.Filled.CloudDownload, contentDescription = "pending")
                    }
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.SkipNext, contentDescription = "next")
                }
            }

        }
    }
}

@Preview
@Composable
fun MusicPlayingBarPreview() {
    MusicPlayingBar(
        MusicBarState()
    )
}
