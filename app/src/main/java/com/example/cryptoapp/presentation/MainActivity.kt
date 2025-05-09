package com.example.cryptoapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cryptoapp.presentation.coin_detail.CoinDetailScreen
import com.example.cryptoapp.presentation.coin_list.CoinListScreen
import com.example.cryptoapp.presentation.theme.CryptoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //private lateinit var coinsViewModel: CoinsViewModel
    //private lateinit var mainRepository: MainRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val apiService = ApiClient.apiService
        //mainRepository = MainRepositoryImpl(apiService)
        //val coinsFactory = CoinsViewModelFactory(mainRepository)
        //coinsViewModel = ViewModelProvider(this, coinsFactory)[CoinsViewModel::class.java]

        setContent {

            //val coinsState by coinsViewModel.coinsUiState.collectAsState()

            CryptoAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = NavigationItem.CoinList.route
                    ) {
                        composable(route = NavigationItem.CoinList.route) {
                            CoinListScreen(navController)
                        }
                        composable(route = NavigationItem.CoinDetails.route + "/{coinId}") {
                            CoinDetailScreen()
                        }
                    }
                }
            }
        }
    }
}

