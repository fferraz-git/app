package ipvc.estg.room

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ipvc.estg.room.adapters.NoteAdapter
import ipvc.estg.room.entities.Note
import ipvc.estg.room.viewModel.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*

private lateinit var noteViewModel: NoteViewModel

class MainActivity : AppCompatActivity(), NoteAdapter.OnItemClickListener /*NoteAdapter.OnItemLongClickListener*/{


    private val AddNoteRequestCode = 1
    private val UpdateActivityRequestCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = NoteAdapter(this, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // view model
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.allNotes.observe(this, Observer { notes ->
            // Update the cached copy of the words in the adapter.
            notes?.let { adapter.setNotes(it) }
        })

        //Fab
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddNote::class.java)
            startActivityForResult(intent, AddNoteRequestCode)
        }

        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback( 0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT ) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.deleteNote( adapter.getNoteAt(viewHolder.adapterPosition) )
            }

        }

        val itemTouchHelper = ItemTouchHelper( itemTouchHelperCallback )
        itemTouchHelper.attachToRecyclerView( recyclerview )


        /*fun onItemLongClick(viewHolder: RecyclerView.ViewHolder) {
            noteViewModel.deleteNote( adapter.getNoteAt(viewHolder.adapterPosition) )
        }*/

    }


/*fun (note: Note, viewHolder: RecyclerView.ViewHolder){
        noteViewModel.deleteNote( recyclerView.adapter.getNoteAt(viewHolder.adapterPosition) )
    }*/

    override fun onItemClicked(note: Note ) {
        val intent = Intent( this, EditNote::class.java)
        intent.putExtra(EditNote.EXTRA_ID, note.id)
        intent.putExtra(EditNote.EXTRA_REPLY, note.title)
        intent.putExtra(EditNote.EXTRA1_REPLY, note.content)
        startActivityForResult(intent, UpdateActivityRequestCode)
    }

    //gets the data that is added in the AddCity Activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AddNoteRequestCode && resultCode == RESULT_OK) {
            val title = data?.getStringExtra(AddNote.EXTRA_REPLY_TITLE).toString()
            val content =  data?.getStringExtra(AddNote.EXTRA_REPLY_CONTENT).toString()
            val note = Note(title = (title), content = (content))
            noteViewModel.insert(note)

            Toast.makeText(applicationContext,"Nota Adicionada",Toast.LENGTH_LONG).show()
        }
        else if(requestCode == AddNoteRequestCode) {
            Toast.makeText(applicationContext,"Campos Incompletos",Toast.LENGTH_LONG).show()
        }

        if (requestCode == UpdateActivityRequestCode && resultCode == RESULT_OK) {
            val id = data?.getIntExtra( EditNote.EXTRA_ID, -1 )

            val title = data?.getStringExtra( EditNote.EXTRA_REPLY ).toString()
            val content = data?.getStringExtra( EditNote.EXTRA1_REPLY ).toString()
            val note = Note(id,title,content)

            noteViewModel.updateNote(note)
            Toast.makeText(applicationContext,"Nota Editada",Toast.LENGTH_LONG).show()
        }
        else if(requestCode == UpdateActivityRequestCode) {
            Toast.makeText(applicationContext,"Campos Incompletos",Toast.LENGTH_LONG).show()
        }
    }

    //creates the top right menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    //options presented on the menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            //delete all entries in the db
            R.id.DeleteAll -> {
                noteViewModel.deleteAll()
                true
            }
            R.id.AllNotes -> {
                // recycler view
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                val adapter = NoteAdapter(this, this)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)
                // view model
                noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
                noteViewModel.allNotes.observe(this, Observer { notes ->
                    // Update the cached copy of the words in the adapter.
                    notes?.let { adapter.setNotes(it) }
                })
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}