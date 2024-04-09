package com.wradchuk.game_engine.core

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.wradchuk.game_engine.utils.getDisplayHeightInPixels
import com.wradchuk.game_engine.utils.getDisplayWidthInPixels

/**
 * Управляет всеми объектами в игре.
 *
 * @property context Контекст приложения.
 * @property iBaseGame Реализация интерфейса IBaseGame для обработки событий и рисования игрового мира.
 */
class BaseGame internal constructor(
    private val context: Context,
    private val iBaseGame: IBaseGame,
): SurfaceView(context), SurfaceHolder.Callback {

    /**
     * Игровая петля, управляющая обновлением кадров.
     */
    var gameLoop: GameLoop? = GameLoop(this, holder)
        private set

    // Перо для рисования
    private val paint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
    }

    /**
     * Обрабатывает события нажатия экрана.
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event == null) return false
        return iBaseGame.event(event)
    }

    /**
     * Создает игровую петлю при создании поверхности.
     */
    override fun surfaceCreated(holder: SurfaceHolder) {
        if(gameLoop?.state != null && gameLoop?.state!! == Thread.State.TERMINATED) {
            gameLoop = GameLoop(this, holder)
        }
        gameLoop?.startLoop()
    }

    /**
     * Обрабатывает изменения поверхности.
     */
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    /**
     * Обрабатывает уничтожение поверхности.
     */
    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    // Создает изображение для рисования игрового мира
    private val bitmap = Bitmap.createBitmap(
        context.getDisplayWidthInPixels(),
        context.getDisplayHeightInPixels(),
        Bitmap.Config.ARGB_8888
    )

    /**
     * Рисует все объекты игры.
     */
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawColor(Color.WHITE)
        bitmap.eraseColor(Color.DKGRAY)

        val mCanvas = Canvas(bitmap)
        mCanvas.drawColor(Color.DKGRAY)

        iBaseGame.draw(mCanvas)

        canvas.drawBitmap(bitmap, 0f, 0f, paint)
    }

    /**
     * Обновляет состояние всех объектов в игре.
     */
    fun update() {
        iBaseGame.update()
    }

    init {
        // Добавляет колбэк для реакции на игровой ввод
        holder.addCallback(this)
    }
}