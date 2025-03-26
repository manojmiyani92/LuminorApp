package com.luminor.interviewtest.screen

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import com.luminor.interviewtest.navigation.AuthNavigationGraph
import com.luminor.interviewtest.theme.LuminorAndroidTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LuminorAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AuthNavigationGraph()
                }
            }
        }
    }
}
