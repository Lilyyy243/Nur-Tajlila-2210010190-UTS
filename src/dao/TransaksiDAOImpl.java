package dao;

import java.sql.*;
import java.util.*;
import model.Transaksi;
import database.Koneksi;

public class TransaksiDAOImpl implements TransaksiDAO {
    private Connection connection;
    
    public TransaksiDAOImpl() {
        connection = Koneksi.getConnection();
    }
    
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
            e.printStackTrace();
        }
    }
    
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
    
    @Override
    public List<Transaksi> getAll() {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM transaksi ORDER BY tanggal DESC";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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
}