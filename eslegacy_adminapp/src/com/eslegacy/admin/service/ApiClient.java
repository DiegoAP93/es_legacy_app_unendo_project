package com.eslegacy.admin.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.eslegacy.admin.model.LoginResponse;
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
}