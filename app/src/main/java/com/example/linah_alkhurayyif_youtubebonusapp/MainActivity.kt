package com.example.linah_alkhurayyif_youtubebonusapp

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AlertDialog
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class MainActivity : AppCompatActivity() {
    private lateinit var youTubePlayerView: YouTubePlayerView
    private lateinit var player: YouTubePlayer
    private val videos: ArrayList<videoClass> = arrayListOf(
        videoClass("How to Wrap Korean Gimbab | Beautiful Gimbab | Vegetable Gimbab", "9HDQ84KksLU", R.drawable.gimbab,false),
        videoClass("Tantanmen Ramen Recipe :: Really Easy Asian Ramen Recipe", "NiPZY3UwQn0", R.drawable.ramen,false),
        videoClass("Famous Korean Cheese Corn Dog Recipe :: Myungrang Hot Dog Recipe :: Korean Street Food", "-p7YMtvYvSM",  R.drawable.cheese,false),
        videoClass("It's getting hot in Korea Definitely need more Bingsu | Cafe vlog by Zoe!", "CKdCdwYjHy0",  R.drawable.cafe,false),
    )
    private var currentVideo = 0
    private var timeStamp = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkInternetConnection()
        youTubePlayerView = findViewById(R.id.youtube_player_view)
        youTubePlayerView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                player = youTubePlayer
                player.loadVideo(videos[currentVideo].videoLink, timeStamp)
                initializeRV()
            }
        })
    }
    private fun initializeRV(){
        val gridView: GridView = findViewById(R.id.gridView)
        gridView.adapter = GridAdapter(this@MainActivity, videos, player)
    }


    private fun checkInternetConnection(){
        if(!connectedToInternet()){
            AlertDialog.Builder(this)
                .setTitle("No Internet Connection")
                .setPositiveButton("Try again"){_, _ -> checkInternetConnection()}
                .show()
        }
    }
    private fun connectedToInternet(): Boolean{
        val connectivity_S = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivity_S.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("currentVideo", currentVideo)
        outState.putFloat("timeStamp", timeStamp)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentVideo = savedInstanceState.getInt("currentVideo", 0)
        timeStamp = savedInstanceState.getFloat("timeStamp", 0f)
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            youTubePlayerView.enterFullScreen()
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            youTubePlayerView.exitFullScreen()
        }
    }
}