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
        initComponents();
        initializeData();
        setupTable();
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
        // Styling header
        tableKuisioner.getTableHeader().setBackground(new Color(70, 130, 180));
        tableKuisioner.getTableHeader().setForeground(Color.WHITE);
        tableKuisioner.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableKuisioner.getTableHeader().setReorderingAllowed(false);
        
        // Set row height
        tableKuisioner.setRowHeight(45);
        
        // Populate table
        loadTableData();
        
        // Set column widths
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

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(833, 516));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel1.setText("KUISIONER PENILAIAN KINERJA DOSEN");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel2.setText("SELAMAT DATANG DI");

        tableKuisioner.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No", "Mata Kuliah", "Jadwal", "Status Penilaian"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableKuisioner);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(238, 238, 238)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))))
                .addContainerGap(130, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.getAccessibleContext().setAccessibleName("");
        jLabel1.getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableKuisioner;
    // End of variables declaration//GEN-END:variables
}
