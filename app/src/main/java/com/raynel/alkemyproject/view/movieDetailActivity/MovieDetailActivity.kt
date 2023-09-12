package com.raynel.alkemyproject.view.movieDetailActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raynel.alkemyproject.databinding.ActivityDetailMovieBinding

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding
    var movieId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // inflate
        binding = ActivityDetailMovieBinding
            .inflate(layoutInflater)
        // set view in the activity
        setContentView(binding.root)

        movieId = intent.getLongExtra("id", -1)

    }
}