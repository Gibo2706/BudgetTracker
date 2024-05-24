package pmf.it.app.budgettracker.data.network

import okhttp3.ResponseBody
import pmf.it.app.budgettracker.data.model.Plan
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("auth/login")
    suspend fun login(username: String, password: String): String

    @POST("plan/add")
    @Headers("Accept: text/plain")
    fun addPlan(@Body plan: Plan): Call<ResponseBody>

    @GET("plan/allByUser/{id}")
    suspend fun getAllByUser(@Path("id") id: Long): List<Plan>
}