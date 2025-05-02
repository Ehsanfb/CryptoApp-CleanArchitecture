package com.example.cryptoapp.data.remote.dto

import com.example.cryptoapp.domain.model.CoinDetail
import com.google.gson.annotations.SerializedName


data class CoinDetailDto(
    val id: String,
    val name: String,
    val description: String,
    val symbol: String,
    val rank: Int,
    @SerializedName("is_active")
    val isActive: Boolean,
    val tags: List<Tag>,
    val team: List<Team>
) {

    data class Tag(
        val id: String,
        val name: String
    )

    data class Team(
        val id: String,
        val name: String,
        val position: String
    )

    fun toCoinDetail(): CoinDetail {
        return CoinDetail(id, name, description, symbol, rank, isActive, tags.map { it.name }, team)
    }

}