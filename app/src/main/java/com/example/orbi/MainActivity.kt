package com.example.orbi


// Importações :
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.orbi.ui.theme.OrbiTheme


// Definindo a atividade principal e ponto de entrada do aplicativo :
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


        // Habilitando o layout de ponta a ponta na tela e configurando o conteúdo visual com o tema global do Orbi :
        enableEdgeToEdge()
        setContent {


            // O NavHost e o gerenciamento de rotas serão inseridos aqui futuramente :
            OrbiTheme {




            }


        }


    }


}