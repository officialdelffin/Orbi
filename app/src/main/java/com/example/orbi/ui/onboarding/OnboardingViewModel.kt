package com.example.orbi.ui.onboarding


// Importações :
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orbi.R
import com.example.orbi.domain.model.OnboardingPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


// Gerenciando o estado do Onboarding com StateFlow e Coroutines :
@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {


    // Lista das paginas de apresentacao do produto :
    val pages = listOf(


        OnboardingPage(
            imageRes = R.drawable.img_onboarding_one,
            titleRes = R.string.onboarding_title_one,
            descriptionRes = R.string.onboarding_desc_one
        ),


        OnboardingPage(
            imageRes = R.drawable.img_onboarding_one,
            titleRes = R.string.onboarding_title_two,
            descriptionRes = R.string.onboarding_desc_two
        ),


        OnboardingPage(
            imageRes = R.drawable.img_onboarding_one,
            titleRes = R.string.onboarding_title_three,
            descriptionRes = R.string.onboarding_desc_three
        )


    )


    // Expondo o fluxo do indice da pagina atual :
    private val _currentPage = MutableStateFlow(0)


    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()


    // Atualizando o indice da pagina reativamente :
    fun updatePage(position: Int) {


        viewModelScope.launch {


            _currentPage.value = position


        }


    }


}