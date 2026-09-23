package com.example.longarms

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var isLongArmOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnToggle = findViewById<Button>(R.id.btnToggleLongArm)

        btnToggle.setOnClickListener {
            if (!isLongArmOn) {
                // Activer Long Arm
                val success = runShellCommand("setprop debug.oculus.headlock 3")
                if (success) {
                    isLongArmOn = true
                    btnToggle.text = "Long Arm : ON"
                } else {
                    Toast.makeText(this, "Échec d'exécution", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Désactiver Long Arm
                val success = runShellCommand("setprop debug.oculus.headlock 0")
                if (success) {
                    isLongArmOn = false
                    btnToggle.text = "Long Arm : OFF"
                } else {
                    Toast.makeText(this, "Échec de la désactivation", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun runShellCommand(command: String): Boolean {
        return try {
            val process = Runtime.getRuntime().exec(arrayOf("sh", "-c", command))
            process.waitFor() == 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}

