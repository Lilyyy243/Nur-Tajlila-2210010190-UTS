package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.File;
import javax.swing.JOptionPane;

/**
 * Kelas Koneksi menangani koneksi ke database SQLite dan inisialisasi tabel.
 */
public class Koneksi {

    private static Connection conn;
    // Lokasi file database di folder src/database
    private static final String DB_URL = "jdbc:sqlite:src/database/db_keuangan.db";

    /**
     * Membuat koneksi ke database SQLite. Jika database belum ada, akan dibuat
     * secara otomatis.
     */
    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                // Memuat driver SQLite JDBC
                Class.forName("org.sqlite.JDBC");

                // Membuat direktori database jika belum ada
                new File("src/database").mkdirs();

                // Membuat koneksi ke database
                conn = DriverManager.getConnection(DB_URL);
                System.out.println("Koneksi ke database berhasil dibuat");

                // Membuat tabel jika belum ada
                if (!isTableExists("transaksi")) {
                    createTable();
                }
            }
        } catch (Exception e) {
            System.err.println("Kesalahan koneksi: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Kesalahan koneksi database!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return conn;
    }

    private static boolean isTableExists(String tableName) throws SQLException {
        ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null);
        return rs.next();
    }

    /**
     * Membuat tabel transaksi dengan struktur yang telah ditentukan.
     */
    private static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS transaksi ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "tanggal DATE NOT NULL,"
                + "jumlah_uang DECIMAL(10,2) NOT NULL,"
                + "kategori TEXT CHECK(kategori IN ('Pendapatan', 'Pengeluaran', 'Tabungan')) NOT NULL,"
                + "deskripsi TEXT"
                + ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabel 'transaksi' berhasil dibuat");
        } catch (SQLException e) {
            System.err.println("Kesalahan membuat tabel: " + e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed successfully");
            }
        } catch (SQLException e) {
            System.err.println("Close connection error: " + e.getMessage());
        }
    }
}
