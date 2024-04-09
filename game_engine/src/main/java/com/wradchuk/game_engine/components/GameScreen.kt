package com.wradchuk.game_engine.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.wradchuk.game_engine.core.BaseGame
import com.wradchuk.game_engine.core.IBaseGame

/**
 * Абстрактный класс GameScreen представляет экран игры и наследуется от ComponentActivity и IBaseGame.
 * Реализует методы для создания и управления игровым миром.
 */
abstract class GameScreen: ComponentActivity(), IBaseGame {

    /**
     * Ленивая инициализация игрового движка.
     */
    private val _baseGame: BaseGame by lazy {
        BaseGame(context = this, iBaseGame = this)
    }

    /**
     * Возвращает экземпляр игрового движка.
     */
    fun getBaseGame() = _baseGame

    /**
     * Вызывается при создании активности.
     * Настраивает интерфейс пользователя и устанавливает контент экрана.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                AndroidView(
                    factory = { getBaseGame() },
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}
