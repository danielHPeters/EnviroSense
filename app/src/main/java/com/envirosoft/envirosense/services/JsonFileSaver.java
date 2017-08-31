package com.envirosoft.envirosense.services;

import android.util.JsonWriter;

import com.envirosoft.envirosense.model.EnvironmentDataEntry;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Created by admin on 31.08.2017.
 */
public class JsonFileSaver {

    public void writeJsonStream(OutputStream out, List<EnvironmentDataEntry> data) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("  ");
        saveEnvirodataToJson(writer, data);
        writer.close();
    }

    public static void saveEnvirodataToJson(JsonWriter writer, List<EnvironmentDataEntry> entries) throws IOException{
            writer.beginObject();

            writer.endObject();
    }
}
