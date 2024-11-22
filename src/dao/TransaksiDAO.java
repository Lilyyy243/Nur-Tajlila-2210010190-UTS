package dao;

import model.Transaksi;
import java.util.List;

/**
 * Interface TransaksiDAO mendefinisikan operasi-operasi dasar yang dapat dilakukan
 * pada data transaksi dalam database.
 */
public interface TransaksiDAO {
    // Menyimpan data transaksi baru ke database
    void insert(Transaksi transaksi);
    
    // Memperbarui data transaksi yang sudah ada di database
    void update(Transaksi transaksi);
    
    // Menghapus data transaksi berdasarkan ID
    void delete(int id);
    
    // Mengambil semua data transaksi dari database
    List<Transaksi> getAll();
    
    // Mencari data transaksi berdasarkan kata kunci
    List<Transaksi> search(String keyword);
    
    // Mencari satu data transaksi berdasarkan ID
    Transaksi findById(int id);
    
    // Menghapus semua data transaksi dari database
    void clearAll();
}