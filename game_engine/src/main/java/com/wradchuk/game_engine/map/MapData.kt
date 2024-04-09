package com.wradchuk.game_engine.map

/**
 * Представляет данные карты тайлов.
 *
 * @property maxRowIndex Максимальное количество рядов на карте.
 * @property maxColumnIndex Максимальное количество столбцов на карте.
 * @property tileSize Размер тайла в пикселях.
 * @property orientation Ориентация карты.
 * @property layersData Двумерный массив с данными о тайлах на карте.
 */
data class MapData(
    val maxRowIndex: Int,
    val maxColumnIndex: Int,
    val tileSize: Int,
    val orientation: String,
    val layersData: Array<IntArray>
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MapData

        if (maxRowIndex != other.maxRowIndex) return false
        if (maxColumnIndex != other.maxColumnIndex) return false
        if (tileSize != other.tileSize) return false
        if (orientation != other.orientation) return false
        if (!layersData.contentDeepEquals(other.layersData)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = maxRowIndex
        result = 31 * result + maxColumnIndex
        result = 31 * result + tileSize
        result = 31 * result + orientation.hashCode()
        result = 31 * result + layersData.contentDeepHashCode()
        return result
    }
}