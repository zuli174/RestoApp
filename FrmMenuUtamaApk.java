

package Aplikasi;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.Timer;


public class FrmMenuUtamaApk extends javax.swing.JFrame {
    java.util.Date tglsekarang = new java.util.Date();
    private SimpleDateFormat format = new SimpleDateFormat("dd MMMMMMMMM yyyy", Locale.getDefault());
    private String tanggal = format.format(tglsekarang);
    
    /**
     * Creates new form MenuUtamaOke
     */
    private String idpengguna=Session.getIdpengguna();
    private String namapengguna=Session.getNamaPengguna();
    private String username=Session.getUsername();
    private String password=Session.getPassword();
    private String level=Session.getLevel();
    
    public FrmMenuUtamaApk() {
        initComponents();
        setLocationRelativeTo(null);
        lbnamapetugas.setText(namapengguna);
        lbjenispekerjaan.setText (level);
        lbtanggal.setText(tanggal);
        
        setJam();
       if (level.equalsIgnoreCase("Admin")) {
        admin();
    } else {
        if (level.equalsIgnoreCase("Kasir")) {
            kasir();
        } else {
            manager();
        }
    }
    }
    
    public void admin() {
    btnpengguna.setEnabled(true);
    btnpelanggan.setEnabled(true);
    btnmeja.setEnabled(true);
    btnmenu.setEnabled(true);
    btnpegawai.setEnabled(true);
    btntransaksi.setEnabled(true);
    btnlap.setEnabled(true);
    lbpengguna.setEnabled(true);
    lbpelanggan.setEnabled(true);
    lbpegawai.setEnabled(true);
    lbmeja.setEnabled(true);
    lbmenu.setEnabled(true);
    lbtransaksi.setEnabled(true);
    lblaporan.setEnabled(true);
    }
    public void kasir() {
    btntransaksi.setEnabled(true);
    btnlap.setEnabled(true);
    lbtransaksi.setEnabled(true);
    lblaporan.setEnabled(true);
    btnpengguna.setEnabled(false);
    btnpelanggan.setEnabled(false);
    btnmeja.setEnabled(false);
    btnmenu.setEnabled(false);
    btnpegawai.setEnabled(false);
    lbpengguna.setEnabled(false);
    lbpelanggan.setEnabled(false);
    lbpegawai.setEnabled(false);
    lbmeja.setEnabled(false);
    lbmenu.setEnabled(false);
    }
    
    public void manager() {
    btntransaksi.setEnabled(true);
    btnlap.setEnabled(true);
    lbtransaksi.setEnabled(true);
    lblaporan.setEnabled(true);
    btnpengguna.setEnabled(false);
    btnpelanggan.setEnabled(false);
    btnmeja.setEnabled(false);
    btnmenu.setEnabled(false);
    btnpegawai.setEnabled(false);
    lbpengguna.setEnabled(false);
    lbpelanggan.setEnabled(false);
    lbpegawai.setEnabled(false);
    lbmeja.setEnabled(false);
    lbmenu.setEnabled(false);
    }
    
    public final void setJam() {
    ActionListener taskPerformer = new ActionListener() {
    @Override
    public void actionPerformed (ActionEvent evt) {
    String nol_jam = "", nol_menit = "",nol_detik = "";
    java.util.Date dateTime = new java.util.Date();
    int nilai_jam = dateTime.getHours();
    int nilai_menit = dateTime.getMinutes();
    int nilai_detik = dateTime.getSeconds();
    if (nilai_jam <= 9) nol_jam= "0";
    if (nilai_menit <= 9) nol_menit= "0";
    if (nilai_detik <= 9) nol_detik= "0";
    String jam = nol_jam + Integer.toString(nilai_jam);
    String menit = nol_menit + Integer.toString(nilai_menit);
    String detik = nol_detik + Integer.toString(nilai_detik);
    lbjam.setText(jam+":"+menit+":"+detik+"");
    }
    };
new Timer(1000, taskPerformer).start();
}  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnpengguna = new javax.swing.JButton();
        lbpengguna = new javax.swing.JLabel();
        btnpelanggan = new javax.swing.JButton();
        lbpelanggan = new javax.swing.JLabel();
        btnmeja = new javax.swing.JButton();
        lbmeja = new javax.swing.JLabel();
        btnmenu = new javax.swing.JButton();
        btntransaksi = new javax.swing.JButton();
        btnlap = new javax.swing.JButton();
        btninfo = new javax.swing.JButton();
        btnkeluar = new javax.swing.JButton();
        btnpegawai = new javax.swing.JButton();
        lbmenu = new javax.swing.JLabel();
        lbpegawai = new javax.swing.JLabel();
        lbtransaksi = new javax.swing.JLabel();
        lbinfo = new javax.swing.JLabel();
        lblaporan = new javax.swing.JLabel();
        lbkeluar = new javax.swing.JLabel();
        lbjenispekerjaan = new javax.swing.JLabel();
        lbtanggal = new javax.swing.JLabel();
        lbnamapetugas = new javax.swing.JLabel();
        lbjam = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 204, 204));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 204, 153));

        jLabel1.setFont(new java.awt.Font("Modern No. 20", 1, 36)); // NOI18N
        jLabel1.setText("Menu Utama");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(264, 264, 264)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        btnpengguna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/1.png"))); // NOI18N
        btnpengguna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpenggunaActionPerformed(evt);
            }
        });

        lbpengguna.setFont(new java.awt.Font("Century Schoolbook", 1, 14)); // NOI18N
        lbpengguna.setText("Pengguna");

        btnpelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/2.png"))); // NOI18N
        btnpelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpelangganActionPerformed(evt);
            }
        });

        lbpelanggan.setFont(new java.awt.Font("Century Schoolbook", 1, 14)); // NOI18N
        lbpelanggan.setText("Pelanggan");

        btnmeja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/3.png"))); // NOI18N
        btnmeja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmejaActionPerformed(evt);
            }
        });

        lbmeja.setFont(new java.awt.Font("Century Schoolbook", 1, 14)); // NOI18N
        lbmeja.setText("Meja");

        btnmenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/4.png"))); // NOI18N
        btnmenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmenuActionPerformed(evt);
            }
        });

        btntransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/6.png"))); // NOI18N
        btntransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntransaksiActionPerformed(evt);
            }
        });

        btnlap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/7.png"))); // NOI18N
        btnlap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlapActionPerformed(evt);
            }
        });

        btninfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/8-removebg-preview.png"))); // NOI18N

        btnkeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/9-removebg-preview.png"))); // NOI18N
        btnkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkeluarActionPerformed(evt);
            }
        });

        btnpegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/5.png"))); // NOI18N
        btnpegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpegawaiActionPerformed(evt);
            }
        });

        lbmenu.setFont(new java.awt.Font("Century Schoolbook", 1, 14)); // NOI18N
        lbmenu.setText("Menu");

        lbpegawai.setFont(new java.awt.Font("Century Schoolbook", 1, 14)); // NOI18N
        lbpegawai.setText("Pegawai");

        lbtransaksi.setFont(new java.awt.Font("Century Schoolbook", 1, 14)); // NOI18N
        lbtransaksi.setText("Transaksi");

        lbinfo.setFont(new java.awt.Font("Century Schoolbook", 1, 14)); // NOI18N
        lbinfo.setText("INFO");

        lblaporan.setFont(new java.awt.Font("Century Schoolbook", 1, 14)); // NOI18N
        lblaporan.setText("Laporan");

        lbkeluar.setFont(new java.awt.Font("Century Schoolbook", 1, 14)); // NOI18N
        lbkeluar.setText("KELUAR");

        lbjenispekerjaan.setFont(new java.awt.Font("Century Schoolbook", 1, 14)); // NOI18N
        lbjenispekerjaan.setText("Level");

        lbtanggal.setFont(new java.awt.Font("Century Schoolbook", 1, 14)); // NOI18N
        lbtanggal.setText("Tanggal Sekarang");

        lbnamapetugas.setFont(new java.awt.Font("Century Schoolbook", 1, 14)); // NOI18N
        lbnamapetugas.setText("Nama");

        lbjam.setFont(new java.awt.Font("Modern No. 20", 0, 14)); // NOI18N
        lbjam.setText("Jam");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btninfo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnkeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lbinfo)
                        .addGap(32, 32, 32)
                        .addComponent(lbkeluar)))
                .addGap(38, 38, 38))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbtransaksi))
                    .addComponent(btntransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbjenispekerjaan)
                    .addComponent(lbnamapetugas))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(lbmeja))
                            .addComponent(btnmeja, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(btnmenu, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(104, 104, 104)
                                .addComponent(lbmenu)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(btnlap, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(89, 89, 89)
                                .addComponent(lblaporan))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(lbtanggal)
                        .addGap(59, 59, 59)
                        .addComponent(lbjam)))
                .addContainerGap(64, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbpengguna)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbpegawai)
                        .addGap(94, 94, 94))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnpengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(btnpegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbpelanggan))
                    .addComponent(btnpelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(140, 140, 140))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btntransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbtransaksi))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnmeja, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbmeja))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnmenu, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnlap, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbmenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblaporan))))
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnpelanggan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnpengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnpegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(lbpelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbpengguna)
                            .addComponent(lbpegawai))
                        .addGap(5, 5, 5)))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbjenispekerjaan)
                            .addComponent(lbtanggal)
                            .addComponent(lbjam))
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btninfo, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnkeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbkeluar)
                            .addComponent(lbinfo)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lbnamapetugas)
                        .addGap(98, 98, 98)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnpegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpegawaiActionPerformed
        // TODO add your handling code here:
        new frmPegawai().setVisible(true);
            this.setVisible(false);
    }//GEN-LAST:event_btnpegawaiActionPerformed

    private void btnpenggunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpenggunaActionPerformed
            // TODO add your handling code here:
            new frmPengguna().setVisible(true);
            this.setVisible(false);
    }//GEN-LAST:event_btnpenggunaActionPerformed

    private void btnpelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpelangganActionPerformed
        // TODO add your handling code here:
        new frmPelanggan().setVisible(true);
            this.setVisible(false);
    }//GEN-LAST:event_btnpelangganActionPerformed

    private void btnmejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmejaActionPerformed
            // TODO add your handling code here:
            new frmMeja().setVisible(true);
            this.setVisible(false);
    }//GEN-LAST:event_btnmejaActionPerformed

    private void btnmenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmenuActionPerformed
            // TODO add your handling code here:
            new frmMenu().setVisible(true);
            this.setVisible(false);
    }//GEN-LAST:event_btnmenuActionPerformed

    private void btnkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkeluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnkeluarActionPerformed

    private void btntransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntransaksiActionPerformed
            // TODO add your handling code here:
           new frmTransaksi().setVisible(true);
            this.setVisible(false);
    }//GEN-LAST:event_btntransaksiActionPerformed

    private void btnlapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlapActionPerformed
            // TODO add your handling code here:
            new frmLaporan().setVisible(true);
            this.setVisible(false);
    }//GEN-LAST:event_btnlapActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmMenuUtamaApk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMenuUtamaApk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMenuUtamaApk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMenuUtamaApk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMenuUtamaApk().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btninfo;
    private javax.swing.JButton btnkeluar;
    private javax.swing.JButton btnlap;
    private javax.swing.JButton btnmeja;
    private javax.swing.JButton btnmenu;
    private javax.swing.JButton btnpegawai;
    private javax.swing.JButton btnpelanggan;
    private javax.swing.JButton btnpengguna;
    private javax.swing.JButton btntransaksi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbinfo;
    private javax.swing.JLabel lbjam;
    private javax.swing.JLabel lbjenispekerjaan;
    private javax.swing.JLabel lbkeluar;
    private javax.swing.JLabel lblaporan;
    private javax.swing.JLabel lbmeja;
    private javax.swing.JLabel lbmenu;
    private javax.swing.JLabel lbnamapetugas;
    private javax.swing.JLabel lbpegawai;
    private javax.swing.JLabel lbpelanggan;
    private javax.swing.JLabel lbpengguna;
    private javax.swing.JLabel lbtanggal;
    private javax.swing.JLabel lbtransaksi;
    // End of variables declaration//GEN-END:variables
}
