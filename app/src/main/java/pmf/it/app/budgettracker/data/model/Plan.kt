package pmf.it.app.budgettracker.data.model

data class Plan (
    val name: String,
    val troskovi: List<Trosak> = emptyList(),
    val prihodi: List<Prihod> = emptyList(),
    val cilj: Number? = null,
    val timePeriod: Long = 0,
    val korisnik: User? = null
){

}