package com.example.linah_alkhurayyif_youtubebonusapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

internal class GridAdapter(
    private val context: Context,
    private val videos: ArrayList<videoClass>,
    private val player: YouTubePlayer
) :
    BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private lateinit var video_image: ImageView
    private lateinit var videwTitle: TextView
    lateinit var videoLayout: LinearLayout
    val opendvideos: ArrayList<videoClass> = arrayListOf(videoClass("No-Bake Strawberry Cheesecake＊No egg, No oven｜HidaMari Cooking", "3DJILHsdOlw",   R.drawable.strawberry_cheesecake,false))
    override fun getCount(): Int {
        return videos.size
    }
    override fun getItem(position: Int): Any? {
        return videos[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View? {
        var convertView = convertView
        val currentvideo = videos[position]
        val currentTitle = currentvideo.videoTitle
        val currentImage = currentvideo.videos_image
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.videos_item, null)
        }
        if(convertView != null){
            video_image = convertView!!.findViewById(R.id.video_image)
            videwTitle = convertView!!.findViewById(R.id.videwTitle)
            video_image.setImageResource(currentImage)
            videwTitle.text = currentTitle
            videoLayout = convertView!!.findViewById(R.id.videoLayout)
            videoLayout.setOnClickListener {
                currentvideo.videos_opend = true
                player.loadVideo(currentvideo.videoLink, 0f)
                opendvideos.add(videoClass(currentvideo.videoTitle,currentvideo.videoLink,currentvideo.videos_image,false))
                deleteTask()

            }
        }

        return convertView
    }
    fun deleteTask(){
        videos.add(opendvideos[0])
        opendvideos.removeFirst()
        videos.removeAll{video -> video.videos_opend }
        notifyDataSetChanged()
    }
}