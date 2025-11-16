/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example;

/**
 *
 * @author Nahda
 */
public class MataKuliah {
     private int no;
    private String namaMataKuliah;
    private String jadwal;
    private boolean sudahDiisi;
    
    public MataKuliah(int no, String namaMataKuliah, String jadwal, boolean sudahDiisi) {
        this.no = no;
        this.namaMataKuliah = namaMataKuliah;
        this.jadwal = jadwal;
        this.sudahDiisi = sudahDiisi;
    }
    
    // Getters and Setters
    public int getNo() {
        return no;
    }
    
    public void setNo(int no) {
        this.no = no;
    }
    
    public String getNamaMataKuliah() {
        return namaMataKuliah;
    }
    
    public void setNamaMataKuliah(String namaMataKuliah) {
        this.namaMataKuliah = namaMataKuliah;
    }
    
    public String getJadwal() {
        return jadwal;
    }
    
    public void setJadwal(String jadwal) {
        this.jadwal = jadwal;
    }
    
    public boolean isSudahDiisi() {
        return sudahDiisi;
    }
    
    public void setSudahDiisi(boolean sudahDiisi) {
        this.sudahDiisi = sudahDiisi;
    }
    
    public String getStatusText() {
        return sudahDiisi ? "Sudah diisi" : "Belum diisi";
    }
}
