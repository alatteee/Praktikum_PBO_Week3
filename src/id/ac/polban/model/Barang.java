package id.ac.polban.model;

import java.math.BigDecimal;

public class Barang {
    private final String kode;
    private String nama;
    private BigDecimal harga;
    private int stok;

    public Barang(String kode, String nama, BigDecimal harga, int stok) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public BigDecimal getHarga() { return harga; }
    public int getStok() { return stok; }

    public void kurangiStok(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Qty harus > 0");
        if (qty > stok) throw new IllegalStateException("Stok tidak cukup");
        this.stok -= qty;
    }
}
