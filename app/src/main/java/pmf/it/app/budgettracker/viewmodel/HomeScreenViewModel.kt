package pmf.it.app.budgettracker.viewmodel

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import pmf.it.app.budgettracker.data.PreferencesManager
import pmf.it.app.budgettracker.data.model.Plan
import pmf.it.app.budgettracker.data.network.ApiService
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val apiService: ApiService,
    preferencesManager: PreferencesManager
) : ViewModel() {
    var isUserLoggedIn = false
    var currentPlan = mutableStateOf(Plan("", emptyList(), emptyList()))
    var currentProgress = mutableDoubleStateOf(0.0)
    var allPlans = mutableStateListOf<Plan>()
    var planIndex: Int = 0;
    val token = preferencesManager.getData("token", "")
    val user = preferencesManager.getData("user", "")
    init {
        if(token.isNotEmpty())
            getAllPlans(1);
    }



    fun checkIfUserIsLoggedIn() {
       //isUserLoggedIn = apiService.isUserLoggedIn()
    }

    fun changeCurPlan(newPlan: Plan, index: Int = 0){
        planIndex = index
        currentPlan.value = newPlan
        currentProgress.doubleValue =
                currentPlan.value.prihodi.sumOf { it.amount } * currentPlan.value.timePeriod -
                    currentPlan.value.troskovi.sumOf { it.amount } * currentPlan.value.timePeriod
    }

    fun getAllPlans(userId: Int){
        viewModelScope.launch {
            try {
                var temp = apiService.getAllByUser(token, user, userId.toLong())
                allPlans.clear()
                allPlans.addAll(temp)
                if (currentPlan.value.name == "" && allPlans.isNotEmpty())
                    changeCurPlan(allPlans[planIndex])
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}