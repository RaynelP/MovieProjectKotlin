package com.raynel.alkemyproject.view.loginActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.raynel.alkemyproject.R
import com.raynel.alkemyproject.databinding.ActivityLoginBinding
import com.raynel.alkemyproject.model.User
import com.raynel.alkemyproject.showMessageWithSnackBar
import com.raynel.alkemyproject.view.mainActivity.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityLoginBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var dataBase: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        //inflate the dataBinding and set it in the activity
        binding = ActivityLoginBinding
            .inflate(layoutInflater)
        setContentView(binding.root)

        //congifure the navigationView and the navController
        configNavigation()

        //instance the auth
        auth = FirebaseAuth
            .getInstance()

        //instance the dataBase
        dataBase = FirebaseFirestore
            .getInstance()

    }

    private fun configNavigation() {
        val navFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_login)
                as NavHostFragment

        val navController = navFragment.findNavController()

        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBarWithNavController(
            navController,
            appBarConfiguration)
    }

    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload()
        }

    }

    //create a user with email and password
    fun onCreateUser(email: String, password: String, name: String, image: String = "") {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                val TAG = "signUp"
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    showMessageWithSnackBar(applicationContext, binding.root, "User created successful")

                    val userModel = User(name, email, image)

                    dataBase.collection("Users")
                        .document(userModel.email)
                        .set(userModel)
                        .addOnSuccessListener { documentReference ->
                            val user = auth.currentUser

                            showMessageWithSnackBar(applicationContext, binding.root, "Bienvenid@ ${userModel.name}")
                            //Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                            updateUI(user)
                        }
                        .addOnFailureListener { e ->
                            showMessageWithSnackBar(applicationContext, binding.root, "An error ocurred, please try again")
                            Log.w(TAG, "Error adding document", e)
                            updateUI(null)
                        }

                } else {
                    showMessageWithSnackBar(applicationContext, binding.root, "An error ocurred, please try again")
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    //Toast.makeText(baseContext, "Authentication failed.",
                    //    Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    fun onLogInUser(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    //Log.w(TAG, "signInWithEmail:failure", task.exception)
                    //Toast.makeText(baseContext, "Authentication failed.",
                    //    Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }


    private fun updateUI(user: FirebaseUser?){
        val validUser = (user != null)
        if(validUser){
            reload()
        }else{
            showMessageWithSnackBar(
                applicationContext,
                binding.root,
                "Ha ocurrido un error de autenticacion"
            )
        }
    }

    private fun reload() {
        val intent = Intent(
            this,
            MainActivity::class.java
        )
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_login)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}