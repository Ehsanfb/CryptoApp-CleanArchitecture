package com.example.cryptoapp.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cryptoapp.domain.model.Coin
import com.example.cryptoapp.domain.use_case.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    val coinsFlow: Flow<PagingData<Coin>> = getCoinsUseCase().cachedIn(viewModelScope)

}