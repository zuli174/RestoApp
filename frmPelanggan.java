
package Aplikasi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class frmPelanggan extends javax.swing.JFrame {
private DefaultTableModel model;
//variabel
String idpelanggan,namapelanggan,jeniskelamin,tempatlahir,tanggallahir,alamat,nohp;
    public frmPelanggan() {
        initComponents();
        setLocationRelativeTo(null);
        
        //ini adalah untuk membuat judul dalam tabel  (3)
        model = new DefaultTableModel();
        tblpelanggan.setModel(model);
        model.addColumn("Id Pelanggan");
        model.addColumn("Nama Pelanggan");
        model.addColumn("Jenis Kelamin");
        model.addColumn("Tempat Lahir");
        model.addColumn("Tanggal Lahir");
        model.addColumn("Alamat");
        model.addColumn("NO HP");
        
        GetData(); //untuk memanggil data ke dalam tabel
        nonaktif();  // agar pada tampilan pertama idnya muncul secara otomatis
    }
    
    //mengubah
   
    
     public void GetData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            
            Statement st = (Statement)Koneksi.koneksiDb().createStatement();
            String sql ="Select * from tbpelanggan";
            ResultSet rs=st.executeQuery(sql);
            
            while (rs.next()){
            
                Object[] obj = new Object[7];
                obj[0]=rs.getString("idpelanggan");
                obj[1]=rs.getString("namapelanggan");
                obj[2]=rs.getString("jk");
                obj[3]=rs.getString("tempatlahir");
                obj[4]=rs.getString("tanggallahir");
                obj[5]=rs.getString("alamat");
                obj[6]=rs.getString("nohp");
            
            model.addRow(obj);
        }
    }catch (SQLException error) { //untuk memberikan notifikasi error
        JOptionPane.showMessageDialog(null, error.getMessage());
    }
    }
     
     //mengubah data
     public void Ubahdata() {
        loaddata();
        String ps = "yy-MM-dd";
         SimpleDateFormat format=new SimpleDateFormat(ps);
         tanggallahir=String.valueOf(format.format(txttanggallahir.getDate()));
        try {
            Statement st = (Statement) Koneksi.koneksiDb().createStatement();
            String sql = "UPDATE tbpelanggan SET " + "namapelanggan='" + namapelanggan + "', " + "jk='" + jeniskelamin + "', " 
                    + "tempatlahir='" + tempatlahir + "', " + "tanggallahir='" + tanggallahir + "', " + "alamat='" + alamat + "', "
                    + "nohp='" + nohp + "' " + "WHERE idpelanggan='" + idpelanggan + "'";

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
         int pesan=JOptionPane.showConfirmDialog(this, "Anda Yakin Ingin Menghapus Data Menu"+idpelanggan+"?","konfirmasi",JOptionPane.OK_CANCEL_OPTION);
            try {
                com.mysql.jdbc.Statement st=(com.mysql.jdbc.Statement) Koneksi.koneksiDb().createStatement();
                String sql="DELETE from tbpelanggan Where idpelanggan='"+idpelanggan+"'";
                PreparedStatement p=(PreparedStatement) Koneksi.koneksiDb().prepareStatement(sql);
                p.executeUpdate();
                GetData();
                
                JOptionPane.showMessageDialog(null, "Data Berhasil dihapus");
             
            }catch (SQLException err) {
                JOptionPane.showMessageDialog(null, err.getMessage());
            }
     }
     
     
    public void kosongkan (){
     txtidpelanggan.setText("");
     txtnamapelanggan.setText("");
     cbjeniskelamin.setSelectedItem("==PILIH==");
     txttempatlahir.setText("");
     txttanggallahir.setDateFormatString("yy-MM-dd");
     txtalamat.setText("");
     txtnohp.setText("");
    }
    
    public void nonaktif(){
        txtidpelanggan.setEnabled(false);
        txtnamapelanggan.setEnabled(false);
        cbjeniskelamin.setEnabled(false);
        txttempatlahir.setEnabled(false);
        txttanggallahir.setEnabled(false);
        txtalamat.setEnabled(false);
        txtnohp.setEnabled(false);
        btnsimpan.setEnabled(false);
        btnedit.setEnabled(false);
        btnhapus.setEnabled(false);
    }
    
    public void aktif(){
        txtnamapelanggan.setEnabled(true);
        txtnamapelanggan.requestFocus();
        cbjeniskelamin.setEnabled(true);
        txttempatlahir.setEnabled(true);
        txttanggallahir.setEnabled(true);
        txtalamat.setEnabled(true);
        txtnohp.setEnabled(true);
        btnsimpan.setEnabled(true);
        btnedit.setEnabled(true);
        btnhapus.setEnabled(true);
        btnkeluar.setEnabled(true);
    }
    //select
    public void selecdata()
    {
        int i = tblpelanggan.getSelectedRow();
        if (i == -1)
        {
            //tidak ada baris yang terpilih
            return;
        }txtidpelanggan.setText(""+model.getValueAt(i, 0));
        txtnamapelanggan.setText(""+model.getValueAt(i, 1));
        cbjeniskelamin.setSelectedItem(""+model.getValueAt(i, 2));
        txttempatlahir.setText(""+model.getValueAt(i, 3));
        String s=(String)tblpelanggan.getModel().getValueAt(i, 4);
        try{
            SimpleDateFormat f= new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date d=f.parse(s);
        
        txtalamat.setText(""+model.getValueAt(i, 5));
        txtnohp.setText(""+model.getValueAt(i, 6));
        btnhapus.setEnabled(true);
        btnedit.setEnabled(true);
        
        aktif();
        }catch(Exception ex) {
            JOptionPane.showMessageDialog(null,"gagal");
        }
    }
    
    //loaddata
    public void loaddata(){
        idpelanggan=txtidpelanggan.getText();
        namapelanggan=txtnamapelanggan.getText();
        jeniskelamin=(String)cbjeniskelamin.getSelectedItem();
        tempatlahir=txttempatlahir.getText();
        
        alamat=txtalamat.getText();
        nohp=txtnohp.getText();
        
    }
    
    //simpan data
    public void simpandata() {
        loaddata();
        String ps="yy-MM-dd";
        SimpleDateFormat format=new SimpleDateFormat(ps);
         tanggallahir=String.valueOf(format.format(txttanggallahir.getDate()));
        try
        {
            Statement st = (Statement) Koneksi.koneksiDb().createStatement();
            String sql = "insert into tbpelanggan (idpelanggan,namapelanggan,jk,tempatlahir,tanggallahir,alamat,nohp)" + "values" + "('" + idpelanggan + "'," + "'" + namapelanggan + "','" + jeniskelamin + "','" + tempatlahir + "','" + tanggallahir + "','" + alamat + "','" + nohp + "')";

            PreparedStatement p = (PreparedStatement) Koneksi.koneksiDb().prepareStatement(sql);
            p.executeUpdate();
            GetData();
            idotomatis();
            aktif();
            JOptionPane.showMessageDialog(null, "Data berhasil di simpan");
        } catch (SQLException error)
        {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }

    }

    
    public void idotomatis() {
       try{
       com.mysql.jdbc.Statement st=(com.mysql.jdbc.Statement)Koneksi.koneksiDb().createStatement();
       String sql="select * from tbpelanggan order by idpelanggan desc";
       ResultSet rs=st.executeQuery(sql);
       if(rs.next()){
           String id=rs.getString("idpelanggan").substring(3);
           String AN=""+(Integer.parseInt(id)+1);
           String nol="";

           if(AN.length()==1)
           {nol="000";}
           else if(AN.length()==2)
           {nol="00";}
           else if(AN.length()==3)
           {nol="";}
               txtidpelanggan.setText("PL"+nol+AN);
           }else{
               txtidpelanggan.setText("PL0001");
       }
       } catch (SQLException error) {
           JOptionPane.showMessageDialog(null,error.getMessage());
       }
    }
    
    public void Cari(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try {
            Connection con = Koneksi.koneksiDb();
            Statement st = con.createStatement();
            String sql = "select * from tbpelanggan where namapelanggan like '%" + txtcari.getText() + "%'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Object[] ob = new Object[7];
                ob[0] = rs.getString(1);
                ob[1] = rs.getString(2);
                ob[2] = rs.getString(3);
                ob[3] = rs.getString(4);
                ob[4] = rs.getString(5);
                ob[5] = rs.getString(6);
                ob[6] = rs.getString(7);
                
                model.addRow(ob);
            }
        }catch (Exception e){
            System.out.println(e);
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
        txtidpelanggan = new javax.swing.JTextField();
        txtnamapelanggan = new javax.swing.JTextField();
        txttempatlahir = new javax.swing.JTextField();
        btntambah = new javax.swing.JButton();
        btnsimpan = new javax.swing.JButton();
        btnedit = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        btnkeluar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpelanggan = new javax.swing.JTable();
        cbjeniskelamin = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtalamat = new javax.swing.JTextField();
        txtnohp = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();
        txttanggallahir = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 204, 153));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Palatino Linotype", 1, 24)); // NOI18N
        jLabel1.setText("FORM PELANGGAN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(29, 29, 29))
        );

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Id Pelanggan");

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Nama Pelanggan");

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Jenis Kelamin");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Tempat Lahir");

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Tanggal Lahir");

        txtnamapelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamapelangganActionPerformed(evt);
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

        tblpelanggan.setModel(new javax.swing.table.DefaultTableModel(
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
        tblpelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblpelangganMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblpelanggan);

        cbjeniskelamin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "=== Pilih===", "p", "l", " " }));
        cbjeniskelamin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbjeniskelaminActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Alamat");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("No HP");

        txtnohp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnohpActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("CARI DATA PELANGGAN");

        txtcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcariActionPerformed(evt);
            }
        });
        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcariKeyTyped(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btntambah)
                            .addComponent(jLabel9))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                                .addComponent(btnsimpan)
                                .addGap(66, 66, 66)
                                .addComponent(btnedit, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addComponent(btnhapus)
                                .addGap(60, 60, 60)
                                .addComponent(btnkeluar)
                                .addGap(85, 85, 85))))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(46, 46, 46)
                                    .addComponent(txttanggallahir, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtalamat, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txttempatlahir, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(47, 47, 47)
                                        .addComponent(txtidpelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtnamapelanggan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbjeniskelamin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtnohp, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtidpelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtnamapelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbjeniskelamin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txttempatlahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txttanggallahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtalamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(txtnohp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btntambah)
                    .addComponent(btnhapus)
                    .addComponent(btnkeluar)
                    .addComponent(btnsimpan)
                    .addComponent(btnedit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(744, 514));
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

    private void cbjeniskelaminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbjeniskelaminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbjeniskelaminActionPerformed

    private void btntambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahActionPerformed
        aktif();
        idotomatis();
        
    }//GEN-LAST:event_btntambahActionPerformed

    private void tblpelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpelangganMouseClicked
        // TODO add your handling code here:
        selecdata();
        btnsimpan.setEnabled(false);
    }//GEN-LAST:event_tblpelangganMouseClicked

    private void btneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditActionPerformed
        // TODO add your handling code here:
        Ubahdata();
        GetData();
        
    }//GEN-LAST:event_btneditActionPerformed

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        // TODO add your handling code here:
        hapusdata();
        
    }//GEN-LAST:event_btnhapusActionPerformed

    private void txtnamapelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamapelangganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnamapelangganActionPerformed

    private void txtcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyTyped
        // TODO add your handling code here:
        Cari();
    }//GEN-LAST:event_txtcariKeyTyped

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtcariActionPerformed

    private void txtnohpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnohpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnohpActionPerformed

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
            java.util.logging.Logger.getLogger(frmPelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPelanggan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnedit;
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btnkeluar;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JButton btntambah;
    private javax.swing.JComboBox<String> cbjeniskelamin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblpelanggan;
    private javax.swing.JTextField txtalamat;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtidpelanggan;
    private javax.swing.JTextField txtnamapelanggan;
    private javax.swing.JTextField txtnohp;
    private com.toedter.calendar.JDateChooser txttanggallahir;
    private javax.swing.JTextField txttempatlahir;
    // End of variables declaration//GEN-END:variables

   
   
}
