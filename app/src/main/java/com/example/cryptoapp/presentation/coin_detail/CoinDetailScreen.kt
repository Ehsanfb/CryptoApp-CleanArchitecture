package com.example.cryptoapp.presentation.coin_detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cryptoapp.presentation.theme.Typography
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cryptoapp.presentation.coin_detail.components.CoinTag
import com.example.cryptoapp.presentation.coin_detail.components.TeamListItem


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CoinDetailScreen(
    navController: NavController,
    viewModel: CoinDetailViewModel = hiltViewModel()
    /*coinDetailState: UiState<CoinDetail>,*/
) {

    val coinDetailState by viewModel.coinDetailUiState.collectAsState()

    Column {

        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .padding(20.dp)
                .size(32.dp)
                .clickable {
                    navController.popBackStack()
                }
        )

        Box(modifier = Modifier.fillMaxSize()) {

            coinDetailState.coinDetail?.let { coinDetail ->
                LazyColumn(
                    Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(20.dp)
                ) {

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${coinDetail.rank}. ${coinDetail.name} (${coinDetail.symbol})",
                                style = Typography.headlineMedium,
                                modifier = Modifier.weight(8f)
                            )
                            Text(
                                text = if (coinDetail.isActive) "active" else "inactive",
                                color = if (coinDetail.isActive) MaterialTheme.colorScheme.primary else Color.Red,
                                fontStyle = FontStyle.Italic,
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .align(CenterVertically)
                                    .weight(2f)
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = coinDetail.description,
                            style = Typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = "Tags",
                            style = Typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            coinDetail.tags.forEach { tag ->
                                CoinTag(tag = tag)
                            }
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = "Team members",
                            style = Typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                    }

                    items(coinDetail.team) { team ->
                        TeamListItem(
                            team = team,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        )
                        HorizontalDivider()
                    }


                }
            }

            if (coinDetailState.error.isNotEmpty()) {
                Text(
                    text = coinDetailState.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }

            if (coinDetailState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

        }

    }

}

