import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mydiary.DiaryPage
import com.example.mydiary.DiaryPageDatabaseHelper
import com.example.mydiary.R
import com.example.mydiary.UpdateDiaryPageActivity

class DiaryPagesAdapter(private var diaryPages: List<DiaryPage>, context: Context) :
    RecyclerView.Adapter<DiaryPagesAdapter.DiaryPageViewHolder>() {

    private val db: DiaryPageDatabaseHelper = DiaryPageDatabaseHelper(context)

    class DiaryPageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val moodTextView: TextView = itemView.findViewById(R.id.moodTextView)
        var updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        var deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryPageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.diary_page_item, parent, false)
        return DiaryPageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return diaryPages.size
    }

    override fun onBindViewHolder(holder: DiaryPageViewHolder, position: Int) {
        val diaryPage = diaryPages[position]
        holder.titleTextView.text = diaryPage.title
        holder.dateTextView.text = diaryPage.date.toString() // Adjust this based on your date representation
        holder.moodTextView.text = diaryPage.mood

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateDiaryPageActivity::class.java).apply {
                putExtra("diary_page_id", diaryPage.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteDiaryPage(diaryPage.id)
            refreshData(db.getAllDiaryPages())
            Toast.makeText(holder.itemView.context, "Diary Page deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newDiaryPages: List<DiaryPage>) {
        diaryPages = newDiaryPages
        notifyDataSetChanged()
    }
}
