package dao;

import model.Transaksi;
import java.util.List;

public interface TransaksiDAO {
    void insert(Transaksi transaksi);
    void update(Transaksi transaksi);
    void delete(int id);
    List<Transaksi> getAll();
    List<Transaksi> search(String keyword);
    Transaksi findById(int id);
    void clearAll(); // Optional: Add this if you want a more efficient way to clear data
}