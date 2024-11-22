
package dao;

import model.Transaksi;
import java.util.List;

public interface TransaksiDAO {
    void insert(Transaksi transaksi);
    void update(Transaksi transaksi);
    void delete(int id);
    List<Transaksi> getAll();
    List<Transaksi> search(String keyword);
}