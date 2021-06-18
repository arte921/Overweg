package arte921.overweg

class Leeg

data class FeatureGeometry (
    val type: String,
    val coordinates: ArrayList<Array<Float>>
)

data class FeatureProperties (
    val to: String,
    val from: String
)

data class Feature (
    val type: String,
    val properties: FeatureProperties,
    val geometry: FeatureGeometry
)

data class Spoorkaartpayload (
    val type: String,
    val features: ArrayList<Feature>
)

data class Spoorkaart (
    val links: Leeg ,
    val payload: Spoorkaartpayload,
    val meta: Leeg
)