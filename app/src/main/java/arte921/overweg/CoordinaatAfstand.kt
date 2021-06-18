package arte921.overweg

import android.location.Location
import java.lang.Math.PI
import kotlin.math.*

const val RADIALENFACTOR = PI / 180.0

fun treinAfstand(gebruikerLocatie: Location, trein: Trein): Int {
    val lat1 = gebruikerLocatie.latitude * RADIALENFACTOR
    val lon1 = gebruikerLocatie.longitude * RADIALENFACTOR
    val lat2 = trein.lat * RADIALENFACTOR
    val lon2 = trein.lng * RADIALENFACTOR

    val dlat = lat1 - lat2
    val dlon = lon1 - lon2

    val a = sin(dlat / 2).pow(2.0) +
            cos(lat1) *
            cos(lat2) *
            sin(dlon / 2).pow(2.0)

    return round(6371.0 * 2.0 * atan2(sqrt(a), sqrt(1.0 - a)) * 1000).toInt()
};