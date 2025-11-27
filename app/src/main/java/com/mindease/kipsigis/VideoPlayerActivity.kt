package com.mindease.kipsigis

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.mindease.kipsigis.databinding.ActivityVideoPlayerBinding

class VideoPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoPlayerBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra("url") ?: ""

        binding.webVideo.settings.javaScriptEnabled = true
        binding.webVideo.webViewClient = WebViewClient()
        binding.webVideo.webChromeClient = WebChromeClient()

        val embedUrl = url.replace("watch?v=", "embed/").replace("youtu.be/", "youtube.com/embed/")
        binding.webVideo.loadUrl(embedUrl)
    }
}
