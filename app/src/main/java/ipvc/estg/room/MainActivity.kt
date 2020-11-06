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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ipvc.estg.room.adapters.NoteAdapter
import ipvc.estg.room.entities.Note
import ipvc.estg.room.viewModel.NoteViewModel

class MainActivity : AppCompatActivity(), NoteAdapter.OnItemClickListener {

    private lateinit var noteViewModel: NoteViewModel
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

    }

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
            //queries the database for all the cities in portugal
            /*R.id.NotesTitle -> {
                // recycler view
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                val adapter = NoteAdapter(this)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)
                // view model
                noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
                noteViewModel.getNotesByTitle("Portugal").observe(this, Observer { notes ->
                    // Update the cached copy of the words in the adapter.
                    notes?.let { adapter.setNotes(it) }
                })
                true
            }*/

            //queries the database and orders all the cities in DESC by alphabet
           /* R.id.AllNotes -> {
                // recycler view
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                val adapter = NoteAdapter(this)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)
                // view model
                noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
                noteViewModel.allNotes.observe(this, Observer { notes ->
                    // Update the cached copy of the words in the adapter.
                    notes?.let { adapter.setNotes(it) }
                })
                true
            }*/

            //queries the database and toasts the contry from where the city aveiro is
            /*R.id.getCountryFromAveiro -> {
                cityViewModel = ViewModelProvider(this).get(CityViewModel::class.java)
                cityViewModel.getCountryFromCity("Aveiro").observe(this, Observer { city ->
                    Toast.makeText(this, city.country, Toast.LENGTH_SHORT).show()
                })
                true
            }*/
            //deletes the entry aveiro from the db
            R.id.DeleteByTitle -> {
                noteViewModel.deleteByTitle("Aveiro")
                true
            }

            //alter the db aveiro entry
            /*R.id.alterar -> {
                val city = City(id = 1, city = "xxx", country = "xxx")
                cityViewModel.updateCity(city)
                true
            }
            //changes aveiro country
            R.id.alteraraveiro -> {
                cityViewModel.updateCountryFromCity("Aveiro", "Japão")
                true
            }*/

            else -> super.onOptionsItemSelected(item)
        }
    }

}