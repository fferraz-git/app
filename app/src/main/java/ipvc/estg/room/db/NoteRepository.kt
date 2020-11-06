package ipvc.estg.room.db

import androidx.lifecycle.LiveData
import ipvc.estg.room.dao.NoteDao
import ipvc.estg.room.entities.Note


// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class NoteRepository(private val noteDao: NoteDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    fun getNotesByTitle(title: String): LiveData<List<Note>> {
        return noteDao.getNotesByTitle(title)
    }

   /* fun getCountryFromCity(title: String): LiveData<Note> {
        return noteDao.getCountryFromCity(title)
    }*/

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun deleteAll(){
        noteDao.deleteAll()
    }

    suspend fun deleteByTitle(title: String){
        noteDao.deleteByTitle(title)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }


   /* suspend fun updateCity(note: Note) {
        noteDao.updateCity(note)
    }

    suspend fun updateCountryFromCity(title: String, content: String){
        noteDao.updateCountryFromCity(title, content)
    }*/
}
