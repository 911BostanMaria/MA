package com.example.mydiary

import java.util.Date

data class DiaryPage(
    val title: String,
    val date: Date,
    val content: String,
    val mood: String,
    val location: String
)
