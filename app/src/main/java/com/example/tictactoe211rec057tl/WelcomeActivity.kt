package com.example.tictactoe211rec057tl

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val nameEt = findViewById<EditText>(R.id.nameEt)
        val pvpBtn = findViewById<Button>(R.id.pvpBtn)
        val pvcBtn = findViewById<Button>(R.id.pvcBtn)

        val onModeSelected: (Boolean) -> Unit = { isPvC ->
            val name = nameEt.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(this, "Ievadiet NickName!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("playerName", name)
                intent.putExtra("isPvC", isPvC)
                startActivity(intent)
            }
        }

        pvpBtn.setOnClickListener { onModeSelected(false) }
        pvcBtn.setOnClickListener { onModeSelected(true) }
    }
}
