package com.mindease.kipsigis

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindease.kipsigis.databinding.ActivityChatBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var responsesJson: JSONObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        chatAdapter = ChatAdapter(mutableListOf())
        binding.recyclerChat.layoutManager = LinearLayoutManager(this)
        binding.recyclerChat.adapter = chatAdapter

        // Load dataset
        responsesJson = loadResponses()

        // Send button
        binding.sendButton.setOnClickListener {
            val text = binding.inputBox.text.toString().trim()
            if (text.isNotEmpty()) {
                sendUserMessage(text)
                binding.inputBox.text.clear()
                getBotResponse(text)
            }
        }
    }

    private fun loadResponses(): JSONObject {
        val json = assets.open("responses.json").bufferedReader().use { it.readText() }
        return JSONObject(json)
    }

    private fun sendUserMessage(text: String) {
        chatAdapter.addMessage(ChatMessage(text, true))
        binding.recyclerChat.scrollToPosition(chatAdapter.itemCount - 1)
    }

    private fun sendBotMessage(text: String) {
        chatAdapter.addMessage(ChatMessage(text, false))
        binding.recyclerChat.scrollToPosition(chatAdapter.itemCount - 1)
    }

    private fun getBotResponse(userMsg: String) {

        // SHOW “typing…” indicator
        runOnUiThread {
            binding.typingIndicator.visibility = View.VISIBLE
        }

        val prompt = """
You are a response selector. 
You DO NOT generate new Kipsigis sentences.
You ONLY choose an answer from the provided dataset.

DATASET:
${responsesJson.toString()}

USER MESSAGE (in Kipsigis):
"$userMsg"

TASK:
1. Read the user message as raw text (do NOT try to understand the language).
2. Match the message with the keywords inside each category.
3. Select the closest matching category.
4. Choose ONE answer from the list.
5. Output ONLY that Kipsigis response.
""".trimIndent()

        val request = GeminiRequest(
            contents = listOf(
                GeminiContent(
                    parts = listOf(GeminiPart(prompt))
                )
            )
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Small delay to make typing effect realistic
                delay(600)

                val result = RetrofitClient.api.generate(
                    apiKey = BuildConfig.GEMINI_API_KEY,
                    body = request
                )

                val reply = result.candidates[0].content.parts[0].text

                withContext(Dispatchers.Main) {
                    // HIDE typing animation
                    binding.typingIndicator.visibility = View.GONE

                    // Show bot reply
                    sendBotMessage(reply)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    binding.typingIndicator.visibility = View.GONE
                    sendBotMessage("Kabit tabu ketien kora")
                }
            }
        }
    }
}
