package com.github.tasubo.jgmp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

class JavaGetConnectionRequester implements HttpRequester {

    private static final Logger LOGGER = Logger.getLogger(JavaGetConnectionRequester.class.getCanonicalName());

    static {
    }

    @Override
    public void send(final String url) {
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
}
