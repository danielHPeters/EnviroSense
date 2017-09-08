package com.envirosoft.envirosense.services;

import com.envirosoft.envirosense.model.EnvironmentDataEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by admin on 31.08.2017.
 */
public class JsonFileReader {

    private static DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

    /**
     * Load the json file to string
     *
     * @return
     */
    public static String loadData(FileInputStream stream) {


        StringBuilder sb = new StringBuilder();

        try {


            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            String line = null;


            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Load the JSON file into an ArrayList of EnvironmentDataEntry objects
     *
     * @return
     * @throws JSONException
     * @throws ParseException
     */
    public static List<EnvironmentDataEntry> getEntriesFromJson(FileInputStream fileInputStream) throws JSONException, ParseException, FileNotFoundException {

        List<EnvironmentDataEntry> entriesList = new ArrayList<>();

        JSONObject jsonObject;

        String date;

        String pressure;

        String light;

        String temperature;

        Date parsedDate;


        JSONArray jsonArray = new JSONArray(JsonFileReader.loadData(fileInputStream));

        for (int i = 0; i < jsonArray.length(); i++) {

            jsonObject = new JSONObject(jsonArray.get(i).toString());
            date = jsonObject.get("entryDate").toString();
            pressure = jsonObject.get("entryPressure").toString();
            light = jsonObject.get("entryLight").toString();
            temperature = jsonObject.get("entryTemperature").toString();
            parsedDate = df.parse(date);
            entriesList.add(new EnvironmentDataEntry(parsedDate, pressure, light, temperature));

        }


        return entriesList;
    }


}
