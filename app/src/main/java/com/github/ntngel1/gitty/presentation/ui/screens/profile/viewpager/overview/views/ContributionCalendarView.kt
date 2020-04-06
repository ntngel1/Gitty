/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.overview.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.github.ntngel1.gitty.domain.entities.contribution_calendar.ContributionCalendarEntity

class ContributionCalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var contributionCalendar: ContributionCalendarEntity? = null
        set(value) {
            field = value
            invalidate()
        }

    private var squareSize = 0f
    private var paddingSize = 0f

    private val paint = Paint().apply {
        style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        contributionCalendar?.let { contributionCalendar ->
            val totalDaysCount = contributionCalendar.weeks.fold(0) { count, week ->
                count + week.days.count()
            }

            val numberOfColumns = 14 // Columns represents days of week, so we have 7 days in week
            val numberOfRows = totalDaysCount / numberOfColumns

            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = width / numberOfColumns * numberOfRows

            val unit = width / numberOfColumns / 4f
            squareSize = unit * 3f
            paddingSize = unit

            setMeasuredDimension(width, height)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            contributionCalendar?.weeks?.forEachIndexed { weekIndex, week ->
                week.days.forEachIndexed { dayIndex, day ->
                    paint.color = Color.parseColor(day.color)

                    val startX = dayIndex * (squareSize + paddingSize)
                    val startY = weekIndex * (squareSize + paddingSize)
                    drawRect(
                        startX,
                        startY,
                        startX + squareSize,
                        startY + squareSize,
                        paint
                    )
                }
            }
        }
    }
}