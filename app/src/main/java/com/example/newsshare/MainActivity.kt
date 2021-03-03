package com.example.newsshare

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter
    }
    private fun fetchData(){
        val url = "https://spaceflightnewsapi.net/api/v1/articles"
        val jsonRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener {
                Log.d("hello","This is my message")
                val newsJsonArray = it.getJSONArray("docs")
                val newsArray = ArrayList<News>()
                for(i in 0 until 10){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("news_site"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("featured_image")
                    )
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {

                Toast.makeText(this,"Something went Wrong",Toast.LENGTH_SHORT).show()
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonRequest)
    }

    override fun onItemClicked(item: News) {
        Toast.makeText(this,"Welcome to the article by ${item.author}",Toast.LENGTH_SHORT).show()
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}