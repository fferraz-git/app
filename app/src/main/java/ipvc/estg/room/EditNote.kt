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

    private lateinit var editWordView: EditText
    private lateinit var editnumberView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        val intent = intent
        editTitle.setText( intent.getStringExtra(EXTRA_REPLY) )
        editContent.setText( intent.getStringExtra(EXTRA1_REPLY) )
        val id = intent.getIntExtra( EXTRA_ID , -1)
        val button = findViewById<Button>(R.id.updateBtt)
        button.setOnClickListener {
            val replyIntent = Intent()

            val title = editWordView.text.toString()
            val content = editnumberView.text.toString()
            if( id != -1 ) {
                replyIntent.putExtra(EXTRA_ID, id)
            }
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