package id.indrasudirman.movieappusingroomandcoroutines.director

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import id.indrasudirman.movieappusingroomandcoroutines.database.Director
import id.indrasudirman.movieappusingroomandcoroutines.databinding.ItemListDirectorBinding

class DirectorsListAdapter(val context: Context) : RecyclerView.Adapter<DirectorsListAdapter.DirectorsViewHolder> () {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var directorList: List<Director>? = null

    fun setDirectorList(directorList: List<Director>) {
        this.directorList = directorList
        notifyDataSetChanged()
    }

    inner class DirectorsViewHolder(val itemListDirectorBinding: ItemListDirectorBinding): RecyclerView.ViewHolder(itemListDirectorBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectorsViewHolder {
        val itemListDirectorBinding = ItemListDirectorBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)
        return DirectorsViewHolder(itemListDirectorBinding)
    }

    override fun onBindViewHolder(holder: DirectorsViewHolder, position: Int) {
        directorList?.let {
            val director = it[position]
            holder.itemListDirectorBinding.tvDirector.text = director.fullName
            holder.itemListDirectorBinding.root.setOnClickListener {
                val dialogFragment : DialogFragment = DirectorSaveDialogFragment.newInstance(director.fullName)
                dialogFragment.show(
                    (context as AppCompatActivity).supportFragmentManager,
                    DirectorSaveDialogFragment.TAG_DIALOG_DIRECTOR_SAVE
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return if (directorList == null) {
            0
        } else {
            directorList!!.size
        }
    }
}