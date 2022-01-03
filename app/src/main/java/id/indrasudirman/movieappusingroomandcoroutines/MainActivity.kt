package id.indrasudirman.movieappusingroomandcoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.indrasudirman.movieappusingroomandcoroutines.databinding.ActivityMainBinding
import id.indrasudirman.movieappusingroomandcoroutines.movie.MoviesListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private var shownFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //View Binding
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

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
        mainBinding.contentMain.navigation.setOnItemSelectedListener(object: BottomNavigationView.OnNavigationItemReselectedListener {

        })
    }

    private fun showFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_holder, fragment)
        fragmentTransaction.commitNow()
        shownFragment = fragment
    }
}

//Jangan lupa check Manifest, ada file Provider
