package com.example.mealhabittracker.feature_meal.utils

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.util.concurrent.TimeUnit

class WebSocketManager(private val url: String) {
    private val client = OkHttpClient.Builder()
    .readTimeout(3, TimeUnit.SECONDS)
    .build()

    private val request = Request.Builder()
        .url(url)
        .build()

    private val webSocket = client.newWebSocket(request, object : WebSocketListener() {
        override fun onMessage(webSocket: WebSocket, text: String) {
            Log.d("WebSocket", "Receiving : ${text!!}")
            // You can use a callback or LiveData to notify ViewModel about changes
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.d("WebSocket","Error : ${t.message}", t)
        }
    })

    // Method to send messages to the WebSocket server
    fun sendMessage(message: String) {
        webSocket.send(message)
    }

    // Close the WebSocket connection when necessary
    fun closeWebSocket() {
        webSocket.close(1000, null)
    }
}