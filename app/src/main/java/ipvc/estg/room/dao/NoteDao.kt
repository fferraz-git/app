package ipvc.estg.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ipvc.estg.room.entities.Note

/* where the actual queries are and where the repository is connected
* so that it doesnt have to connect directly to the database
 */
@Dao
interface NoteDao {

    @Query("SELECT * from notes ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun updateNote( notes: Note)

    @Delete
    suspend fun deleteNote( note: Note )

    @Query("DELETE FROM notes")
    suspend fun deleteAll()

}