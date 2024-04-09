package com.wradchuk.game_engine.core

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.wradchuk.game_engine.common.Vector2
import com.wradchuk.game_engine.utils.calculateDistance
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Класс, представляющий джойстик для управления движением.
 *
 * @property outerCircleRadius Радиус внешнего круга джойстика.
 * @property outerCircleCenterPosition Позиция центра внешнего круга джойстика.
 * @property innerCircleRadius Радиус внутреннего круга джойстика.
 * @property innerCircleCenterPosition Позиция центра внутреннего круга джойстика.
 */
class Joystick constructor(
    private var outerCircleRadius: Int,
    private var outerCircleCenterPosition: Vector2<Int>,
    private var innerCircleRadius: Int,
    private var innerCircleCenterPosition: Vector2<Int>,
) {

    /**
     * Вектор, представляющий воздействие пользователя на джойстик.
     */
    var actuator: Vector2<Double> = Vector2(0.0, 0.0)
        private set

    /**
     * Расстояние от центра джойстика до позиции касания.
     */
    private var joystickCenterToTouchDistance: Double = 0.0

    /**
     * Флаг, указывающий, нажат ли джойстик.
     */
    var isPressed = false
        private set

    private val outerCirclePaint: Paint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL_AND_STROKE
    }
    private val innerCirclePaint: Paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL_AND_STROKE
    }

    /**
     * Рисует джойстик на указанном холсте.
     *
     * @param canvas Холст для рисования.
     */
    fun draw(canvas: Canvas) {
        canvas.drawCircle(
            outerCircleCenterPosition.x.toFloat(),
            outerCircleCenterPosition.y.toFloat(),
            outerCircleRadius.toFloat(),
            outerCirclePaint
        )

        canvas.drawCircle(
            innerCircleCenterPosition.x.toFloat(),
            innerCircleCenterPosition.y.toFloat(),
            innerCircleRadius.toFloat(),
            innerCirclePaint
        )
    }

    /**
     * Обновляет состояние джойстика.
     */
    fun update() {
        updateInnerCirclePosition()
    }

    private fun updateInnerCirclePosition() {
        innerCircleCenterPosition = Vector2(
            (outerCircleCenterPosition.x + actuator.x * outerCircleRadius).toInt(),
            (outerCircleCenterPosition.y + actuator.y * outerCircleRadius).toInt()
        )
    }

    /**
     * Проверяет, нажат ли джойстик в указанной позиции касания.
     *
     * @param touchPosition Позиция касания.
     * @return true, если джойстик нажат, иначе false.
     */
    fun isPressed(touchPosition: Vector2<Double>): Boolean {
        joystickCenterToTouchDistance = calculateDistance(
            outerCircleCenterPosition, touchPosition)
        return joystickCenterToTouchDistance < outerCircleRadius
    }

    /**
     * Устанавливает состояние нажатия джойстика.
     *
     * @param isPressed Флаг, указывающий, нажат ли джойстик.
     */
    fun setIsPressed(isPressed: Boolean) {
        this.isPressed = isPressed
    }

    /**
     * Возвращает состояние нажатия джойстика.
     *
     * @return true, если джойстик нажат, иначе false.
     */
    fun getIsPressed() = isPressed

    /**
     * Устанавливает вектор воздействия пользователя на джойстик по указанной позиции касания.
     *
     * @param touchPosition Позиция касания.
     */
    fun setActuator(touchPosition: Vector2<Double>) {
        val deltaX = touchPosition.x - outerCircleCenterPosition.x
        val deltaY = touchPosition.y - outerCircleCenterPosition.y
        val deltaDistance = sqrt(deltaX.pow(2.0) + deltaY.pow(2.0))

        if(deltaDistance < outerCircleRadius) {
            actuator.x = deltaX/outerCircleRadius
            actuator.y = deltaY/outerCircleRadius
        } else {
            actuator.x = deltaX/deltaDistance
            actuator.y = deltaY/deltaDistance
        }
    }

    /**
     * Сбрасывает вектор воздействия пользователя на джойстик.
     */
    fun resetActuator() {
        actuator.x = 0.0
        actuator.y = 0.0
    }

}