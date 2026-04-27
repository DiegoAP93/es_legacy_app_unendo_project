package com.ethersteamboys.network;

import android.util.Base64;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // 10.0.2.2 = localhost del PC cuando se usa el emulador de Android Studio
    // Si usas dispositivo físico en la misma red, cambia a la IP local de tu PC (ej: 192.168.1.X)
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    private static Retrofit retrofit = null;
    private static String credenciales = null; // "usuario:contraseña" en Base64

    /**
     * Llama a este método tras el login exitoso para guardar las credenciales.
     */
    public static void setCredenciales(String username, String password) {
        String raw = username + ":" + password;
        credenciales = Base64.encodeToString(raw.getBytes(), Base64.NO_WRAP);
        retrofit = null; // forzar recreación con nuevas credenciales
    }

    public static void clearCredenciales() {
        credenciales = null;
        retrofit = null;
    }

    public static ApiService getApiService() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            final String authHeader = credenciales != null ? "Basic " + credenciales : null;

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(chain -> {
                        Request.Builder builder = chain.request().newBuilder()
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Accept", "application/json");
                        if (authHeader != null) {
                            builder.addHeader("Authorization", authHeader);
                        }
                        return chain.proceed(builder.build());
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}
