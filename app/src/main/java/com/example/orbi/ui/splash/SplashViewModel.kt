package com.example.orbi.ui.splash


// Importações :
import androidx.lifecycle.ViewModel
import com.example.orbi.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


// Define os caminhos possíveis a partir da inicialização :
sealed class SplashDestination {


    object Main : SplashDestination()
    object Onboarding : SplashDestination()


}


// Avalia o estado de autenticação antes de renderizar qualquer interface :
@HiltViewModel
class SplashViewModel @Inject constructor(


    private val repository: AuthRepository


) : ViewModel() {


    // Estado reativo para guiar a rota :
    private val _destination = MutableStateFlow<SplashDestination?>(null)
    val destination: StateFlow<SplashDestination?> = _destination.asStateFlow()


    // Verifica imediatamente se existe uma sessão NoSQL ativa :
    fun verifyUserSession() {


        val currentUser = repository.getCurrentUser()


        if (currentUser != null) {


            _destination.value = SplashDestination.Main


        } else {


            _destination.value = SplashDestination.Onboarding


        }


    }


}