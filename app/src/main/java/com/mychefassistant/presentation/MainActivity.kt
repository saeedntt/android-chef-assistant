package com.mychefassistant.presentation

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.mychefassistant.BuildConfig
import com.mychefassistant.R

class MainActivity : AppCompatActivity() {
    private lateinit var layout: DrawerLayout
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLayout();
    }

    private fun initLayout() {
        layout = findViewById(R.id.layout)
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            layout.openDrawer(GravityCompat.START)
        }

        findViewById<TextView>(R.id.version).text =
            getString(R.string.version) + " " + BuildConfig.VERSION_NAME
    }
}