package com.example;

// Program ini dibuat oleh kelompok 2

public class Main {
    public static void main(String[] args) {
        // Run Swing UI on Event Dispatch Thread
        java.awt.EventQueue.invokeLater(() -> {
            LoginPageFrame frame = new LoginPageFrame();
            frame.setTitle("SISTEM PENILAIAN KINERJA DOSEN");
            frame.setVisible(true);
        });
    }
}