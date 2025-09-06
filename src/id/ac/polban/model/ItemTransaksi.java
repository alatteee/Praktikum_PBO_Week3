package id.ac.polban.model;

import java.math.BigDecimal;

public class ItemTransaksi {
    private final Barang barang;
    private final int qty;

    public ItemTransaksi(Barang barang, int qty) {
        this.barang = barang;
        this.qty = qty;
    }

    public Barang getBarang() { return barang; }
    public int getQty() { return qty; }

    public BigDecimal getSubtotal() {
        return barang.getHarga().multiply(BigDecimal.valueOf(qty));
    }
}
