package pmf.it.app.budgettracker.data.model

data class Plan (val name: String, val trosak: List<Trosak> = emptyList(), val prihod: List<Prihod> = emptyList(), val user: User){

}