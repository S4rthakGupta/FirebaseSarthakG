package com.example.firebasesarthakg

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasesarthakg.databinding.ActivityMainBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    // This is the adapter, which connects data from Firebase to the list shown on the screen.
    private var adapter: PersonAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // This tells the app to get data from the "people" section in the Firebase database.
        val query = FirebaseDatabase.getInstance().reference.child("people")

        // This sets up the options for the adapter, telling it what data to show.
        val options = FirebaseRecyclerOptions.Builder<Person>().setQuery(query, Person:: class.java).build()

        // This below line initializes the adapter with the options.
        adapter = PersonAdapter(options)

        // Set up the RecyclerView with a linear layout manager and attach the adapter
        val rView : RecyclerView = findViewById(R.id.rView)
        rView.layoutManager = LinearLayoutManager(this)
        rView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        // When the app starts, this tells the adapter to start showing data from Firebase.
        adapter?.startListening()
    }
}