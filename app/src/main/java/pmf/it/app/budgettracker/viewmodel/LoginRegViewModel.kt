package pmf.it.app.budgettracker.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pmf.it.app.budgettracker.MainActivity
import pmf.it.app.budgettracker.data.PreferencesManager
import pmf.it.app.budgettracker.data.dto.RegisterDTO
import pmf.it.app.budgettracker.data.dto.ResponseDTO
import pmf.it.app.budgettracker.data.network.ApiService
import pmf.it.app.budgettracker.ui.Screen
import javax.inject.Inject

@HiltViewModel
class LoginRegViewModel @Inject constructor(
    private val apiService: ApiService,
    private val preferencesManager: PreferencesManager,
) : ViewModel(){
    val success = mutableStateOf(false)
    fun login(username: String, password: String, navController: NavController){
        Log.d("LoginRegViewModel", "login: $username, $password")
        viewModelScope.launch {
            var resp = ResponseDTO("", "")
            try {
                resp = apiService.login(username, password)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (resp.status == "Successfully") {
                val mess = resp.message.split("|")
                Log.d("LoginRegViewModel", "login: ${mess[0]} ${mess[1]}")
                preferencesManager.saveData("token", mess[0])
                preferencesManager.saveData("userId", mess[1])
                preferencesManager.saveData("user", username)
                navController.navigate(Screen.Home.route){
                    popUpTo(Screen.Login.route){
                        inclusive = true
                    }
                }
                success.value = true
            }else
                success.value = false
        }
    }

    fun register(regUser: RegisterDTO, navController: NavController){
        viewModelScope.launch {
            var resp = ResponseDTO("", "")
            try {
                resp = apiService.register(regUser)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (resp.status == "Successfully") {
                val mess = resp.message.split("|")
                Log.d("LoginRegViewModel", "register: ${mess[0]} ${mess[1]}")
                preferencesManager.saveData("token", mess[0])
                preferencesManager.saveData("userId", mess[1])
                preferencesManager.saveData("user", regUser.username)
                navController.navigate(Screen.Home.route){
                    popUpTo(Screen.Register.route){
                        inclusive = true
                    }
                }
                success.value = true
            }else
                success.value = false
        }
    }
}