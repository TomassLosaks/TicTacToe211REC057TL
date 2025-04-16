package com.example.tictactoe211rec057tl

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isPvC = intent?.getBooleanExtra("isPvC", true) ?: true
        val playerName = intent?.getStringExtra("playerName") ?: "Spēlētājs"

        Toast.makeText(this, "Sveicināti, $playerName!", Toast.LENGTH_SHORT).show()

        val recyclerView = findViewById<RecyclerView>(R.id.fieldRv)
        val resetBtn = findViewById<Button>(R.id.resetBtn)

        fun startNewGame() {
            game = GameImp(isPvC)
            recyclerView.layoutManager = GridLayoutManager(this, game.field.size)
            recyclerView.adapter = FieldAdapter(game.field) { row, col ->
                if (game.act(row, col)) {
                    recyclerView.adapter?.notifyDataSetChanged()
                    if (game.isFinished) {
                        val winnerText = game.winner?.let {
                            if (it) "Uzvarēja X" else "Uzvarēja O"
                        } ?: "Neizšķirts"
                        Toast.makeText(this, winnerText, Toast.LENGTH_SHORT).show()

                        resetBtn.visibility = Button.VISIBLE
                    }
                }
            }
        }

        resetBtn.setOnClickListener {
            resetBtn.visibility = Button.GONE
            startNewGame()
        }

        startNewGame()
    }
}
