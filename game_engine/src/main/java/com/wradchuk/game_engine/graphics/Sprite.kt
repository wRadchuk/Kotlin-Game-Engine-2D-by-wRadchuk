package com.wradchuk.game_engine.graphics

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import com.wradchuk.game_engine.common.Vector2
import com.wradchuk.game_engine.core.GameViewport

/**
 * Класс, представляющий спрайт в игре.
 *
 * @property bitmap Битмап спрайта.
 * @property position Позиция спрайта на игровом поле.
 */
class Sprite constructor(
    private val bitmap: Bitmap,
    private val spriteSizeX: Int,
    private val spriteSizeY: Int,
    private val maxFrame: Int = bitmap.width/spriteSizeX-1,
    val position: Vector2<Double> = Vector2(0.0,0.0),
) {

    private var currentFrameID: Int = 3

    /**
     * Рисует спрайт на указанном холсте с учетом игрового отображения.
     *
     * @param canvas Холст для рисования.
     * @param gameViewport Игровое отображение для преобразования координат спрайта.
     */
    fun draw(canvas: Canvas, gameViewport: GameViewport) {
        val x = gameViewport.gameToDisplayCoordinatesX(position.x)
        val y = gameViewport.gameToDisplayCoordinatesY(position.y)

        val rect = Rect(
            spriteSizeX * currentFrameID,
            0,
            spriteSizeX * (currentFrameID + 1),
            spriteSizeY
        )

        val dstRect = Rect(x.toInt(), y.toInt(), (x + spriteSizeX).toInt(), (y + spriteSizeY).toInt())

        canvas.drawBitmap(bitmap, rect, dstRect, null)

    }

    /**
     * Возвращает ширину спрайта.
     *
     * @return Ширина спрайта в пикселях.
     */
    fun getWidth() = bitmap.width

    /**
     * Возвращает высоту спрайта.
     *
     * @return Высота спрайта в пикселях.
     */
    fun getHeight() = bitmap.height
}