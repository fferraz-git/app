package ipvc.estg.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ipvc.estg.room.entities.Note

@Dao
interface NoteDao {

    @Query("SELECT * from notes ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE title == :title")
    fun getNotesByTitle(title: String): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun updateNote(notes: Note)

    @Query("DELETE FROM notes")
    suspend fun deleteAll()

    @Query("DELETE FROM notes where title == :title")
    suspend fun deleteByTitle(title: String)

    /*
    @Query("UPDATE notes SET content=:content WHERE title == :title")
    suspend fun updateCountryFromCity(title: String, content: String)*/

}