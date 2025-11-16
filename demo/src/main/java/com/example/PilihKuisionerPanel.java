/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.example;

/**
 *
 * @author Nahda
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class PilihKuisionerPanel extends javax.swing.JPanel {

    /**
     * Creates new form PilihKuisionerPanel
     */
    // List untuk menyimpan data mata kuliah
    private List<MataKuliah> daftarMataKuliah;
    
    public PilihKuisionerPanel() {
        System.out.println("[PilihKuisionerPanel] Constructor start");
        try {
            initComponents();
            initializeData();
            setupTable();
            System.out.println("[PilihKuisionerPanel] Constructor complete");
        } catch (Throwable t) {
            System.err.println("[PilihKuisionerPanel] Error during construction:");
            t.printStackTrace();
            javax.swing.SwingUtilities.invokeLater(() -> {
                javax.swing.JOptionPane.showMessageDialog(null,
                    "Gagal membuat PilihKuisionerPanel: " + t.toString() + "\nSee console for full stack trace",
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            });
            throw t;
        }
    }

    //Initialize data mata kuliah
    private void initializeData() {
        daftarMataKuliah = new ArrayList<>();
        
        // Tambahkan data mata kuliah (status awal: belum diisi)
        daftarMataKuliah.add(new MataKuliah(1, "Aljabar Linier dan Matriks", "Selasa, 08:00-10:30, C.314", false));
        daftarMataKuliah.add(new MataKuliah(2, "Basis Data", "Senin, 13:00-15:30, C.304", false));
        daftarMataKuliah.add(new MataKuliah(3, "Sistem Operasi", "Kamis, 14:45-16:25, C.315", false));
        daftarMataKuliah.add(new MataKuliah(4, "Teori Bahasa dan Automata", "Jumat, 08:00-10:30, C.314", false));
    }

    
    /**
     * Setup table dengan data dan custom renderer
     */
    private void setupTable() {
        // Ensure table has a DefaultTableModel with columns before touching columnModel
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                new Object[]{"No", "Mata Kuliah", "Jadwal", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // make only the Status column editable (for button editor)
                return column == 3;
            }
        };
        tableKuisioner.setModel(model);

        // Styling header
        tableKuisioner.getTableHeader().setBackground(new Color(70, 130, 180));
        tableKuisioner.getTableHeader().setForeground(Color.WHITE);
        tableKuisioner.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableKuisioner.getTableHeader().setReorderingAllowed(false);

        // Set row height
        tableKuisioner.setRowHeight(45);

        // Populate table
        loadTableData();

        // Set column widths (safe now because model has columns)
        tableKuisioner.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableKuisioner.getColumnModel().getColumn(1).setPreferredWidth(300);
        tableKuisioner.getColumnModel().getColumn(2).setPreferredWidth(250);
        tableKuisioner.getColumnModel().getColumn(3).setPreferredWidth(150);

        // Center align untuk kolom No
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tableKuisioner.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        // Custom renderer dan editor untuk kolom Status
        tableKuisioner.getColumnModel().getColumn(3).setCellRenderer(new StatusButtonRenderer());
        tableKuisioner.getColumnModel().getColumn(3).setCellEditor(new StatusButtonEditor());
    }

    
    //Load data ke table
    
    private void loadTableData() {
        DefaultTableModel model = (DefaultTableModel) tableKuisioner.getModel();
        model.setRowCount(0); // Clear existing data
        
        for (MataKuliah mk : daftarMataKuliah) {
            model.addRow(new Object[]{
                mk.getNo(),
                mk.getNamaMataKuliah(),
                mk.getJadwal(),
                mk.getStatusText()
            });
        }
    }
    
    //Refresh table setelah update status
    public void refreshTable() {
        loadTableData();
        tableKuisioner.repaint();
    }
    
    //Custom renderer untuk menampilkan button dengan warna berbeda
    class StatusButtonRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            String status = value != null ? value.toString() : "Belum diisi";
            JButton button = new JButton(status);
            
            // Warna berbeda berdasarkan status
            if (status.equals("Sudah diisi")) {
                button.setBackground(new Color(76, 175, 80)); // Hijau
            } else {
                button.setBackground(new Color(244, 67, 54)); // Merah
            }
            
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Segoe UI", Font.BOLD, 12));
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setOpaque(true);
            
            return button;
        }
    }
    
    //Hanya editor untuk handle button click
    class StatusButtonEditor extends javax.swing.DefaultCellEditor {
        private JButton button;
        private String label;
        private int currentRow;
        
        public StatusButtonEditor() {
            super(new javax.swing.JCheckBox());
            button = new JButton();
            button.setOpaque(true);
            button.setFont(new Font("Segoe UI", Font.BOLD, 12));
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            
            button.addActionListener(e -> fireEditingStopped());
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
            
            currentRow = row;
            label = value != null ? value.toString() : "Belum diisi";
            button.setText(label);
            
            // Set warna sesuai status
            if (label.equals("Sudah diisi")) {
                button.setBackground(new Color(76, 175, 80)); // Hijau
            } else {
                button.setBackground(new Color(244, 67, 54)); // Merah
            }
            button.setForeground(Color.WHITE);
            
            return button;
        }
        
        @Override
        public Object getCellEditorValue() {
            // Handle button click
            handleButtonClick(currentRow);
            return label;
        }

        //Handle ketika button diklik
        private void handleButtonClick(int row) {
            MataKuliah mk = daftarMataKuliah.get(row);
            
            if (mk.isSudahDiisi()) {
                // Jika sudah diisi, tampilkan info
                javax.swing.JOptionPane.showMessageDialog(PilihKuisionerPanel.this,
                    "Penilaian untuk " + mk.getNamaMataKuliah() + " sudah diisi.\n" +
                    "Anda bisa melihat hasilnya atau mengedit.",
                    "Info",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Jika belum diisi, buka form penilaian
                // TODO: Buat dan buka FormPengisianPanel
                
                openFormPengisian(mk);
            }
        }
    }
    
    private void openFormPengisian(MataKuliah mk) {
        MainJFrame mainFrame = (MainJFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (mainFrame != null) {
            mainFrame.showFormPengisianPanel(mk, this);
        }
    }

    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableKuisioner = new javax.swing.JTable();

        setBackground(new java.awt.Color(245, 246, 250));
        setLayout(new java.awt.BorderLayout());

        // --- HEADER (top) ---
        javax.swing.JPanel headerPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        headerPanel.setBackground(java.awt.Color.decode("#334EAC"));
        headerPanel.setPreferredSize(new java.awt.Dimension(10, 72));
        javax.swing.JLabel headerTitle = new javax.swing.JLabel("KUISIONER PENILAIAN KINERJA DOSEN");
        headerTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 22));
        headerTitle.setForeground(java.awt.Color.white);
        headerTitle.setBorder(new javax.swing.border.EmptyBorder(8, 16, 8, 8));
        headerPanel.add(headerTitle, java.awt.BorderLayout.WEST);

        // simple profile placeholder on right
        javax.swing.JPanel profilePanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 8));
        profilePanel.setOpaque(false);
        javax.swing.JLabel profileName = new javax.swing.JLabel("Nama Mahasiswa");
        profileName.setForeground(java.awt.Color.white);
        profileName.setFont(new java.awt.Font("Georgia", java.awt.Font.BOLD, 12));
        profilePanel.add(profileName);
        headerPanel.add(profilePanel, java.awt.BorderLayout.EAST);

        this.add(headerPanel, java.awt.BorderLayout.NORTH);

        // --- MAIN (center) ---
        javax.swing.JPanel mainArea = new javax.swing.JPanel();
        mainArea.setOpaque(false);
        mainArea.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 28));

        // White rounded card
        javax.swing.JPanel card = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                int arc = 20;
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(java.awt.Color.white);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new java.awt.BorderLayout());
        card.setBorder(new javax.swing.border.EmptyBorder(32, 36, 32, 36));
        card.setPreferredSize(new java.awt.Dimension(900, 580));

        // Title area inside card
        javax.swing.JPanel titleArea = new javax.swing.JPanel();
        titleArea.setOpaque(false);
        titleArea.setLayout(new javax.swing.BoxLayout(titleArea, javax.swing.BoxLayout.Y_AXIS));
        jLabel2.setFont(new java.awt.Font("Georgia", java.awt.Font.PLAIN, 14));
        jLabel2.setText("SELAMAT DATANG DI");
        jLabel2.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        jLabel1.setFont(new java.awt.Font("Georgia", java.awt.Font.BOLD, 22));
        jLabel1.setText("DAFTAR MATA KULIAH");
        jLabel1.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        titleArea.add(jLabel2);
        titleArea.add(javax.swing.Box.createVerticalStrut(6));
        titleArea.add(jLabel1);
        titleArea.add(javax.swing.Box.createVerticalStrut(12));
        card.add(titleArea, java.awt.BorderLayout.NORTH);

        // Table (scroll) occupies card center and expands to fullscreen inside card
        jScrollPane1.setViewportView(tableKuisioner);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jScrollPane1.setPreferredSize(new java.awt.Dimension(820, 380));
        card.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        mainArea.add(card);
        this.add(mainArea, java.awt.BorderLayout.CENTER);

        // sticky footer help label
        javax.swing.JPanel footer = new javax.swing.JPanel();
        footer.setOpaque(false);
        javax.swing.JLabel help = new javax.swing.JLabel("Klik tombol Status untuk mengisi kuisioner atau melihat hasil.");
        help.setFont(new java.awt.Font("Georgia", 0, 12));
        footer.add(help);
        footer.setBorder(new javax.swing.border.EmptyBorder(8, 12, 12, 12));
        this.add(footer, java.awt.BorderLayout.SOUTH);

    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableKuisioner;
    // End of variables declaration//GEN-END:variables
}
