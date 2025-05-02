package com.example.cryptoapp.presentation

enum class Screen {
    CoinList,
    CoinDetails,
}
sealed class NavigationItem(val route: String) {
    object CoinList : NavigationItem(Screen.CoinList.name)
    object CoinDetails : NavigationItem(Screen.CoinDetails.name)
}