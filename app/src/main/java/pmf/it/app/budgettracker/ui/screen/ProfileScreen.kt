package pmf.it.app.budgettracker.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pmf.it.app.budgettracker.R
import pmf.it.app.budgettracker.data.model.User
import pmf.it.app.budgettracker.viewmodel.ProfileScreenViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val user = viewModel.user
    val showEditDialog = remember {
        mutableStateOf(false)
    }
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.surface, CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
            ){
                Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription =  "Profile Picture")
            }
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(text = "Profile", style = MaterialTheme.typography.titleLarge)
                HorizontalDivider(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .fillMaxWidth()
                )
                Text(text = "Username: ${user.value.username}", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(8.dp))
                Text(text = "Name: ${user.value.name} ${user.value.surname}", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(8.dp))
                Text(text = "Email: ${user.value.email}", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(8.dp))
            }
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { viewModel.logout(navController) },
                    modifier = Modifier
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.surface),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)

                ) {
                    Text(text = "Logout")
                }
                Button(
                    onClick = { showEditDialog.value = true},
                    modifier = Modifier
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.surface),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)

                ) {
                    Text(text = "Edit")
                }

            }
            if(showEditDialog.value){
                EditProfileDialog(
                    user = user.value,
                    onDismiss = { showEditDialog.value = false },
                    onEdit = { user:User -> viewModel.updateUserData(user) }
                )
            }
        }
    }
}

@Composable
fun EditProfileDialog(user: User, onDismiss: () -> Unit, onEdit: (User) -> Unit) {
    var username by remember{ mutableStateOf(user.username) }
    var name by remember{ mutableStateOf(user.name) }
    var surname by remember{ mutableStateOf(user.surname) }
    var email by remember{ mutableStateOf(user.email) }

    AlertDialog(
        modifier = Modifier
            .imePadding(),
        onDismissRequest = onDismiss,
        confirmButton = { onEdit(User(username, name, surname, email)) },
        title = { Text(text = "Edit profile") },
        text = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    modifier = Modifier.padding(16.dp)
                )
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.padding(16.dp)
                )
                TextField(
                    value = surname,
                    onValueChange = { surname = it },
                    label = { Text("Surname") },
                    modifier = Modifier.padding(16.dp)
                )
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    )
}
