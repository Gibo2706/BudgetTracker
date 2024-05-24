package pmf.it.app.budgettracker.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import okhttp3.internal.wait
import pmf.it.app.budgettracker.data.model.Plan
import pmf.it.app.budgettracker.data.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PlanScreenViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {
    val responseMessage = mutableStateOf("") // response message from the server
    val currentPlan = mutableStateOf(Plan("", emptyList(), emptyList())) // current plan
    var allPlans = mutableStateListOf<Plan>()

    init {
        getAllPlans(1)
    }
    fun addPlan(plan: Plan) {
        apiService.addPlan(plan).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        responseMessage.value = it.string()
                        currentPlan.value = plan
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
    }

    fun getAllPlans(id: Long){
        GlobalScope.launch {
            allPlans.addAll(apiService.getAllByUser(id))
            allPlans.forEach {
                Log.d("PlanScreenViewModel", it.toString())
            }
        }
    }

    fun changeCurPlan(newPlan: Plan){
        currentPlan.value = newPlan
    }

}