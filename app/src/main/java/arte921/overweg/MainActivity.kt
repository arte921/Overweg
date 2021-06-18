package arte921.overweg

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.apache.commons.io.IOUtils
import java.io.InputStream


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gson = Gson()


        val spoorkaartStream: InputStream = resources.openRawResource(R.raw.spoorkaart)
        val spoorkaartJson: String = IOUtils.toString(spoorkaartStream)
        IOUtils.closeQuietly(spoorkaartStream)

        val spoorkaart = gson.fromJson(spoorkaartJson, Spoorkaart::class.java)

        val textView = findViewById<TextView>(R.id.text)

        textView.text = spoorkaart.payload.type

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)

        val url = "https://hastebin.com/raw/ebuhuvunug"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
//                textView.text = response.getString("a")
            },
            { _ ->
                // TODO: Handle error
            }
        )

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
}