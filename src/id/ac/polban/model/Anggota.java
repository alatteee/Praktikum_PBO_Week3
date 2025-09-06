package id.ac.polban.model;

import java.math.BigDecimal;

public class Anggota {
    private final String id;
    private String nama;
    private boolean aktif;
    private BigDecimal diskonPersen;

    public Anggota(String id, String nama, boolean aktif, BigDecimal diskonPersen) {
        this.id = id;
        this.nama = nama;
        this.aktif = aktif;
        this.diskonPersen = diskonPersen;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public boolean isAktif() { return aktif; }
    public BigDecimal getDiskonPersen() { return diskonPersen; }
}
