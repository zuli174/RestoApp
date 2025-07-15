
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


public class frmPegawai extends javax.swing.JFrame {
private DefaultTableModel model;
//variabel
String idpegawai,namapegawai,jeniskelamin,tempatlahir,tanggallahir,alamat,nohp,jenispekerjaan;
    public frmPegawai() {
        initComponents();
        setLocationRelativeTo(null);
        
        //ini adalah untuk membuat judul dalam tabel  (3)
        model = new DefaultTableModel();
        tblpegawai.setModel(model);
        model.addColumn("Id Pegawai");
        model.addColumn("Nama Pegawai");
        model.addColumn("Jenis Kelamin");
        model.addColumn("Tempat Lahir");
        model.addColumn("Tanggal Lahir");
        model.addColumn("Alamat");
        model.addColumn("NO HP");
        model.addColumn("Jenis Pekerjaan");
        
        
        GetData(); //untuk memanggil data ke dalam tabel
        nonaktif();  // agar pada tampilan pertama idnya muncul secara otomatis
    }
    
    //mengubah
   
    
     public void GetData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            
            Statement st = (Statement)Koneksi.koneksiDb().createStatement();
            String sql ="Select * from tbpegawai";
            ResultSet rs=st.executeQuery(sql);
            
            while (rs.next()){
            
                Object[] obj = new Object[8];
                obj[0]=rs.getString("idpegawai");
                obj[1]=rs.getString("namapegawai");
                obj[2]=rs.getString("jk");
                obj[3]=rs.getString("tempatlahir");
                obj[4]=rs.getString("tanggallahir");
                obj[5]=rs.getString("alamat");
                obj[6]=rs.getString("nohp");
                obj[7]=rs.getString("jenispekerjaan");
            
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
            String sql = "UPDATE tbpegawai SET " + "namapegawai='" + namapegawai + "', " + "jk='" + jeniskelamin + "', " 
                    + "tempatlahir='" + tempatlahir + "', " + "tanggallahir='" + tanggallahir + "', " + "alamat='" + alamat + "', "
                    + "nohp='" + nohp + "', " + "jenispekerjaan='" + jenispekerjaan + "'" + "WHERE idpegawai='" + idpegawai + "'";

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
         int pesan=JOptionPane.showConfirmDialog(this, "Anda Yakin Ingin Menghapus Data Pegawai"+idpegawai+"?","konfirmasi",JOptionPane.OK_CANCEL_OPTION);
            try {
                com.mysql.jdbc.Statement st=(com.mysql.jdbc.Statement) Koneksi.koneksiDb().createStatement();
                String sql="DELETE from tbpegawai Where idpegawai='"+idpegawai+"'";
                PreparedStatement p=(PreparedStatement) Koneksi.koneksiDb().prepareStatement(sql);
                p.executeUpdate();
                GetData();
                
                JOptionPane.showMessageDialog(null, "Data Berhasil dihapus");
             
            }catch (SQLException err) {
                JOptionPane.showMessageDialog(null, err.getMessage());
            }
     }
     
     
    public void kosongkan (){
     txtidpegawai.setText("");
     txtnamapegawai.setText("");
     cbjeniskelamin.setSelectedItem("==PILIH==");
     txttempatlahir.setText("");
     txttanggallahir.setDateFormatString("yy-MM-dd");
     txtalamat.setText("");
     txtnohp.setText("");
     cbjp.setSelectedItem("===PILIH===");
    }
    
    public void nonaktif(){
        txtidpegawai.setEnabled(false);
        txtnamapegawai.setEnabled(false);
        cbjeniskelamin.setEnabled(false);
        txttempatlahir.setEnabled(false);
        txttanggallahir.setEnabled(false);
        txtalamat.setEnabled(false);
        txtnohp.setEnabled(false);
        btnsimpan.setEnabled(false);
        cbjp.setEnabled(false);
        btnedit.setEnabled(false);
        btnhapus.setEnabled(false);
    }
    
    public void aktif(){
        txtnamapegawai.setEnabled(true);
        txtnamapegawai.requestFocus();
        cbjeniskelamin.setEnabled(true);
        txttempatlahir.setEnabled(true);
        txttanggallahir.setEnabled(true);
        txtalamat.setEnabled(true);
        txtnohp.setEnabled(true);
        cbjp.setEnabled(true);
        btnsimpan.setEnabled(true);
        btnedit.setEnabled(true);
        btnhapus.setEnabled(true);
        btnkeluar.setEnabled(true);
    }
    //select
    public void selecdata()
    {
        int i = tblpegawai.getSelectedRow();
        if (i == -1)
        {
            //tidak ada baris yang terpilih
            return;
        }txtidpegawai.setText(""+model.getValueAt(i, 0));
        txtnamapegawai.setText(""+model.getValueAt(i, 1));
        cbjeniskelamin.setSelectedItem(""+model.getValueAt(i, 2));
        txttempatlahir.setText(""+model.getValueAt(i, 3));
        String s=(String)tblpegawai.getModel().getValueAt(i, 4);
        try{
            SimpleDateFormat f= new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date d=f.parse(s);
        
        txtalamat.setText(""+model.getValueAt(i, 5));
        txtnohp.setText(""+model.getValueAt(i, 6));
        cbjp.setSelectedItem(""+model.getValueAt(i, 7));
        btnhapus.setEnabled(true);
        btnedit.setEnabled(true);
        
        aktif();
        }catch(Exception ex) {
            JOptionPane.showMessageDialog(null,"gagal");
        }
    }
    
    //loaddata
    public void loaddata(){
        idpegawai=txtidpegawai.getText();
        namapegawai=txtnamapegawai.getText();
        jeniskelamin=(String)cbjeniskelamin.getSelectedItem();
        tempatlahir=txttempatlahir.getText();
        alamat=txtalamat.getText();
        nohp=txtnohp.getText();
        jenispekerjaan=(String)cbjp.getSelectedItem();
        
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
            String sql = "insert into tbpegawai (idpegawai,namapegawai,jk,tempatlahir,tanggallahir,alamat,nohp,jenispekerjaan)" + "values" + ""
                    + "('" + idpegawai + "'," + "'" + namapegawai + "','" + jeniskelamin + "','" + tempatlahir + "','" + tanggallahir + "','" + alamat + "','" + nohp + "','" +jenispekerjaan +"')";

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
       String sql="select * from tbpegawai order by idpegawai desc";
       ResultSet rs=st.executeQuery(sql);
       if(rs.next()){
           String id=rs.getString("idpegawai").substring(3);
           String AN=""+(Integer.parseInt(id)+1);
           String nol="";

           if(AN.length()==1)
           {nol="000";}
           else if(AN.length()==2)
           {nol="00";}
           else if(AN.length()==3)
           {nol="";}
               txtidpegawai.setText("P"+nol+AN);
           }else{
               txtidpegawai.setText("P0001");
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
            String sql = "select * from tbpegawai where namapegawai like '%" + txtcari.getText() + "%'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Object[] ob = new Object[8];
                ob[0] = rs.getString(1);
                ob[1] = rs.getString(2);
                ob[2] = rs.getString(3);
                ob[3] = rs.getString(4);
                ob[4] = rs.getString(5);
                ob[5] = rs.getString(6);
                ob[6] = rs.getString(7);
                ob[7] = rs.getString(8);
                
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
        txtidpegawai = new javax.swing.JTextField();
        txtnamapegawai = new javax.swing.JTextField();
        txttempatlahir = new javax.swing.JTextField();
        btntambah = new javax.swing.JButton();
        btnsimpan = new javax.swing.JButton();
        btnedit = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        btnkeluar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpegawai = new javax.swing.JTable();
        cbjeniskelamin = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtalamat = new javax.swing.JTextField();
        txtnohp = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();
        txttanggallahir = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        cbjp = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 204, 153));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Palatino Linotype", 1, 24)); // NOI18N
        jLabel1.setText("FORM PEGAWAI");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(287, 287, 287))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Id Pegawai");

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Nama Pegawai");

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Jenis Kelamin");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Tempat Lahir");

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Tanggal Lahir");

        txtnamapegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamapegawaiActionPerformed(evt);
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

        tblpegawai.setModel(new javax.swing.table.DefaultTableModel(
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
        tblpegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblpegawaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblpegawai);

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

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("CARI DATA PEGAWAI");

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

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Jenis Pekerjaan");

        cbjp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "=== Pilih ===", "admin", "kasir", "manager" }));
        cbjp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbjpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(47, 47, 47)
                                        .addComponent(txtidpegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel6)
                                    .addComponent(txttanggallahir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel3))
                                        .addGap(27, 27, 27)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtnamapegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbjeniskelamin, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(54, 54, 54)
                                .addComponent(jLabel10))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(280, 280, 280)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7))
                                .addGap(74, 74, 74)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtalamat, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtnohp, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(35, 35, 35)
                                        .addComponent(txttempatlahir, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(35, 35, 35)
                                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(59, 59, 59)
                                .addComponent(cbjp, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 161, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(btntambah)
                .addGap(48, 48, 48)
                .addComponent(btnsimpan)
                .addGap(41, 41, 41)
                .addComponent(btnedit, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(btnhapus)
                .addGap(54, 54, 54)
                .addComponent(btnkeluar)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtidpegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtnamapegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cbjeniskelamin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txttempatlahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txttanggallahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtnohp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtalamat, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(cbjp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btntambah)
                    .addComponent(btnsimpan)
                    .addComponent(btnedit)
                    .addComponent(btnhapus)
                    .addComponent(btnkeluar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(805, 499));
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

    private void tblpegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpegawaiMouseClicked
        // TODO add your handling code here:
        selecdata();
        btnsimpan.setEnabled(false);
    }//GEN-LAST:event_tblpegawaiMouseClicked

    private void btneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditActionPerformed
        // TODO add your handling code here:
        Ubahdata();
        GetData();
        
    }//GEN-LAST:event_btneditActionPerformed

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        // TODO add your handling code here:
        hapusdata();
        
    }//GEN-LAST:event_btnhapusActionPerformed

    private void txtnamapegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamapegawaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnamapegawaiActionPerformed

    private void txtcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyTyped
        // TODO add your handling code here:
        Cari();
    }//GEN-LAST:event_txtcariKeyTyped

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtcariActionPerformed

    private void cbjpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbjpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbjpActionPerformed

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
            java.util.logging.Logger.getLogger(frmPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPegawai().setVisible(true);
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
    private javax.swing.JComboBox<String> cbjp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JTable tblpegawai;
    private javax.swing.JTextField txtalamat;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtidpegawai;
    private javax.swing.JTextField txtnamapegawai;
    private javax.swing.JTextField txtnohp;
    private com.toedter.calendar.JDateChooser txttanggallahir;
    private javax.swing.JTextField txttempatlahir;
    // End of variables declaration//GEN-END:variables

   
   
}
