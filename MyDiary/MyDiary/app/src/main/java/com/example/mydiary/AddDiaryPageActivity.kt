package com.example.mydiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mydiary.databinding.ActivityAddDiaryPageBinding
import java.util.Date

class AddDiaryPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddDiaryPageBinding
    private lateinit var db: DiaryPageDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDiaryPageBinding.inflate(layoutInflater)

        setContentView(binding.root)

        db = DiaryPageDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val date = Date() // You might want to choose a specific way to get the date
            val content = binding.contentEditText.text.toString()
            val mood = binding.moodEditText.text.toString()
            val location = binding.locationEditText.text.toString()

            val diaryPage = DiaryPage(title, date, content, mood, location)
            db.insertDiaryPage(diaryPage)

            finish()
            Toast.makeText(this, "Diary Page Saved", Toast.LENGTH_SHORT).show()
        }
    }
}
