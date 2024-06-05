package pmf.it.app.budgettracker.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pmf.it.app.budgettracker.data.PreferencesManager
import pmf.it.app.budgettracker.data.network.ApiService
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val apiService: ApiService,
    private val preferencesManager: PreferencesManager
) : ViewModel() {


    fun logout() {
        preferencesManager.saveData("token", "")
        preferencesManager.saveData("user", "")
    }

}