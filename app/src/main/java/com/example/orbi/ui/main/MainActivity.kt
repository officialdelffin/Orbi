package com.example.orbi.ui.main


// Importações :
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.orbi.databinding.ActivityMainBinding
import com.example.orbi.ui.createcycle.CreateCycleActivity


// Gerenciando a tela hospedeira principal do aplicativo :
class MainActivity : AppCompatActivity() {


    // Declarando a variavel de ligacao com o layout XML :
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


        // Inflando o layout XML e conectando os elementos visuais a Activity e definindo a view raiz na tela :
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Definindo o clique no button de criar ciclos
        binding.btnAddCycle.setOnClickListener {


            val intent = Intent(this, CreateCycleActivity::class.java)
            startActivity(intent)


        }


    }


}