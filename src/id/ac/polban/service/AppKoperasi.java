package id.ac.polban.service;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Date;

import id.ac.polban.model.Anggota;
import id.ac.polban.model.Barang;
import id.ac.polban.model.Transaksi;

public class AppKoperasi {
    private static String uang(BigDecimal v) {
        return "Rp" + v.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Koperasi store = seedData();

        while (true) {
            System.out.println("\n=== MENU KOPERASI ===");
            System.out.println("1. Lihat daftar anggota");
            System.out.println("2. Lihat daftar barang");
            System.out.println("3. Buat transaksi penjualan");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");
            String pilih = in.nextLine().trim();

            switch (pilih) {
                case "1" -> tampilkanAnggota(store);
                case "2" -> tampilkanBarang(store);
                case "3" -> buatTransaksiInteraktif(store, in);
                case "0" -> { System.out.println("Bye!"); return; }
                default -> System.out.println("Menu tidak dikenali.");
            }
        }
    }

    private static Koperasi seedData() {
        Koperasi store = new Koperasi();
        store.tambahAnggota(new Anggota("A001", "Ali",  true, new BigDecimal("10")));
        store.tambahAnggota(new Anggota("A002", "Budi", true, new BigDecimal("5")));
        store.tambahBarang(new Barang("P01", "Gula 1kg",   new BigDecimal("15000"), 20));
        store.tambahBarang(new Barang("P02", "Minyak 1L",  new BigDecimal("18000"), 15));
        store.tambahBarang(new Barang("P03", "Mie Instan", new BigDecimal("3500"), 100));
        return store;
    }

    private static void tampilkanAnggota(Koperasi store) {
        System.out.println("=== DAFTAR ANGGOTA ===");
        for (Anggota a : store.semuaAnggota()) {
            System.out.println(a.getId() + " - " + a.getNama() + " (Aktif: " + a.isAktif()
                    + ", Diskon " + a.getDiskonPersen() + "%)");
        }
    }

    private static void tampilkanBarang(Koperasi store) {
        System.out.println("=== DAFTAR BARANG ===");
        for (Barang b : store.semuaBarang()) {
            System.out.println(b.getKode() + " - " + b.getNama() + " @ " + uang(b.getHarga())
                    + " | Stok: " + b.getStok());
        }
    }

    private static void buatTransaksiInteraktif(Koperasi store, Scanner in) {
        System.out.print("ID Anggota (kosong jika non-anggota): ");
        String idA = in.nextLine().trim();
        Anggota pembeli = idA.isEmpty() ? null : store.cariAnggota(idA);
        if (!idA.isEmpty() && pembeli == null) {
            System.out.println("Anggota tidak ditemukan. Transaksi sebagai non-anggota.");
        }

        Transaksi trx = new Transaksi("TRX-" + System.currentTimeMillis(), new Date(), pembeli);

        while (true) {
            System.out.print("Kode barang (atau 'selesai'): ");
            String kode = in.nextLine().trim();
            if (kode.equalsIgnoreCase("selesai")) break;

            Barang b = store.cariBarang(kode);
            if (b == null) {
                System.out.println("Barang tidak ditemukan.");
                continue;
            }

            System.out.print("Qty: ");
            int qty = Integer.parseInt(in.nextLine().trim());

            try {
                trx.tambahItem(b, qty);
                System.out.println("Ditambahkan: " + b.getNama() + " x" + qty);
            } catch (Exception ex) {
                System.out.println("Gagal tambah item: " + ex.getMessage());
            }
        }

        System.out.println();
        System.out.println(trx.cetakStruk());
        tampilkanBarang(store);
    }
}
