package com.ethersteamboys.network;

import com.ethersteamboys.models.Enemigo;
import com.ethersteamboys.models.Jugador;
import com.ethersteamboys.models.LoginRequest;
import com.ethersteamboys.models.LoginResponse;
import com.ethersteamboys.models.Objeto;
import com.ethersteamboys.models.Personaje;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // ── Jugadores / Auth ───────────────────────────────────────────
    // POST /jugadores  → registro de cuenta nueva
    @POST("jugadores")
    Call<Jugador> registro(@Body Jugador jugador);

    // POST /jugadores/login → comprueba credenciales, devuelve LoginResponse
    @POST("jugadores/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    // GET /jugadores/{username} → datos del jugador logueado
    @GET("jugadores/{username}")
    Call<Jugador> getJugador(@Path("username") String username);

    // PUT /jugadores/{username} → editar perfil
    @PUT("jugadores/{username}")
    Call<Jugador> editarJugador(@Path("username") String username, @Body Jugador cambios);

    // ── Personajes ─────────────────────────────────────────────────
    @GET("personajes")
    Call<List<Personaje>> getPersonajes();

    @GET("personajes/{id}")
    Call<Personaje> getPersonaje(@Path("id") int id);

    // ── Objetos ────────────────────────────────────────────────────
    @GET("objetos")
    Call<List<Objeto>> getObjetos();

    @GET("objetos/{id}")
    Call<Objeto> getObjeto(@Path("id") int id);

    // ── Enemigos ───────────────────────────────────────────────────
    @GET("enemigos")
    Call<List<Enemigo>> getEnemigos();

    @GET("enemigos/{id}")
    Call<Enemigo> getEnemigo(@Path("id") int id);
}
