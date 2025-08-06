package com.himanshu.testchat

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cometchat.chat.core.CometChat
import com.cometchat.chat.exceptions.CometChatException
import com.cometchat.chat.models.User
import com.cometchat.chatuikit.shared.cometchatuikit.CometChatUIKit
import com.cometchat.chatuikit.shared.cometchatuikit.UIKitSettings
import com.himanshu.testchat.ui.theme.TestChatTheme

class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    private val appID ="27980873074afab9"
    private val region ="IN" // Replace with your App Region
    private val authKey = "AUTH_KEY" // Replace with your Auth Key or leave blank if you are authenticating using Auth Token

    private val uiKitSettings = UIKitSettings.UIKitSettingsBuilder()
        .setRegion(region)
        .setAppId(appID)
        .setAuthKey(authKey)
        .subscribePresenceForAllUsers()
        .build()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestChatTheme {
                CometChatUIKit.init(this, uiKitSettings, object : CometChat.CallbackListener<String?>() {
                    override fun onSuccess(successString: String?) {

                        Log.d(TAG, "Initialization completed successfully")

                        loginUser()
                    }

                    override fun onError(p0: CometChatException?) {
                        TODO("Not yet implemented")
                    }

                    private fun loginUser() {
                            CometChatUIKit.login("cometchat-uid-1", object : CometChat.CallbackListener<User>() {
                                override fun onSuccess(user: User) {
                                }

                                override fun onError(e: CometChatException) {
                                    Log.e("Login", "Login failed: ${e.message}")
                                }
                            })

                    }

                })
            }
        }
    }
}