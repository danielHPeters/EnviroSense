package com.envirosoft.envirosense;

import com.envirosoft.envirosense.model.EnvironmentDataEntry;
import com.envirosoft.envirosense.services.JsonFileReader;
import com.envirosoft.envirosense.services.JsonFileSaver;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SaveAndLoadTest {
  private List<EnvironmentDataEntry> input = new ArrayList<>();

  @Test
  public void testLoadAndSave() {
    String file = "test.json";
    input.add(new EnvironmentDataEntry("Zürich", "12", "21", "19"));
    input.add(new EnvironmentDataEntry("Zürich", "24", "11", "12"));

    try {
      FileOutputStream outputStream = new FileOutputStream(file);
      JsonFileSaver.saveToFileStream(outputStream, input);
      FileInputStream inputStream = new FileInputStream(file);
      List<EnvironmentDataEntry> output = JsonFileReader.getEntriesFromJson(inputStream);

      assertEquals(input, output);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
