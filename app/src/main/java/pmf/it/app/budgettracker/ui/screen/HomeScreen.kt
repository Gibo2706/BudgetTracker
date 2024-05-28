package pmf.it.app.budgettracker.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import pmf.it.app.budgettracker.util.CircleProgressBar
import pmf.it.app.budgettracker.viewmodel.HomeScreenViewModel
import pmf.it.app.budgettracker.viewmodel.PlanScreenViewModel

@Composable
@Preview(showBackground = true, backgroundColor = 0x000000)
fun HomeScreen(viewModel: HomeScreenViewModel = hiltViewModel() ) {
    val currentPlan by viewModel.currentPlan
    val currentProgress by viewModel.currentProgress
    var showAllPlansDialog by remember{
        mutableStateOf(false)
    }
    val allPlans = viewModel.allPlans
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        when(lifecycleState) {
            Lifecycle.State.STARTED -> {
                //viewModel.checkIfUserIsLoggedIn()
            }
            Lifecycle.State.RESUMED -> {
                viewModel.getAllPlans(1)
            }
            else -> {}
        }
    }
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
                    CircleProgressBar(percentage = (currentProgress/ (currentPlan.cilj?.toDouble()
                        ?: 1.0)).toFloat(), radius = 100.dp, strokeWidth = 8.dp)
                }
                Text(text = "${currentProgress}/${currentPlan.cilj}", style = MaterialTheme.typography.titleMedium)
            }

            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = "Plan: ${currentPlan.name}", style = MaterialTheme.typography.titleMedium)
                    IconButton(onClick = { showAllPlansDialog = true }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            }

            if(showAllPlansDialog){
                ChangePlanDialog(allPlans = allPlans, onDismiss = { showAllPlansDialog = false }) { it, i ->
                    viewModel.planIndex = i
                    viewModel.changeCurPlan(it, i)
                    showAllPlansDialog = false
                }
            }
        }
    }
}

