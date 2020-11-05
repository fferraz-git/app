package ipvc.estg.room

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class EditNote : AppCompatActivity() {

    private lateinit var titleText: EditText
    private lateinit var contentText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

       titleText = findViewById(R.id.editTitle)
       contentText = findViewById(R.id.editContent)

        val button = findViewById<Button>(R.id.updateBtt)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(titleText.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra(EXTRA_REPLY_TITLE, titleText.text.toString())
                replyIntent.putExtra(EXTRA_REPLY_CONTENT, contentText.text.toString())
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY_TITLE = "com.example.androaid.city"
        const val EXTRA_REPLY_CONTENT = "com.example.android.country"
    }
}