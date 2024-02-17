package com.geekconvert.customcircularprogressbar

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun progressFlow(targetProgress: Float = 1f, step: Float = 0.01f, delayTime: Long = 1L): Flow<Float> {
    return flow {
        var progress = 0f
        while (progress <= targetProgress){
            emit(progress)
            progress += step
            delay(delayTime)
        }
    }
}
