package com.example.orbi.ui.onboarding


// Importações :
import androidx.lifecycle.ViewModel
import com.example.orbi.R
import com.example.orbi.domain.model.OnboardingPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


// Gerenciando o estado e a lógica de apresentação do onboarding :
@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {


    // Construindo a lista estática com as três páginas de introdução :
    val pages = listOf(


        OnboardingPage(


            imageRes = R.drawable.img_onboarding_one,
            titleRes = R.string.onboarding_title_one,
            descriptionRes = R.string.onboarding_desc_one


        ),


        // Substituiremos as imagens das páginas 2 e 3 posteriormente :
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


    // Mantendo o controle reativo da página atual visualizada pelo usuário :
    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()


    // Atualizando o estado do índice quando o usuário desliza a tela :
    fun updateCurrentPage(page: Int) {


        _currentPage.value = page


    }


}