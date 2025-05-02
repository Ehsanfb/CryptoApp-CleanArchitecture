package com.example.cryptoapp.presentation.coin_detail

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptoapp.presentation.theme.Typography

@Composable
fun CoinTag(tag: String, modifier: Modifier = Modifier) {

    Box(
        modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(10.dp)
    ) {

        Text(
            text = tag,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            style = Typography.bodyMedium
        )

    }

    Log.d("CoinTag", "Primary color: ${MaterialTheme.colorScheme.primary}")

}

@Preview(showBackground = true)
@Composable
private fun CoinTagPreview() {
    CoinTag("ABC")
}