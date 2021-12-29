package id.indrasudirman.movieappusingroomandcoroutines.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import id.indrasudirman.movieappusingroomandcoroutines.database.Movie
import id.indrasudirman.movieappusingroomandcoroutines.databinding.ItemListDirectorBinding
import id.indrasudirman.movieappusingroomandcoroutines.databinding.ItemListMovieBinding

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
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}