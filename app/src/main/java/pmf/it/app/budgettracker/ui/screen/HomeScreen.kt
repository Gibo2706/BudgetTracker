package pmf.it.app.budgettracker.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pmf.it.app.budgettracker.util.CircleProgressBar
import pmf.it.app.budgettracker.viewmodel.HomeScreenViewModel

@Composable
@Preview(showBackground = true, backgroundColor = 0x000000)
fun HomeScreen(viewModel: HomeScreenViewModel = hiltViewModel() ) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Text(text = "Welcome back", style = MaterialTheme.typography.titleLarge)
                Text(text = "User", style = MaterialTheme.typography.titleLarge)
            }
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Text(text = "Current progress", style = MaterialTheme.typography.titleLarge)
                Box(
                    modifier = Modifier
                        .padding(24.dp)
                        .width(200.dp)
                        .height(200.dp)
                        .background(MaterialTheme.colorScheme.surface, shape = CircleShape)
                        .border(8.dp, MaterialTheme.colorScheme.primary, shape = CircleShape),
                ) {
                    CircleProgressBar(percentage = 0.5f, radius = 100.dp, strokeWidth = 8.dp)
                }
                Text(text = "500,00/1000,00", style = MaterialTheme.typography.titleMedium)
            }

            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {

            }
        }

    }
}

