package com.envirosoft.envirosense.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by admin on 31.08.2017.
 */
public class JsonFileReader {


    private static String streamToString(InputStream streamReader){
        BufferedReader reader = new BufferedReader(new InputStreamReader(streamReader));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static String getStringFromFile (String filePath) throws Exception {
        File jsonFile = new File(filePath);
        FileInputStream fileStream = new FileInputStream(jsonFile);
        String jsonString = streamToString(fileStream);

        fileStream.close();
        return jsonString;
    }


}
