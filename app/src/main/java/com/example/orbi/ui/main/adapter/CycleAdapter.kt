package com.example.orbi.ui.main


// Importações :
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.orbi.R
import com.example.orbi.databinding.ItemCycleCardBinding
import com.example.orbi.domain.model.Cycle


// Vincula os dados do banco NoSQL ao layout visual do cartão individual :
class CycleAdapter : RecyclerView.Adapter<CycleAdapter.CycleViewHolder>() {


    // Lista em memória que será atualizada reativamente :
    private var cycles: List<Cycle> = emptyList()


    fun updateList(newCycles: List<Cycle>) {


        this.cycles = newCycles
        notifyDataSetChanged()


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CycleViewHolder {


        val binding = ItemCycleCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CycleViewHolder(binding)


    }


    override fun onBindViewHolder(holder: CycleViewHolder, position: Int) {


        holder.bind(cycles[position])


    }


    override fun getItemCount(): Int = cycles.size


    // Classe interna que segura os elementos visuais do cartão :
    inner class CycleViewHolder(private val binding: ItemCycleCardBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(cycle: Cycle) {


            val context = binding.root.context
            binding.tvCycleTitle.text = cycle.title


            // Oculta a descrição se ela estiver vazia no banco de dados :
            if (cycle.description.isNullOrBlank()) {


                binding.tvCycleDesc.visibility = android.view.View.GONE


            } else {


                binding.tvCycleDesc.visibility = android.view.View.VISIBLE
                binding.tvCycleDesc.text = cycle.description


            }


            // 2. Monta o texto dinâmico de resumo :
            val summaryText = "Ciclo de ${cycle.repetitions} repetições de ${cycle.focusDurationMinutes} minutos com ${cycle.breakDurationMinutes} minutos de pausa entre eles"
            binding.tvCycleSummary.text = summaryText


            // 3. Gera as bolinhas dos dias da semana dinamicamente :
            binding.layoutDaysContainer.removeAllViews()
            val daysInitials = listOf("D", "S", "T", "Q", "Q", "S", "S")


            for (i in 1..7) {


                val isActive = cycle.activeDays.contains(i)


                // Criação da bolinha (TextView) via código para garantir flexibilidade :
                val dayTextView = TextView(context).apply {


                    text = daysInitials[i - 1]
                    textSize = 12f
                    gravity = android.view.Gravity.CENTER


                    // Definindo um tamanho fixo de 28dp x 28dp para as bolinhas :
                    val size = (28 * context.resources.displayMetrics.density).toInt()


                    layoutParams = LinearLayout.LayoutParams(size, size).apply {


                        marginEnd = (4 * context.resources.displayMetrics.density).toInt() // Margem de 4dp entre elas


                    }


                    // Pinta de Olive se ativo, ou mantém o fundo creme vazado se inativo :
                    val bgDrawable = GradientDrawable().apply {


                        shape = GradientDrawable.OVAL


                        if (isActive) {


                            setColor(ContextCompat.getColor(context, R.color.orbi_olive))


                        } else {


                            setColor(Color.TRANSPARENT)
                            setStroke(2, ContextCompat.getColor(context, R.color.orbi_olive)) // Borda para os não selecionados


                        }


                    }


                    background = bgDrawable

                    // Cor do texto :
                    setTextColor(


                        if (isActive) ContextCompat.getColor(context, R.color.orbi_cream)
                        else ContextCompat.getColor(context, R.color.orbi_dark_brown)


                    )


                }


                binding.layoutDaysContainer.addView(dayTextView)
            }


        }


    }


}