package dao;

import java.sql.*;
import java.util.*;
import model.Transaksi;
import database.Koneksi;

/**
 * Implementasi dari interface TransaksiDAO untuk mengelola data transaksi dalam
 * database SQLite.
 */
public class TransaksiDAOImpl implements TransaksiDAO {

    private final Connection connection;

    /**
     * Konstruktor untuk membuat koneksi ke database
     */
    public TransaksiDAOImpl() {
        connection = Koneksi.getConnection();
    }

    /**
     * Menyimpan data transaksi baru ke database
     *
     * @param transaksi objek transaksi yang akan disimpan
     */
    @Override
    public void insert(Transaksi transaksi) {
        String sql = "INSERT INTO transaksi (tanggal, jumlah_uang, kategori, deskripsi) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(transaksi.getTanggal().getTime()));
            stmt.setDouble(2, transaksi.getJumlahUang());
            stmt.setString(3, transaksi.getKategori());
            stmt.setString(4, transaksi.getDeskripsi());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Gagal menyimpan data: " + e.getMessage());
        }
    }

    /**
     * Memperbarui data transaksi yang sudah ada
     *
     * @param transaksi objek transaksi dengan data terbaru
     */
    @Override
    public void update(Transaksi transaksi) {
        String sql = "UPDATE transaksi SET tanggal=?, jumlah_uang=?, kategori=?, deskripsi=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(transaksi.getTanggal().getTime()));
            stmt.setDouble(2, transaksi.getJumlahUang());
            stmt.setString(3, transaksi.getKategori());
            stmt.setString(4, transaksi.getDeskripsi());
            stmt.setInt(5, transaksi.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Menghapus data transaksi berdasarkan ID
     *
     * @param id ID transaksi yang akan dihapus
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM transaksi WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mengambil semua data transaksi dari database
     *
     * @return List berisi semua transaksi
     */
    @Override
    public List<Transaksi> getAll() {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM transaksi ORDER BY tanggal DESC";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Transaksi t = new Transaksi();
                t.setId(rs.getInt("id"));
                t.setTanggal(rs.getDate("tanggal"));
                t.setJumlahUang(rs.getDouble("jumlah_uang"));
                t.setKategori(rs.getString("kategori"));
                t.setDeskripsi(rs.getString("deskripsi"));
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Mencari data transaksi berdasarkan kata kunci
     *
     * @param keyword kata kunci pencarian (ID, kategori, atau deskripsi)
     * @return List transaksi yang sesuai dengan kata kunci
     */
    @Override
    public List<Transaksi> search(String keyword) {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM transaksi WHERE id = ? OR kategori LIKE ? OR deskripsi LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Try parsing the keyword as ID first
            try {
                int id = Integer.parseInt(keyword);
                stmt.setInt(1, id);
            } catch (NumberFormatException e) {
                stmt.setInt(1, -1); // Invalid ID to ensure no match
            }
            stmt.setString(2, "%" + keyword + "%");
            stmt.setString(3, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Transaksi t = new Transaksi();
                t.setId(rs.getInt("id"));
                t.setTanggal(rs.getDate("tanggal"));
                t.setJumlahUang(rs.getDouble("jumlah_uang"));
                t.setKategori(rs.getString("kategori"));
                t.setDeskripsi(rs.getString("deskripsi"));
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Mencari satu transaksi berdasarkan ID
     *
     * @param id ID transaksi yang dicari
     * @return objek Transaksi jika ditemukan, null jika tidak
     */
    @Override
    public Transaksi findById(int id) {
        String sql = "SELECT * FROM transaksi WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Transaksi t = new Transaksi();
                t.setId(rs.getInt("id"));
                t.setTanggal(rs.getDate("tanggal"));
                t.setJumlahUang(rs.getDouble("jumlah_uang"));
                t.setKategori(rs.getString("kategori"));
                t.setDeskripsi(rs.getString("deskripsi"));
                return t;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Menghapus semua data transaksi dari database
     */
    @Override
    public void clearAll() {
        String sql = "DELETE FROM transaksi";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
