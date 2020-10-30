package ipvc.estg.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ipvc.estg.room.entities.City
import ipvc.estg.room.entities.Note

@Dao
interface NoteDao {

    @Query("SELECT * from notes ORDER BY title ASC")
    fun getAllCities(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE title == :title")
    fun getCitiesByCountry(title: String): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE title == :title")
    fun getCountryFromCity(title: String): LiveData<Note>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun updateCity(notes: City)

    @Query("DELETE FROM notes")
    suspend fun deleteAll()

    @Query("DELETE FROM notes where title == :title")
    suspend fun deleteByCity(title: String)

    @Query("UPDATE notes SET content=:content WHERE title == :title")
    suspend fun updateCountryFromCity(title: String, content: String)

}