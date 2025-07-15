
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


public class frmMeja extends javax.swing.JFrame {
private DefaultTableModel model;
//variabel
String idmeja,nomormeja,kategori,status;
    public frmMeja() {
        initComponents();
        setLocationRelativeTo(null);
        
        //ini adalah untuk membuat judul dalam tabel  (3)
        model = new DefaultTableModel();
        tblmeja.setModel(model);
        model.addColumn("ID MEJA");
        model.addColumn("NO MEJA");
        model.addColumn("KATEGORI MEJA");
        model.addColumn("STATUS");
    
        
        GetData(); //untuk memanggil data ke dalam tabel
        nonaktif();  // agar pada tampilan pertama idnya muncul secara otomatis
    }
    
    //MENGAMBIL DATA
     public void GetData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            
            Statement st = (Statement)Koneksi.koneksiDb().createStatement();
            String sql ="Select * from tbmeja";
            ResultSet rs=st.executeQuery(sql);
            
            while (rs.next()){
            
                Object[] obj = new Object[5];
                obj[0]=rs.getString("idmeja");
                obj[1]=rs.getString("nomormeja");
                obj[2]=rs.getString("kategori");
                obj[3]=rs.getString("status");
            
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
            String sql = "Update tbmeja set nomormeja='" + nomormeja + "'," + "kategori='" + kategori + "'," + "status='" + status + "' WHERE idmeja='" + idmeja + "'";

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
     
     public void hapusdata(){
         loaddata ();
         int pesan=JOptionPane.showConfirmDialog(this, "Anda Yakin Ingin Menghapus Data Meja"+idmeja+"?","konfirmasi",JOptionPane.OK_CANCEL_OPTION);
            try {
                com.mysql.jdbc.Statement st=(com.mysql.jdbc.Statement) Koneksi.koneksiDb().createStatement();
                String sql="DELETE from tbmeja Where idmeja='"+idmeja+"'";
                PreparedStatement p=(PreparedStatement) Koneksi.koneksiDb().prepareStatement(sql);
                p.executeUpdate();
                GetData();
                
                JOptionPane.showMessageDialog(null, "Data Berhasil dihapus");
             
            }catch (SQLException err) {
                JOptionPane.showMessageDialog(null, err.getMessage());
            }
     }
     
     
    public void kosongkan (){
     txtidmeja.setText("");
     txtnomeja.setText("");
     cbKategorimeja.setSelectedItem("==PILIH==");
     cbstatus.setSelectedItem("==PILIH==");
     
    }
    
    public void nonaktif(){
        txtidmeja.setEnabled(false);
        txtnomeja.setEnabled(false);
        cbKategorimeja.setEnabled(false);
        cbstatus.setEnabled(false);
        btnsimpan.setEnabled(false);
        btnedit.setEnabled(false);
        btnhapus.setEnabled(false);
    }
    
    public void aktif(){
        txtnomeja.setEnabled(true);
        txtnomeja.requestFocus();
        cbKategorimeja.setEnabled(true);
        cbstatus.setEnabled(true);
        btnsimpan.setEnabled(true);
        btnedit.setEnabled(true);
        btnhapus.setEnabled(true);
        btnkeluar.setEnabled(true);
    }
    //select
    public void selecdata()
    {
        int i = tblmeja.getSelectedRow();
        if (i == -1)
        {
            //tidak ada baris yang terpilih
            return;
        }txtidmeja.setText(""+model.getValueAt(i, 0));
        txtnomeja.setText(""+model.getValueAt(i, 1));
        cbKategorimeja.setSelectedItem(""+model.getValueAt(i, 2));
        cbstatus.setSelectedItem(""+model.getValueAt(i, 3));
        btnhapus.setEnabled(true);
        btnedit.setEnabled(true);
        
        aktif();
    }
    
    //loaddata
    public void loaddata(){
        idmeja=txtidmeja.getText();
        nomormeja=txtnomeja.getText();
        kategori=(String)cbKategorimeja.getSelectedItem();
        status=(String)cbstatus.getSelectedItem();
      
    }
    
    //simpan data
    public void simpandata(){
        loaddata();
        
        try
        {
            com.mysql.jdbc.Statement st=(com.mysql.jdbc.Statement)Koneksi.koneksiDb().createStatement();
            String sql="insert into tbmeja (idmeja,nomormeja,kategori,status)"+"values"+"('"+idmeja+"',"+"'"+nomormeja+"','"+kategori+"','"+status+"')";
            PreparedStatement p=(PreparedStatement) Koneksi.koneksiDb().prepareStatement(sql);
            p.executeUpdate();
            GetData();
            nonaktif();
            kosongkan();
            JOptionPane.showMessageDialog(null,"Data berhasil disimpan");
            }
        catch (SQLException ex) {
            Logger.getLogger(frmMeja.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void idotomatis() {
       try{
       com.mysql.jdbc.Statement st=(com.mysql.jdbc.Statement)Koneksi.koneksiDb().createStatement();
       String sql="select * from tbmeja order by Idmeja desc";
       ResultSet rs=st.executeQuery(sql);
       if(rs.next()){
           String id=rs.getString("Idmeja").substring(3);
           String AN=""+(Integer.parseInt(id)+1);
           String nol="";

           if(AN.length()==1)
           {nol="000";}
           else if(AN.length()==2)
           {nol="00";}
           else if(AN.length()==3)
           {nol="";}
               txtidmeja.setText("MN"+nol+AN);
           }else{
               txtidmeja.setText("MN0001");
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
        txtidmeja = new javax.swing.JTextField();
        txtnomeja = new javax.swing.JTextField();
        btntambah = new javax.swing.JButton();
        btnsimpan = new javax.swing.JButton();
        btnedit = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        btnkeluar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblmeja = new javax.swing.JTable();
        cbKategorimeja = new javax.swing.JComboBox<>();
        cbstatus = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 204, 153));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Palatino Linotype", 1, 24)); // NOI18N
        jLabel1.setText("FORM MEJA");

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
        jLabel2.setText("Id Meja");

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("No Meja");

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Kategori");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Status");

        txtidmeja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidmejaActionPerformed(evt);
            }
        });

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

        tblmeja.setModel(new javax.swing.table.DefaultTableModel(
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
        tblmeja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblmejaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblmeja);

        cbKategorimeja.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "=== Pilih===", "Single", "Berdua", "Family", " " }));
        cbKategorimeja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbKategorimejaActionPerformed(evt);
            }
        });

        cbstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "=== Pilih Status ===", "Kosong", "Terisi" }));

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
                                    .addComponent(jLabel2)
                                    .addGap(47, 47, 47)
                                    .addComponent(txtidmeja, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel5))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtnomeja, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cbKategorimeja, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cbstatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                    .addComponent(txtidmeja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtnomeja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbKategorimeja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
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

    private void cbKategorimejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbKategorimejaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbKategorimejaActionPerformed

    private void btntambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahActionPerformed
        aktif();
        idotomatis();
        
    }//GEN-LAST:event_btntambahActionPerformed

    private void tblmejaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblmejaMouseClicked
        // TODO add your handling code here:
        selecdata();
        btnsimpan.setEnabled(false);
    }//GEN-LAST:event_tblmejaMouseClicked

    private void btneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditActionPerformed
        // TODO add your handling code here:
        Ubahdata();
        GetData();
        
    }//GEN-LAST:event_btneditActionPerformed

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        // TODO add your handling code here:
        hapusdata();
        
    }//GEN-LAST:event_btnhapusActionPerformed

    private void txtidmejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidmejaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidmejaActionPerformed

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
            java.util.logging.Logger.getLogger(frmMeja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMeja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMeja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMeja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMeja().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnedit;
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btnkeluar;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JButton btntambah;
    private javax.swing.JComboBox<String> cbKategorimeja;
    private javax.swing.JComboBox<String> cbstatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblmeja;
    public javax.swing.JTextField txtidmeja;
    public javax.swing.JTextField txtnomeja;
    // End of variables declaration//GEN-END:variables

   
   
}
