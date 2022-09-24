package com.raynel.alkemyproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.raynel.alkemyproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configActionBarWithNavController()

        configBottomNavigation()
    }

    private fun configBottomNavigation() {
        val appConfig = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.searchFragment
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
}