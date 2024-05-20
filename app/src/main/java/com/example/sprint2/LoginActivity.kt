package com.example.sprint2

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var senha: EditText
    private lateinit var btnLogin: Button
    private lateinit var txtEsqueceuSenha: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_login)

        email = findViewById(R.id.email)
        senha = findViewById(R.id.senha)
        btnLogin = findViewById(R.id.btnLogin)
        txtEsqueceuSenha = findViewById(R.id.txtEsqueceuSenha)

        btnLogin.setOnClickListener { login() }
        txtEsqueceuSenha.setOnClickListener { esqueceuSenha() }
    }

    private fun login() {
        val emailText = email.text.toString()
        val senhaText = senha.text.toString()

        val apiService = ApiClient.client.create(ApiService::class.java)
        val call = apiService.login(emailText, senhaText)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, "Login falhou", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Erro de rede", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun esqueceuSenha() {
        val emailText = email.text.toString()

        val apiService = ApiClient.client.create(ApiService::class.java)
        val call = apiService.esqueceuSenha(emailText)

        call.enqueue(object : Callback<GenericResponse> {
            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@LoginActivity, "Email enviado com sucesso", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@LoginActivity, "Falha ao enviar email", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Erro de rede", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
