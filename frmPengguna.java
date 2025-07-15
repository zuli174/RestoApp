package Aplikasi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.lang.Exception;

public class frmPengguna extends javax.swing.JFrame {

    private DefaultTableModel model;
    private DefaultTableModel modelp;

    String idpengguna, idpegawai, namapengguna, level, username, password;

    public frmPengguna() {
        initComponents();
        setLocationRelativeTo(null);

        model = new DefaultTableModel();
        tblpengguna.setModel(model);
        model.addColumn("Id Pengguna");
        model.addColumn("Id Pegawai");
        model.addColumn("Nama Pegawai");
        model.addColumn("Username");
        model.addColumn("Password");
        model.addColumn("Level");
        
        modelp = new DefaultTableModel();
        tblpegawai.setModel(modelp);
        modelp.addColumn("Id Pegawai");
        modelp.addColumn("Nama Pegawai");
        modelp.addColumn("Jenis Pekerjaan");

        GetData();
        nonaktif();
    }
// Langkah kedua menampilkan data kedalam tabel

    public void GetData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try {
            Statement st = (Statement) Koneksi.koneksiDb().createStatement();
            String sql = "Select * from tbpengguna";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Object[] obj = new Object[6];
                obj[0] = rs.getString("idpengguna");
                obj[1] = rs.getString("idpegawai");
                obj[2] = rs.getString("namapengguna");
                obj[3] = rs.getString("username");
                obj[4] = rs.getString("password");
                obj[5] = rs.getString("level");

                model.addRow(obj);

            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
        
        
        
        
        modelp.getDataVector().removeAllElements();
        modelp.fireTableDataChanged();
        try {
            Statement st = (Statement) Koneksi.koneksiDb().createStatement();
            String sql = "Select * from tbpegawai";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Object[] objp = new Object[8];
                objp[0] = rs.getString("idpegawai");
                objp[1] = rs.getString("namapegawai");
                objp[2] = rs.getString("jenispekerjaan");
                modelp.addRow(objp);

            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
    public void selectpegawai (){
        int p = tblpegawai.getSelectedRow();
        
        if (p == -1)
        {
            return;
        }
        txtidpegawai.setText ("" + modelp.getValueAt(p, 0));
        
        aktif();
        
    }
    
    public void cari() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try
        {
            Connection con = Koneksi.koneksiDb();
            Statement st = con.createStatement();
            String sql = "Select * from tbpengguna where namapengguna like '%" + txtcari.getText() + "%'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
                Object[] ob = new Object[6];
                ob[0] = rs.getString(1);
                ob[1] = rs.getString(2);
                ob[2] = rs.getString(3);
                ob[3] = rs.getString(4);
                ob[4] = rs.getString(5);
                ob[5] = rs.getString(6);

                model.addRow(ob);
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void carip() {
        modelp.getDataVector().removeAllElements();
        modelp.fireTableDataChanged();
        try
        {
            Connection con = Koneksi.koneksiDb();
            Statement st = con.createStatement();
            String sql = "Select * from tbpegawai where namapegawai like '%" + txtcarip.getText() + "%'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
                Object[] obp = new Object[8];
                obp[0] = rs.getString("idpegawai");
                obp[1] = rs.getString("namapegawai");
                obp[2] = rs.getString("jk");
                obp[3] = rs.getString("tempatlahir");
                obp[4] = rs.getString("tanggallahir");
                obp[5] = rs.getString("alamat");
                obp[6] = rs.getString("nohp");
                obp[7] = rs.getString("jenispekerjaan");

                modelp.addRow(obp);
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void kosongkan() {
        txtidpengguna.setText("");
        txtidpegawai.setText("");
        txtnamapengguna.setText("");
        cblevel.setSelectedItem("====PILIH====");
        txtusername.setText("");
        txtpass.setText("");
    }

    public void nonaktif() {
        tblpegawai.setEnabled(false);
        
        txtidpengguna.setEnabled(false);
        txtidpegawai.setEnabled(false);
        txtnamapengguna.setEnabled(false);
        cblevel.setEnabled(false);
        txtusername.setEnabled(false);
        txtpass.setEnabled(false);
        btnsimpan.setEnabled(false);
        btnedit.setEnabled(false);
        btnhapus.setEnabled(false);
    }

    public void aktif() {
        tblpegawai.setEnabled(true);
        txtnamapengguna.setEnabled(true);
        txtnamapengguna.requestFocus();
        cblevel.setEnabled(true);
        txtusername.setEnabled(true);
        txtpass.setEnabled(true);
        btnsimpan.setEnabled(true);
    }

    public void selectdata() {

        int i = tblpengguna.getSelectedRow();
        if (i == -1) {
            // tidak ada baris yang terpilih
            return;
        }
        txtidpengguna.setText("" + model.getValueAt(i, 0));
        txtidpegawai.setText("" + model.getValueAt(i, 1));
        txtnamapengguna.setText("" + model.getValueAt(i, 2));
        txtusername.setText("" + model.getValueAt(i, 3));
        txtpass.setText("" + model.getValueAt(i, 4));
        cblevel.setSelectedItem("" + model.getValueAt(i, 5));
        btnhapus.setEnabled(true);
        btnedit.setEnabled(true);
    }

    public void loaddata() {
        idpengguna = txtidpengguna.getText();
        idpegawai = txtidpegawai.getText();
        namapengguna = txtnamapengguna.getText();
        level = (String) cblevel.getSelectedItem();
        username = txtusername.getText();
        password = txtpass.getText();
    }

    public void simpandata() {

        loaddata();
        try {
            com.mysql.jdbc.Statement st = (com.mysql.jdbc.Statement) Koneksi.koneksiDb().createStatement();
            String sql = "insert into tbpengguna (idpengguna,idpegawai,namapengguna,username,password,level)" + "values" + "('" + idpengguna + "'," + "'" + idpegawai + "','" + namapengguna + "','" + username + "','" + password + "','" + level + "')";
            PreparedStatement p = (PreparedStatement) Koneksi.koneksiDb().prepareStatement(sql);
            p.executeUpdate();
            GetData();
            nonaktif();
            kosongkan();
            JOptionPane.showMessageDialog(null, "Data berhasil di simpan");

        } catch (SQLException ex) {
            Logger.getLogger(frmPengguna.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ubahdata() {
        loaddata();
        try {
            com.mysql.jdbc.Statement st = (com.mysql.jdbc.Statement) Koneksi.koneksiDb().createStatement();
            String sql = "Update tbpengguna set idpegawai='" + idpegawai + "'," + "namapengguna='" + namapengguna + "'," + "username='" + username + "'," + "password='" + password + "'," + "level='" + level + "' WHERE idpengguna='" + idpengguna + "'";

            PreparedStatement p = (PreparedStatement) Koneksi.koneksiDb().prepareStatement(sql);
            p.executeUpdate();
            GetData();
            kosongkan();
            selectdata();

            JOptionPane.showMessageDialog(null, "Data berhasil di rubah");
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }

    public void hapusdata() {
        loaddata();
        int pesan = JOptionPane.showConfirmDialog(null, "anda yakin ingin menghapus data menu" + idpengguna + "?", "konfirmasi", JOptionPane.OK_OPTION);
        if (pesan == JOptionPane.OK_OPTION) {
            try {
                com.mysql.jdbc.Statement stat = (com.mysql.jdbc.Statement) Koneksi.koneksiDb().createStatement();
                String sql = "DELETE from tbpengguna where idpengguna='" + idpengguna + "'";
                PreparedStatement p = (PreparedStatement) Koneksi.koneksiDb().prepareStatement(sql);
                p.executeUpdate();
                GetData();

                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
            } catch (SQLException err) {
                JOptionPane.showConfirmDialog(null, err.getMessage());
            }
        }
    }

    public void idotomatis() {
        try {
            com.mysql.jdbc.Statement st = (com.mysql.jdbc.Statement) Koneksi.koneksiDb().createStatement();
            String sql = "select * from tbpengguna order by idpengguna desc";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                String id = rs.getString("idpengguna").substring(3);
                String AN = "" + (Integer.parseInt(id) + 1);
                String nol = "";

                if (AN.length() == 1) {
                    nol = "000";
                } else if (AN.length() == 2) {
                    nol = "00";
                } else if (AN.length() == 3) {
                    nol = "";
                }
                txtidpengguna.setText("PN" + nol + AN);
            } else {
                txtidpengguna.setText("PN0001");
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtidpengguna = new javax.swing.JTextField();
        txtidpegawai = new javax.swing.JTextField();
        txtnamapengguna = new javax.swing.JTextField();
        btntambah = new javax.swing.JButton();
        btnsimpan = new javax.swing.JButton();
        btnedit = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        btnkeluar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpengguna = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtcarip = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cblevel = new javax.swing.JComboBox<>();
        txtpass = new javax.swing.JPasswordField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblpegawai = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtusername = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 204, 153));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Palatino Linotype", 1, 24)); // NOI18N
        jLabel1.setText("FORM PENGGUNA");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(214, 214, 214))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(22, 22, 22))
        );

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Id Pngguna");

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Id Pegawai");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Nama Pengguna");

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Password");

        txtidpegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidpegawaiActionPerformed(evt);
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

        tblpengguna.setModel(new javax.swing.table.DefaultTableModel(
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
        tblpengguna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblpenggunaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblpengguna);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("CARI DATA PEGAWAI");

        txtcarip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcaripActionPerformed(evt);
            }
        });
        txtcarip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcaripKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Level");

        cblevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "=== Pilih ===", "Admin", "Kasir", "Manager" }));
        cblevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cblevelActionPerformed(evt);
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
        tblpegawai.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                tblpegawaiComponentShown(evt);
            }
        });
        jScrollPane2.setViewportView(tblpegawai);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Username");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Cari");

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
                .addGap(54, 54, 54)
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtnamapengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(txtusername, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtidpengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtidpegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cblevel, 0, 144, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtpass)
                                        .addGap(42, 42, 42))
                                    .addComponent(txtcari))))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(txtcarip, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 57, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel9)
                        .addComponent(txtcarip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtidpengguna, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtidpegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtnamapengguna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtusername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtpass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(cblevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btntambah)
                    .addComponent(btnsimpan)
                    .addComponent(btnedit)
                    .addComponent(btnhapus)
                    .addComponent(btnkeluar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );

        setSize(new java.awt.Dimension(701, 513));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsimpanActionPerformed
        // TODO add your handling code here:
        simpandata();
    }//GEN-LAST:event_btnsimpanActionPerformed

    private void btnkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkeluarActionPerformed
        // TODO add your handling code here
            new FrmMenuUtamaApk().setVisible(true);
            this.setVisible(false);
    }//GEN-LAST:event_btnkeluarActionPerformed

    private void btntambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahActionPerformed
        kosongkan();
        idotomatis();
        tblpegawai.setEnabled(true);
        btntambah.setEnabled(false);
    }//GEN-LAST:event_btntambahActionPerformed

    private void tblpenggunaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpenggunaMouseClicked
       selectdata();
        aktif();
        txtidpengguna.setEnabled(false);
        btnsimpan.setEnabled(false);
        btntambah.setEnabled(false);
    }//GEN-LAST:event_tblpenggunaMouseClicked

    private void btneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditActionPerformed
        // TODO add your handling code here:
        ubahdata();
        idotomatis();
        txtidpegawai.setEnabled(false);
        cblevel.setEnabled(false);
        txtusername.setEnabled(false);
        txtpass.setEnabled(false);
        btntambah.setEnabled(true);
        btnedit.setEnabled(false);
        btnhapus.setEnabled(false);
    }//GEN-LAST:event_btneditActionPerformed

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        // TODO add your handling code here:
        hapusdata();
        idotomatis();
        txtidpegawai.setEnabled(false);
        cblevel.setEnabled(false);
        txtusername.setEnabled(false);
        txtpass.setEnabled(false);
        btntambah.setEnabled(true);
        btnedit.setEnabled(false);
        btnhapus.setEnabled(false);
        
    }//GEN-LAST:event_btnhapusActionPerformed

    private void txtidpegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidpegawaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidpegawaiActionPerformed

    private void txtcaripKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcaripKeyTyped
        // TODO add your handling code here:
        carip(); 
    }//GEN-LAST:event_txtcaripKeyTyped

    private void txtcaripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcaripActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtcaripActionPerformed

    private void cblevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cblevelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cblevelActionPerformed

    private void tblpegawaiComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tblpegawaiComponentShown
        // TODO add your handling code here:
       
    }//GEN-LAST:event_tblpegawaiComponentShown

    private void tblpegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpegawaiMouseClicked
        selectpegawai();
    }//GEN-LAST:event_tblpegawaiMouseClicked

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariActionPerformed

    private void txtcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyTyped
        // TODO add your handling code here:
        cari(); 
    }//GEN-LAST:event_txtcariKeyTyped

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
            java.util.logging.Logger.getLogger(frmPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPengguna().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnedit;
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btnkeluar;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JButton btntambah;
    private javax.swing.JComboBox<String> cblevel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblpegawai;
    private javax.swing.JTable tblpengguna;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtcarip;
    private javax.swing.JTextField txtidpegawai;
    private javax.swing.JTextField txtidpengguna;
    private javax.swing.JTextField txtnamapengguna;
    private javax.swing.JPasswordField txtpass;
    private javax.swing.JTextField txtusername;
    // End of variables declaration//GEN-END:variables

   
   
}
