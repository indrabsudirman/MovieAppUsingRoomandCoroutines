package id.indrasudirman.movieappusingroomandcoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import id.indrasudirman.movieappusingroomandcoroutines.database.MovieDatabase
import id.indrasudirman.movieappusingroomandcoroutines.database.rePopulateDb
import id.indrasudirman.movieappusingroomandcoroutines.databinding.ActivityMainBinding
import id.indrasudirman.movieappusingroomandcoroutines.databinding.ContentMainBinding
import id.indrasudirman.movieappusingroomandcoroutines.director.DirectorSaveDialogFragment
import id.indrasudirman.movieappusingroomandcoroutines.director.DirectorsListFragment
import id.indrasudirman.movieappusingroomandcoroutines.movie.MovieSaveDialogFragment
import id.indrasudirman.movieappusingroomandcoroutines.movie.MoviesListFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var contentMainBinding: ContentMainBinding
    private var shownFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //View Binding
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        contentMainBinding = ContentMainBinding.bind(mainBinding.root)

        initToolbar(getString(R.string.app_name))
        initView()
        if (savedInstanceState == null) {
            showFragment(MoviesListFragment.newInstance())
        }


    }

    private fun initToolbar(@Nullable title: String?) {
        mainBinding.toolbar.title = title
        setSupportActionBar(mainBinding.toolbar)
    }

    private fun initView() {
        contentMainBinding.navigation.setOnItemSelectedListener {
            item -> when (item.itemId) {
                R.id.navigation_movies -> {
                    MOVIES_SHOWN = true
                    showFragment(MoviesListFragment.newInstance())
                    return@setOnItemSelectedListener true
                }
            R.id.navigation_directors -> {
                MOVIES_SHOWN = false
                showFragment(DirectorsListFragment.newInstance())
                return@setOnItemSelectedListener true
            }
            }
            return@setOnItemSelectedListener false
        }

        contentMainBinding.fab.setOnClickListener { showSaveDialog() }
    }

    private fun showFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_holder, fragment)
        fragmentTransaction.commitNow()
        shownFragment = fragment
    }

    private fun showSaveDialog() {
        val dialogFragment: DialogFragment
        val tag: String
        if (MOVIES_SHOWN) {
            dialogFragment = MovieSaveDialogFragment.newInstance(null, null)
            tag = MovieSaveDialogFragment.TAG_DIALOG_MOVIE_SAVE
        } else {
            dialogFragment = DirectorSaveDialogFragment.newInstance(null)
            tag = DirectorSaveDialogFragment.TAG_DIALOG_DIRECTOR_SAVE
        }
        dialogFragment.show(supportFragmentManager, tag)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_list_data -> {
                deleteCurrentListData()
                true
            }
            R.id.action_re_create_database -> {
                reCreateDatabase()
                true
            }
            R.id.action_export_to_csv_file -> {
                exportDatabaseToCSVFile()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteCurrentListData() {
        if (MOVIES_SHOWN) {
            (shownFragment as MoviesListFragment).removeData()
        } else {
            (shownFragment as DirectorsListFragment).removeData()
        }
    }

    private fun reCreateDatabase() {
        GlobalScope.launch(Dispatchers.IO) {
            rePopulateDb(MovieDatabase.getDatabase(this@MainActivity))
        }
    }

    private fun getCSVFileName() : String =
        if (MOVIES_SHOWN) "MoviesRoomExample.csv" else "DirectorRoomExample.csv"

    private fun exportDatabaseToCSVFile() {
        val csvFile = generatedFile(this, getCSVFileName())
        if (csvFile != null) {
            if (MOVIES_SHOWN) {
                (shownFragment as MoviesListFragment).exportMoviesWithDirectorsToCSVFile(csvFile)
            } else {
                (shownFragment as DirectorsListFragment).exportDirectorsToCSVFile(csvFile)
            }

            Toast.makeText(this, getString(R.string.csv_file_generated_text), Toast.LENGTH_LONG).show()
            val intent = goToFileIntent(this, csvFile)
            startActivity(intent)
        } else {
            Toast.makeText(this, getString(R.string.csv_file_not_generated_text), Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private var MOVIES_SHOWN = true
    }
}

//Jangan lupa check Manifest, ada file Provider
