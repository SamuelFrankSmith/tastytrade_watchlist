package com.samuelfranksmith.tastytrade.watchlists.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samuelfranksmith.tastytrade.watchlists.ui.theme.Grey
import com.samuelfranksmith.tastytrade.watchlists.ui.theme.LightGrey
import com.samuelfranksmith.tastytrade.watchlists.ui.theme.Purple500

@Preview
@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .background(LightGrey)
            .fillMaxSize(),
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp)
                .align(Alignment.Center),
            color = Grey,
            trackColor = Purple500,
        )
    }
}
