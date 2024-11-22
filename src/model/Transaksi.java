package model;

import java.util.Date;

/**
 * Model data untuk menyimpan informasi transaksi keuangan.
 */
public class Transaksi {
    // Atribut-atribut transaksi
    private int id;                // ID unik transaksi
    private Date tanggal;         // Tanggal transaksi
    private double jumlahUang;    // Jumlah uang dalam transaksi
    private String kategori;      // Kategori: Pendapatan/Pengeluaran/Tabungan
    private String deskripsi;     // Keterangan tambahan
    
    /**
     * Konstruktor default
     */
    public Transaksi() {}
    
    /**
     * Konstruktor dengan parameter lengkap
     */
    public Transaksi(Date tanggal, double jumlahUang, String kategori, String deskripsi) {
        this.tanggal = tanggal;
        this.jumlahUang = jumlahUang;
        this.kategori = kategori;
        this.deskripsi = deskripsi;
    }
    
    // Getter dan Setter untuk setiap atribut
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }
    public double getJumlahUang() { return jumlahUang; }
    public void setJumlahUang(double jumlahUang) { this.jumlahUang = jumlahUang; }
    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }
    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
}