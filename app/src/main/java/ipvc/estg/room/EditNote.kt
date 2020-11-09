package ipvc.estg.room

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_edit_note.*

class EditNote : AppCompatActivity() {

    private lateinit var editTitle: EditText
    private lateinit var editContent: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        //associates the variables with the layout editexts
        editTitle = findViewById(R.id.editTitle)
        editContent = findViewById(R.id.editContent)

        //set the values that where already there in the edit text
        val intent = intent
        editTitle.setText( intent.getStringExtra(EXTRA_REPLY))
        editContent.setText( intent.getStringExtra(EXTRA1_REPLY))

        val id = intent.getIntExtra( EXTRA_ID , -1)
        val button = findViewById<Button>(R.id.updateBtt)

        //puts the button "listenning" to click
        button.setOnClickListener {
            val replyIntent = Intent()
            //puts the new values of the edit text in values
            val title = editTitle.text.toString()
            val content = editContent.text.toString()

            if( id != -1 ) {
                replyIntent.putExtra(EXTRA_ID, id)
            }
            //sends the new values with the reply intent
            replyIntent.putExtra(EXTRA_REPLY, title)
            replyIntent.putExtra(EXTRA1_REPLY, content)
            setResult(Activity.RESULT_OK, replyIntent)

            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.REPLY"
        const val EXTRA1_REPLY = "com.example.android.REPLY1"
        const val EXTRA_ID = "com.example.android.ID"
    }
}