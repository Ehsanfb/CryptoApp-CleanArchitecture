package com.example.cryptoapp.data.remote.dto


import com.example.cryptoapp.domain.model.Coin
import com.google.gson.annotations.SerializedName

data class CoinDto(
    val id: String,
    @SerializedName("is_active")
    val isActive: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
) {

    fun toCoin(): Coin {
        return Coin(id, isActive, name, rank, symbol)
    }

}