package pmf.it.app.budgettracker.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pmf.it.app.budgettracker.MainActivity
import pmf.it.app.budgettracker.data.PreferencesManager
import pmf.it.app.budgettracker.data.dto.ResponseDTO
import pmf.it.app.budgettracker.data.network.ApiService
import pmf.it.app.budgettracker.ui.Screen
import javax.inject.Inject

@HiltViewModel
class LoginRegViewModel @Inject constructor(
    private val apiService: ApiService,
    private val preferencesManager: PreferencesManager,
) : ViewModel(){
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
                preferencesManager.saveData("token", resp.message)
                preferencesManager.saveData("user", username)
                navController.navigate(Screen.Home.route){
                    popUpTo(Screen.Login.route){
                        inclusive = true
                    }
                }
                MainActivity().token = resp.message
            }
        }
    }
}