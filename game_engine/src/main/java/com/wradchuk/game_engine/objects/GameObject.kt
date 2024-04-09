package com.wradchuk.game_engine.objects

import android.graphics.Canvas
import com.wradchuk.game_engine.common.Vector2
import com.wradchuk.game_engine.core.GameViewport

/**
 * Абстрактный класс, представляющий игровой объект.
 *
 * @property position Позиция игрового объекта на игровом поле.
 */
abstract class GameObject internal constructor(
    open val position: Vector2<Double>,
) {

    /**
     * Рисует игровой объект на указанном холсте с учетом игрового отображения.
     *
     * @param canvas Холст для рисования.
     * @param gameViewport Игровое отображение для преобразования координат игрового объекта.
     */
    abstract fun draw(canvas: Canvas, gameViewport: GameViewport)

    /**
     * Обновляет состояние игрового объекта.
     */
    abstract fun update()
}