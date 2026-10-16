package com.example.orbi.ui.onboarding


// Importações :
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.orbi.R
import com.example.orbi.domain.model.OnboardingPage
import kotlinx.coroutines.launch


// Desenhando a interface gráfica completa da tela de onboarding :
@Composable
fun OnboardingScreen(


    onFinishOnboarding: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()


) {


    val pages = viewModel.pages
    val currentPage by viewModel.currentPage.collectAsState()
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()


    // Sincronizando o deslize manual da tela com o estado da ViewModel :
    LaunchedEffect(pagerState.currentPage) {


        viewModel.updateCurrentPage(pagerState.currentPage)


    }


    Column(


        modifier = Modifier


            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)


    ) {


        Spacer(modifier = Modifier.height(16.dp))


        // Posicionando o cabeçalho superior com o logotipo e os indicadores :
        Row(


            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically


        ) {


            Text(


                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground


            )


            PageIndicator(


                pageSize = pages.size,
                currentPage = currentPage


            )


            // Inserindo um espaço vazio para manter o indicador perfeitamente centralizado :
            Spacer(modifier = Modifier.width(32.dp))


        }


        Spacer(modifier = Modifier.height(32.dp))


        // Gerenciando as páginas deslizantes do componente HorizontalPager :
        HorizontalPager(


            state = pagerState,
            modifier = Modifier.weight(1f)


        ) { position ->


            OnboardingPageContent(page = pages[position])


        }


        // Desenhando a barra inferior de botões com controle condicional :
        Row(


            modifier = Modifier


                .fillMaxWidth()
                .padding(bottom = 16.dp),


            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically


        ) {


            // Escondendo o botão voltar na primeira página e exibindo nas seguintes :
            if (currentPage > 0) {


                OutlinedButton(


                    onClick = {


                        coroutineScope.launch {


                            pagerState.animateScrollToPage(currentPage - 1)


                        }


                    },


                    modifier = Modifier.height(56.dp)


                ) {


                    Text(


                        text = stringResource(id = R.string.btn_back),
                        color = MaterialTheme.colorScheme.onBackground


                    )


                }


            } else {


                // Espaçador invisível para manter o botão "Próximo" alinhado à direita :
                Spacer(modifier = Modifier.width(1.dp))


            }


            Button(


                onClick = {


                    if (currentPage < pages.size - 1) {


                        coroutineScope.launch {


                            pagerState.animateScrollToPage(currentPage + 1)


                        }


                    } else {


                        onFinishOnboarding()


                    }


                },


                modifier = Modifier.height(56.dp),
                colors = ButtonDefaults.buttonColors(


                    containerColor = MaterialTheme.colorScheme.secondary


                )


            ) {


                Text(


                    text = if (currentPage == pages.size - 1) stringResource(id = R.string.btn_start) else stringResource(id = R.string.btn_next),
                    color = MaterialTheme.colorScheme.onSecondary


                )


                Spacer(modifier = Modifier.width(8.dp))


                Icon(


                    imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondary


                )


            }


        }


    }


}


// Renderizando o conteúdo interno de uma página específica com imagem e textos :
@Composable
fun OnboardingPageContent(page: OnboardingPage) {


    Column(


        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally


    ) {


        Image(


            painter = painterResource(id = page.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier


                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(24.dp))


        )


        Spacer(modifier = Modifier.height(32.dp))


        Text(


            text = stringResource(id = page.titleRes),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxWidth()


        )


        Spacer(modifier = Modifier.height(16.dp))


        Text(


            text = stringResource(id = page.descriptionRes),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier


                .fillMaxWidth()
                .alpha(0.8f)


        )


        Spacer(modifier = Modifier.height(16.dp))


    }


}


// Animando o indicador de progresso no formato de pílula :
@Composable
fun PageIndicator(pageSize: Int, currentPage: Int) {


    Row(


        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically


    ) {


        repeat(pageSize) { index ->


            val isSelected = index == currentPage


            val color by animateColorAsState(


                targetValue = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceVariant,
                label = "ColorAnimation"


            )


            val width by animateDpAsState(


                targetValue = if (isSelected) 24.dp else 8.dp,
                label = "WidthAnimation"


            )


            Box(


                modifier = Modifier


                    .padding(horizontal = 4.dp)
                    .height(8.dp)
                    .width(width)
                    .clip(CircleShape)
                    .background(color)


            )


        }


    }


}