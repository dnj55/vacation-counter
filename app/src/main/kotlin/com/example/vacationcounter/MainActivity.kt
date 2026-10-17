package com.example.vacationcounter

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

/** A deliberately simple, completely offline first Android app. */
class MainActivity : Activity() {
    private val preferences by lazy { getSharedPreferences("vacation_counter", MODE_PRIVATE) }
    private var count = 0
    private lateinit var countText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        count = preferences.getInt("count", 0)

        val screen = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            fitsSystemWindows = true
            setPadding(dp(24), dp(32), dp(24), dp(32))
            setBackgroundColor(Color.rgb(249, 250, 252))
        }

        val heading = TextView(this).apply {
            text = "Vacation Counter"
            textSize = 27f
            setTextColor(Color.rgb(33, 43, 54))
            gravity = Gravity.CENTER
        }
        screen.addView(heading, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))

        val subtitle = TextView(this).apply {
            text = "Things we've counted"
            textSize = 16f
            setTextColor(Color.DKGRAY)
            gravity = Gravity.CENTER
        }
        screen.addView(subtitle, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            topMargin = dp(20)
        })

        countText = TextView(this).apply {
            textSize = 82f
            gravity = Gravity.CENTER
            setTextColor(Color.rgb(28, 99, 165))
            setPadding(0, dp(8), 0, dp(24))
        }
        showCount()
        screen.addView(countText, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))

        val addButton = Button(this).apply {
            text = "Count one +"
            textSize = 20f
            isAllCaps = false
            minHeight = dp(64)
            setOnClickListener {
                if (count < Int.MAX_VALUE) {
                    count += 1
                    saveCount()
                }
            }
        }
        screen.addView(addButton, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, dp(68)))

        val resetButton = Button(this).apply {
            text = "Reset counter"
            isAllCaps = false
            setOnClickListener {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Reset counter?")
                    .setMessage("This will set the counter back to zero.")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Reset") { _, _ ->
                        count = 0
                        saveCount()
                    }
                    .show()
            }
        }
        screen.addView(resetButton, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, dp(56)).apply { topMargin = dp(12) })

        val footer = TextView(this).apply {
            text = "Works offline · Saved on this phone"
            textSize = 13f
            setTextColor(Color.GRAY)
            gravity = Gravity.CENTER
        }
        screen.addView(footer, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply { topMargin = dp(24) })

        setContentView(screen)
    }

    private fun showCount() {
        countText.text = count.toString()
        countText.contentDescription = "$count things counted"
    }

    private fun saveCount() {
        preferences.edit().putInt("count", count).apply()
        showCount()
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density + 0.5f).toInt()
}
