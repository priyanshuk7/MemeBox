package com.example.memebox

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
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

    var currentImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()
    }

    private fun loadMeme(){



        currentImageUrl = "https://meme-api.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, currentImageUrl, null,
            Response.Listener { response ->
                currentImageUrl = response.getString("url")
                Glide.with(this).load(currentImageUrl).into(findViewById(R.id.memeImageView))
            },
            Response.ErrorListener {

            })

// Add the request to the RequestQueue.
       MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    fun shareImage(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey! Checkout this cool meme I got\n\n $currentImageUrl")
        val chooser = Intent.createChooser(intent, "Share this meme using>>>")
        startActivity(chooser)
    }
    fun nextImage(view: View) {
        loadMeme()
    }

}