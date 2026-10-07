package com.example.softchat

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val cream = Color.rgb(255,251,247)
    private val surface = Color.rgb(255,248,243)
    private val pink = Color.rgb(246,221,229)
    private val blue = Color.rgb(227,239,246)
    private val text = Color.rgb(81,71,71)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = cream
        window.navigationBarColor = surface

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(cream)
        }

        val top = TextView(this).apply {
            this.text = "SoftChat"
            setTextColor(text)
            textSize = 19f
            gravity = Gravity.CENTER_VERTICAL
            setPadding(22, 0, 22, 0)
            background = rounded(surface, 0f)
        }
        root.addView(top, LinearLayout.LayoutParams(-1, dp(58)))

        val list = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.BOTTOM
            setPadding(dp(10), dp(12), dp(10), dp(12))
        }
        val scroll = ScrollView(this).apply {
            addView(list, ViewGroup.LayoutParams(-1, -2))
        }
        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))

        addBubble(list, "你好呀，小希。", false)

        val bottom = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(10), dp(8), dp(10), dp(10))
            setBackgroundColor(surface)
        }

        val input = EditText(this).apply {
            hint = "写点什么吧……"
            setTextColor(text)
            setHintTextColor(Color.rgb(154,142,142))
            textSize = 16f
            setSingleLine(false)
            maxLines = 4
            background = rounded(cream, 24f, Color.rgb(235,210,218))
            setPadding(dp(16), dp(8), dp(16), dp(8))
        }

        val send = Button(this).apply {
            this.text = "发送"
            setTextColor(text)
            textSize = 14f
            background = rounded(pink, 20f)
            setOnClickListener {
                val s = input.text.toString().trim()
                if (s.isNotEmpty()) {
                    addBubble(list, s, true)
                    input.text.clear()
                    scroll.post { scroll.fullScroll(View.FOCUS_DOWN) }
                }
            }
        }

        bottom.addView(input, LinearLayout.LayoutParams(0, dp(56), 1f))
        bottom.addView(send, LinearLayout.LayoutParams(dp(78), dp(56)).apply {
            leftMargin = dp(8)
        })
        root.addView(bottom)

        setContentView(root)
    }

    private fun addBubble(parent: LinearLayout, message: String, user: Boolean) {
        val row = LinearLayout(this).apply {
            gravity = if (user) Gravity.END else Gravity.START
            setPadding(0, dp(5), 0, dp(5))
        }
        val bubble = TextView(this).apply {
            text = message
            setTextColor(text)
            textSize = 16f
            setPadding(dp(15), dp(11), dp(15), dp(11))
            background = rounded(if (user) pink else blue, 22f)
            maxWidth = dp(300)
        }
        row.addView(bubble, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ))
        parent.addView(row)
    }

    private fun rounded(color: Int, radiusDp: Float, stroke: Int? = null): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = dp(radiusDp).toFloat()
            stroke?.let { setStroke(dp(1f), it) }
        }
    }

    private fun dp(v: Float): Int =
        (v * resources.displayMetrics.density + 0.5f).toInt()

    private fun dp(v: Int): Int = dp(v.toFloat())
}
