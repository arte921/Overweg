package arte921.overweg

data class trein (
    val treinNummer: Int,
    val ritId: Int,
    val lat: Float,
    val lng: Float,
    val snelheid: Int,
    val richting: Float,
    val horizontaleNauwkeurigheid: Float,
    val type: String,
    val bron: String
)

data class treinen (
    val treinen: ArrayList<trein>
)

data class Vehicles (
    val links: Leeg,
    val payload: treinen,
    val meta: Leeg
)