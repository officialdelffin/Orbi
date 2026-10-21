package com.example.orbi.ui.login


// Importações :
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.example.orbi.ui.main.MainActivity
import com.example.orbi.R
import com.example.orbi.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient


    // Launcher responsável por abrir o pop-up nativo do Google e capturar a resposta :
    private val googleSignInLauncher = registerForActivityResult(


        ActivityResultContracts.StartActivityForResult()


    ) { result ->


        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)


        try {


            val account = task.getResult(ApiException::class.java)


            val idToken = account?.idToken


            if (idToken != null) {


                viewModel.signInWithGoogle(idToken)


            } else {


                Toast.makeText(this, "Falha ao obter o token do Google", Toast.LENGTH_SHORT).show()


            }


        } catch (e: ApiException) {


            Toast.makeText(this, "Erro no login: ${e.message}", Toast.LENGTH_SHORT).show()


        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


        // Inicializa o ViewBinding :
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupSystemBars()
        setupGoogleSignIn()
        setupClickListeners()
        observeViewModel()


    }


    // Configura as cores da barra de status e navegação para manter a imersão :
    private fun setupSystemBars() {


        window.statusBarColor = ContextCompat.getColor(this, R.color.orbi_olive)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.orbi_olive)


        // Define os ícones do sistema como brancos para dar contraste com o fundo escuro :
        WindowInsetsControllerCompat(window, window.decorView).apply {


            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false


        }


    }


    // Configura o cliente do Google Sign-In vinculando com a chave gerada pelo google-services.json :
    private fun setupGoogleSignIn() {


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)


            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()


        googleSignInClient = GoogleSignIn.getClient(this, gso)


    }


    // Configura os eventos de clique da tela :
    private fun setupClickListeners() {


        // Ação do botão visual de voltar :
        binding.btnBackLogin.setOnClickListener {


            executeBackNavigation()


        }


        // Ação do botão de login com o Google :
        binding.btnGoogleLogin.setOnClickListener {


            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)


        }


        // Ação do gesto nativo de voltar do aparelho :
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {


            override fun handleOnBackPressed() {


                executeBackNavigation()


            }


        })


    }


    // Observa os estados emitidos pela LoginViewModel :
    private fun observeViewModel() {


        lifecycleScope.launch {


            viewModel.loginState.collect { state ->


                when (state) {


                    is LoginState.Idle -> {


                        // Estado inicial aguardando ação do usuário :


                    }


                    is LoginState.Loading -> {


                        // Estado de carregamento :


                    }


                    is LoginState.Success -> {


                        // Mensagem amigável de boas-vindas :
                        Toast.makeText(this@LoginActivity, "Bem-vindo ao Orbi!", Toast.LENGTH_SHORT).show()


                        // Cria a rota de navegação para a tela principal :
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)


                        // Destrói a tela de login atual da memória RAM :
                        finish()


                    }


                    is LoginState.Error -> {


                        Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_LONG).show()


                    }


                }


            }


        }


    }


    // Centraliza a navegação de retorno para o Onboarding :
    private fun executeBackNavigation() {


        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out_up)


    }


}