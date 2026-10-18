package com.example.orbi.ui.login


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.orbi.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Futuro: Aqui colocaremos o clique do binding.btnGoogleLogin para disparar a autenticação


    }


}