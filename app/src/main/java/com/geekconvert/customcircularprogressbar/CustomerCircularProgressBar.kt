package com.geekconvert.customcircularprogressbar

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomerCircularProgressBar(
    progress: Float = 0f,
    startAngle: Float = 270f,
    size: Dp = 96.dp,
    strokeWidth: Dp = 12.dp,
    backgroundArcColor: Color = Color.LightGray,
    progressArcColor1: Color = Color.Blue,
    progressArcColor2: Color = progressArcColor1,
    animationOn: Boolean = false,
    animationDuration: Int = 1000
) {
    val currentProgress = remember {
        mutableFloatStateOf(0f)
    }
    val animatedProgress by animateFloatAsState(
        targetValue = currentProgress.value,
        animationSpec = if (animationOn) tween(animationDuration) else tween(0),
        label = "Progress Animation"
    )
    Log.i("anisham", "anisham progress = " + currentProgress.value)
    Log.i("anisham", "anisham animatedProgress = " + animatedProgress)
    LaunchedEffect(animationOn, progress) {
        if (animationOn) {
            progressFlow(progress).collect { value ->
                currentProgress.floatValue = value
            }
        } else {
            currentProgress.floatValue = progress
        }
    }
    Canvas(modifier = Modifier.size(size)) {
        val strokeWidthPx = strokeWidth.toPx()
        val arcSize = size.toPx() - strokeWidthPx
        drawArc(
            color = backgroundArcColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
            size = Size(arcSize, arcSize),
            style = Stroke(width = strokeWidthPx)
        )
        val gradientBrush = Brush.verticalGradient(
            colors = listOf(progressArcColor1, progressArcColor2, progressArcColor1)
        )

        withTransform({ rotate(degrees = startAngle, pivot = center) }) {
            drawArc(
                brush = gradientBrush,
                startAngle = 0f,
                sweepAngle = animatedProgress * 360,
                useCenter = false,
                topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
                size = Size(arcSize, arcSize),
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
            )
        }
    }
}

@Preview
@Composable
fun CustomerCircularProgressBarPreview() {
    CustomerCircularProgressBar(
        progress = 0.85f,
        progressArcColor1 = Color(0xFF673AB7),
        progressArcColor2 = Color(0xFF4CAF50),
    )
}