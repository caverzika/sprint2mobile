package com.example.sprint2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaEmpresasActivity : AppCompatActivity() {
    private lateinit var listaEmpresas: RecyclerView
    private lateinit var filtroSetor: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_lista_empresas)

        listaEmpresas = findViewById(R.id.listaEmpresas)
        filtroSetor = findViewById(R.id.filtroSetor)

        listaEmpresas.layoutManager = LinearLayoutManager(this)

        carregarEmpresas()

        filtroSetor.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                carregarEmpresas(filtroSetor.text.toString())
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    private fun carregarEmpresas() {
        carregarEmpresas(null)
    }

    private fun carregarEmpresas(setor: String?) {
        val apiService = ApiClient.client.create(ApiService::class.java)
        val call = apiService.listarEmpresas(setor)

        call.enqueue(object : Callback<List<Empresa>> {
            override fun onResponse(call: Call<List<Empresa>>, response: Response<List<Empresa>>) {
                if (response.isSuccessful) {
                    val empresas = response.body()
                    val adapter = EmpresasAdapter(empresas!!)
                    listaEmpresas.adapter = adapter
                } else {
                    Toast.makeText(this@ListaEmpresasActivity, "Falha ao carregar empresas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Empresa>>, t: Throwable) {
                Toast.makeText(this@ListaEmpresasActivity, "Erro de rede", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
