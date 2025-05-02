package com.example.cryptoapp.presentation.coin_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptoapp.data.remote.dto.CoinDetailDto
import com.example.cryptoapp.presentation.theme.Typography

@Composable
fun TeamListItem(team: CoinDetailDto.Team, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = team.name,
            style = Typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = team.position, style = Typography.bodyMedium, fontStyle = FontStyle.Italic)
    }
}

@Preview(showBackground = true)
@Composable
private fun TeamListItemPreview() {
    TeamListItem(CoinDetailDto.Team(id = "1", name = "Bitcoin", position = "Inja"))
}