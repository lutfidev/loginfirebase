package com.skywolke.beratbadankitaskywolke

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseUser
import com.skywolke.beratbadankitaskywolke.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val contract = FirebaseAuthUIActivityResultContract()
    private val signInLauncher = registerForActivityResult(contract) { }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener { mulaiLogin() }
        viewModel.authState.observe(this) {updateUI(it)}
    }

    private fun updateUI(user: FirebaseUser?) = with(binding){
        if (user == null) {
            namaTextView.visibility = View.GONE
            imageView.visibility = View.GONE
            login.text = getString(R.string.login)
        }
        else {
            namaTextView.text = user.displayName
            Glide.with(this@MainActivity).load(user.photoUrl).into(imageView)

            namaTextView.visibility = View.VISIBLE
            imageView.visibility = View.VISIBLE
            login.text = getString(R.string.logout)
        }
    }

    private fun mulaiLogin() {
        if (binding.login.text == getString(R.string.logout)) {
            AuthUI.getInstance().signOut(this)
            return
        }

        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(intent)
    }
}