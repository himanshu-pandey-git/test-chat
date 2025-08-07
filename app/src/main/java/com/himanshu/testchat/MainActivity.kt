package com.himanshu.testchat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.cometchat.chat.core.CometChat
import com.cometchat.chat.exceptions.CometChatException
import com.cometchat.chat.models.User
import com.cometchat.chatuikit.shared.cometchatuikit.CometChatUIKit
import com.cometchat.chatuikit.shared.cometchatuikit.UIKitSettings
import com.himanshu.testchat.features.conversations.ConversationActivity

class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    private val appID = "27980873074afab9"
    private val region = "IN"
    private val authKey = "d29fdb457306c344461c35c689210187707b13ef"

    private val uiKitSettings = UIKitSettings.UIKitSettingsBuilder()
        .setRegion(region)
        .setAppId(appID)
        .setAuthKey(authKey)
        .subscribePresenceForAllUsers()
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        CometChatUIKit.init(this, uiKitSettings, object : CometChat.CallbackListener<String?>() {
            override fun onSuccess(successString: String?) {

                Log.d(TAG, "Initialization completed successfully")

                loginUser()
            }

            override fun onError(e: CometChatException?) {}
        })
    }

    private fun loginUser() {
        CometChatUIKit.login("cometchat-uid-1", object : CometChat.CallbackListener<User>() {
            override fun onSuccess(user: User) {

                // Launch Conversation List + Message View (Split-Screen Style)
                startActivity(Intent(this@MainActivity, ConversationActivity::class.java))
            }

            override fun onError(e: CometChatException) {
                // Handle login failure (e.g. show error message or retry)
                Log.e("Login", "Login failed: ${e.message}")
            }
        })
    }
}