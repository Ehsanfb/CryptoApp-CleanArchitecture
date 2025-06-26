package com.example.cryptoapp.presentation.coin_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cryptoapp.presentation.Screen
import com.example.cryptoapp.presentation.coin_list.components.CoinListItem

@Composable
fun CoinListScreen(
    navController: NavController,
    viewModel: CoinsViewModel = hiltViewModel()
    /*coinsState: UiState<List<Coin>>,*/
) {

    val coins = viewModel.coinsFlow.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()) {

        when (val state = coins.loadState.refresh) {

            is LoadState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is LoadState.Error -> {
                Text(
                    text = state.error.message ?: "An unknown error occurred",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }

            is LoadState.NotLoading -> {
                LazyColumn(Modifier.fillMaxSize()) {
                    items(coins.itemCount) { index ->
                        coins[index]?.let { coin ->
                            CoinListItem(coin, onItemClicked = {
                                navController.navigate(Screen.CoinDetails.name + "/${coin.id}")
                            })
                        }
                    }

                    if (coins.loadState.append is LoadState.Loading) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(16.dp)
                            )
                        }
                    }
                }
            }

        }

    }

}
