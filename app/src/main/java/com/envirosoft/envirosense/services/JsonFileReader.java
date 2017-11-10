package com.envirosoft.envirosense.services;

import com.envirosoft.envirosense.model.EnvironmentDataEntry;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonFileReader {
  private static DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

  /**
   * Load the json file to string.
   *
   * @return loaded dataL
   */
  private static String loadData(FileInputStream stream) {
    StringBuilder sb = new StringBuilder();

    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
      String line;

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
   * Load the JSON file into an ArrayList of EnvironmentDataEntry objects.
   *
   * @return data as List
   * @throws JSONException  json parsing errors
   * @throws ParseException parsing errors
   */
  public static List<EnvironmentDataEntry> getEntriesFromJson(
      FileInputStream fileInputStream) throws JSONException, ParseException, FileNotFoundException {
    List<EnvironmentDataEntry> entriesList = new ArrayList<>();
    JSONObject jsonObject;
    String date;
    String location;
    String pressure;
    String light;
    String temperature;
    Date parsedDate;

    JSONArray jsonArray = new JSONArray(JsonFileReader.loadData(fileInputStream));

    for (int i = 0; i < jsonArray.length(); i++) {
      jsonObject = new JSONObject(jsonArray.get(i).toString());
      date = jsonObject.get("date").toString();
      location = jsonObject.get("location").toString();
      pressure = jsonObject.get("pressure").toString();
      light = jsonObject.get("luminance").toString();
      temperature = jsonObject.get("temperature").toString();
      parsedDate = df.parse(date);
      entriesList.add(new EnvironmentDataEntry(parsedDate, location, pressure, light, temperature));
    }
    return entriesList;
  }
}
