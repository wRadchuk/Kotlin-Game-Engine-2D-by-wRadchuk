package com.wradchuk.game_engine.core

import android.graphics.Rect
import com.wradchuk.game_engine.objects.GameObject

/**
 * Класс, представляющий игровое отображение.
 *
 * @property camera Камера для отображения игрового мира.
 * @property widthPixels Ширина экрана в пикселях.
 * @property heightPixels Высота экрана в пикселях.
 */
class GameViewport constructor(
    private val camera: GameObject,
    val widthPixels: Int,
    val heightPixels: Int,
) {

    /**
     * Прямоугольник для отображения
     */
    val DISPLAY_RECT = Rect(0, 0, widthPixels, heightPixels)


    /**
     * Смещение координат игрового мира к экрану по оси X
     */
    private var gameToDisplayCoordinateOffsetX: Double = 0.0
    /**
     * Смещение координат игрового мира к экрану по оси Y
     */
    private var gameToDisplayCoordinateOffsetY: Double = 0.0

    /**
     * Центр экрана по оси X
     */
    private var displayCentreX: Double = widthPixels / 2.0

    /**
     * Центр экрана по оси Y
     */
    private var displayCentreY: Double = heightPixels / 2.0
    /**
     * Центр игрового мира по оси X
     */
    private var gameCenterX: Double = 0.0

    /**
     * Центр игрового мира по оси Y
     */
    private var gameCenterY: Double = 0.0

    /**
     * Обновляет состояние игрового отображения.
     */
    fun update() {
        // Устанавливаем центр игрового мира в камере
        gameCenterX = camera.position.x
        gameCenterY = camera.position.y

        // Вычисляем смещение координат игрового мира к экрану
        gameToDisplayCoordinateOffsetX = displayCentreX - gameCenterX
        gameToDisplayCoordinateOffsetY = displayCentreY - gameCenterY
    }

    /**
     * Преобразует координату X из координат игрового мира в координаты экрана.
     *
     * @param positionX Координата X в игровом мире.
     * @return Преобразованная координата X в координаты экрана.
     */
    fun gameToDisplayCoordinatesX(positionX: Double): Double {
        return positionX + gameToDisplayCoordinateOffsetX
    }

    /**
     * Преобразует координату Y из координат игрового мира в координаты экрана.
     *
     * @param positionY Координата Y в игровом мире.
     * @return Преобразованная координата Y в координаты экрана.
     */
    fun gameToDisplayCoordinatesY(positionY: Double): Double {
        return positionY + gameToDisplayCoordinateOffsetY
    }

    /**
     * Получает прямоугольник, представляющий игровую область на экране.
     *
     * @return Прямоугольник, представляющий игровую область на экране.
     */
    fun getGameRect(): Rect {
        return Rect(
            (gameCenterX - widthPixels / 2).toInt(),
            (gameCenterY - heightPixels / 2).toInt(),
            (gameCenterX + widthPixels / 2).toInt(),
            (gameCenterY + heightPixels / 2).toInt()
        )
    }

}