package pmf.it.app.budgettracker.data.network

import okhttp3.ResponseBody
import pmf.it.app.budgettracker.data.dto.ResponseDTO
import pmf.it.app.budgettracker.data.model.Plan
import pmf.it.app.budgettracker.data.model.Prihod
import pmf.it.app.budgettracker.data.model.Trosak
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("auth/login")
    suspend fun login(@Query("username") username: String, @Query("password") password: String): ResponseDTO

    @POST("plan/add")
    @Headers("Accept: text/plain")
    fun addPlan(@Header("token") token:String, @Header("user") user:String, @Body plan: Plan): Call<ResponseBody>

    @GET("plan/allByUser/{id}")
    suspend fun getAllByUser(@Header("token") token:String, @Header("user") user:String, @Path("id") id: Long): List<Plan>

    @PUT("plan/addTrosak")
    fun addTrosak(@Header("token") token:String, @Header("user") user:String, @Query("plan") plan: String, @Body trosak: Trosak): Call<ResponseBody>

    @PUT("plan/addPrihod")
    fun addPrihod(@Header("token") token:String, @Header("user") user:String, @Query("plan") plan: String, @Body prihod: Prihod): Call<ResponseBody>

    @PUT("plan/addGoal")
    fun addGoal(@Header("token") token:String, @Header("user") user:String, @Query("plan") plan: String, @Query("goal") goal: Number, @Query("timePeriod") timePeriod: Long): Call<ResponseBody>

    @POST("plan/deleteTrosak")
    fun deleteTrosak(@Header("token") token:String, @Header("user") user:String, @Query("plan") plan: String, @Body trosak: Trosak): Call<ResponseBody>

    @POST("plan/deletePrihod")
    fun deletePrihod(@Header("token") token:String, @Header("user") user:String, @Query("plan") plan: String, @Body prihod: Prihod): Call<ResponseBody>
}