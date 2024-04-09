package com.wradchuk.game_engine.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import com.wradchuk.game_engine.core.GameViewport
import com.wradchuk.game_engine.map.MapData
import com.wradchuk.game_engine.map.TileMap
import com.wradchuk.game_engine.utils.loadMapDataFromRaw

/**
 * Представляет уровень игры, содержащий карту тайлов.
 *
 * @property context Контекст приложения для доступа к ресурсам.
 * @property bitmap Набор тайловых плиток для карты уровня.
 * @property levelData Данные об уровне из ресурсов в формате JSON.
 */
class GameLevel(
    private val context: Context,
    private val bitmap: Bitmap,
    private val levelData: Int,
) {

    private val mapData: MapData = checkNotNull(context.loadMapDataFromRaw(levelData)) // Загрузка данных карты уровня
    private val tileMap: TileMap = TileMap( // Создание карты тайлов
        bitmap = bitmap,
        mapData = mapData
    )

    /**
     * Возвращает размер карты уровня.
     *
     * @return Прямоугольник, представляющий размер карты в пикселях.
     */
    fun getMapSize(): Rect = tileMap.mapSize

    /**
     * Рисует уровень игры на указанном холсте с заданным объектом отображения игрового мира.
     *
     * @param canvas Холст для рисования уровня.
     * @param gameViewport Объект отображения игрового мира.
     */
    fun drawLevel(canvas: Canvas, gameViewport: GameViewport) {
        tileMap.draw(canvas, gameViewport) // Рисование карты тайлов
    }
}