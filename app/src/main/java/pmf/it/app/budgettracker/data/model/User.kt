package pmf.it.app.budgettracker.data.model

data class User(val username: String, val email: String, val name: String, val surname: String) {
    val fullName: String
        get() = "$name $surname"
}
