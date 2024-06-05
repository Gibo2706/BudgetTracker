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
    val token = preferencesManager.getData("token", "")
    val user = preferencesManager.getData("user", "")
    init {
        if(token.isNotEmpty())
            getAllPlans(1)
        if(allPlans.isNotEmpty())
            changeCurPlan(allPlans[0])
    }
    fun addPlan(plan: Plan) {
        try {
            apiService.addPlan(token, user, plan).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            responseMessage.value = it.string()
                            changeCurPlan(plan)
                            getAllPlans(1)
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
                val temp = apiService.getAllByUser(token, user, id)
                allPlans.clear()
                allPlans.addAll(temp)
                if (currentPlan.value.name == "" && allPlans.isNotEmpty())
                    changeCurPlan(allPlans[0])
            }catch (e: Exception){
                responseMessage.value = e.message.toString()
                Log.d("PlanScreenViewModel", e.message.toString())
            }
        }
    }

    fun changeCurPlan(newPlan: Plan, index: Int = 0){
        currentPlan.value = newPlan
        currentProgress.doubleValue = (currentPlan.value.prihodi.sumOf { it.amount } * currentPlan.value.timePeriod - currentPlan.value.troskovi.sumOf { it.amount } * currentPlan.value.timePeriod)
    }

    fun addTrosak(trosak: Trosak){
        try {
            apiService.addTrosak(token, user, currentPlan.value.name, trosak).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            responseMessage.value = it.string()
                            getAllPlans(1)
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
            apiService.deleteTrosak(token, user, currentPlan.value.name, trosak).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            responseMessage.value = it.string()
                            getAllPlans(1)
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
            apiService.addPrihod(token, user, currentPlan.value.name, prihod).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            responseMessage.value = it.string()
                            getAllPlans(1)
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
            apiService.deletePrihod(token, user, currentPlan.value.name, prihod).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            responseMessage.value = it.string()
                            getAllPlans(1)
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
            apiService.addGoal(token, user, currentPlan.value.name, goal.toDouble(), timePeriod).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            responseMessage.value = it.string()
                            getAllPlans(1)
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