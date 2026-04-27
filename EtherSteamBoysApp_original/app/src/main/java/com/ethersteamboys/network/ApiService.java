package com.ethersteamboys.network;

import com.ethersteamboys.models.ActualizarPerfilRequest;
import com.ethersteamboys.models.Enemigo;
import com.ethersteamboys.models.Objeto;
import com.ethersteamboys.models.Personaje;
import com.ethersteamboys.models.RegistroRequest;
import com.ethersteamboys.models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // ── Autenticación ──────────────────────────────────────────────
    @POST("usuarios/registro")
    Call<Usuario> registro(@Body RegistroRequest request);

    // El login se valida via Basic Auth; este endpoint devuelve el usuario autenticado
    @GET("usuarios/login")
    Call<Usuario> login();

    // ── Personajes ─────────────────────────────────────────────────
    @GET("personajes")
    Call<List<Personaje>> getPersonajes();

    @GET("personajes/{id}")
    Call<Personaje> getPersonaje(@Path("id") Long id);

    // ── Objetos ────────────────────────────────────────────────────
    @GET("objetos")
    Call<List<Objeto>> getObjetos();

    @GET("objetos/{id}")
    Call<Objeto> getObjeto(@Path("id") Long id);

    // ── Enemigos ───────────────────────────────────────────────────
    @GET("enemigos")
    Call<List<Enemigo>> getEnemigos();

    @GET("enemigos/{id}")
    Call<Enemigo> getEnemigo(@Path("id") Long id);

    // ── Perfil ─────────────────────────────────────────────────────
    @GET("usuarios/perfil")
    Call<Usuario> getPerfil();

    @PUT("usuarios/perfil")
    Call<Usuario> actualizarPerfil(@Body ActualizarPerfilRequest request);
}
