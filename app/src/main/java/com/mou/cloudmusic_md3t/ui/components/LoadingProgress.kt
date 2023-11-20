package com.mou.cloudmusic_md3t.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mou.cloudmusic_md3t.ui.theme.AppTheme

@Composable
fun LoadingProgress(
    title: String = "Loading",
    needTitle: Boolean = true,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .background(color = Color.White.copy(alpha = 0.6f))
            .clickable { },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        if (needTitle) {
            Text(text = title, modifier = Modifier.padding(top = 10.dp))
        }
    }
}

@Preview
@Composable
fun LoadingProgressPreview() {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            LoadingProgress()
        }
    }
}