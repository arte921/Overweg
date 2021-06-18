package arte921.overweg

data class Trein (
    val treinNummer: Int,
    val ritId: Int,
    val lat: Float,
    val lng: Float,
    val snelheid: Float,
    val richting: Float,
    val horizontaleNauwkeurigheid: Float,
    val type: String,
    val bron: String
)

data class Treinen (
    val treinen: ArrayList<Trein>
)

data class Vehicles (
    val links: Leeg,
    val payload: Treinen,
    val meta: Leeg
)