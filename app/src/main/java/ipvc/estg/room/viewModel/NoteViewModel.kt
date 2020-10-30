package ipvc.estg.room.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ipvc.estg.room.db.NoteDB
import ipvc.estg.room.db.NoteRepository
import ipvc.estg.room.entities.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    var allNotes: LiveData<List<Note>>

    init {
        val notesDao = NoteDB.getDatabase(application, viewModelScope).noteDao()
        repository = NoteRepository(notesDao)
        allNotes = repository.all
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    // delete all
    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    // delete by city
    fun deleteByCity(title: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteByCity(title)
    }

    fun getCitiesByCountry(content: String): LiveData<List<Note>> {
        return repository.getCitiesByCountry(content)
    }

    fun getCountryFromCity(title: String): LiveData<Note> {
        return repository.getCountryFromCity(title)
    }

    fun updateCity(note: Note) = viewModelScope.launch {
        repository.updateCity(note)
    }

    fun updateCountryFromCity(title: String, content: String) = viewModelScope.launch {
        repository.updateCountryFromCity(title, content)
    }