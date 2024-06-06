package pmf.it.app.budgettracker.ui.screen

import android.util.Log
import android.view.KeyEvent.ACTION_DOWN
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pmf.it.app.budgettracker.ui.Screen
import pmf.it.app.budgettracker.viewmodel.LoginRegViewModel

@Composable
fun LoginScreen(
    viewModel: LoginRegViewModel = hiltViewModel(),
    navController: NavController,
){
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val success by viewModel.success
    Surface(
        modifier = Modifier.imePadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Login", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
                HorizontalDivider()
            }
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text("Username") },
                    modifier = Modifier
                        .padding(16.dp)
                        .onPreviewKeyEvent {
                            if (it.key == Key.Tab && it.nativeKeyEvent.action == ACTION_DOWN) {
                                focusManager.moveFocus(FocusDirection.Down)
                                true
                            } else
                                false
                        },
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    })
                )
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier
                        .padding(16.dp)
                        .onPreviewKeyEvent {
                               if(it.key == Key.Tab && it.nativeKeyEvent.action == ACTION_DOWN){
                                   focusManager.moveFocus(FocusDirection.Down)
                                   true
                               }else
                                   false
                        },
                    visualTransformation = if(showPassword){
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            if(showPassword)
                                Icon(imageVector = Icons.Outlined.VisibilityOff, contentDescription = "show_password")
                            else
                                Icon(imageVector = Icons.Outlined.Visibility, contentDescription = "hide_password")
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    })

                )
            }
            Row (
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                Button(onClick = { navController.navigate(Screen.Register.route) }) {
                    Text(text = "Register")
                }
                Button(onClick = {
                    if(userName.isEmpty() || password.isEmpty()){
                        Toast.makeText(context, "Username or password is empty", Toast.LENGTH_SHORT).show()
                        Log.d("LoginScreen", "Username or password is empty")
                        return@Button
                    }
                    viewModel.login(userName, password, navController)
                }) {
                    Text(text = "Login")
                }
            }
            if(success){
                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
            }
        }
    }
}