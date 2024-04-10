package com.wradchuk.game_engine.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.TypedValue
import com.wradchuk.game_engine.common.Vector2
import com.wradchuk.game_engine.map.MapData
import org.json.JSONObject
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Получает ширину экрана в пикселях.
 *
 * @return Ширина экрана в пикселях.
 */
fun Context.getDisplayWidthInPixels(): Int = this.resources.displayMetrics.widthPixels

/**
 * Получает высоту экрана в пикселях.
 *
 * @return Высота экрана в пикселях.
 */
fun Context.getDisplayHeightInPixels(): Int = this.resources.displayMetrics.heightPixels

val Int.dp: Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()

/**
 * Получает битмап из ресурсов.
 *
 * @param resId Идентификатор ресурса изображения.
 * @return Битмап изображения.
 */
fun Context.getBitmap(resId: Int): Bitmap {
    val bitmapOptions = BitmapFactory.Options()
    bitmapOptions.inScaled = false
    return BitmapFactory.decodeResource(this.resources, resId, bitmapOptions)
}

/**
 * Рисует текст на холсте с заданными параметрами.
 *
 * @param color Цвет текста.
 * @param textSizeF Размер текста.
 * @param message Текст для отображения.
 * @param position Позиция текста на холсте.
 * @param visible Флаг видимости текста.
 */
fun Canvas.drawText(
    color: Int,
    textSizeF: Float = 30f,
    message: String?,
    position: Vector2<Int>,
    visible: Boolean = true,
) {
    if(visible) {
        val paint = Paint().apply {
            this.color = color
            this.textSize = textSizeF
        }
        this.drawText(message ?: "", position.x.toFloat(), position.y.toFloat(), paint)
    }
}


/**
 * Функция для вычисления расстояния между двумя точками в двумерном пространстве.
 *
 * @param point1 Первая точка в формате Vector2<Int>.
 * @param point2 Вторая точка в формате Vector2<Double>.
 * @return Расстояние между двумя точками в формате Double.
 */
fun calculateDistance(point1: Vector2<Int>, point2: Vector2<Double>): Double {
    return sqrt(
        (point1.x - point2.x).pow(2.0) +
                (point1.y - point2.y).pow(2.0)
    )
}


/**
 * Загружает данные карты из ресурсов raw и возвращает объект MapData.
 *
 * @param rawResourceId Идентификатор ресурса raw с данными карты.
 * @return Объект MapData, представляющий данные карты, или null, если загрузка не удалась.
 */
fun Context.loadMapDataFromRaw(rawResourceId: Int): MapData? {
    val inputStream = this.resources.openRawResource(rawResourceId)
    val jsonString = inputStream.bufferedReader().use { it.readText() }

    val jsonObject = JSONObject(jsonString)

    val maxRowIndex = jsonObject.getInt("width")
    val maxColumnIndex = jsonObject.getInt("height")
    val tileSize = jsonObject.getInt("tilewidth")
    val orientation = jsonObject.getString("orientation")

    val layersArray = jsonObject.getJSONArray("layers").getJSONObject(0).getJSONArray("data")

    if (maxRowIndex * maxColumnIndex != layersArray.length()) {
        // Проверка на корректность размеров массива
        return null
    }

    val layersData = Array(maxColumnIndex) { IntArray(maxRowIndex) }

    var dataIndex = 0
    for (row in 0 until maxColumnIndex) {
        for (col in 0 until maxRowIndex) {
            layersData[row][col] = layersArray.getInt(dataIndex)
            dataIndex++
        }
    }

    return MapData(
        maxRowIndex = maxRowIndex,
        maxColumnIndex =  maxColumnIndex,
        tileSize = tileSize,
        orientation = orientation,
        layersData = layersData
    )
}