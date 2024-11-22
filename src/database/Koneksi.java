package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.File;
import javax.swing.JOptionPane;

public class Koneksi {
    private static Connection conn;
    // Update database path to use src/database folder
    private static String dbUrl = "jdbc:sqlite:src/database/db_keuangan.db";
    
    public static Connection getConnection() {
        try {
            // Load SQLite JDBC Driver
            Class.forName("org.sqlite.JDBC");
            
            if (conn == null || conn.isClosed()) {
                // Create database directory if it doesn't exist
                File dbDir = new File("src/database");
                if (!dbDir.exists()) {
                    dbDir.mkdirs();
                }
                
                // Connect or create database
                conn = DriverManager.getConnection(dbUrl);
                System.out.println("Database connection established successfully");
                
                // Create table if not exists
                if (!isTableExists("transaksi")) {
                    createTable();
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC Driver not found: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Database driver not found!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return conn;
    }
    
    private static boolean isTableExists(String tableName) throws SQLException {
        ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null);
        return rs.next();
    }
    
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
            System.out.println("Table 'transaksi' created successfully");
        } catch (SQLException e) {
            System.err.println("Create table error: " + e.getMessage());
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