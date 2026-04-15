package com.eslegacy.admin.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.eslegacy.admin.model.Enemigo;
import com.eslegacy.admin.model.Jugador;
import com.eslegacy.admin.model.LoginResponse;
import com.eslegacy.admin.model.Objeto;
import com.eslegacy.admin.model.Personaje;
import com.google.gson.Gson;

public class ApiClient {

    private static final String API_URL = "http://localhost:8080/jugadores/login";

    public static LoginResponse login(String username, String password) {

        try {

            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInput = String.format(
                    "{\"username\":\"%s\",\"password\":\"%s\"}",
                    username, password);

            OutputStream os = conn.getOutputStream();
            os.write(jsonInput.getBytes());
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {

                BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                br.close();

                Gson gson = new Gson();
                return gson.fromJson(response.toString(), LoginResponse.class);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public static Personaje[] getPersonajes() {

        try {

            URL url = new URL("http://localhost:8080/personajes");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {

                BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                br.close();

                Gson gson = new Gson();
                return gson.fromJson(response.toString(), Personaje[].class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Personaje[0];
    }
    
    public static Objeto[] getObjetos() {

        try {

            URL url = new URL("http://localhost:8080/objetos");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {

                BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                br.close();

                Gson gson = new Gson();
                return gson.fromJson(response.toString(), Objeto[].class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Objeto[0];
    }
    
    public static Enemigo[] getEnemigos() {

        try {

            URL url = new URL("http://localhost:8080/enemigos");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {

                BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                br.close();

                Gson gson = new Gson();
                return gson.fromJson(response.toString(), Enemigo[].class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Enemigo[0];
    }
    
    public static Jugador[] getJugadores() {

        try {

            URL url = new URL("http://localhost:8080/jugadores");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {

                BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                br.close();

                Gson gson = new Gson();
                return gson.fromJson(response.toString(), Jugador[].class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Jugador[0];
    }
    
    public static boolean crearJugador(String username, String password,
			String nombre, String correo, String rol) {

		try {

			URL url = new URL("http://localhost:8080/jugadores");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);

			String jsonInput = String.format(
					"{\"username\":\"%s\",\"password\":\"%s\",\"nombreCompleto\":\"%s\",\"correo\":\"%s\",\"rol\":\"%s\"}",
					username, password, nombre, correo, rol);

			OutputStream os = conn.getOutputStream();
			os.write(jsonInput.getBytes());
			os.flush();
			os.close();

			int responseCode = conn.getResponseCode();

			return responseCode == 200;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
    
    public static boolean bloquearUsuario(String username) {
        try {
            URL url = new URL("http://localhost:8080/jugadores/" + username + "/bloquear");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("PUT");

            return conn.getResponseCode() == 200;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean eliminarUsuario(String username) {
        try {
            URL url = new URL("http://localhost:8080/jugadores/" + username + "/eliminar");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("PUT");

            return conn.getResponseCode() == 200;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
		}
	}

	public static boolean crearObjeto(String nombre, String descripcion, String categoria, Integer precio) {

		try {

			URL url = new URL("http://localhost:8080/objetos");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);

			String json = String.format("{\"nombre\":\"%s\",\"descripcion\":\"%s\",\"categoria\":\"%s\",\"precio\":%s}",
					nombre, descripcion, categoria, precio != null ? precio : "null");

			OutputStream os = conn.getOutputStream();
			os.write(json.getBytes());
			os.flush();
			os.close();

			return conn.getResponseCode() == 200;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean eliminarObjeto(int id) {
	    try {
	        URL url = new URL("http://localhost:8080/objetos/" + id);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	        conn.setRequestMethod("DELETE");

	        return conn.getResponseCode() == 204;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public static Objeto getObjetoById(int id) {

	    try {
	        URL url = new URL("http://localhost:8080/objetos/" + id);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	        conn.setRequestMethod("GET");

	        if (conn.getResponseCode() == 200) {

	            BufferedReader br = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream()));

	            StringBuilder response = new StringBuilder();
	            String line;

	            while ((line = br.readLine()) != null) {
	                response.append(line);
	            }

	            br.close();

	            Gson gson = new Gson();
	            return gson.fromJson(response.toString(), Objeto.class);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	public static boolean editarObjeto(int id, String nombre,
            String descripcion,
            String categoria,
            Integer precio) {

		try {

			URL url = new URL("http://localhost:8080/objetos/" + id);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);

			String json = String.format(
				    "{\"nombre\":\"%s\",\"descripcion\":\"%s\",\"categoria\":\"%s\",\"precio\":%s}",
				    nombre,
				    descripcion,
				    categoria,
				    precio != null ? precio : "null"
				);

			OutputStream os = conn.getOutputStream();
			os.write(json.getBytes());
			os.flush();
			os.close();

			return conn.getResponseCode() == 200;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}