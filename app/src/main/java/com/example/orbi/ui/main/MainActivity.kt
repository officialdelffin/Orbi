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
import com.example.orbi.domain.model.Cycle
import com.example.orbi.ui.createcycle.CreateCycleActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar


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


                // 2. Atualiza a mensagem dinâmica do dia cruzando os dados :
                updateTodayStatusMessage(cyclesList)


                // 3. Envia os dados para a fábrica de cartões (Adaptador) :
                cycleAdapter.updateList(cyclesList)


                // 4. Verifica se a lista está vazia para mostrar ou esconder a ilustração :
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


    // Filtra os ciclos de hoje, calcula o tempo total de foco e formata a mensagem :
    private fun updateTodayStatusMessage(cyclesList: List<Cycle>) {


        // 1. Identifica o dia atual (1 = Domingo, 7 = Sábado) :
        val currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)


        // 2. Filtra a lista deixando apenas os ciclos ativos para o dia de hoje :
        val todayCycles = cyclesList.filter { it.activeDays.contains(currentDayOfWeek) }
        val todayCyclesCount = todayCycles.size


        if (todayCyclesCount > 0) {


            // 3. Calcula o total de minutos focados hoje (Duração x Repetições de cada ciclo) :
            var totalFocusMinutes = 0
            for (cycle in todayCycles) {


                totalFocusMinutes += (cycle.focusDurationMinutes * cycle.repetitions)


            }


            // 4. Converte os minutos totais em Horas e Minutos :
            val hours = totalFocusMinutes / 60
            val minutes = totalFocusMinutes % 60


            // 5. Formata o texto de tempo dinamicamente (ex: "1 hora e 30 minutos" ou apenas "45 minutos") :
            val timeString = buildString {


                if (hours > 0) {


                    append("$hours ${if (hours == 1) "hora" else "horas"}")


                }
                if (hours > 0 && minutes > 0) {


                    append(" e ")


                }
                if (minutes > 0) {


                    append("$minutes ${if (minutes == 1) "minuto" else "minutos"}")


                }


            }


            // 6. Define o plural da palavra 'ciclo' e injeta na interface :
            val pluralCycle = if (todayCyclesCount == 1) "1 ciclo" else "$todayCyclesCount ciclos"

            binding.tvTodayStatusMsg.text = "$pluralCycle para hoje, contando $timeString de foco."


        } else {


            binding.tvTodayStatusMsg.text = "Nenhum ciclo disponível para hoje."


        }


    }


}