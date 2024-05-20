package com.example.sprint2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_home)

        val btnListaEmpresas: Button = findViewById(R.id.btnListaEmpresas)
        val btnPerfil: Button = findViewById(R.id.btnPerfil)

        btnListaEmpresas.setOnClickListener {
            val intent = Intent(this@HomeActivity, ListaEmpresasActivity::class.java)
            startActivity(intent)
        }

        btnPerfil.setOnClickListener {
            // Lógica para ir para a tela de perfil
        }
    }
}
