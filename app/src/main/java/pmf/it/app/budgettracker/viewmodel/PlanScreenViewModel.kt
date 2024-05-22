package pmf.it.app.budgettracker.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun addPlan(plan: Plan) {
        apiService.addPlan(plan).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        responseMessage.value = it.string()
                        currentPlan.value = plan
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
}