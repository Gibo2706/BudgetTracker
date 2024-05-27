package pmf.it.app.budgettracker.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.DeleteForever
import androidx.compose.material.icons.sharp.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pmf.it.app.budgettracker.data.model.Plan
import pmf.it.app.budgettracker.data.model.Prihod
import pmf.it.app.budgettracker.data.model.Trosak
import pmf.it.app.budgettracker.data.model.User
import pmf.it.app.budgettracker.viewmodel.PlanScreenViewModel

@Composable
@Preview(showBackground = true, backgroundColor = 0x000000, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PlanScreen(
    viewModel: PlanScreenViewModel = hiltViewModel(),
) {
    val showAddGoalDialog = remember { mutableStateOf(false) }
    val showAddIncomeDialog = remember { mutableStateOf(false) }
    val showAddExpensesDialog = remember { mutableStateOf(false) }
    val showChangePlanDialog = remember { mutableStateOf(false) }
    val showNewPlanDialog = remember { mutableStateOf(false) }
    val showPrihodListDialog = remember { mutableStateOf(false) }
    val showTrosakListDialog = remember { mutableStateOf(false) }
    val statusSnackBar by viewModel.responseMessage
    val currentPlan by viewModel.currentPlan
    val context = LocalContext.current
    val allPlans = viewModel.allPlans
    val currentProgress by viewModel.currentProgress
    Surface {
        LaunchedEffect(statusSnackBar) {
            if(statusSnackBar.isNotEmpty()) {
                Toast.makeText(context, statusSnackBar, Toast.LENGTH_LONG).show()
                viewModel.responseMessage.value = ""
            }
        }
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
                Text(text = currentPlan.name, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(24.dp))
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
                    Text(text = "${currentPlan.prihodi.size}", style = MaterialTheme.typography.titleLarge)
                    SmallFloatingActionButton(onClick = {
                        showPrihodListDialog.value = true
                    }) {
                        Icon(Icons.Sharp.List, contentDescription = "List")
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Expenses", style = MaterialTheme.typography.titleLarge)
                    Text(text = "${currentPlan.troskovi.size}", style = MaterialTheme.typography.titleLarge)
                    SmallFloatingActionButton(onClick = {
                        showTrosakListDialog.value = true
                    }) {
                        Icon(Icons.Sharp.List, contentDescription = "List")
                    }
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
                    Text(text = "Goal (${currentPlan.timePeriod} months)", style = MaterialTheme.typography.titleLarge)
                    Text(text = "${currentProgress}/${currentPlan.cilj}", style = MaterialTheme.typography.titleLarge)
                }
            }
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Button(onClick = { showAddIncomeDialog.value = true }) {
                    Text(text = "Add Income", style = MaterialTheme.typography.titleMedium)
                }
                Button(onClick = { showAddExpensesDialog.value = true}) {
                    Text(text = "Add Expenses", style = MaterialTheme.typography.titleMedium)
                }
            }
            Button(onClick = { showAddGoalDialog.value = true}) {
                Text(text = "Add Goal", style = MaterialTheme.typography.titleMedium)
            }
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FloatingActionButton(
                    onClick = {
                        showChangePlanDialog.value = true
                        viewModel.getAllPlans(1)
                    }) {
                    Text(text = "List", style = MaterialTheme.typography.titleMedium)
                }
                FloatingActionButton(
                    onClick = { showNewPlanDialog.value = true }) {
                    Text(text = "New", style = MaterialTheme.typography.titleMedium)
                }
            }

            if (showAddGoalDialog.value) {
                AddGoalDialog(
                    onDismiss = { showAddGoalDialog.value = false },
                    onAdd = { p1, p2 ->
                        viewModel.setGoal(p1, p2)
                        showAddGoalDialog.value = false
                    }
                )
            }
            if (showAddIncomeDialog.value) {
                AddIncomeDialog(
                    onDismiss = { showAddIncomeDialog.value = false },
                    onAdd = {
                        viewModel.addPrihod(it)
                        showAddIncomeDialog.value = false
                    }
                )
            }
            if (showAddExpensesDialog.value) {
                AddExpensesDialog(
                    onDismiss = { showAddExpensesDialog.value = false },
                    onAdd = {
                        viewModel.addTrosak(it)
                        showAddExpensesDialog.value = false
                    }
                )
            }
            if (showChangePlanDialog.value) {
                ChangePlanDialog(
                    allPlans = allPlans.toList(),
                    onDismiss = { showChangePlanDialog.value = false },
                    onChange = {it, i ->
                        showChangePlanDialog.value = false
                        viewModel.changeCurPlan(it, i)
                    }
                )
            }
            if (showNewPlanDialog.value) {
                NewPlanDialog(
                    onDismiss = { showNewPlanDialog.value = false },
                    onAdd = {
                        viewModel.addPlan(it)
                        showNewPlanDialog.value = false
                    }
                )
            }
            if (showPrihodListDialog.value) {
                PrihodListDialog(
                    plan = currentPlan,
                    onDismiss = { showPrihodListDialog.value = false },
                    onDelete = {
                        showPrihodListDialog.value = false
                        viewModel.deletePrihod(it)
                    }
                )
            }
            if (showTrosakListDialog.value) {
                TrosakListDialog(
                    plan = currentPlan,
                    onDismiss = { showTrosakListDialog.value = false },
                    onDelete = {
                        showTrosakListDialog.value = false
                        viewModel.deleteTrosak(it)
                    }
                )
            }
        }
    }
}

@Composable
fun AddGoalDialog(onDismiss: () -> Unit, onAdd: (String, Long) -> Unit) {
    var goal by remember { mutableStateOf("") }
    var timePeriod by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Goal") },
        text = {
               Column(
                     modifier = Modifier.padding(16.dp),
                     verticalArrangement = Arrangement.Top,
                     horizontalAlignment = Alignment.CenterHorizontally
                ) {
                   Text(text = "Change your current goal", style = MaterialTheme.typography.titleMedium)
                    TextField(
                        value = goal,
                        onValueChange = { goal = it },
                        label = { Text("Goal") },
                        modifier = Modifier.padding(16.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    TextField(
                        value = timePeriod,
                        onValueChange = { timePeriod = it },
                        label = { Text("Time period") },
                        modifier = Modifier.padding(16.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
               },
        confirmButton = {
            Button(onClick = { onAdd(goal, timePeriod.toLong()) }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun AddIncomeDialog(onDismiss: () -> Unit, onAdd: (Prihod) -> Unit = {}) {
    var income by remember { mutableStateOf("") }
    var incomeAmount by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Income") },
        text = {
                Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "Add income to your current plan.", style = MaterialTheme.typography.titleMedium)
                    TextField(
                        value = income,
                        onValueChange = { income = it },
                        label = { Text("Income") },
                        modifier = Modifier.padding(16.dp)
                    )
                    TextField(
                        value = incomeAmount,
                        onValueChange = { incomeAmount = it },
                        label = { Text("Amount") },
                        modifier = Modifier.padding(16.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
               },
        confirmButton = {
            Button(onClick = { onAdd(Prihod(incomeAmount.toDouble(), income)) }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun AddExpensesDialog(onDismiss: () -> Unit, onAdd: (Trosak) -> Unit = {}) {
    var expenses by remember { mutableStateOf("") }
    var expensesAmount by remember { mutableStateOf("") }
    var isImpulse by remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Expenses") },
        text = {
                Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "Add expenses to your current plan.", style = MaterialTheme.typography.titleMedium)
                    TextField(
                        value = expenses,
                        onValueChange = { expenses = it },
                        label = { Text("Expenses") },
                        modifier = Modifier.padding(16.dp)
                    )
                    TextField(
                        value = expensesAmount,
                        onValueChange = { expensesAmount = it },
                        label = { Text("Amount") },
                        modifier = Modifier.padding(16.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(text = "Is it impulse purchase?", style = MaterialTheme.typography.titleMedium)
                        Checkbox(
                            checked = isImpulse,
                            onCheckedChange = { isImpulse = it },
                        )
                    }
                }
               },
        confirmButton = {
            Button(onClick = { onAdd(Trosak(expensesAmount.toDouble(), expenses, isImpulse)) }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun ChangePlanDialog(
    allPlans: List<Plan>,
    onDismiss: () -> Unit,
    onChange: (Plan, Int) -> Unit = {_,_ -> }
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Change Plan") },
        text = {
            LazyColumn {
                if(allPlans.isNotEmpty() ){
                    itemsIndexed(allPlans) {i, it ->
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Text(text = it.name)
                            Button(onClick = { onChange(it, i)  }) {
                                Text(text = "Select")
                            }
                        }
                    }
                }else{
                    item {
                        Text(text = "Either you don't have any plan or it's still loading!")
                    }
                }
            }
        },
        confirmButton = { },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun NewPlanDialog(onDismiss: () -> Unit, onAdd: (Plan) -> Unit = {}) {
    var plan by remember { mutableStateOf("") }
    var goal by remember { mutableStateOf("") }
    var timePeriod by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Plan") },
        text = {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Create a new plan.", style = MaterialTheme.typography.titleMedium)
                TextField(
                    value = plan,
                    onValueChange = { plan  = it },
                    label = { Text("Title of plan") },
                    modifier = Modifier.padding(16.dp),
                )
                TextField(
                    value = goal,
                    onValueChange = { goal = it },
                    label = { Text("Goal") },
                    modifier = Modifier.padding(16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                TextField(
                    value = timePeriod,
                    onValueChange = { timePeriod = it },
                    label = { Text("Time period") },
                    modifier = Modifier.padding(16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

        },
        confirmButton = {
            Button(onClick = {  // TODO: Add user
                onAdd(Plan(
                    plan,
                    emptyList(),
                    emptyList(),
                    goal.toDouble(),
                    timePeriod.toLong(),
                    User(
                        "username",
                        "Test@test.com",
                        "Test",
                        "Testovic"
                    )
                ))
            }) {
                Text("Create")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun PrihodListDialog(
    plan: Plan,
    onDismiss: () -> Unit,
    onDelete: (Prihod) -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Change Plan") },
        text = {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(plan.prihodi.isNotEmpty())
                    items(plan.prihodi) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Text(text = "${it.name} - ${it.amount}")
                            SmallFloatingActionButton(onClick = { onDelete(it)  }) {
                                Icon(Icons.Sharp.DeleteForever, contentDescription = "Delete")
                            }
                        }
                    }
                else
                    item {
                        Text(text = "No income added yet!")
                    }

            }
        },
        confirmButton = {  },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun TrosakListDialog(
    plan: Plan,
    onDismiss: () -> Unit,
    onDelete: (Trosak) -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Change Plan") },
        text = {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(plan.troskovi.isNotEmpty())
                    items(plan.troskovi) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Text(text = "${it.name} - ${it.amount}")
                            SmallFloatingActionButton(onClick = { onDelete(it)  }) {
                                Icon(Icons.Sharp.DeleteForever, contentDescription = "Delete")
                            }
                        }
                    }
                else
                    item {
                        Text(text = "No expenses added yet!")
                    }

            }
        },
        confirmButton = {  },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}