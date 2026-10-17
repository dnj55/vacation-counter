package com.example.vacationcounter

import android.app.Activity
import android.app.AlertDialog
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

/** Simple offline counter with light and dark modes. */
class MainActivity : Activity() {
    private val preferences by lazy { getSharedPreferences("vacation_counter", MODE_PRIVATE) }
    private var count = 0
    private var darkMode = false
    private lateinit var countText: TextView
    private lateinit var decrementButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        val systemDark = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
            Configuration.UI_MODE_NIGHT_YES
        darkMode = preferences.getBoolean("dark_mode", systemDark)
        setTheme(
            if (darkMode) android.R.style.Theme_Material_NoActionBar
            else android.R.style.Theme_Material_Light_NoActionBar
        )
        super.onCreate(savedInstanceState)
        count = preferences.getInt("count", 0)

        val background = if (darkMode) Color.rgb(22, 26, 34) else Color.rgb(249, 250, 252)
        val titleColor = if (darkMode) Color.rgb(242, 246, 252) else Color.rgb(33, 43, 54)
        val secondaryText = if (darkMode) Color.rgb(188, 198, 212) else Color.DKGRAY
        val numberColor = if (darkMode) Color.rgb(126, 193, 255) else Color.rgb(28, 99, 165)
        val primaryButton = if (darkMode) Color.rgb(50, 110, 189) else Color.rgb(37, 104, 181)
        val secondaryButton = if (darkMode) Color.rgb(55, 65, 80) else Color.rgb(222, 229, 237)
        val secondaryButtonText = if (darkMode) Color.WHITE else Color.rgb(33, 43, 54)

        // Ensure status/navigation icons are legible on the selected background.
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = if (darkMode) 0 else
            (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
        @Suppress("DEPRECATION")
        window.statusBarColor = background
        @Suppress("DEPRECATION")
        window.navigationBarColor = background

        val screen = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            fitsSystemWindows = true
            setPadding(dp(24), dp(32), dp(24), dp(32))
            setBackgroundColor(background)
        }
        val heading = TextView(this).apply {
            text = "Vacation Counter"
            textSize = 27f
            setTextColor(titleColor)
            gravity = Gravity.CENTER
        }
        screen.addView(heading, LinearLayout.LayoutParams(-1, -2))
        val subtitle = TextView(this).apply {
            text = "Things we've counted"
            textSize = 16f
            setTextColor(secondaryText)
            gravity = Gravity.CENTER
        }
        screen.addView(subtitle, LinearLayout.LayoutParams(-1, -2).apply { topMargin = dp(20) })
        countText = TextView(this).apply {
            textSize = 82f
            gravity = Gravity.CENTER
            setTextColor(numberColor)
            setPadding(0, dp(8), 0, dp(24))
        }
        screen.addView(countText, LinearLayout.LayoutParams(-1, -2))

        val controls = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        decrementButton = Button(this).apply {
            text = "− One"
            textSize = 19f
            isAllCaps = false
            backgroundTintList = ColorStateList.valueOf(secondaryButton)
            setTextColor(secondaryButtonText)
            setOnClickListener {
                if (count > 0) {
                    count--
                    saveCount()
                }
            }
        }
        controls.addView(decrementButton, LinearLayout.LayoutParams(0, dp(68), 1f).apply {
            rightMargin = dp(6)
        })
        val incrementButton = Button(this).apply {
            text = "+ One"
            textSize = 19f
            isAllCaps = false
            backgroundTintList = ColorStateList.valueOf(primaryButton)
            setTextColor(Color.WHITE)
            setOnClickListener {
                if (count < Int.MAX_VALUE) {
                    count++
                    saveCount()
                }
            }
        }
        controls.addView(incrementButton, LinearLayout.LayoutParams(0, dp(68), 1f).apply {
            leftMargin = dp(6)
        })
        screen.addView(controls, LinearLayout.LayoutParams(-1, -2))

        val resetButton = Button(this).apply {
            text = "Reset counter"
            isAllCaps = false
            backgroundTintList = ColorStateList.valueOf(secondaryButton)
            setTextColor(secondaryButtonText)
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
        screen.addView(resetButton, LinearLayout.LayoutParams(-1, dp(56)).apply {
            topMargin = dp(12)
        })

        val themeButton = Button(this).apply {
            text = if (darkMode) "Switch to light mode" else "Switch to dark mode"
            isAllCaps = false
            backgroundTintList = ColorStateList.valueOf(secondaryButton)
            setTextColor(secondaryButtonText)
            setOnClickListener {
                preferences.edit().putBoolean("dark_mode", !darkMode).apply()
                recreate()
            }
        }
        screen.addView(themeButton, LinearLayout.LayoutParams(-1, dp(56)).apply {
            topMargin = dp(12)
        })
        val footer = TextView(this).apply {
            text = "Works offline · Saved on this phone"
            textSize = 13f
            setTextColor(secondaryText)
            gravity = Gravity.CENTER
        }
        screen.addView(footer, LinearLayout.LayoutParams(-1, -2).apply { topMargin = dp(24) })
        setContentView(screen)
        showCount()
    }

    private fun showCount() {
        countText.text = count.toString()
        countText.contentDescription = "$count things counted"
        decrementButton.isEnabled = count > 0
        decrementButton.alpha = if (count > 0) 1f else 0.5f
    }

    private fun saveCount() {
        preferences.edit().putInt("count", count).apply()
        showCount()
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density + 0.5f).toInt()
}
