package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Transaksi;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Kelas utilitas untuk mengekspor dan mengimpor data transaksi ke/dari format
 * JSON.
 */
public class JsonUtil {

    private static final String JSON_FILE_PATH = "src/database/transaksi_data.json";
    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .setPrettyPrinting()
            .create();

    /**
     * Mengekspor daftar transaksi ke file JSON.
     *
     * @param transaksiList Daftar transaksi yang akan diekspor
     * @throws IOException jika terjadi kesalahan saat menulis file
     */
    public static void exportToJson(List<Transaksi> transaksiList) throws IOException {
        File directory = new File("src/database");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (Writer writer = new FileWriter(JSON_FILE_PATH)) {
            gson.toJson(transaksiList, writer);
        }
    }

    /**
     * Mengimpor daftar transaksi dari file JSON.
     *
     * @return Daftar transaksi yang diimpor
     * @throws IOException jika terjadi kesalahan saat membaca file
     */
    public static List<Transaksi> importFromJson() throws IOException {
        try (Reader reader = new FileReader(JSON_FILE_PATH)) {
            Type listType = new TypeToken<List<Transaksi>>() {
            }.getType();
            return gson.fromJson(reader, listType);
        }
    }
}
