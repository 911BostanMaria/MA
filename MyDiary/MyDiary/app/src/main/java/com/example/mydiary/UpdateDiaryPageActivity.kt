package com.example.mydiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mydiary.databinding.ActivityUpdateDiaryPageBinding

class UpdateDiaryPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateDiaryPageBinding
    private lateinit var db: DiaryPageDatabaseHelper
    private var diaryPageId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateDiaryPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DiaryPageDatabaseHelper(this)

        diaryPageId = intent.getIntExtra("diary_page_id", -1)
        if(diaryPageId == -1){
            finish()
            return
        }

        val diaryPage = db.getDiaryPageById(diaryPageId)
        binding.updateTitleEditText.setText(diaryPage.title)
        binding.updateDateEditText.setText(diaryPage.date.toString()) // Adjust this based on your date representation
        binding.updateContentEditText.setText(diaryPage.content)
        binding.updateMoodEditText.setText(diaryPage.mood)
        binding.updateLocationEditText.setText(diaryPage.location)

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString()
            // Adjust the way you handle the date based on your representation
            val newDate = parseDate(binding.updateDateEditText.text.toString())
            val newContent = binding.updateContentEditText.text.toString()
            val newMood = binding.updateMoodEditText.text.toString()
            val newLocation = binding.updateLocationEditText.text.toString()

            val updatedDiaryPage = DiaryPage(diaryPageId, newTitle, newDate, newContent, newMood, newLocation)
            db.updateDiaryPage(updatedDiaryPage)
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
        }
    }

    // Adjust the date parsing logic based on your representation
    private fun parseDate(dateString: String): Date {
        // Implement the logic to parse the date string into a Date object
    }
}
