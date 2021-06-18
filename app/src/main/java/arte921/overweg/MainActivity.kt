package arte921.overweg

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.*
import com.google.gson.Gson
import org.apache.commons.io.IOUtils
import java.io.InputStream


class MainActivity : AppCompatActivity() {
    private lateinit var locationCallback: LocationCallback
    private val locationRequest = LocationRequest.create()?.apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.text)
        val nsapi = ""
        val headers = mutableMapOf("Ocp-Apim-Subscription-Key" to nsapi)


        val gson = Gson()
        val spoorkaartStream: InputStream = resources.openRawResource(R.raw.spoorkaart)
        val spoorkaartJson: String = IOUtils.toString(spoorkaartStream)
        IOUtils.closeQuietly(spoorkaartStream)
        val spoorkaart = gson.fromJson(spoorkaartJson, Spoorkaart::class.java)

        val queue = Volley.newRequestQueue(this)



        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    val nsRequestUrl = "https://gateway.apiportal.ns.nl/virtual-train-api/api/vehicle?lat=${location.latitude}&lng=${location.longitude}&radius=10000000&limit=1";

                    queue.add(GsonRequest(nsRequestUrl, Vehicles::class.java, headers, {
                        textView.text = it.payload.treinen[0].treinNummer.toString()
                    }, {
                        textView.text = it.toString()
                    }))

                    textView.text = location.longitude.toString()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

}