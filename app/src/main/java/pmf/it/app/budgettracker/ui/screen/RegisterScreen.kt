package pmf.it.app.budgettracker.ui.screen

import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pmf.it.app.budgettracker.data.dto.RegisterDTO
import pmf.it.app.budgettracker.ui.Screen
import pmf.it.app.budgettracker.viewmodel.LoginRegViewModel

@Composable
fun RegisterScreen(
    viewModel: LoginRegViewModel = hiltViewModel(),
    navController: NavController,
) {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val success by viewModel.success
    val focusManager = LocalFocusManager.current
    Surface(
        modifier = Modifier.imePadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Register", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
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
                            if (it.key == Key.Tab && it.nativeKeyEvent.action == KeyEvent.ACTION_DOWN) {
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
                            if(it.key == Key.Tab && it.nativeKeyEvent.action == KeyEvent.ACTION_DOWN){
                                focusManager.moveFocus(FocusDirection.Down)
                                true
                            } else
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
                TextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    modifier = Modifier
                        .padding(16.dp)
                        .onPreviewKeyEvent {
                            if(it.key == Key.Tab && it.nativeKeyEvent.action == KeyEvent.ACTION_DOWN){
                                focusManager.moveFocus(FocusDirection.Down)
                                true
                            } else
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
                    }),
                    isError = password != confirmPassword
                )
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier
                        .padding(16.dp)
                        .onPreviewKeyEvent {
                            if(it.key == Key.Tab && it.nativeKeyEvent.action == KeyEvent.ACTION_DOWN){
                                focusManager.moveFocus(FocusDirection.Down)
                                true
                            } else
                                false
                        },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    isError = (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotEmpty())
                )
                TextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    modifier = Modifier
                        .padding(16.dp)
                        .onPreviewKeyEvent {
                            if(it.key == Key.Tab && it.nativeKeyEvent.action == KeyEvent.ACTION_DOWN){
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
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") },
                    modifier = Modifier
                        .padding(16.dp)
                        .onPreviewKeyEvent {
                            if(it.key == Key.Tab && it.nativeKeyEvent.action == KeyEvent.ACTION_DOWN){
                                focusManager.moveFocus(FocusDirection.Down)
                                true
                            } else
                                false
                        },
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
                Button(onClick = { navController.navigate(Screen.Login.route) }) {
                    Text(text = "Login")
                }
                Button(onClick = {
                    if(password != confirmPassword){
                        Toast.makeText(navController.context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if(userName.isEmpty() || password.isEmpty() || email.isEmpty() || firstName.isEmpty() || lastName.isEmpty()){
                        Toast.makeText(navController.context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    Log.d("RegisterScreen", "Registering $userName, $password, $email, $firstName, $lastName")
                    viewModel.register(
                        RegisterDTO(
                            username = userName,
                            password = password,
                            email = email,
                            firstName = firstName,
                            lastName = lastName
                        ),
                        navController
                    )
                }) {
                    Text(text = "Register")
                }
            }
            if(success){
                Toast.makeText(navController.context, "Successfully registered", Toast.LENGTH_SHORT).show()
            }
        }
    }
}