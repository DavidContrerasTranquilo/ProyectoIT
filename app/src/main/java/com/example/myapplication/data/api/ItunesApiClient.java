package com.example.myapplication.data.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ItunesApiClient {

    private static final String BASE_URL = "https://itunes.apple.com/search";
    private static final int TIMEOUT = 5000;

    public static String searchSongs(String term, int limit, int offset) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            // Construim la URL amb paràmetres codificats
            String encodedTerm = URLEncoder.encode(term, "UTF-8");
            String fullUrl = BASE_URL
                    + "?term=" + encodedTerm
                    + "&media=music"
                    + "&entity=musicTrack"
                    + "&limit=" + limit
                    + "&offset=" + offset;

            URL url = new URL(fullUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Llegim la resposta
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                return response.toString(); // Retornem el JSON com String
            } else {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } finally {
            // Tanquem recursos
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {}
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static String searchAlbums(String term, int limit, int offset) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            String encodedTerm = URLEncoder.encode(term, "UTF-8");
            String fullUrl = BASE_URL
                    + "?term=" + encodedTerm
                    + "&media=music"
                    + "&entity=album"
                    + "&limit=" + limit
                    + "&offset=" + offset;

            URL url = new URL(fullUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            } else {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) try { reader.close(); } catch (IOException ignored) {}
            if (connection != null) connection.disconnect();
        }
    }
}
