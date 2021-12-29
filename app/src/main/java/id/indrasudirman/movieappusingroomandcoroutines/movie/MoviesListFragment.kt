package id.indrasudirman.movieappusingroomandcoroutines.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.indrasudirman.movieappusingroomandcoroutines.database.Movie

class MoviesListFragment : Fragment() {

    private lateinit var moviesListAdapter: MoviesListAdapter
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var moviesList: List<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun initData() {}
}