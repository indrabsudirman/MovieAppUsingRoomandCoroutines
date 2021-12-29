package id.indrasudirman.movieappusingroomandcoroutines.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import id.indrasudirman.movieappusingroomandcoroutines.database.Movie
import id.indrasudirman.movieappusingroomandcoroutines.database.MovieDatabase
import id.indrasudirman.movieappusingroomandcoroutines.databinding.ItemListDirectorBinding
import id.indrasudirman.movieappusingroomandcoroutines.databinding.ItemListMovieBinding
import id.indrasudirman.movieappusingroomandcoroutines.movie.MovieSaveDialogFragment.Companion.newInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MoviesListAdapter(private val parent: Fragment) : RecyclerView.Adapter<MoviesListAdapter.MoviesViewHolder>() {

    private val layOutInflater: LayoutInflater = LayoutInflater.from(parent.requireContext())
    private var movieList: List<Movie>? = null

    fun setMovieList(movieList: List<Movie>?) {
        this.movieList = movieList
        notifyDataSetChanged()
    }

    inner class MoviesViewHolder (val itemListMovieBinding: ItemListMovieBinding): RecyclerView.ViewHolder(itemListMovieBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemListMovieBinding = ItemListMovieBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return MoviesViewHolder(itemListMovieBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        movieList?.let { list ->
            val movie = list[position]
            holder.itemListMovieBinding.tvMovieTitle.text = movie.title
            runBlocking {
                val directorFullName = withContext(Dispatchers.Default) {
                    getDirectorFullName(movie)
                }

                holder.itemListMovieBinding.tvMovieDirectorFullName.text = directorFullName ?: ""
                holder.itemListMovieBinding.root.setOnClickListener {
                    val dialogFragment: DialogFragment = newInstance(movie.title, directorFullName)
                    dialogFragment.setTargetFragment(parent, 99)
                    dialogFragment.show(
                        (parent.activity as AppCompatActivity).supportFragmentManager,
                        MovieSaveDialogFragment.TAG_DIALOG_MOVIE_SAVE
                    )
                }
            }
        }
    }

    private suspend fun getDirectorFullName(movie: Movie) : String? {
        return MovieDatabase.getDatabase(parent.requireContext()).directorDao().findDirectorById(movie.directorId)?.fullName
    }

    override fun getItemCount(): Int {
        return if (movieList == null) {
            0
        } else {
            movieList!!.size
        }
    }
}