package com.example.orbi.ui.login


// Importações :
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orbi.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


// Define os possíveis estados que a tela de login pode assumir :
sealed class LoginState {


    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val userUid: String) : LoginState()
    data class Error(val message: String) : LoginState()


}


// Gerencia a lógica de autenticação e o estado da tela isolando isso da Activity :
@HiltViewModel
class LoginViewModel @Inject constructor(


    private val repository: AuthRepository


) : ViewModel() {


    // Gerenciadores de estado reativo para a interface observar :
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()


    // Recebe o token do Google e aciona o repositório para validar o acesso :
    fun signInWithGoogle(idToken: String) {


        viewModelScope.launch {


            // Avisa a tela que o processo de carregamento começou :
            _loginState.value = LoginState.Loading


            // Chama a camada de dados (Repositório) :
            val result = repository.signInWithGoogle(idToken)


            // Trata o resultado de sucesso ou falha :
            result.onSuccess { user ->


                _loginState.value = LoginState.Success(user.uid)


            }.onFailure { error ->


                _loginState.value = LoginState.Error(error.message ?: "Erro desconhecido")


            }


        }


    }


}