package ipvc.estg.room

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class AddNote : AppCompatActivity() {

    private lateinit var titleText: EditText
    private lateinit var contentText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)

       titleText = findViewById(R.id.title)
       contentText = findViewById(R.id.content)

        //puts the button "listening" to click
        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            // value that initiates the intent
            val replyIntent = Intent()
            if (TextUtils.isEmpty(titleText.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                //sends the added values with the reply intent
                replyIntent.putExtra(EXTRA_REPLY_TITLE, titleText.text.toString())
                replyIntent.putExtra(EXTRA_REPLY_CONTENT, contentText.text.toString())
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY_TITLE = "com.example.android.title"
        const val EXTRA_REPLY_CONTENT = "com.example.android.content"
    }
}