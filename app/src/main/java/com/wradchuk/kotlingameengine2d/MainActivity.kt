package com.wradchuk.kotlingameengine2d

import android.graphics.Canvas
import android.graphics.Color
import android.view.MotionEvent
import com.wradchuk.game_engine.core.Joystick
import com.wradchuk.game_engine.common.Vector2
import com.wradchuk.game_engine.components.GameLevel
import com.wradchuk.game_engine.components.GameScreen
import com.wradchuk.game_engine.core.GameViewport
import com.wradchuk.game_engine.objects.Camera
import com.wradchuk.game_engine.utils.drawText
import com.wradchuk.game_engine.utils.getBitmap
import com.wradchuk.game_engine.utils.getDisplayHeightInPixels
import com.wradchuk.game_engine.utils.getDisplayWidthInPixels

class MainActivity : GameScreen() {

    // Карта тайлов
    private val gameLevel by lazy {
        GameLevel(
            context = this,
            bitmap = this.getBitmap(R.drawable.terrain_tiles_v2),
            levelData = R.raw.test_level
        )
    }

    // Джойстик для управления
    private val joystick  by lazy {
        Joystick(
            outerCircleRadius = 50,
            outerCircleCenterPosition = Vector2(80, this.getDisplayHeightInPixels() - 120),
            innerCircleRadius = 30,
            innerCircleCenterPosition = Vector2(80, this.getDisplayHeightInPixels() - 120),

            )
    }

    // Камера для отображения игрового мира
    private val camera  by lazy {
        Camera(
            position = Vector2(1000.0, 1000.0),
            context = this,
            joystick = joystick,
            mapSize = gameLevel.getMapSize(),
        )
    }

    // Отображение игрового мира
    private val gameViewport: GameViewport  by lazy {
        GameViewport(
            camera =  camera,
            widthPixels = this.getDisplayWidthInPixels(),
            heightPixels = this.getDisplayHeightInPixels()
        )
    }

    override fun draw(canvas: Canvas) {
        gameLevel.drawLevel(canvas, gameViewport)

        camera.draw(canvas, gameViewport)

        joystick.draw(canvas)

        // Рисует текстовые данные о FPS и координатах игровой области
        canvas.drawText(
            color = Color.MAGENTA,
            textSizeF = 30f,
            message = "FPS: ${getBaseGame().gameLoop?.averageFPS?.toInt()?.toString()}",
            position = Vector2(10f, 30f)
        )
        canvas.drawText(
            color = Color.MAGENTA,
            textSizeF = 30f,
            message = "x: ${gameViewport.getGameRect().left}, y: ${gameViewport.getGameRect().top}",
            position = Vector2(250f, 30f)
        )
    }

    override fun update() {
        joystick.update()
        camera.update()
        gameViewport.update()
    }

    override fun event(event: MotionEvent): Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                if(joystick.isPressed(Vector2(event.x.toDouble(), event.y.toDouble()))) {
                    joystick.setIsPressed(true)
                }
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if(joystick.getIsPressed()) {
                    joystick.setActuator(Vector2(event.x.toDouble(), event.y.toDouble()))
                }

                return true
            }
            MotionEvent.ACTION_UP -> {
                joystick.setIsPressed(false)
                joystick.resetActuator()
            }
        }

        return true
    }
}