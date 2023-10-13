package com.tsoft.taskcase.model

data class ImageResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<ImageHit>
)
