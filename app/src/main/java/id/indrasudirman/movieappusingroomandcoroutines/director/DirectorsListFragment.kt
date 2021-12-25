package id.indrasudirman.movieappusingroomandcoroutines.director

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.indrasudirman.movieappusingroomandcoroutines.R
import id.indrasudirman.movieappusingroomandcoroutines.database.Director

class DirectorsListFragment : Fragment() {

    private lateinit var directorsListAdapter: DirectorsListAdapter
    private lateinit var directorsViewModel: DirectorsViewModel
    private lateinit var directorList: List<Director>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_directors, container, false)
        initView(view)

        return view
    }

    private fun initView(view: View) {
        directorsListAdapter = DirectorsListAdapter(requireContext())
    }
}