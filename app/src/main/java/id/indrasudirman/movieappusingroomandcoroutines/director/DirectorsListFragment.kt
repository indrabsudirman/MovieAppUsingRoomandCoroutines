package id.indrasudirman.movieappusingroomandcoroutines.director

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import id.indrasudirman.movieappusingroomandcoroutines.R
import id.indrasudirman.movieappusingroomandcoroutines.database.Director
import id.indrasudirman.movieappusingroomandcoroutines.databinding.FragmentDirectorsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class DirectorsListFragment : Fragment() {

    private lateinit var directorsListAdapter: DirectorsListAdapter
    private lateinit var directorsViewModel: DirectorsViewModel
    private lateinit var directorList: List<Director>
    private  var _directorBinding: FragmentDirectorsBinding? = null
    private val directorsBinding get() = _directorBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _directorBinding = FragmentDirectorsBinding.inflate(inflater, container, false)
        val view = directorsBinding.root
        initView(view)

        return view
    }

    private fun initData(){
        directorsViewModel = ViewModelProvider(this).get(DirectorsViewModel::class.java)
        directorsViewModel.directorList.observe(this,
        Observer { directors: List<Director> ->
            directorList = directors
            directorsListAdapter.setDirectorList(directors)
        })
    }

    private fun initView(view: View) {
        val recyclerView: RecyclerView = directorsBinding.rvFragmentDirectors
        directorsListAdapter = DirectorsListAdapter(requireContext())
        recyclerView.adapter = directorsListAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    fun removeData() {
        GlobalScope.launch (Dispatchers.IO) { directorsViewModel.deleteAll() }
    }

    fun exportDirectorsToCSVFile (csvFile: File) {
        csvWriter().open(csvFile, append = false) {
            //Header
            writeRow(listOf("[id]", "[${Director.TABLE_NAME}]"))
            directorList.forEachIndexed { index, director ->
                writeRow(listOf(index, director.fullName))
            }
        }
    }

    companion object {
        fun newInstance(): DirectorsListFragment = DirectorsListFragment()
    }
}