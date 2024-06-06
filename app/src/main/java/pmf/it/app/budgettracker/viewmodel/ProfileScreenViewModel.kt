package pmf.it.app.budgettracker.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pmf.it.app.budgettracker.data.PreferencesManager
import pmf.it.app.budgettracker.data.model.User
import pmf.it.app.budgettracker.data.network.ApiService
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val apiService: ApiService,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {

    val user = mutableStateOf(User("", "", "", ""))

    init {
        getUserData()
    }

    fun logout(navController: NavController) {
        preferencesManager.saveData("token", "")
        preferencesManager.saveData("user", "")
        preferencesManager.saveData("userId", "")
        navController.navigate("login"){
            popUpTo("home"){
                inclusive = true
            }
        }
    }

    private fun getUserData(){
        val userName = preferencesManager.getData("user", "")
        val token = preferencesManager.getData("token", "")
        viewModelScope.launch {
            try{
                apiService.getUser(token, userName).let {
                    user.value = it
                }
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateUserData(user: User) {
        val userName = preferencesManager.getData("user", "")
        val token = preferencesManager.getData("token", "")
        viewModelScope.launch {
            try{
                apiService.updateUser(token, userName, user).let {
                    this@ProfileScreenViewModel.user.value = it;
                    preferencesManager.saveData("user", it.username)
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}