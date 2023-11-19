package com.example.mydiary

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DiaryPageDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "diarypages.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "alldiarypages"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_CONTENT = "content"
        private const val COLUMN_MOOD = "mood"
        private const val COLUMN_LOCATION = "location"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_DATE TEXT, $COLUMN_CONTENT TEXT, $COLUMN_MOOD TEXT, $COLUMN_LOCATION TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertDiaryPage(diaryPage: DiaryPage) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, diaryPage.title)
            put(COLUMN_DATE, formatDate(diaryPage.date))
            put(COLUMN_CONTENT, diaryPage.content)
            put(COLUMN_MOOD, diaryPage.mood)
            put(COLUMN_LOCATION, diaryPage.location)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllDiaryPages(): List<DiaryPage> {
        val diaryPagesList = mutableListOf<DiaryPage>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val dateString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
            val date = parseDate(dateString)
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            val mood = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOOD))
            val location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION))

            val diaryPage = DiaryPage(id, title, date, content, mood, location)
            diaryPagesList.add(diaryPage)
        }
        cursor.close()
        db.close()
        return diaryPagesList
    }

    fun updateDiaryPage(diaryPage: DiaryPage) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, diaryPage.title)
            put(COLUMN_DATE, formatDate(diaryPage.date))
            put(COLUMN_CONTENT, diaryPage.content)
            put(COLUMN_MOOD, diaryPage.mood)
            put(COLUMN_LOCATION, diaryPage.location)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(diaryPage.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getDiaryPageById(diaryPageId: Int): DiaryPage {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $diaryPageId"

        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val dateString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
        val date = parseDate(dateString)
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
        val mood = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOOD))
        val location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION))

        cursor.close()
        db.close()
        return DiaryPage(id, title, date, content, mood, location)
    }

    fun deleteDiaryPage(diaryPageId: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(diaryPageId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

    private fun formatDate(date: Date): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }

    private fun parseDate(dateString: String): Date {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return format.parse(dateString) ?: Date()
    }
}
