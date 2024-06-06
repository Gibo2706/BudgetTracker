package pmf.it.app.budgettracker.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import pmf.it.app.budgettracker.data.PreferencesManager
import pmf.it.app.budgettracker.data.model.Plan
import pmf.it.app.budgettracker.data.model.Prihod
import pmf.it.app.budgettracker.data.model.Trosak
import pmf.it.app.budgettracker.data.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PlanScreenViewModel @Inject constructor(
    private val apiService: ApiService,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    val responseMessage = mutableStateOf("") // response message from the server
    val currentPlan = mutableStateOf(Plan("", emptyList(), emptyList())) // current plan
    var allPlans = mutableStateListOf<Plan>()
    var currentProgress = mutableDoubleStateOf(0.0)

    init {
        val userId = preferencesManager.getData("userId", "1").toLong()
        val token = preferencesManager.getData("token", "")
        if(token.isNotEmpty())
            getAllPlans(userId)
        if(allPlans.isNotEmpty())
            changeCurPlan(allPlans[0])
    }
    fun addPlan(plan: Plan) {
        try {
            val token = preferencesManager.getData("token", "")
            val user = preferencesManager.getData("user", "")
            val userId = preferencesManager.getData("userId", "1").toLong()
            apiService.addPlan(token, user, plan).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            responseMessage.value = it.string()
                            changeCurPlan(plan)
                            getAllPlans(userId)
                        } ?: run {
                            responseMessage.value = "Server error"
                        }
                    } else {
                        responseMessage.value = response.errorBody().toString()

                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    responseMessage.value = t.message.toString()
                }
            })
        }catch (e: Exception){
            responseMessage.value = e.message.toString()
        }
    }

    fun getAllPlans(id: Long){
        viewModelScope.launch {
            try {
                val token = preferencesManager.getData("token", "")
                val user = preferencesManager.getData("user", "")
                val temp = apiService.getAllByUser(token, user, id)
                allPlans.clear()
                allPlans.addAll(temp)
                if (currentPlan.value.name == "" && allPlans.isNotEmpty())
                    changeCurPlan(allPlans[0])
                else if(allPlans.isEmpty())
                    changeCurPlan(Plan("", emptyList(), emptyList()))
            }catch (e: Exception){
                responseMessage.value = e.message.toString()
                Log.d("PlanScreenViewModel", e.message.toString())
            }
        }
    }

    fun changeCurPlan(newPlan: Plan){
        currentPlan.value = newPlan
        currentProgress.doubleValue = (currentPlan.value.prihodi.sumOf { it.amount } * currentPlan.value.timePeriod - currentPlan.value.troskovi.sumOf { it.amount } * currentPlan.value.timePeriod)
    }

    fun addTrosak(trosak: Trosak){
        try {
            val token = preferencesManager.getData("token", "")
            val user = preferencesManager.getData("user", "")
            val userId = preferencesManager.getData("userId", "1").toLong()
            apiService.addTrosak(token, user, currentPlan.value.name, trosak).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            responseMessage.value = it.string()
                            getAllPlans(userId)
                            changeCurPlan(currentPlan.value.copy(troskovi = currentPlan.value.troskovi + trosak))
                        } ?: run {
                            responseMessage.value = "Server error"
                        }
                    } else {
                        responseMessage.value = response.errorBody().toString()

                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    responseMessage.value = t.message.toString()
                }
            })
        }catch (e: Exception){
            responseMessage.value = e.message.toString()
            Log.d("PlanScreenViewModel", e.message.toString())
        }
    }

    fun deleteTrosak(trosak: Trosak){
        try {
            val token = preferencesManager.getData("token", "")
            val user = preferencesManager.getData("user", "")
            val userId = preferencesManager.getData("userId", "1").toLong()
            apiService.deleteTrosak(token, user, currentPlan.value.name, trosak).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            responseMessage.value = it.string()
                            getAllPlans(userId)
                            changeCurPlan(currentPlan.value.copy(troskovi = currentPlan.value.troskovi - trosak))
                        } ?: run {
                            responseMessage.value = "Server error"
                        }
                    } else {
                        responseMessage.value = response.errorBody().toString()

                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    responseMessage.value = t.message.toString()
                }
            })
        }catch (e: Exception){
            responseMessage.value = e.message.toString()
            Log.d("PlanScreenViewModel", e.message.toString())
        }
    }


    fun addPrihod(prihod: Prihod){
        try {
            val token = preferencesManager.getData("token", "")
            val user = preferencesManager.getData("user", "")
            val userId = preferencesManager.getData("userId", "1").toLong()
            apiService.addPrihod(token, user, currentPlan.value.name, prihod).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            responseMessage.value = it.string()
                            getAllPlans(userId)
                            changeCurPlan(currentPlan.value.copy(prihodi = currentPlan.value.prihodi + prihod))
                        } ?: run {
                            responseMessage.value = "Server error"
                        }
                    } else {
                        responseMessage.value = response.errorBody().toString()

                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    responseMessage.value = t.message.toString()
                }
            })
        }catch (e: Exception){
            responseMessage.value = e.message.toString()
            Log.d("PlanScreenViewModel", e.message.toString())
        }
    }

    fun deletePrihod(prihod: Prihod){
        try {
            val token = preferencesManager.getData("token", "")
            val user = preferencesManager.getData("user", "")
            val userId = preferencesManager.getData("userId", "1").toLong()
            apiService.deletePrihod(token, user, currentPlan.value.name, prihod).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            responseMessage.value = it.string()
                            getAllPlans(userId)
                            changeCurPlan(currentPlan.value.copy(prihodi = currentPlan.value.prihodi - prihod))
                        } ?: run {
                            responseMessage.value = "Server error"
                        }
                    } else {
                        responseMessage.value = response.errorBody().toString()

                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    responseMessage.value = t.message.toString()
                }
            })
        }catch (e: Exception){
            responseMessage.value = e.message.toString()
            Log.d("PlanScreenViewModel", e.message.toString())
        }
    }

    fun setGoal(goal: String, timePeriod: Long){
        try {
            val token = preferencesManager.getData("token", "")
            val user = preferencesManager.getData("user", "")
            val userId = preferencesManager.getData("userId", "1").toLong()
            apiService.addGoal(token, user, currentPlan.value.name, goal.toDouble(), timePeriod).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            responseMessage.value = it.string()
                            getAllPlans(userId)
                            changeCurPlan(currentPlan.value.copy(cilj = goal.toDouble(), timePeriod = timePeriod))
                        } ?: run {
                            responseMessage.value = "Server error"
                        }
                    } else {
                        responseMessage.value = response.errorBody().toString()

                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    responseMessage.value = t.message.toString()
                }
            })
        }catch (e: Exception){
            responseMessage.value = e.message.toString()
            Log.d("PlanScreenViewModel", e.message.toString())
        }
    }

}