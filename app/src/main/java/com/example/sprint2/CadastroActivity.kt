package com.example.sprint2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var senha: EditText
    private lateinit var btnCadastro: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_cadastro)

        email = findViewById(R.id.email)
        senha = findViewById(R.id.senha)
        btnCadastro = findViewById(R.id.btnCadastro)

        btnCadastro.setOnClickListener { cadastrar() }
    }

    private fun cadastrar() {
        val emailText = email.text.toString()
        val senhaText = senha.text.toString()

        val apiService = ApiClient.client.create(ApiService::class.java)
        val call = apiService.cadastrar(emailText, senhaText)

        call.enqueue(object : Callback<GenericResponse> {
            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CadastroActivity, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@CadastroActivity, "Falha ao cadastrar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                Toast.makeText(this@CadastroActivity, "Erro de rede", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
