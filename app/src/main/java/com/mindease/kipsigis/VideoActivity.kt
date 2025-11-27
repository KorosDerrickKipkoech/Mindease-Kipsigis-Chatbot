package com.mindease.kipsigis

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindease.kipsigis.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoBinding

    private val videoList = listOf(
        Video("Tienwokik (Songs)", "https://youtu.be/R6d4lAQoiRI?si=-rtllSlvvoaz6UW6", "Tienwokik che kilosunen Jehova"),
        Video("Nerekutik (Sorrow)", "https://youtu.be/dhgMwA6fA6g?si=V7PokOPeWpPAWA_b", "Kaguiyet ab nerekutik"),
        Video("Kaimwotugik (Anxiety)", "https://youtu.be/aKqd75zvpKs?si=5H_amxerMrLHswrH", "Kaguiyet ab kaimwogutik"),
        Video("Katunisiet (Marriage)", "https://youtu.be/lf5wtDIowhw?si=kph8d7zvg-qkHLwj", "Kaguiyet ab katuniet"),
        Video("Chametabke (Self-Love)", "https://youtu.be/rif-vi9YOc8?si=zYo49EpLSLyb0iS4", "Muhimi nebo chametabge"),
        Video("Jarirenet (Laziness)", "https://youtu.be/C3twfP1IDl8?si=H2BIpJ0UoAl4U7iH", "Kebakach jarirenet"),
        Video("Atebet (Morals)", "https://youtu.be/5xSenxx2Z-8?si=v2FST7t5b4HOUiec", "Atebetosiek che kororon"),
        Video("Chorwandit (Friendship)", "https://www.youtube.com/watch?v=IkH0VeRQ_8I", "Kaguiyet ab chorwandishek"),
        Video("Aragenet (Mourning)", "https://youtu.be/46w1RnWlxxk?si=Qh9_iTqS9oWoukPS", "Toretet en aragenet"),
        Video("Stress", "https://youtu.be/4u7PpyZaYaY?si=eta6caJxVr4isjG6", "Amunee si komi stress")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerVideos.layoutManager = LinearLayoutManager(this)
        binding.recyclerVideos.adapter = VideoAdapter(videoList) { video ->
            val i = Intent(this, VideoPlayerActivity::class.java)
            i.putExtra("url", video.url)
            startActivity(i)
        }
    }
}
