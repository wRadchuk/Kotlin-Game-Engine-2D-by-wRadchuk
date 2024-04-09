package com.wradchuk.game_engine.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import com.wradchuk.game_engine.core.GameViewport

/**
 * Представляет собой карту тайлов, состоящую из тайловых изображений.
 * Каждый тайл определяется индексом в bitmap, который хранится в объекте [MapData].
 *
 * @property bitmap Битмап, содержащий изображения тайлов.
 * @property mapData Данные карты, включая массив с данными о тайлах, их размер и максимальный индекс.
 */
class TileMap(
    private val bitmap: Bitmap,
    private val mapData: MapData,
) {

    private var map: Array<IntArray> = mapData.layersData

    /**
     * Размер карты в пикселях.
     */
    val mapSize: Rect = Rect(0, 0, mapData.maxRowIndex * mapData.tileSize + 1, (mapData.maxColumnIndex) * mapData.tileSize)

    /**
     * Возвращает координаты изображения тайла по его индексу.
     *
     * @param tileId Индекс тайла.
     * @return Пара координат (ряд, столбец) изображения тайла в bitmap.
     */
    private fun getTileCoordinates(tileId: Int): Pair<Int, Int>? {
        if (tileId < 0 || tileId >= (bitmap.width / mapData.tileSize) * (bitmap.height / mapData.tileSize)) return null

        val row = tileId % (bitmap.width / mapData.tileSize)  // Вычисляем ряд плитки
        val column = tileId / (bitmap.width / mapData.tileSize)  // Вычисляем колонку плитки

        return Pair(row, column)
    }

    /**
     * Инициализирует карту тайлов, рисуя ее на холсте.
     *
     * @param canvas Холст для рисования.
     * @param gameViewport Объект отображения игрового мира.
     */
    private fun initializeMap(canvas: Canvas, gameViewport: GameViewport) {
        val newVisibleFrames = calculateVisibleFrames(gameViewport)

        val renderMap: Bitmap?

        val config = Bitmap.Config.ARGB_8888
        renderMap = Bitmap.createBitmap(
            (newVisibleFrames.right - newVisibleFrames.left) * mapData.tileSize,
            (newVisibleFrames.bottom - newVisibleFrames.top) * mapData.tileSize,
            config
        )

        val mapCanvas = Canvas(renderMap)

        for (iRow in newVisibleFrames.top until newVisibleFrames.bottom + 1) { // iRow - по Y
            for (iCol in newVisibleFrames.left until newVisibleFrames.right + 1) { // iCol - по X

                val tileID = map[iRow][iCol] // достаём с верха и до правого края, затем идём вниз к ряду iRow

                val tileCoordinate = getTileCoordinates(tileID - 1) ?: return

                val rect = Rect(
                    mapData.tileSize * tileCoordinate.first,
                    mapData.tileSize * tileCoordinate.second,
                    tileCoordinate.first * mapData.tileSize + mapData.tileSize,
                    tileCoordinate.second * mapData.tileSize + mapData.tileSize,
                )

                val bitmapPart = Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), rect.height())

                val positionRect = Rect(
                    gameViewport.gameToDisplayCoordinatesX((mapData.tileSize * iCol).toDouble()).toInt(), // 0, 128, 256 по координате X
                    gameViewport.gameToDisplayCoordinatesY((mapData.tileSize * iRow).toDouble()).toInt(), // 0, 128, 256 по координате Y
                    gameViewport.gameToDisplayCoordinatesX((mapData.tileSize * iCol + mapData.tileSize).toDouble()).toInt(), // 0, 128, 256 (c добавлением ширины картинки) по координате X
                    gameViewport.gameToDisplayCoordinatesY((mapData.tileSize * iRow + mapData.tileSize).toDouble()).toInt(), // 0, 128, 256 (c добавлением высоты картинки) по координате Y

                )

                mapCanvas.drawBitmap(
                    bitmapPart,
                    null,
                    positionRect,
                    null
                )
            }
        }

        canvas.drawBitmap(
            renderMap,
            gameViewport.DISPLAY_RECT,
            gameViewport.DISPLAY_RECT,
            null
        )

    }

    /**
     * Вычисляет видимую область карты тайлов на экране.
     *
     * @param gameViewport Объект отображения игрового мира.
     * @return Прямоугольник, представляющий видимую область карты.
     */
    private fun calculateVisibleFrames(gameViewport: GameViewport): Rect {

        val xMax = gameViewport.widthPixels / mapData.tileSize + 4

        val yMax = gameViewport.heightPixels / mapData.tileSize + 4

        val topLeftXIndex = (gameViewport.getGameRect().left - mapData.tileSize) / mapData.tileSize
        val topLeftYIndex = (gameViewport.getGameRect().top - mapData.tileSize) / mapData.tileSize

        val left = if (topLeftXIndex < 0) 0 else topLeftXIndex
        val right = if (left + xMax < map[0].size - 1) left + xMax else map[0].size - 1

        val top = if (topLeftYIndex > 0) topLeftYIndex else 0
        val bottom = if (top + yMax < map.size - 1) top + yMax else map.size - 1
        return Rect(left, top, right, bottom)
    }

    /**
     * Рисует карту тайлов на экране.
     *
     * @param canvas Холст для рисования.
     * @param gameViewport Объект отображения игрового мира.
     */
    fun draw(canvas: Canvas, gameViewport: GameViewport) {

        initializeMap(canvas, gameViewport)

    }


}