package com.example.kotlinrss

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView_main.layoutManager = LinearLayoutManager(this)
        //recyclerView_main.adapter = MainAdapter(item)

        fetchJson()


    }

    fun fetchJson(){

        val url = "https://api.rss2json.com/v1/api.json?rss_url=https://rss.nytimes.com/services/xml/rss/nyt/Science.xml"
        //https://skythewood.blogspot.com/feeds/posts/default
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("failed to fetch json")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val feed = gson.fromJson(body, Items::class.java)
                println(body)

                runOnUiThread { recyclerView_main.adapter = MainAdapter(feed) }

            }
        })
    }


}




class Items(val items:List<ItemList>)
class ItemList (val title:String, val pubDate:String, val link:String, val guid:String, val author:String,
                val thumbnail:String, val description:String, val content:String, val enclousure:List<String>,
                val categories:List<String>)

