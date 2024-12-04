package com.samuelfranksmith.tastytrade.watchlists.listdetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.samuelfranksmith.tastytrade.watchlists.R
import com.samuelfranksmith.tastytrade.watchlists.listdetails.data.models.SymbolMarketPriceModel
import com.samuelfranksmith.tastytrade.watchlists.ui.theme.LightGrey
import com.samuelfranksmith.tastytrade.watchlists.ui.theme.TTTextStyle.PriceTitle
import com.samuelfranksmith.tastytrade.watchlists.ui.theme.TTTextStyle.ScreenTitle
import com.samuelfranksmith.tastytrade.watchlists.ui.theme.TTTextStyle.SymbolTitle
import com.samuelfranksmith.tastytrade.watchlists.ui.theme.TTTextStyle.PriceValue

data class WatchlistDetailsComposableData(
    val watchlistName: String,
    val watchlistEntries: List<SymbolMarketPriceModel>,
)


@Preview
@Composable
fun WatchlistDetailsScreen(
    @PreviewParameter(WatchlistDetailsComposableDataProvider::class)
    model: WatchlistDetailsComposableData
) {
    Column (
        modifier = Modifier
            .background(LightGrey)
    ) {
        WatchlistDetailsHeader(model.watchlistName)
        Column (
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(weight = 1f, fill = false)
        ){
            model.watchlistEntries.forEach { entry ->
                WatchlistDetailsRow(entry)
            }
        }
    }
}

@Composable
fun WatchlistDetailsHeader(name: String) {
    Text(
        modifier = Modifier.padding(20.dp),
        text = name,
        style = ScreenTitle
    )
}

@Composable
fun WatchlistDetailsRow(entry: SymbolMarketPriceModel) {
    Text(
        modifier = Modifier.padding(start = 20.dp),
        text = entry.symbol ?: "",
        style = SymbolTitle
    )
    Row (
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column {
            Text(
                text = stringResource(R.string.bid_price),
                style = PriceTitle,
            )
            Text(
                text = entry.bidPrice ?: stringResource(R.string.monetary_fallback),
                style = PriceValue,
            )
        }
        Column {
            Text(
                text = stringResource(R.string.ask_price),
                style = PriceTitle,
            )
            Text(
                text = entry.askPrice ?: stringResource(R.string.monetary_fallback),
                style = PriceValue,
            )
        }
        Column {
            Text(
                text = stringResource(R.string.last_price),
                style = PriceTitle,
            )
            Text(
                text = entry.lastPrice ?: stringResource(R.string.monetary_fallback),
                style = PriceValue,
            )
        }
    }

}

private class WatchlistDetailsComposableDataProvider :
    PreviewParameterProvider<WatchlistDetailsComposableData> {
    val symbolOne = SymbolMarketPriceModel(
        symbol = "AAPL",
        bidPrice = "243.3",
        askPrice = "244.3",
        lastPrice = "243.31",
        instrument = "Equity",
    )
    val symbolTwo = SymbolMarketPriceModel(
        symbol = "SPY",
        bidPrice = "243.3",
        askPrice = "244.3",
        lastPrice = "243.31",
        instrument = "Equity",
    )
    val symbolThree = SymbolMarketPriceModel(
        symbol = "A",
        bidPrice = null,
        askPrice = "1234.3",
        lastPrice = "12.31",
        instrument = "Equity",
    )
    val symbolFour = SymbolMarketPriceModel(
        symbol = "WOOF",
        bidPrice = "243.3",
        askPrice = null,
        lastPrice = "243.31",
        instrument = "Equity",
    )
    val symbolFive = SymbolMarketPriceModel(
        symbol = "TGT",
        bidPrice = "243.3",
        askPrice = "244.3",
        lastPrice = null,
        instrument = "Equity",
    )
    val symbolSix = SymbolMarketPriceModel(
        symbol = "HOG",
        bidPrice = "243.3",
        askPrice = "244.3",
        lastPrice = "243.31",
        instrument = "Equity",
    )
    val symbolSeven = SymbolMarketPriceModel(
        symbol = "GOOG",
        bidPrice = "243.3",
        askPrice = "244.3",
        lastPrice = "243.31",
        instrument = "Equity",
    )
    val symbolEight = SymbolMarketPriceModel(
        symbol = "HPQ",
        bidPrice = "243.3",
        askPrice = "244.3",
        lastPrice = "243.31",
        instrument = "Equity",
    )
    val symbolNine = SymbolMarketPriceModel(
        symbol = "MSFT",
        bidPrice = "243.3",
        askPrice = "244.3",
        lastPrice = "243.31",
        instrument = "Equity",
    )
    val symbolTen = SymbolMarketPriceModel(
        symbol = "INTC",
        bidPrice = "243.3",
        askPrice = "244.3",
        lastPrice = "243.31",
        instrument = "Equity",
    )
    val symbolEleven = SymbolMarketPriceModel(
        symbol = "TXN",
        bidPrice = "243.3",
        askPrice = "244.3",
        lastPrice = "243.31",
        instrument = "Equity",
    )
    val symbolTwelve = SymbolMarketPriceModel(
        symbol = "KO",
        bidPrice = "243.3",
        askPrice = "244.3",
        lastPrice = "243.31",
        instrument = "Equity",
    )
    val symbolThirteen = SymbolMarketPriceModel(
        symbol = "BRK.A",
        bidPrice = "243.3",
        askPrice = "244.3",
        lastPrice = "243.31",
        instrument = "Equity",
    )
    val symbolList = listOf(
        symbolOne, symbolTwo, symbolThree, symbolFour, symbolFive, symbolSix,
        symbolSeven, symbolEight, symbolNine, symbolTen, symbolEleven,
        symbolTwelve, symbolThirteen,
    )

    override val values = sequenceOf(
        WatchlistDetailsComposableData(
            watchlistName = "My favorite list",
            watchlistEntries = symbolList,
        )
    )
}
