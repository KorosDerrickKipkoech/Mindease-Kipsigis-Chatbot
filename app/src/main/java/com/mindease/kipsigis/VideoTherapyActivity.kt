package com.mindease.kipsigis

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.mindease.kipsigis.databinding.ActivityVideoTherapyBinding

class VideoTherapyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoTherapyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoTherapyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = WebViewClient()

        binding.webView.loadUrl("https://www.youtube.com/watch?v=inpok4MKVLM") // relaxation video
    }
}
