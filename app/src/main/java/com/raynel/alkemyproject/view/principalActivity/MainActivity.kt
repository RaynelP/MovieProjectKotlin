package com.raynel.alkemyproject.view.principalActivity

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.raynel.alkemyproject.R
import com.raynel.alkemyproject.databinding.ActivityMainBinding
import com.raynel.alkemyproject.view.loginActivity.LoginActivity
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        configActionBarWithNavController()
        configBottomNavigation()

        //get an instance of auth
        auth = FirebaseAuth.getInstance()
        //get an instance of storage
        storage = FirebaseStorage.getInstance()
        //reference
        val storageReference = storage.reference
    }

    override fun onStart() {
        super.onStart()
        //
        if(auth.currentUser == null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun configBottomNavigation() {
        val appConfig = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.searchFragment,
                R.id.favoritesFragment,
                R.id.userFragment
            )
        )
        NavigationUI
            .setupActionBarWithNavController(this, getNavController(), appConfig)
        NavigationUI
            .setupWithNavController(binding.navigationBottom, getNavController())

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI
            .navigateUp(getNavController(), null)
    }

    private fun configActionBarWithNavController(){
        NavigationUI
            .setupActionBarWithNavController(this, getNavController())
    }

    private fun getNavController(): NavController {

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        return navHostFragment.navController
    }

    fun signUp(){

        FirebaseAuth.getInstance().signOut()

        startActivity(Intent(
            this,
            MainActivity::class.java
        ))

        finish()
    }

    fun showOrDoneProgressBar(visibility: Int){
        binding.progressBar.visibility = visibility
    }

}