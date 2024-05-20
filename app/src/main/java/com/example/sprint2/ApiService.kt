package com.example.sprint2

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("senha") senha: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("esqueceu-senha")
    fun esqueceuSenha(
        @Field("email") email: String
    ): Call<GenericResponse>

    @GET("empresas")
    fun listarEmpresas(
        @Query("setor") setor: String?
    ): Call<List<Empresa>>

    @GET("empresa/{id}")
    fun obterDetalhesEmpresa(
        @Path("id") id: Int
    ): Call<EmpresaDetalhes>

    @POST("empresa/{id}/favoritar")
    fun favoritarEmpresa(
        @Path("id") id: Int
    ): Call<GenericResponse>

}
