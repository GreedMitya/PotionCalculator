package com.greedmitya.albcalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greedmitya.albcalculator.domain.AdvisorOutput
import com.greedmitya.albcalculator.domain.COMPONENT_ITEM_IDS
import com.greedmitya.albcalculator.domain.FetchMarketDataUseCase
import com.greedmitya.albcalculator.domain.HERB_ITEM_IDS
import com.greedmitya.albcalculator.domain.MarketPriceRow
import com.greedmitya.albcalculator.domain.PotionAdvisorUseCase
import com.greedmitya.albcalculator.model.ApiResult
import com.greedmitya.albcalculator.model.PotionInfo
import kotlinx.coroutines.launch

sealed interface MarketsUiState {
    data object Idle : MarketsUiState
    data object Loading : MarketsUiState
    data class Success(val rows: List<MarketPriceRow>) : MarketsUiState
    data class Error(val message: String) : MarketsUiState
}

sealed interface AdvisorUiState {
    data object Idle : AdvisorUiState
    data object Loading : AdvisorUiState
    data class Success(val output: AdvisorOutput) : AdvisorUiState
    data class Error(val message: String) : AdvisorUiState
}

class MarketsViewModel(
    private val fetchMarketDataUseCase: FetchMarketDataUseCase,
    private val potionAdvisorUseCase: PotionAdvisorUseCase,
) : ViewModel() {

    var herbsState by mutableStateOf<MarketsUiState>(MarketsUiState.Idle)
        private set
    var componentsState by mutableStateOf<MarketsUiState>(MarketsUiState.Idle)
        private set
    var potionsState by mutableStateOf<MarketsUiState>(MarketsUiState.Idle)
        private set
    var advisorState by mutableStateOf<AdvisorUiState>(AdvisorUiState.Idle)
        private set

    fun loadHerbs(serverCode: String) {
        herbsState = MarketsUiState.Loading
        viewModelScope.launch {
            herbsState = when (val result = fetchMarketDataUseCase.execute(HERB_ITEM_IDS, serverCode)) {
                is ApiResult.Success -> MarketsUiState.Success(result.data)
                is ApiResult.Error -> MarketsUiState.Error(result.exception.message ?: "Unknown error")
                ApiResult.Loading -> MarketsUiState.Loading
            }
        }
    }

    fun loadComponents(serverCode: String) {
        componentsState = MarketsUiState.Loading
        viewModelScope.launch {
            componentsState = when (val result = fetchMarketDataUseCase.execute(COMPONENT_ITEM_IDS, serverCode)) {
                is ApiResult.Success -> MarketsUiState.Success(result.data)
                is ApiResult.Error -> MarketsUiState.Error(result.exception.message ?: "Unknown error")
                ApiResult.Loading -> MarketsUiState.Loading
            }
        }
    }

    fun loadPotions(potionItemIds: List<String>, serverCode: String) {
        potionsState = MarketsUiState.Loading
        viewModelScope.launch {
            potionsState = when (val result = fetchMarketDataUseCase.execute(potionItemIds, serverCode)) {
                is ApiResult.Success -> MarketsUiState.Success(result.data)
                is ApiResult.Error -> MarketsUiState.Error(result.exception.message ?: "Unknown error")
                ApiResult.Loading -> MarketsUiState.Loading
            }
        }
    }

    fun analyzeAdvisor(
        allPotions: List<PotionInfo>,
        city: String,
        serverCode: String,
        isPremium: Boolean,
    ) {
        advisorState = AdvisorUiState.Loading
        viewModelScope.launch {
            advisorState = when (val result = potionAdvisorUseCase.execute(allPotions, city, serverCode, isPremium)) {
                is ApiResult.Success -> AdvisorUiState.Success(result.data)
                is ApiResult.Error -> AdvisorUiState.Error(result.exception.message ?: "Unknown error")
                ApiResult.Loading -> AdvisorUiState.Loading
            }
        }
    }
}
