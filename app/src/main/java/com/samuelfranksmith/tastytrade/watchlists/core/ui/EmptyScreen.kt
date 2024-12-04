package com.samuelfranksmith.tastytrade.watchlists.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.samuelfranksmith.tastytrade.watchlists.ui.theme.LightGrey

@Preview
@Composable
fun EmptyScreen() {
    Box(
        modifier = Modifier
            .background(LightGrey)
    )
}
