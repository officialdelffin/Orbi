package com.example.orbi.ui.timer


// Importações :
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.orbi.R
import com.example.orbi.databinding.ActivityTimerBinding
import dagger.hilt.android.AndroidEntryPoint


// Tela responsável por rodar o cronômetro do Pomodoro :
@AndroidEntryPoint
class TimerActivity : AppCompatActivity() {


    private lateinit var binding: ActivityTimerBinding


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSystemBars()
        setupListeners()


        // Obter o ID do ciclo que foi passado pela MainActivity (Usaremos em breve) :
        val cycleId = intent.getStringExtra("CYCLE_ID")


    }


    // Mantém a imersão visual da tela :
    private fun setupSystemBars() {


        window.statusBarColor = ContextCompat.getColor(this, R.color.orbi_cream)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.orbi_cream)


        WindowInsetsControllerCompat(window, window.decorView).apply {


            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true


        }


    }


    private fun setupListeners() {


        // Retorna para a Home :
        binding.btnBack.setOnClickListener {


            finish()


        }


    }


}