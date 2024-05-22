package pmf.it.app.budgettracker.data.network

    import okhttp3.ResponseBody
    import pmf.it.app.budgettracker.data.model.Plan
    import retrofit2.Call
    import retrofit2.Response
    import retrofit2.http.Body
    import retrofit2.http.Headers
    import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(username: String, password: String): String

    @POST("plan/add")
    @Headers("Accept: text/plain")
    fun addPlan(@Body plan: Plan): Call<ResponseBody>
}