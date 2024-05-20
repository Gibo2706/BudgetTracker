package pmf.it.app.budgettracker.data.network

    import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(username: String, password: String): String
}