
package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Transaksi;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.text.SimpleDateFormat;

public class JsonUtil {
    private static final String JSON_FILE_PATH = "src/database/transaksi_data.json";
    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .setPrettyPrinting()
            .create();

    public static void exportToJson(List<Transaksi> transaksiList) throws IOException {
        File directory = new File("src/database");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        try (Writer writer = new FileWriter(JSON_FILE_PATH)) {
            gson.toJson(transaksiList, writer);
        }
    }

    public static List<Transaksi> importFromJson() throws IOException {
        try (Reader reader = new FileReader(JSON_FILE_PATH)) {
            Type listType = new TypeToken<List<Transaksi>>(){}.getType();
            return gson.fromJson(reader, listType);
        }
    }
}