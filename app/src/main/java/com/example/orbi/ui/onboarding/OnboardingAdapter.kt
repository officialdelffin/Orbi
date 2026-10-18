package com.example.orbi.ui.onboarding


// Importações :
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.orbi.databinding.ItemOnboardingPageBinding
import com.example.orbi.domain.model.OnboardingPage


// Adaptador do ViewPager2 utilizando ViewBinding para inflar os itens :
class OnboardingAdapter(


    private val pages: List<OnboardingPage>


) : RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {


    // Holding a referencia dos componentes visuais de cada item :
    inner class OnboardingViewHolder(


        val binding: ItemOnboardingPageBinding


    ) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {


        val binding = ItemOnboardingPageBinding.inflate(


            LayoutInflater.from(parent.context),
            parent,
            false


        )


        return OnboardingViewHolder(binding)


    }


    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {


        val page = pages[position]


        with(holder.binding) {


            ivOnboardingImage.setImageResource(page.imageRes)
            tvOnboardingTitle.setText(page.titleRes)
            tvOnboardingDescription.setText(page.descriptionRes)


        }


    }


    override fun getItemCount(): Int = pages.size


}