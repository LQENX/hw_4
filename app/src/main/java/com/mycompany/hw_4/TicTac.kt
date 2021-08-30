package com.mycompany.hw_4

import android.content.Context
import android.graphics.*
import android.graphics.drawable.VectorDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.*
import kotlin.math.cos
import kotlin.math.sin


class TicTac(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint: Paint = Paint()
    private val hourArrow: RectF = RectF()
    private val minuteArrow: RectF = RectF()
    private val secondArrow: RectF = RectF()

    private var hourArrowAngle = 0f
    private var minuteArrowAngle = 0f
    private var secondArrowAngle = 0f

    // Arrow's size (width, height)
    private val hourArrowSize = Pair(20f, 250f)
    private val minuteArrowSize = Pair(20f, 225f)
    private val secondArrowSize = Pair(10f, 200f)

    companion object{
        // in millis
        private const val TICK = 1000L
    }


    init {
        context.obtainStyledAttributes(attrs, R.styleable.TicTac).recycle()
        initHourHand()
        initMinuteHand()
        initSecondHand()
    }


    private fun initHourHand() {
        hourArrow.set(
            0f,
            hourArrowSize.second,
            hourArrowSize.first,
            0f
        )
    }

    private fun initMinuteHand() {
        minuteArrow.set(
            0f,
            minuteArrowSize.second,
            minuteArrowSize.first,
            0f
        )
    }

    private fun initSecondHand () {
        secondArrow.set(
            0f,
            secondArrowSize.second,
            secondArrowSize.first,
            0f
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawWatchDial(canvas)
        drawHourHand(canvas)
        drawMinuteHand(canvas)
        drawSecondHand(canvas)
        drawCenterCircle(canvas)

        postInvalidateDelayed(TICK)
    }

    private fun drawWatchDial(canvas: Canvas) {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 20f

        val watchDial = Path()
        watchDial.addCircle(canvas.width/2f , canvas.height/2f, 400f, Path.Direction.CCW)
        canvas.drawPath(watchDial, paint)

        val watchDialMeasure = PathMeasure(watchDial, false)
        val marksSpacing = watchDialMeasure.length / 12
        for (i in 0 until 12) {
            val matrix = Matrix()
            watchDialMeasure.getMatrix(
                marksSpacing*i,
                matrix,
                PathMeasure.POSITION_MATRIX_FLAG + PathMeasure.TANGENT_MATRIX_FLAG
            )
            canvas.save()
            canvas.setMatrix(matrix)
            paint.color = Color.MAGENTA
            canvas.drawLine(0f, -20f, 0f, -5f, paint)
            canvas.restore()
        }
    }

    private fun drawHourHand(canvas: Canvas) {
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = 10f

        val hourHandWidth = hourArrowSize.first
        hourArrow.offsetTo(
            canvas.width / 2f - hourHandWidth/2f,
            canvas.height / 2f
        )

        hourArrowAngle += 0.0006f
        canvas.rotate(hourArrowAngle, canvas.width/2f, canvas.height/2f)
        canvas.drawRoundRect(hourArrow, 10f, 10f, paint)
        Log.d("ANGLE", "is: $hourArrowAngle")
    }

    private fun drawMinuteHand(canvas: Canvas) {
        paint.color = Color.GRAY
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f

        val minuteHandWidth = minuteArrowSize.first
        minuteArrow.offsetTo(
            canvas.width / 2f - minuteHandWidth/2f,
            canvas.height / 2f
        )

        minuteArrowAngle += 0.06f
        canvas.rotate(minuteArrowAngle, canvas.width/2f, canvas.height/2f)
        canvas.drawRoundRect(minuteArrow, 10f, 10f, paint)
        Log.d("ANGLE", "is: $minuteArrowAngle")
    }

    private fun drawSecondHand(canvas: Canvas) {
        paint.color = Color.GREEN
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f

        val secondHandWidth = secondArrowSize.first
        secondArrow.offsetTo(
            canvas.width / 2f - secondHandWidth/2f,
            canvas.height / 2f
        )

        secondArrowAngle += 6f
        canvas.rotate(secondArrowAngle, canvas.width / 2f, canvas.height / 2f)
        canvas.drawRoundRect(secondArrow, 5f, 5f, paint)
        Log.d("ANGLE", "is: $secondArrowAngle")

    }

    private fun drawCenterCircle(canvas: Canvas) {
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL

        canvas.drawCircle(canvas.width/2f, canvas.height/2f, 20f, paint)
    }
}