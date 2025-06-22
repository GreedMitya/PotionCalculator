// MainActivity.kt (androidMain)
package com.greedmitya.albcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1) Отключаем «подгонку» контента под системные окна
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // 2) Получаем контроллер и сразу скрываем все системные панели
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())

        // 3) Устанавливаем поведение: показывать панели ТОЛЬКО при свайпе от края
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        // Запускаем Compose
        setContent {
            AndroidApp()
        }
    }
}
