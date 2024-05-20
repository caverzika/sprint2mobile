package com.example.sprint2

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalhesEmpresaActivity : AppCompatActivity() {
    private lateinit var nomeEmpresa: TextView
    private lateinit var desempenhoFinanceiro: TextView
    private lateinit var graficoDesempenho: ImageView
    private lateinit var btnFavoritar: Button
    private var empresaId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_detalhes_empresa)

        nomeEmpresa = findViewById(R.id.nomeEmpresa)
        desempenhoFinanceiro = findViewById(R.id.desempenhoFinanceiro)
        graficoDesempenho = findViewById(R.id.graficoDesempenho)
        btnFavoritar = findViewById(R.id.btnFavoritar)

        empresaId = intent.getIntExtra("EMPRESA_ID", -1)
        if (empresaId != -1) {
            carregarDetalhesEmpresa(empresaId)
        }

        btnFavoritar.setOnClickListener { favoritarEmpresa() }
    }

    private fun carregarDetalhesEmpresa(id: Int) {
        val apiService = ApiClient.client.create(ApiService::class.java)
        val call = apiService.obterDetalhesEmpresa(id)

        call.enqueue(object : Callback<EmpresaDetalhes> {
            override fun onResponse(call: Call<EmpresaDetalhes>, response: Response<EmpresaDetalhes>) {
                if (response.isSuccessful) {
                    val detalhes = response.body()
                    nomeEmpresa.text = detalhes?.nome
                    desempenhoFinanceiro.text = detalhes?.desempenhoFinanceiro
                    // Configurar gráfico de desempenho com os dados recebidos
                    // Por exemplo, utilizando uma biblioteca de gráficos
                } else {
                    Toast.makeText(this@DetalhesEmpresaActivity, "Falha ao carregar detalhes da empresa", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<EmpresaDetalhes>, t: Throwable) {
                Toast.makeText(this@DetalhesEmpresaActivity, "Erro de rede", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun favoritarEmpresa() {
        val apiService = ApiClient.client.create(ApiService::class.java)
        val call = apiService.favoritarEmpresa(empresaId)

        call.enqueue(object : Callback<GenericResponse> {
            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@DetalhesEmpresaActivity, "Empresa favoritada com sucesso", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@DetalhesEmpresaActivity, "Falha ao favoritar empresa", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                Toast.makeText(this@DetalhesEmpresaActivity, "Erro de rede", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
