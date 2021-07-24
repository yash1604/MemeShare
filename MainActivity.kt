package com.example.memesharing


import android.app.DownloadManager
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Request.Method.GET
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var currentUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()
    }
    private  fun loadMeme()
    {
        findViewById<View>(R.id.loader).visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"


        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                 currentUrl = response.getString("url")
                Glide.with(this).load(currentUrl).listener(object: RequestListener<Drawable>{

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        findViewById<View>(R.id.loader).visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        findViewById<View>(R.id.loader).visibility = View.GONE
                        return false

                    }

                }).into(findViewById(R.id.memeImage))
            },
            {
                Toast.makeText(this, "Something wrong", Toast.LENGTH_LONG).show()
            })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
    fun nextClick(view: View) {
        loadMeme()
    }
    fun shareClick(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/*"
        intent.putExtra(Intent.EXTRA_TEXT, "$currentUrl")
        val chooser = Intent.createChooser(intent,"Share using" )
        startActivity(chooser)
    }
}