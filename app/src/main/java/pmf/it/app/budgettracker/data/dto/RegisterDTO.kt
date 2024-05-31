package pmf.it.app.budgettracker.data.dto

data class RegisterDTO(
    val username: String,
    val password: String,
    val email: String,
    val firstName: String,
    val lastName: String
)
