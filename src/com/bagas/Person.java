package com.bagas;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Random;

public class Person {
    String name;
    int bodyTemperature;

    public Person(String name) {
        this.name = name;
    }

    public void setBodyTemperature(int bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
    }

    public boolean isSick() {
        // TODO: sakit jika suhu badan lebih dari 37

        if (bodyTemperature > 37){
            return true;
        }
        return false;
    }

    /**
     * CATATAN: Seluruh kerangka code yang dibuat ini hanyalah gambaran saja. Boleh dipakai semua, sebagian atau tidak sama sekali.
     * SOAL: Implementasi code yang bisa mencatat dan menceritakan kejadian berikut:
     *
     * Sejak tanggal 1 sampai 14 November 2018, setiap hari kecuali akhir pekan Budi pergi training dari rumahnya di Menteng menuju Senayan.
     * Budi memiliki 3 opsi cara pergi yang bisa digunakan untuk menuju ke sana, yaitu mobil pribadi, motor pribadi dan ojek.
     * Masing-masing kendaraan memiliki kondisi sebagai berikut:
     * - Mobil: digunakan jika dia berangkat paling siang pukul 06:00. Membutuhkan biaya 20 ribu rupiah dengan waktu tempuh satu jam
     * - Motor: digunakan jika dia berangkat paling siang pukul 07:00. Membutuhkan biaya 5 ribu rupiah dengan waktu tempuh 45 menit
     * - Ojek: digunakan jika dia berangkat lebih dari pukul 07:00 atau sakit. Membutuhkan biaya 10 ribu rupiah dengan waktu tempuh 40 menit
     *
     * Lamanya training adalah 8 jam setiap hari, kecuali hari Jumat yang hanya 7 jam.
     *
     * Gunakan seluruh konsep-konsep pemrograman yang baik yang Anda pahami untuk mengerjakan soal ini.
     */
    public static void main(String[] args) throws ParseException {
        // TODO: immplementasi main method
        int totalBiaya = 0;
        for (int i=1; i<=14; i++) {
            Random random = new Random();
            // set random jam berangkat, antara pukul 05:00 sampai 08:00
            int jamBerangkat = random.nextInt(4)+5;
            int menitBerangkat = random.nextInt(60);
            String waktuBerangkat = String.format("%02d:%02d", jamBerangkat, menitBerangkat);
            LocalTime localTime = LocalTime.parse(waktuBerangkat);

            // set random suhu, antara 35-39
            // (bonus penilaian jika bisa menggenerate secara non-uniform dimana suhu 35-37 memiliki peluang lebih besar, kira-kira 3:1, untuk muncul dibanding 38-39)
            int suhu = 0;

            // random suhu yg non-uniform
            double kemungkinan = random.nextDouble();
            if (kemungkinan < 0.75){
                suhu = random.nextInt(3)+35;
            }else {
                suhu = random.nextInt(2)+38;
            }

            Person budi = new Person("Budi");
            budi.setBodyTemperature(suhu);

            String tanggal = i +"/11/2018";
            Date date =new SimpleDateFormat("dd/MM/yyyy").parse(tanggal);

            // cek nama hari
            Format format = new SimpleDateFormat("EEEE");
            String hari = format.format(date);

            String kendaraan;
            int biaya;
            int lamaPerjalanan;

            // cek sakit atau tidak
            if (budi.isSick()){
                kendaraan ="Ojek";
                biaya = 10000;
                lamaPerjalanan = 40;
            }else {
                if (localTime.isBefore(LocalTime.parse("06:00"))){
                    kendaraan ="Mobil";
                    biaya = 20000;
                    lamaPerjalanan = 60;
                } else if (localTime.isBefore(LocalTime.parse("07:00"))) {
                    kendaraan ="Motor";
                    biaya = 5000;
                    lamaPerjalanan = 45;

                }else {
                    kendaraan ="Ojek";
                    biaya = 10000;
                    lamaPerjalanan = 40;
                }
            }

            int lamaTraining = 8;

            // jika hari Jumat (Friday) lama training 7 jam
            if (hari.equalsIgnoreCase("Friday")){
                lamaTraining = 7;
            }

            System.out.println("Tanggal: "+tanggal);
            System.out.println("Hari: "+hari);
            System.out.println("Suhu: "+suhu);

            // biaya harian adalah biaya yang dikeluarkan pulang - pergi
            int biayaHarian = biaya * 2;

            // jika akhir pekan (Saturday atau Sunday) tidak ada training
            if (hari.equalsIgnoreCase("Saturday")||hari.equalsIgnoreCase("Sunday")){
                System.out.println("Hari ini adalah Akhir Pekan. Hari ini tidak training");
                System.out.println("\n");
                totalBiaya -= biayaHarian;
            }else {
                System.out.println("Waktu berangkat: "+waktuBerangkat);
                System.out.println("Hari ini melaksanakan training");
                // pilih kendaraan yang digunakan

                System.out.println("Kendaraan yang digunakan: "+kendaraan);
                System.out.println("Biaya satu kali perjalanan: "+biaya);

                // print biaya yang dikeluarkan
                // biaya dihitung 2 kali saat perjalanan berangkat dan pulang
                System.out.println("Biaya harian yang dikeluarkan (pulang - pergi): "+biayaHarian);

                // print waktu tiba
                System.out.println("Waktu tiba: "+localTime.plusMinutes(lamaPerjalanan));

                // print waktu sampai di rumah
                // waktu sampai di rumah = waktu berangkat + lama training + waktu pulang
                // diasumsikan kendaraan, lama perjalanan dan kondisi saat berangkat dan pulang adalah SAMA
                System.out.println( String.format("Waktu sampai dirumah: "+localTime.plusHours(lamaTraining).plusMinutes(lamaPerjalanan*2)));
                System.out.println("\n");
            }
            totalBiaya += biayaHarian;
        }

        // print total biaya yang dikeluarkan
        System.out.println("Total biaya selama 14 hari : "+totalBiaya);
    }
}
