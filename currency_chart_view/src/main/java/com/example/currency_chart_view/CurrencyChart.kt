package com.example.currency_chart_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import java.lang.Float.min
import java.lang.Integer.max
import kotlin.properties.Delegates

class CurrencyChart(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr:Int,
    defStyleRes:Int
) : View(context, attrs, defStyleAttr, defStyleRes) {
    /**
     * Адаптер
     */
    var chartAdapter: CurrencyChartAdapter? = null
        set(value){
            field = value
            if(!isInEditMode) {
                updateViewSize()
                requestLayout()
                invalidate()
            }
        }

    /**
     * Цвета кистей
     */
    private var chartColor by Delegates.notNull<Int>()
    private var borderChartColor by Delegates.notNull<Int>()
    private var dotChartColor by Delegates.notNull<Int>()
    private var maxValueChartColor by Delegates.notNull<Int>()
    private var minValueChartColor by Delegates.notNull<Int>()
    private var textChartColor by Delegates.notNull<Int>()
    /**
     * Инициализация кистей для рисования view
     */
    private lateinit var chartPaint: Paint
    private lateinit var borderChartPaint: Paint
    private lateinit var dotChartPaint: Paint
    private lateinit var maxValueChartPaint: Paint
    private lateinit var minValueChartPaint: Paint
    private lateinit var textPaint: Paint
    private lateinit var pointerPaint: Paint
    private lateinit var chartAxisPaint: Paint
    /**
     * Области рисования
     */
    private val viewSize = RectF()
    private val chartSize = RectF()
    private val infoSize = RectF()
    private val infoSizeRect1 = RectF()
    private val infoSizeRect2 = RectF()
    private val switchChartTimelineRect = RectF()
    /**
     * Координаты подписей осей графика
     */
    private lateinit var nameOx: String
    private var oXChartX by Delegates.notNull<Float>()
    private var oXChartY by Delegates.notNull<Float>()
    private lateinit var nameOy: String
    private var oYChartX by Delegates.notNull<Float>()
    private var oYChartY by Delegates.notNull<Float>()
    /**
     * Параметры для вычислений точек графика
     */
    private val listChartCoordinate = mutableListOf<ChartCoordinate>()
    private var currentPointChart: ChartCoordinate? = null
    /**
     * Параметры для отображения информации о выбранном элементе графика
     */
    private lateinit var presentCurrencyText: String
    private var presentCurrencyTextSize by Delegates.notNull<Float>()
    private var presentCurrencyX by Delegates.notNull<Float>()
    private var presentCurrencyY by Delegates.notNull<Float>()

    private lateinit var presentDateText: String
    private var presentDateTextSize by Delegates.notNull<Float>()
    private var presentDateX by Delegates.notNull<Float>()
    private var presentDateY by Delegates.notNull<Float>()

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr:Int,
    ) : this(context, attrs, defStyleAttr, R.style.GlobalCurrencyChartStyle)

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : this(context, attrs, R.attr.currencyChartFieldStyle)

    constructor(
        context:Context
    ) : this(context, null)

    init {
        initAttr(attrs, defStyleAttr, defStyleRes)
        initPaint()
        setBackgroundColor(Color.WHITE)
        if(isInEditMode) {
            chartAdapter = TestCurrencyChartAdapter(
                listOf(
                    Pair("1.1.2000", 453.4f),
                    Pair("2.1.2000", 453.1f),
                    Pair("3.1.2000", 453.5f),
                    Pair("4.1.2000", 453.8f),
                    Pair("5.1.2000", 453.0f),
                    Pair("6.1.2000", 453.1f),
                    Pair("7.1.2000", 453.5f),
                )
            )
        }
    }

    private fun initAttr(
        attrs: AttributeSet?,
        defStyleAttr:Int,
        defStyleRes:Int
    ) {
        if(attrs == null) {
            chartColor = DEFAULT_CHART_COLOR
            borderChartColor = DEFAULT_BORDER_CHART_COLOR
            dotChartColor = DEFAULT_DOT_CHART_COLOR
            maxValueChartColor = DEFAULT_MAX_VALUE_CHART_COLOR
            minValueChartColor = DEFAULT_MIN_VALUE_CHART_COLOR
            textChartColor = DEFAULT_TEXT_COLOR
        }
        else {
            val typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.CurrencyChart,
                defStyleAttr,
                defStyleRes
            )
            chartColor = typedArray.getColor(
                R.styleable.CurrencyChart_chartColor,
                DEFAULT_CHART_COLOR
            )
            borderChartColor = typedArray.getColor(
                R.styleable.CurrencyChart_borderChartColor,
                DEFAULT_BORDER_CHART_COLOR
            )

            dotChartColor = typedArray.getColor(
                R.styleable.CurrencyChart_dotChartColor,
                DEFAULT_DOT_CHART_COLOR
            )
            maxValueChartColor = typedArray.getColor(
                R.styleable.CurrencyChart_maxValueChartColor,
                DEFAULT_MAX_VALUE_CHART_COLOR
            )
            minValueChartColor = typedArray.getColor(
                R.styleable.CurrencyChart_minValueChartColor,
                DEFAULT_MIN_VALUE_CHART_COLOR
            )
            textChartColor = typedArray.getColor(
                R.styleable.CurrencyChart_textChartColor,
                DEFAULT_TEXT_COLOR
            )
            typedArray.recycle()
        }
    }

    private fun initPaint() {
        chartPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        chartPaint.color = chartColor
        chartPaint.style = Paint.Style.STROKE
        chartPaint.strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            2f,
            resources.displayMetrics
        )

        borderChartPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        borderChartPaint.color = borderChartColor
        borderChartPaint.style = Paint.Style.STROKE
        borderChartPaint.strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            5f,
            resources.displayMetrics
        )

        dotChartPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        dotChartPaint.color = dotChartColor
        dotChartPaint.style = Paint.Style.FILL_AND_STROKE
        dotChartPaint.strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            1f,
            resources.displayMetrics
        )

        maxValueChartPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        maxValueChartPaint.color = maxValueChartColor
        maxValueChartPaint.style = Paint.Style.FILL_AND_STROKE
        maxValueChartPaint.strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            1f,
            resources.displayMetrics
        )

        minValueChartPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        minValueChartPaint.color = minValueChartColor
        minValueChartPaint.style = Paint.Style.FILL_AND_STROKE
        minValueChartPaint.strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            1f,
            resources.displayMetrics
        )

        textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint.color = textChartColor

        pointerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        pointerPaint.color = textChartColor
        pointerPaint.style = Paint.Style.STROKE
        pointerPaint.strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            2f,
            resources.displayMetrics
        )

        chartAxisPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        chartAxisPaint.color = textChartColor
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateViewSize()
    }

    //договариваемся о размере view
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minWidth = suggestedMinimumWidth + paddingLeft + paddingRight
        val minHeight = suggestedMinimumHeight + paddingTop + paddingBottom
        val desiredViewInPixel = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            DESIRED_VIEW_SIZE,
            resources.displayMetrics
        ).toInt()
        val desiredWith = max(
            minWidth,
            desiredViewInPixel + paddingLeft + paddingRight
        )
        val desiredHeight = max(
            minHeight,
            desiredViewInPixel + paddingTop + paddingBottom
        )
        setMeasuredDimension(
            resolveSize(desiredWith, widthMeasureSpec),
            resolveSize(desiredHeight, heightMeasureSpec)
        )
    }

    private fun updateViewSize() {
        if(chartAdapter == null) return

        val safeWidth = width - paddingLeft - paddingRight
        val safeHeight = height - paddingTop - paddingBottom

        viewSize.left = paddingLeft.toFloat()
        viewSize.top = paddingTop.toFloat()
        viewSize.right = viewSize.left + safeWidth
        viewSize.bottom = viewSize.top + safeHeight
        run{
            val viewSizeWidthSide = viewSize.right - viewSize.left
            val viewSizeHeightSide = viewSize.bottom - viewSize.top
            val chartSizeSide = min(viewSizeWidthSide, viewSizeHeightSide) * 0.6f
            chartSize.left = viewSize.left + ((viewSizeWidthSide - chartSizeSide) / 2)
            chartSize.top = viewSize.top + ((viewSizeHeightSide - chartSizeSide) / 2)
            chartSize.right = chartSize.left + chartSizeSide
            chartSize.bottom = chartSize.top + chartSizeSide
        }
        infoSize.left = viewSize.left
        infoSize.top = chartSize.bottom
        infoSize.right = viewSize.right
        infoSize.bottom = viewSize.bottom

        infoSizeRect1.left = infoSize.left
        infoSizeRect1.top = infoSize.top
        infoSizeRect1.right = infoSizeRect1.left + (infoSize.right - infoSize.left) / 2
        infoSizeRect1.bottom = infoSize.bottom

        infoSizeRect2.left = infoSizeRect1.right
        infoSizeRect2.top = infoSizeRect1.top
        infoSizeRect2.right = infoSize.right
        infoSizeRect2.bottom = infoSize.bottom

        switchChartTimelineRect.left =
            chartSize.right
        switchChartTimelineRect.top =
            chartSize.top
        switchChartTimelineRect.right =
            switchChartTimelineRect.left + (viewSize.right - chartSize.right)
        switchChartTimelineRect.bottom =
            switchChartTimelineRect.top + (viewSize.right - chartSize.right)

        initListCoordinate()
        initTextParameter()
    }

    private fun initListCoordinate(){
        listChartCoordinate.clear()
        currentPointChart = null

        val xStep = (chartSize.right - chartSize.left) / (chartAdapter!!.currencyPairList.size + 1)
        val yStep = (chartSize.bottom - chartSize.top) / chartAdapter!!.currencyPairList.size
        var x = chartSize.left
        //формируем массив координат
        for(item in chartAdapter!!.currencyPairList) {
            val position =
                chartAdapter!!.sortedCurrencyCostList.indexOf(item.second) + 1
            x += xStep
            val y = chartSize.bottom - (yStep * position)
            val chartDot = when(item.second){
                chartAdapter!!.currencyMaxValue -> ChartDot.MAX
                chartAdapter!!.currencyMinValue -> ChartDot.MIN
                else -> ChartDot.DEFAULT
            }
            listChartCoordinate.add(
                ChartCoordinate(
                    x,
                    y,
                    chartDot,
                    item.second,
                    item.first
                )
            )
        }
        currentPointChart = listChartCoordinate.first()
    }

    private fun initTextParameter() {
        initPresentCurrencyText()
        initPresentDateText()
        initIndentText()
        initOxAndOyChartName()
    }

    private fun initPresentCurrencyText() {
        presentCurrencyText = context.getString(
            R.string.present_value_string,
            String.format("%.4f", currentPointChart!!.value)
        )
        var textWidth: Float
        var textHeight: Float
        for(i in 40 downTo 1) {
            textPaint.textSize = i.toFloat()
            textWidth = textPaint.measureText(presentCurrencyText)
            textHeight = textPaint.descent() - textPaint.ascent()
            if(
                textWidth <= ((infoSizeRect1.right - INDENT_INFO_RECT_TEXT) - (infoSizeRect1.left + INDENT_INFO_RECT_TEXT)) &&
                textHeight <= (infoSizeRect1.bottom - infoSizeRect1.top)
            ) {
                presentCurrencyTextSize = i.toFloat()
                break
            }
            else {
                if(i == 1) presentCurrencyTextSize = 0f
            }
        }
    }

    private fun initPresentDateText() {
        presentDateText = context.getString(
            R.string.present_date_string,
            currentPointChart!!.date
        )
        var textWidth: Float
        var textHeight: Float
        for(i in 40 downTo 1) {
            textPaint.textSize = i.toFloat()
            textWidth = textPaint.measureText(presentDateText)
            textHeight = textPaint.descent() - textPaint.ascent()
            if(
                textWidth <= ((infoSizeRect2.right - INDENT_INFO_RECT_TEXT) - (infoSizeRect2.left + INDENT_INFO_RECT_TEXT)) &&
                textHeight <= (infoSizeRect2.bottom - infoSizeRect2.top)
            ) {
                presentDateTextSize = i.toFloat()
                break
            }
            else {
                if(i == 1) presentDateTextSize = 0f
            }
        }
    }

    private fun initIndentText() {
        val globalTextSize = min(presentDateTextSize, presentCurrencyTextSize)
        textPaint.textSize = globalTextSize
        //1
        run{
            val textBounds = Rect()
            textPaint.getTextBounds(
                presentCurrencyText,
                0,
                presentCurrencyText.length,
                textBounds
            )
            val textWidth = textPaint.measureText(presentCurrencyText)
            val textHeight= textBounds.height()
            val indentWidth = ((infoSizeRect1.right - infoSizeRect1.left) - textWidth) / 2
            val indentHeight = ((infoSizeRect1.bottom - infoSizeRect1.top) - textHeight) / 2
            presentCurrencyX = infoSizeRect1.left + indentWidth
            presentCurrencyY = infoSizeRect1.bottom - indentHeight
        }
        //2
        run{
            val textBounds = Rect()
            textPaint.getTextBounds(
                presentDateText,
                0,
                presentDateText.length,
                textBounds
            )
            val textWidth = textPaint.measureText(presentDateText)
            val textHeight= textBounds.height()
            val indentWidth = ((infoSizeRect2.right - infoSizeRect2.left) - textWidth) / 2
            val indentHeight = ((infoSizeRect2.bottom - infoSizeRect2.top) - textHeight) / 2
            presentDateX = infoSizeRect2.left + indentWidth
            presentDateY = infoSizeRect2.bottom - indentHeight
        }
    }

    private fun initOxAndOyChartName() {
        //oX
        run{
            nameOx = context.getString(R.string.ox_chart_name)
            chartAxisPaint.textSize = 40f
            var rect = Rect()
            chartAxisPaint.getTextBounds(nameOx, 0, nameOx.length, rect)
            var widthText = chartAxisPaint.measureText(nameOx)
            var heightText = rect.height()
            val availableWidth = viewSize.right - chartSize.right
            if(widthText > availableWidth) {
                for(i in 39 downTo 1) {
                    chartAxisPaint.textSize = i.toFloat()
                    rect = Rect()
                    chartAxisPaint.getTextBounds(nameOx, 0, nameOx.length, rect)
                    widthText = chartAxisPaint.measureText(nameOx)
                    heightText = rect.height()
                    if(widthText < availableWidth) break
                }
            }
            oXChartX = chartSize.right + (availableWidth - widthText) / 2
            oXChartY = chartSize.bottom + heightText / 2
        }
        //oY
        run{
            nameOy = context.getString(R.string.oy_chart_name)
            val rect = Rect()
            chartAxisPaint.getTextBounds(nameOx, 0, nameOx.length, rect)
            val widthText = chartAxisPaint.measureText(nameOy)
            val heightText = rect.height()
            oYChartX = chartSize.left - widthText / 2
            oYChartY = chartSize.top - heightText
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(chartAdapter == null) return
        drawBorderChart(canvas)
        drawNameAxisChart(canvas)
        drawChart(canvas)
        drawChartDot(canvas)
        drawPresentValueInfo(canvas)
        drawPointerPresentValue(canvas)
    }

    private fun drawBorderChart(canvas:Canvas) {
        if(chartAdapter == null) return
        canvas.drawLine(
            chartSize.left,
            chartSize.top,
            chartSize.left,
            chartSize.bottom + THICKNESS_BORDER_CHART,
            borderChartPaint
        )
        canvas.drawLine(
            chartSize.left,
            chartSize.bottom,
            chartSize.right,
            chartSize.bottom,
            borderChartPaint
        )
    }

    private fun drawNameAxisChart(canvas: Canvas) {
        if(chartAdapter == null) return
        canvas.drawText(nameOx, oXChartX, oXChartY, chartAxisPaint)
        canvas.drawText(nameOy, oYChartX, oYChartY, chartAxisPaint)
    }

    private fun drawChart(canvas: Canvas) {
        if(chartAdapter == null) return
        for(i in 0 until listChartCoordinate.size - 1) {
            canvas.drawLine(
                listChartCoordinate[i].x,
                listChartCoordinate[i].y,
                listChartCoordinate[i + 1].x,
                listChartCoordinate[i + 1].y,
                chartPaint
            )
        }
    }

    private fun drawChartDot(canvas: Canvas) {
        if(chartAdapter == null) return
        for(i in listChartCoordinate) {
            canvas.drawOval(
                i.x - CHART_DOT_SIZE,
                i.y - CHART_DOT_SIZE,
                i.x + CHART_DOT_SIZE,
                i.y + CHART_DOT_SIZE,
                when(i.chartDot){
                    ChartDot.DEFAULT -> dotChartPaint
                    ChartDot.MAX -> maxValueChartPaint
                    ChartDot.MIN -> minValueChartPaint
                }
            )
        }
    }

    private fun drawPresentValueInfo(canvas:Canvas) {
        if(chartAdapter == null) return
        canvas.drawText(presentCurrencyText, presentCurrencyX, presentCurrencyY ,textPaint)
        canvas.drawText(presentDateText, presentDateX, presentDateY ,textPaint)
    }

    private fun drawPointerPresentValue(canvas: Canvas) {
        if(chartAdapter == null) return
        canvas.drawLine(
            currentPointChart!!.x,
            currentPointChart!!.y + CHART_DOT_SIZE,
            currentPointChart!!.x,
            chartSize.bottom,
            pointerPaint
        )
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> return true

            MotionEvent.ACTION_MOVE -> return if(
                event.x > chartSize.left && event.x < chartSize.right &&
                event.y > chartSize.top && event.y < chartSize.bottom
            ) {
                searchClosestElement(event.x)
                true
            } else false

            MotionEvent.ACTION_UP -> {
                performClick()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    private fun searchClosestElement(x: Float) {
        for(elem in listChartCoordinate) {
            if(x > elem.x - INDENT_COORDINATE && x < elem.x + INDENT_COORDINATE) {
                if(currentPointChart != elem) {
                    currentPointChart = elem
                    initTextParameter()
                    invalidate()
                }
            }
        }
    }

    private enum class ChartDot{
        DEFAULT,
        MAX,
        MIN
    }

    private data class ChartCoordinate(
        val x: Float,
        val y: Float,
        val chartDot: ChartDot,
        val value: Float,
        val date: String
    )

    companion object{
        private const val INDENT_INFO_RECT_TEXT = 32
        private const val INDENT_COORDINATE = 10
        private const val THICKNESS_BORDER_CHART = 5f
        private const val CHART_DOT_SIZE = 10
        private const val DESIRED_VIEW_SIZE = 400f
        /**
         * Цвета кистей по умолчанию
         */
        private const val DEFAULT_CHART_COLOR = Color.BLUE
        private const val DEFAULT_BORDER_CHART_COLOR = Color.GRAY
        private const val DEFAULT_DOT_CHART_COLOR = Color.BLUE
        private const val DEFAULT_MAX_VALUE_CHART_COLOR = Color.GREEN
        private const val DEFAULT_MIN_VALUE_CHART_COLOR = Color.RED
        private const val DEFAULT_TEXT_COLOR = Color.BLACK
    }
}