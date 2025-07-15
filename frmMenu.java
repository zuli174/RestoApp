
package Aplikasi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class frmMenu extends javax.swing.JFrame {
private DefaultTableModel model;
//variabel
String idmenu,namamenu,kategori,harga,stok;
    public frmMenu() {
        initComponents();
        setLocationRelativeTo(null);
        
        //ini adalah untuk membuat judul dalam tabel  (3)
        model = new DefaultTableModel();
        tblmenu.setModel(model);
        model.addColumn("Id Menu");
        model.addColumn("Nama Menu");
        model.addColumn("Kategori");
        model.addColumn("Harga");
        model.addColumn("Stok");
        
        GetData(); //untuk memanggil data ke dalam tabel
        nonaktif();  // agar pada tampilan pertama idnya muncul secara otomatis
    }
    
     public void GetData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            
            Statement st = (Statement)Koneksi.koneksiDb().createStatement();
            String sql ="Select * from tbmenu";
            ResultSet rs=st.executeQuery(sql);
            
            while (rs.next()){
            
                Object[] obj = new Object[5];
                obj[0]=rs.getString("idmenu");
                obj[1]=rs.getString("namamenu");
                obj[2]=rs.getString("kategori");
                obj[3]=rs.getString("harga");
                obj[4]=rs.getString("stok");
            
            model.addRow(obj);
        }
    }catch (SQLException error) { //untuk memberikan notifikasi error
        JOptionPane.showMessageDialog(null, error.getMessage());
    }
    }
     
     //mengubah data
     public void Ubahdata() {
        loaddata();
        try {
            Statement st = (Statement) Koneksi.koneksiDb().createStatement();
            String sql = "Update tbmenu set namamenu='" + namamenu + "'," + "kategori='" + kategori + "'," + "harga='" + harga + "'," + "stok='" + stok + "' WHERE idmenu='" + idmenu + "'";

            PreparedStatement p = (PreparedStatement) Koneksi.koneksiDb().prepareStatement(sql);
            p.executeUpdate();
            GetData();
            kosongkan();
            selecdata();
            JOptionPane.showMessageDialog(null, "Data berhasil di Ubah");
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
     
     //hapus data
     public void hapusdata(){
         loaddata ();
         int pesan=JOptionPane.showConfirmDialog(this, "Anda Yakin Ingin Menghapus Data Menu"+idmenu+"?","konfirmasi",JOptionPane.OK_CANCEL_OPTION);
            try {
                com.mysql.jdbc.Statement st=(com.mysql.jdbc.Statement) Koneksi.koneksiDb().createStatement();
                String sql="DELETE from tbmenu Where idmenu='"+idmenu+"'";
                PreparedStatement p=(PreparedStatement) Koneksi.koneksiDb().prepareStatement(sql);
                p.executeUpdate();
                GetData();
                
                JOptionPane.showMessageDialog(null, "Data Berhasil dihapus");
             
            }catch (SQLException err) {
                JOptionPane.showMessageDialog(null, err.getMessage());
            }
     }
     
     
    public void kosongkan (){
     txtidmenu.setText("");
     txtnamamenu.setText("");
     cbKategori.setSelectedItem("==PILIH==");
     txtharga.setText("");
     txtstock.setText("");
    }
    
    public void nonaktif(){
        txtidmenu.setEnabled(false);
        txtnamamenu.setEnabled(false);
        cbKategori.setEnabled(false);
        txtharga.setEnabled(false);
        txtstock.setEnabled(false);
        btnsimpan.setEnabled(false);
        btnedit.setEnabled(false);
        btnhapus.setEnabled(false);
    }
    
    public void aktif(){
        txtnamamenu.setEnabled(true);
        txtnamamenu.requestFocus();
        cbKategori.setEnabled(true);
        txtharga.setEnabled(true);
        txtstock.setEnabled(true);
        btnsimpan.setEnabled(true);
        btnedit.setEnabled(true);
        btnhapus.setEnabled(true);
        btnkeluar.setEnabled(true);
    }
    //select
    public void selecdata()
    {
        int i = tblmenu.getSelectedRow();
        if (i == -1)
        {
            //tidak ada baris yang terpilih
            return;
        }txtidmenu.setText(""+model.getValueAt(i, 0));
        txtnamamenu.setText(""+model.getValueAt(i, 1));
        cbKategori.setSelectedItem(""+model.getValueAt(i, 2));
        txtharga.setText(""+model.getValueAt(i, 3));
        txtstock.setText(""+model.getValueAt(i, 4));
        btnhapus.setEnabled(true);
        btnedit.setEnabled(true);
        
        aktif();
    }
    
    //loaddata
    public void loaddata(){
        idmenu=txtidmenu.getText();
        namamenu=txtnamamenu.getText();
        kategori=(String)cbKategori.getSelectedItem();
        harga=txtharga.getText();
        stok=txtstock.getText();
        
    }
    
    //simpan data
    public void simpandata(){
        loaddata();
        
        try
        {
            com.mysql.jdbc.Statement st=(com.mysql.jdbc.Statement)Koneksi.koneksiDb().createStatement();
            String sql="insert into tbmenu (idmenu,namamenu,kategori,harga,stok)"+"values"+"('"+idmenu+"',"+"'"+namamenu+"','"+kategori+"','"+harga+"','"+stok+"')";
            PreparedStatement p=(PreparedStatement) Koneksi.koneksiDb().prepareStatement(sql);
            p.executeUpdate();
            GetData();
            nonaktif();
            kosongkan();
            JOptionPane.showMessageDialog(null,"Data berhasil disimpan");
            }
        catch (SQLException ex) {
            Logger.getLogger(frmMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //idotomatis
    public void idotomatis() {
       try{
       com.mysql.jdbc.Statement st=(com.mysql.jdbc.Statement)Koneksi.koneksiDb().createStatement();
       String sql="select * from tbmenu order by Idmenu desc";
       ResultSet rs=st.executeQuery(sql);
       if(rs.next()){
           String id=rs.getString("Idmenu").substring(3);
           String AN=""+(Integer.parseInt(id)+1);
           String nol="";

           if(AN.length()==1)
           {nol="000";}
           else if(AN.length()==2)
           {nol="00";}
           else if(AN.length()==3)
           {nol="";}
               txtidmenu.setText("MN"+nol+AN);
           }else{
               txtidmenu.setText("MN0001");
       }
       } catch (SQLException error) {
           JOptionPane.showMessageDialog(null,error.getMessage());
       }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtidmenu = new javax.swing.JTextField();
        txtnamamenu = new javax.swing.JTextField();
        txtharga = new javax.swing.JTextField();
        txtstock = new javax.swing.JTextField();
        btntambah = new javax.swing.JButton();
        btnsimpan = new javax.swing.JButton();
        btnedit = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        btnkeluar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblmenu = new javax.swing.JTable();
        cbKategori = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 204, 153));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Palatino Linotype", 1, 24)); // NOI18N
        jLabel1.setText("FORM MENU");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(109, 109, 109))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Id Menu");

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Nama Menu");

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Kategori");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Harga");

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Stok");

        btntambah.setBackground(new java.awt.Color(255, 255, 255));
        btntambah.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btntambah.setText("Tambah");
        btntambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntambahActionPerformed(evt);
            }
        });

        btnsimpan.setBackground(new java.awt.Color(255, 255, 255));
        btnsimpan.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnsimpan.setText("Simpan");
        btnsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsimpanActionPerformed(evt);
            }
        });

        btnedit.setBackground(new java.awt.Color(255, 255, 255));
        btnedit.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnedit.setText("Edit");
        btnedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditActionPerformed(evt);
            }
        });

        btnhapus.setBackground(new java.awt.Color(255, 255, 255));
        btnhapus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnhapus.setText("Hapus");
        btnhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhapusActionPerformed(evt);
            }
        });

        btnkeluar.setBackground(new java.awt.Color(255, 255, 255));
        btnkeluar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnkeluar.setText("Keluar");
        btnkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkeluarActionPerformed(evt);
            }
        });

        tblmenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblmenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblmenuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblmenu);

        cbKategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "=== Pilih Menu ===", "Makanan", "Minuman", " " }));
        cbKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbKategoriActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btntambah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnsimpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnedit, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnhapus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnkeluar))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtstock, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(47, 47, 47)
                                    .addComponent(txtidmenu, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtnamamenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cbKategori, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtidmenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtnamamenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtstock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btntambah)
                    .addComponent(btnedit)
                    .addComponent(btnhapus)
                    .addComponent(btnkeluar)
                    .addComponent(btnsimpan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        setSize(new java.awt.Dimension(391, 409));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsimpanActionPerformed
        // TODO add your handling code here:
        simpandata();
        nonaktif();
    }//GEN-LAST:event_btnsimpanActionPerformed

    private void btnkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkeluarActionPerformed
        // TODO add your handling code here:
        new FrmMenuUtamaApk().setVisible(true);
            this.setVisible(false);
    }//GEN-LAST:event_btnkeluarActionPerformed

    private void cbKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbKategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbKategoriActionPerformed

    private void btntambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahActionPerformed
        aktif();
        idotomatis();
        
    }//GEN-LAST:event_btntambahActionPerformed

    private void tblmenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblmenuMouseClicked
        // TODO add your handling code here:
        selecdata();
        btnsimpan.setEnabled(false);
    }//GEN-LAST:event_tblmenuMouseClicked

    private void btneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditActionPerformed
        // TODO add your handling code here:
        Ubahdata();
        GetData();
        
    }//GEN-LAST:event_btneditActionPerformed

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        // TODO add your handling code here:
        hapusdata();
        
    }//GEN-LAST:event_btnhapusActionPerformed

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
            java.util.logging.Logger.getLogger(frmMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnedit;
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btnkeluar;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JButton btntambah;
    private javax.swing.JComboBox<String> cbKategori;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblmenu;
    private javax.swing.JTextField txtharga;
    private javax.swing.JTextField txtidmenu;
    private javax.swing.JTextField txtnamamenu;
    public javax.swing.JTextField txtstock;
    // End of variables declaration//GEN-END:variables

   
   
}
