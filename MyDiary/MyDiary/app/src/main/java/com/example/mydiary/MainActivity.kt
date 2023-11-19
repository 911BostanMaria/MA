package com.example.mydiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: DiaryPageDatabaseHelper
    private lateinit var diaryPagesAdapter: DiaryPagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DiaryPageDatabaseHelper(this)
        diaryPagesAdapter = DiaryPagesAdapter(db.getAllDiaryPages(), this)

        binding.diaryPagesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.diaryPagesRecyclerView.adapter = diaryPagesAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddDiaryPageActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        diaryPagesAdapter.refreshData(db.getAllDiaryPages())
    }
}
