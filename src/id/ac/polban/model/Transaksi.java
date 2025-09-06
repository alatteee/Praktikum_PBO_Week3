package id.ac.polban.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.ArrayList;

public class Transaksi {
    private final String id;
    private final Date tanggal;
    private final Anggota pembeli; // null = non-anggota
    private final java.util.List<ItemTransaksi> items = new ArrayList<>();

    public Transaksi(String id, java.util.Date tanggal, Anggota pembeli) {
        this.id = id;
        this.tanggal = (Date) tanggal;
        this.pembeli = pembeli;
    }

    public void tambahItem(Barang b, int qty) {
        b.kurangiStok(qty);
        items.add(new ItemTransaksi(b, qty));
    }

    public BigDecimal getSubtotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemTransaksi it : items) total = total.add(it.getSubtotal());
        return total;
    }

    public BigDecimal getDiskon() {
        if (pembeli == null || !pembeli.isAktif()) return BigDecimal.ZERO;
        BigDecimal persen = pembeli.getDiskonPersen().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return getSubtotal().multiply(persen);
    }

    public BigDecimal getTotal() {
        return getSubtotal().subtract(getDiskon());
    }

    private static String uang(BigDecimal v) {
        return "Rp" + v.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    public String cetakStruk() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== STRUK KOPERASI ===\n");
        sb.append("No: ").append(id).append("\n");
        sb.append("Tgl: ").append(tanggal).append("\n");
        sb.append("Pembeli: ").append(pembeli != null ? pembeli.getNama()+" (ANGGOTA)" : "Non-anggota").append("\n");
        sb.append("----------------------\n");
        for (ItemTransaksi it : items) {
            sb.append(String.format("%-10s x%2d = %s\n", it.getBarang().getNama(), it.getQty(), uang(it.getSubtotal())));
        }
        sb.append("----------------------\n");
        sb.append("Subtotal: ").append(uang(getSubtotal())).append("\n");
        sb.append("Diskon  : ").append(uang(getDiskon())).append("\n");
        sb.append("TOTAL   : ").append(uang(getTotal())).append("\n");
        sb.append("======================");
        return sb.toString();
    }
}
