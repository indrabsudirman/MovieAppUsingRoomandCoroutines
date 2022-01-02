package id.indrasudirman.movieappusingroomandcoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.Nullable
import id.indrasudirman.movieappusingroomandcoroutines.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //View Binding
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)


    }

    private fun initToolbar(@Nullable title: String?) {

    }
}

//Jangan lupa check Manifest, ada file Provider
