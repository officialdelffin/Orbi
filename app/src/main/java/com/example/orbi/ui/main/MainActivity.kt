package com.example.orbi.ui.main


// Importações :
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.orbi.databinding.ActivityMainBinding
import com.example.orbi.ui.createcycle.CreateCycleActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


// Gerenciando a tela hospedeira principal do aplicativo :
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var cycleAdapter: CycleAdapter


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        // Inflando o layout XML e conectando os elementos visuais :
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
        observeViewModel()


    }

    // Prepara a lista (RecyclerView) para receber os dados :
    private fun setupRecyclerView() {


        cycleAdapter = CycleAdapter()


        binding.rvCycles.apply {


            adapter = cycleAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)


        }


    }


    // Configura os cliques :
    private fun setupListeners() {


        binding.btnAddCycle.setOnClickListener {


            val intent = Intent(this, CreateCycleActivity::class.java)
            startActivity(intent)


        }


    }


    // Observa o banco de dados em tempo real e reage às mudanças :
    private fun observeViewModel() {


        lifecycleScope.launch {


            viewModel.cyclesState.collect { cyclesList ->


                // 1. Atualiza o contador de ciclos :
                binding.tvSavedCyclesCount.text = "${cyclesList.size} disponíveis"


                // 2. Envia os dados para a fábrica de cartões (Adaptador) :
                cycleAdapter.updateList(cyclesList)


                // 3. Verifica se a lista está vazia para mostrar ou esconder a ilustração :
                if (cyclesList.isEmpty()) {


                    binding.rvCycles.visibility = View.GONE
                    binding.ivEmptyState.visibility = View.VISIBLE
                    binding.tvEmptyStateMsg.visibility = View.VISIBLE


                } else {


                    binding.ivEmptyState.visibility = View.GONE
                    binding.tvEmptyStateMsg.visibility = View.GONE
                    binding.rvCycles.visibility = View.VISIBLE


                }


            }


        }


    }


}