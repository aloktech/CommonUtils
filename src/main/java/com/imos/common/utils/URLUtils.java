/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author alok.meher
 */
public class URLUtils {

    private static final Logger LOG = LogManager.getLogger(URLUtils.class);

    public static void downloadFromUrl(String urlString, String destDir) {
        File targetFile = new File(destDir);
        OutputStream outStream;
        try {
            outStream = new FileOutputStream(targetFile);
            URL url = new URL(urlString);
            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            try (InputStream inStream = url.openStream()) {
                while ((bytesRead = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                LOG.error("{} {}", e.getMessage(), e.getCause().getClass().getName());
            }
        } catch (FileNotFoundException | MalformedURLException e) {
            LOG.error("{} {}", e.getMessage(), e.getCause().getClass().getName());
        } finally {
            targetFile.delete();
        }
    }

    public static List<String> readStringFromUrl(String urlString) {
        List<String> lines = new ArrayList<>();
        try {
            URL url = new URL(urlString);
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    lines.add(inputLine);
                }
            }
        } catch (IOException e) {
            LOG.error("{} {}", e.getMessage(), e.getCause().getClass().getName());
            Throwable th = e;
            while (th != null) {
                lines.add(th.getCause().getClass().getName() + ":" + e.getMessage());
                th = th.getCause();
            }
        }
        return lines;
    }
}
