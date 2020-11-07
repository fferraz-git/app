package ipvc.estg.room.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.room.R
import ipvc.estg.room.entities.Note
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class NoteAdapter internal constructor  (
    context: Context,
    val itemClickListener : OnItemClickListener,
    val itemLongClickListener: OnItemLongClickListener)
    : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>()

{

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = emptyList<Note>() // Cached copy of notes

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteItemView= itemView.textView
        val noteTitleView = itemView.titles
        val noteContentView= itemView.contents

        fun bind( note: Note, clickListener: OnItemClickListener) {
            noteTitleView.text = note.title
            noteContentView.text = note.content

            itemView.setOnClickListener {
                clickListener.onItemClicked(note)
            }
        }

        fun bind2(note: Note, longClickListener: OnItemLongClickListener){
            itemView.setOnLongClickListener{
                longClickListener.onItemLongClick(note)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current =  notes[position]
        holder.noteTitleView.text = current.title
        holder.noteContentView.text = current.content

        holder.bind( current, itemClickListener )
        holder.bind2(current, itemLongClickListener )
    }

    internal fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClicked( note: Note )
    }

    interface OnItemLongClickListener{
        fun onItemLongClick( note: Note )
    }

    fun getNoteAt(position: Int): Note {
        return notes[position]
    }
    override fun getItemCount() = notes.size

}