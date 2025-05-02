package com.example.cryptoapp.presentation.coin_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptoapp.domain.model.Coin

@Composable
fun CoinListItem(coin: Coin, onItemClicked: (Coin) -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onItemClicked(coin) },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "${coin.rank}. ${coin.name} (${coin.symbol})",
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "active",
            color = if (coin.isActive) MaterialTheme.colorScheme.primary else Color.Red,
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun CoinListItemPreview() {
    CoinListItem(Coin("2", true, "Bitcoin", 1, "BTC"), {})
}
