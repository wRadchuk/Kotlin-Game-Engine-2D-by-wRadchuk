package com.wradchuk.game_engine.objects

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import com.wradchuk.game_engine.core.Joystick
import com.wradchuk.game_engine.common.Vector2
import com.wradchuk.game_engine.core.GameViewport
import com.wradchuk.game_engine.core.GameLoop
import com.wradchuk.game_engine.utils.getDisplayHeightInPixels
import com.wradchuk.game_engine.utils.getDisplayWidthInPixels

/**
 * Класс, представляющий камеру в игре.
 *
 * @property position Позиция камеры на игровом поле.
 * @property context Контекст приложения.
 * @property mapSize Размер игровой карты.
 * @property joystick Джойстик для управления камерой.
 */
class Camera constructor(
    private val context: Context,
    private val mapSize: Rect,
    private val joystick: Joystick,
    override var position: Vector2<Double> = Vector2((context.getDisplayWidthInPixels() / 2).toDouble(),(context.getDisplayHeightInPixels() / 2).toDouble()),
): GameObject(position) {

    /**
     * Вектор скорости камеры
     */
    private var velocity: Vector2<Double> = Vector2(0.0, 0.0)

    /**
     * Половина ширины экрана в пикселях
     */
    private val halfScreenWidth = context.getDisplayWidthInPixels() / 2

    /**
     * Половина высоты экрана в пикселях
     */
    private val halfScreenHeight = context.getDisplayHeightInPixels() / 2


    /**
     * Метод для рисования камеры
     */
    override fun draw(canvas: Canvas, gameViewport: GameViewport) {
        // todo возможность добавить отрисовку в режиме отладки
    }

    /**
     * Обновляет состояние камеры.
     */
    override fun update() {

        // Рассчитываем скорость камеры по вектору от джойстика
        velocity.x = joystick.actuator.x * MAX_SPEED
        velocity.y = joystick.actuator.y * MAX_SPEED

        // Рассчитываем следующую позицию камеры
        val nextPositionX = position.x + velocity.x
        val nextPositionY = position.y + velocity.y


        if(mapSize.right < context.getDisplayWidthInPixels()) {
            position.x = halfScreenWidth.toDouble()
        }
        else {
            // Проверяем, чтобы камера не выходила за пределы игровой карты
            if(nextPositionX > (mapSize.left+halfScreenWidth) && nextPositionX < (mapSize.right-halfScreenWidth))
                position.x = nextPositionX
            else {
                if(nextPositionX < (mapSize.left+halfScreenWidth)) position.x = (mapSize.left+halfScreenWidth).toDouble()
                if(nextPositionX > (mapSize.right-halfScreenWidth)) position.x = (mapSize.right-halfScreenWidth).toDouble()
            }
        }

        if(mapSize.bottom < context.getDisplayHeightInPixels()) {
            position.y = halfScreenHeight.toDouble()
        }
        else {
            if(nextPositionY > (mapSize.top+halfScreenHeight) && nextPositionY < (mapSize.bottom-halfScreenHeight))
                position.y = nextPositionY
            else {
                if(nextPositionY < (mapSize.top+halfScreenHeight)) position.y = (mapSize.top+halfScreenHeight).toDouble()
                if(nextPositionY > (mapSize.bottom-halfScreenHeight)) position.y = (mapSize.bottom-halfScreenHeight).toDouble()
            }
        }



    }

    companion object {
        /**
         * Количество прохождения пикселей за секкунду
         */
        private const val SPEED_PIXELS_PER_SECOND = 400.0

        /**
         * Максимальная скорость камеры
         */
        private const val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS
    }

}