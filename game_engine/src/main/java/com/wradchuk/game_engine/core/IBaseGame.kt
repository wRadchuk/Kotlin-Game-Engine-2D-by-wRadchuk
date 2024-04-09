package com.wradchuk.game_engine.core

import android.graphics.Canvas
import android.view.MotionEvent

/**
 * Интерфейс IBaseGame определяет методы, которые должны быть реализованы для управления игровым миром.
 */
interface IBaseGame {
    /**
     * Метод draw отвечает за отрисовку игрового мира на холсте.
     *
     * @param canvas Холст, на котором будет производиться отрисовка.
     */
    fun draw(canvas: Canvas)

    /**
     * Метод update обновляет состояние игровых объектов.
     */
    fun update()

    /**
     * Метод event обрабатывает события ввода, такие как касание экрана.
     *
     * @param event Событие ввода, передаваемое при нажатии или перемещении по экрану.
     * @return true, если событие было обработано, в противном случае - false.
     */
    fun event(event: MotionEvent): Boolean
}
