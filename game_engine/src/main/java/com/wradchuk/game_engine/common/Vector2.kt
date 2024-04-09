package com.wradchuk.game_engine.common

/**
 * Представляет двумерный вектор с компонентами типа [T].
 *
 * @property x X-компонента вектора.
 * @property y Y-компонента вектора.
 * @param T Тип компонентов вектора.
 */
data class Vector2<T>(
    var x: T,
    var y: T,
)