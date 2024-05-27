package pmf.it.app.budgettracker.data.network

import okhttp3.ResponseBody
import pmf.it.app.budgettracker.data.model.Plan
import pmf.it.app.budgettracker.data.model.Prihod
import pmf.it.app.budgettracker.data.model.Trosak
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("auth/login")
    suspend fun login(username: String, password: String): String

    @POST("plan/add")
    @Headers("Accept: text/plain")
    fun addPlan(@Body plan: Plan): Call<ResponseBody>

    @GET("plan/allByUser/{id}")
    suspend fun getAllByUser(@Path("id") id: Long): List<Plan>

    @PUT("plan/addTrosak")
    fun addTrosak(@Query("plan") plan: String, @Body trosak: Trosak): Call<ResponseBody>

    @PUT("plan/addPrihod")
    fun addPrihod(@Query("plan") plan: String, @Body prihod: Prihod): Call<ResponseBody>

    @PUT("plan/addGoal")
    fun addGoal(@Query("plan") plan: String, @Query("goal") goal: Number, @Query("timePeriod") timePeriod: Long): Call<ResponseBody>

    @POST("plan/deleteTrosak")
    fun deleteTrosak(@Query("plan") plan: String, @Body trosak: Trosak): Call<ResponseBody>

    @POST("plan/deletePrihod")
    fun deletePrihod(@Query("plan") plan: String, @Body prihod: Prihod): Call<ResponseBody>
}