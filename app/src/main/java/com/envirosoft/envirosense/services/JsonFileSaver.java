package com.envirosoft.envirosense.services;

import android.util.JsonWriter;

import com.envirosoft.envirosense.model.EnvironmentDataEntry;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Created by admin on 31.08.2017.
 */
public class JsonFileSaver {

    public static void writeJsonStream(OutputStream out, List<EnvironmentDataEntry> data) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("  ");
        writeListToJSONArray(writer, data);
        writer.close();
    }

    private static void writeListToJSONArray(JsonWriter writer, List<EnvironmentDataEntry> entries) throws IOException {
        writer.beginArray();
        for (EnvironmentDataEntry entry : entries) {
            writeEntry(writer, entry);
        }
        writer.endArray();
    }

    /**
     * Write object attributes
     *
     * @param writer
     * @param entry
     * @throws IOException
     */
    private static void writeEntry(JsonWriter writer, EnvironmentDataEntry entry) throws IOException {
        writer.beginObject();
        writer.name("date").value(entry.getDate().toString());
        writer.name("location").value(entry.getLocation());
        writer.name("pressure").value(entry.getPressure());
        writer.name("luminance").value(entry.getLuminance());
        writer.name("temperature").value(entry.getTemperature());
        writer.endObject();
    }

    /**
     * Save to stream
     *
     * @param outputStream
     * @throws IOException
     */
    public static void saveToFileStream(FileOutputStream outputStream, List<EnvironmentDataEntry> list) throws IOException {
        JsonFileSaver.writeJsonStream(outputStream, list);
        outputStream.close();

    }
}
