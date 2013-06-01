package com.github.tasubo.jgmp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

final class JavaGetConnectionRequester implements HttpRequester {

    private static final Logger LOGGER = Logger.getLogger(JavaGetConnectionRequester.class.getCanonicalName());

    @Override
    public void sendGet(String host, String payload) {

        String url = host + "?" + payload;

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Starting request to: {0}", url);
        }

        URL myURL = null;
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        try {
            urlConnection = (HttpURLConnection) myURL.openConnection();
            urlConnection.setDoOutput(false);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");

            bufferedReader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));
            while (bufferedReader.readLine() != null) {
                /*
                 * Reading returned stuff just to ensure that http connection is going to be closed - Java SE bug...
                 *
                 */
            }
            int code = urlConnection.getResponseCode();
            if (code != 200) {
                throw new RuntimeException("The request wasn't successful - please revisit payload for url: "
                        + url);
            }
            bufferedReader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }


        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Request done to url: {0}", url);
        }
    }

    @Override
    public void sendPost(String host, String payload) {
        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Starting request to: {0}", payload);
        }

        URL myURL = null;
        try {
            myURL = new URL(host);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        DataOutputStream wr = null;
        try {
            urlConnection = (HttpURLConnection) myURL.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");

            String urlParameters = payload;

            // Send post request
            urlConnection.setDoOutput(true);
            wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            bufferedReader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));
            while (bufferedReader.readLine() != null) {
                /*
                 * Reading returned stuff just to ensure that http connection is going to be closed - Java SE bug...
                 *
                 */
            }
            int code = urlConnection.getResponseCode();
            if (code != 200) {
                throw new RuntimeException("The request wasn't successful - please revisit payload for payload: "
                        + payload);
            }
            bufferedReader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (wr != null) {
                    wr.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Request done for payload: {0}", payload);
        }
    }
}
