package com.mukuljoshi.meamsapp

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var textView:TextView
    var currentImageUrl:String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.title)

        viewMeme()
    }



    private  fun  viewMeme(){

        //progress bar visible
        progressa.visibility= View.VISIBLE


        val url = "https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                currentImageUrl = response.getString("url")
                val title = response.getString("title")
                Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable>{


                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                     progressa.visibility= View.GONE
                        return  false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressa.visibility = View.GONE
                        return false
                    }

                })
                    .into(imageView)
                textView.text = "Title = ${title}"
            },
            {
                Toast.makeText(this, "Something want wrong", Toast.LENGTH_SHORT).show()
            })


        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }


    fun nextMene(view: View) {

        viewMeme()
    }
    fun shrMene(view: View) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, checkout this is cool meme $currentImageUrl")
        val chooser = Intent.createChooser(intent,"Share this is meme using...")
        startActivity(chooser)

    }
}