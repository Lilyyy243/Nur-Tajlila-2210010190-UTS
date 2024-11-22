package model;

import java.util.Date;

/**
 * Model data untuk menyimpan informasi transaksi keuangan.
 */
public class Transaksi {

    private int id;
    private Date tanggal;
    private double jumlahUang;
    private String kategori;
    private String deskripsi;

    // Konstruktor default diperlukan untuk GSON
    public Transaksi() {
    }

    // Konstruktor dengan parameter
    public Transaksi(Date tanggal, double jumlahUang, String kategori, String deskripsi) {
        this.tanggal = tanggal;
        this.jumlahUang = jumlahUang;
        this.kategori = kategori;
        this.deskripsi = deskripsi;
    }

    // Getter dan Setter diperlukan untuk GSON dan akses data
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public double getJumlahUang() {
        return jumlahUang;
    }

    public void setJumlahUang(double jumlahUang) {
        this.jumlahUang = jumlahUang;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
