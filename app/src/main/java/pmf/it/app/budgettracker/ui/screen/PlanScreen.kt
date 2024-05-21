package pmf.it.app.budgettracker.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pmf.it.app.budgettracker.viewmodel.PlanScreenViewModel

@Composable
@Preview(showBackground = true, backgroundColor = 0x000000)
fun PlanScreen(viewModel: PlanScreenViewModel = hiltViewModel()) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Plan", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(24.dp))
                HorizontalDivider()
            }
            Row {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Income", style = MaterialTheme.typography.titleLarge)
                    Text(text = "0", style = MaterialTheme.typography.titleLarge)
                }
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Expenses", style = MaterialTheme.typography.titleLarge)
                    Text(text = "0", style = MaterialTheme.typography.titleLarge)
                }
            }
            /*Row {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Savings", style = MaterialTheme.typography.titleLarge)
                    Text(text = "0", style = MaterialTheme.typography.titleLarge)
                }
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Budget", style = MaterialTheme.typography.titleLarge)
                    Text(text = "0", style = MaterialTheme.typography.titleLarge)
                }
            }*/
            Row {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Goal", style = MaterialTheme.typography.titleLarge)
                    Text(text = "0", style = MaterialTheme.typography.titleLarge)
                }
            }
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Add Income", style = MaterialTheme.typography.titleMedium)
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Add Expenses", style = MaterialTheme.typography.titleMedium)
                }
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Add Goal", style = MaterialTheme.typography.titleMedium)
            }
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FloatingActionButton(onClick = { /*TODO*/ }) {
                    Text(text = "List", style = MaterialTheme.typography.titleMedium)
                }
                FloatingActionButton(onClick = { /*TODO*/ }) {
                    Text(text = "New", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}