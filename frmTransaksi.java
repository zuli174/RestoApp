/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Aplikasi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class frmTransaksi extends javax.swing.JFrame {

    private DefaultTableModel modelMENU;

    java.util.Date tglsekarang = new java.util.Date();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private String tanggal = format.format(tglsekarang);
    private String idpengguna = Session.getIdpengguna();
    private String namapengguna = Session.getNamaPengguna();
    private DefaultTableModel modelTRAN;
    String iddetail, idtransaksi, idmenu, namamenu, jumlah, harga, totalharga, idpelanggan, idmeja, totalbayar, bayar, kembali, nomor;

    public frmTransaksi() {
        initComponents();
        setLocationRelativeTo(null);
        txtpetugas.setText(namapengguna);
        lbtanggal.setText(tanggal);
        txtidpetugas.setText(idpengguna);
        Nonaktif();
        setJam();

        modelMENU = new DefaultTableModel();
        tblmenu.setModel(modelMENU);
        modelMENU.addColumn("Id Menu");
        modelMENU.addColumn("Nama Menu");
        modelMENU.addColumn("Kategori");
        modelMENU.addColumn("Harga");
        modelMENU.addColumn("Stok");
        GetData();
        combo_meja();

        modelTRAN = new DefaultTableModel();
        tbltransaksi.setModel(modelTRAN);
        modelTRAN.addColumn("Id Detail Transaksi");
        modelTRAN.addColumn("Id Transaksi");
        modelTRAN.addColumn("Nama Menu");
        modelTRAN.addColumn("Jumlah");
        modelTRAN.addColumn("Harga");
        modelTRAN.addColumn("Total Harga");
        GetDatadetailtransaksi();
    }

    public void GetData() {
  
        modelMENU.getDataVector().removeAllElements();
        modelMENU.fireTableDataChanged();
        try {
            Statement st = (Statement) Koneksi.koneksiDb().createStatement();
            String sql = "Select * from tbmenu where stok > 0";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Object[] obj = new Object[5];
                obj[0] = rs.getString("idmenu");
                obj[1] = rs.getString("namamenu");
                obj[2] = rs.getString("kategori");
                obj[3] = rs.getString("harga");
                obj[4] = rs.getString("stok");

                modelMENU.addRow(obj);

            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }

    public void GetDatadetailtransaksi() {
        modelTRAN.getDataVector().removeAllElements();
        modelTRAN.fireTableDataChanged();
        try {
            Statement stat = (Statement) Koneksi.koneksiDb().createStatement();
            String sql = "SELECT tbdetailtransaksi.iddetailtransaksi, "
                    + "tbdetailtransaksi.idtransaksi, "
                    + "tbmenu.namamenu, "
                    + "tbdetailtransaksi.jumlah, "
                    + "tbdetailtransaksi.harga, "
                    + "tbdetailtransaksi.totalharga "
                    + "FROM tbdetailtransaksi INNER JOIN tbmenu ON "
                    + "tbdetailtransaksi.idmenu = tbmenu.idmenu where tbdetailtransaksi.idtransaksi='" + idtransaksi + "'";
            ResultSet rs = stat.executeQuery(sql);

            while (rs.next()) {
                Object[] obj = new Object[5];
                //obj[0] = rs.getString("iddetailtransaksi");
                obj[0] = rs.getString("idtransaksi");
                obj[1] = rs.getString("namamenu");
                obj[2] = rs.getString("jumlah");
                obj[3] = rs.getString("harga");
                obj[4] = rs.getString("totalharga");

                modelTRAN.addRow(obj);
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }

    public void selctdatamenu() {
        int i = tblmenu.getSelectedRow();
        if (i == -1) {
            return;
        }
        txtidmenu.setText((String) modelMENU.getValueAt(i, 0));
        txtnamamenu.setText((String) modelMENU.getValueAt(i, 1));
        txtharga.setText((String) modelMENU.getValueAt(i, 3));
        txtstock.setText((String) modelMENU.getValueAt(i, 4));
        btnselesai.setEnabled(true);
    }

    public void cari_meja() {
        try {
            Connection c = Koneksi.koneksiDb();
            String sql_t = "select idmeja from tbmeja where nomormeja='" + cbnomeja.getSelectedItem() + "' and status = 'Kosong'";
            com.mysql.jdbc.Statement st = (com.mysql.jdbc.Statement) Koneksi.koneksiDb().createStatement();
            ResultSet rs = st.executeQuery(sql_t);

            while (rs.next()) {
                this.txtidmeja.setText(rs.getString("idmeja"));

            }
            rs.close();
            st.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void combo_meja() {
        try {
            Connection c = Koneksi.koneksiDb();
            java.sql.Statement st = c.createStatement();
            String sql_tc = "select idmeja,nomormeja,status from tbmeja where status = 'Kosong' order by idmeja asc";
//            String a = "Select * From tb meja where status = 'Kosong'"
            ResultSet rs = st.executeQuery(sql_tc);
//        Resulset rsa = st.executeQuery(a);

            while (rs.next()) {
                String nama = rs.getString("nomormeja");
                cbnomeja.addItem(nama);
            }
            rs.close();
            st.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loaddata() {
        iddetail = txtiddetail.getText();
        idtransaksi = txtidtransaksi.getText();
        idmenu = txtidmenu.getText();
        //namamenu = txtnamamenu.getText();
        jumlah = txtjumlah.getText();
        harga = txtharga.getText();
        totalharga = txthargatotal.getText();
    }

    public void loaddatatransaksi() {
        //iddetail = txtiddetail.getText();
        idtransaksi = txtidtransaksi.getText();
        tanggal = lbtanggal.getText();
        idpengguna = txtidpetugas.getText();
        idpelanggan = txtidpelanggan.getText();
        idmeja = txtidmeja.getText();
        totalbayar = txttotalbayar.getText();
        bayar = txtbayar.getText();
        kembali = txtkembali.getText();
    }

    public void Nonaktif() {
        lbtanggal.setEnabled(false);
        lbjam.setEnabled(false);
        txtpetugas.setEditable(false);
        txtidpelanggan.setEnabled(false);
        txtidmeja.setEnabled(false);
        txtnamamenu.setEnabled(false);
        txtharga.setEnabled(false);
        txtjumlah.setEnabled(false);
        txthargatotal.setEnabled(false);
        txttotalbayar.setEnabled(false);
        txtbayar.setEnabled(false);
        txtkembali.setEnabled(false);
        btncetak.setEnabled(false);
        btnkeluar.setEnabled(false);
        btnproses.setEnabled(false);
        btntambahmenu.setEnabled(false);
        btncarip.setEnabled(false);
        //txtiddetail.setEnabled(false);
    }

    public void aktif() {
        txtcarimenu.requestFocus();
        txtidpelanggan.setEnabled(true);
        txtidmeja.setEnabled(true);
        cbnomeja.setEnabled(true);
        txtnamamenu.setEnabled(true);
        txtnamamenu.setEnabled(true);
        txtjumlah.setEnabled(true);
        txthargatotal.setEnabled(true);
        btnselesai.setEnabled(true);
        btnproses.setEnabled(true);
        btntambah.setEnabled(true);
        //txtiddetail.setEnabled(true);
    }

    public void kosongform() {
        txtidmenu.setText("");
        txtnamamenu.setText("");
        txtjumlah.setText("");
        txtharga.setText("");
        txthargatotal.setText("");
        txtidpelanggan.setText("");
        cbnomeja.setSelectedItem("");
        txtidmeja.setText("");
        txtidtransaksi.setText("");
        txttotalbayar.setText("");
        txtbayar.setText("");
        txtkembali.setText("");
        //txtiddetail.setText("");

    }

    public void simpandata() {
        //loaddata();
        loaddatatransaksi();
        UpdateStock();

        try {
            Connection conn = Koneksi.koneksiDb();
            conn.setAutoCommit(false);

            // Insert into tbtransaksi
            String sqlTransaksi = "INSERT INTO tbtransaksi (idtransaksi, tanggal, idpengguna, idpelanggan, idmeja, totalbayar, bayar, kembali) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pTransaksi = conn.prepareStatement(sqlTransaksi);
            pTransaksi.setString(1, idtransaksi);
            pTransaksi.setString(2, tanggal);
            pTransaksi.setString(3, idpengguna);
            pTransaksi.setString(4, idpelanggan);
            pTransaksi.setString(5, idmeja);
            pTransaksi.setString(6, totalbayar);
            pTransaksi.setString(7, bayar);
            pTransaksi.setString(8, kembali);
            pTransaksi.executeUpdate();

            // Insert into tbdetailtransaksi
            /*String sqlDetailTransaksi = "INSERT INTO tbdetailtransaksi (iddetailtransaksi, idtransaksi, idmenu, namamenu, jumlah, harga, totalharga) "
                    + "VALUES (null, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pDetailTransaksi = conn.prepareStatement(sqlDetailTransaksi);
            for (int i = 0; i < tbltransaksi.getRowCount(); i++) {
                pDetailTransaksi.setString(1, iddetail);
                pDetailTransaksi.setString(2, (String) tbltransaksi.getValueAt(i, 1));
                pDetailTransaksi.setString(3, (String) tbltransaksi.getValueAt(i, 2));
                pDetailTransaksi.setString(4, (String) tbltransaksi.getValueAt(i, 3));
                pDetailTransaksi.setString(5, (String) tbltransaksi.getValueAt(i, 4));
                pDetailTransaksi.setString(6, (String) tbltransaksi.getValueAt(i, 5));
                pDetailTransaksi.setString(6, (String) tbltransaksi.getValueAt(i, 6));
                pDetailTransaksi.executeUpdate();
            }*/

            conn.commit();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Data gagal disimpan: " + ex.getMessage());
        }
    }
    public void simpandetail(){
        loaddata();
        try {
            Connection conn = Koneksi.koneksiDb();
            conn.setAutoCommit(false);
            
            String sqlDetailTransaksi = "INSERT INTO tbdetailtransaksi (iddetailtransaksi, idtransaksi, idmenu, jumlah, harga, totalharga) "
                    + "VALUES (null, ?, ?, ?, ?, ?)";
            PreparedStatement pDetailTransaksi = conn.prepareStatement(sqlDetailTransaksi);
            for (int i = 0; i < tbltransaksi.getRowCount(); i++) {
                pDetailTransaksi.setString(1, idtransaksi);
                pDetailTransaksi.setString(2, (String) tbltransaksi.getValueAt(i, 0)); // ID Menu
                pDetailTransaksi.setString(3, (String) tbltransaksi.getValueAt(i, 2)); // Jumlah
                pDetailTransaksi.setString(4, (String) tbltransaksi.getValueAt(i, 3)); // Harga
                pDetailTransaksi.setString(5, (String) tbltransaksi.getValueAt(i, 4)); // Total Harga
                pDetailTransaksi.executeUpdate();
            }
            conn.commit();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            GetDatadetailtransaksi();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Data gagal disimpan: " + ex.getMessage());
        }
            
    }

    public void idotomatis() {
        try {
            com.mysql.jdbc.Statement st = (com.mysql.jdbc.Statement) Koneksi.koneksiDb().createStatement();
            String sql = "select * from tbtransaksi order by idtransaksi desc";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                String id = rs.getString("idtransaksi").substring(3);
                String AN = "" + (Integer.parseInt(id) + 1);
                String nol = "";

                if (AN.length() == 1) {
                    nol = "000";
                } else if (AN.length() == 2) {
                    nol = "00";
                } else if (AN.length() == 3) {
                    nol = "";
                }
                txtidtransaksi.setText("TR" + nol + AN);
            } else {
                txtidtransaksi.setText("TR0001");
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
    public void iddetail() {
        try {
            com.mysql.jdbc.Statement st = (com.mysql.jdbc.Statement) Koneksi.koneksiDb().createStatement();
            String sql = "select * from tbdetailtransaksi order by iddetailtransaksi desc";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                String id = rs.getString("iddetailtransaksi").substring(3);
                String AN = "" + (Integer.parseInt(id) + 1);
                String nol = "";

                if (AN.length() == 1) {
                    nol = "000";
                } else if (AN.length() == 2) {
                    nol = "00";
                } else if (AN.length() == 3) {
                    nol = "";
                }
                txtiddetail.setText("DT" + nol + AN);
            } else {
                txtiddetail.setText("DT0001");
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }

    public void carimenu() {
        modelMENU.getDataVector().removeAllElements();
        modelMENU.fireTableDataChanged();
        try {
            java.sql.Connection con = Koneksi.koneksiDb();
            Statement st = con.createStatement();
            String sql = "Select * from tbmenu where namamenu like '%" + txtcarimenu.getText() + "%' and stok > 0";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Object[] ob = new Object[5];
                ob[0] = rs.getString(1);
                ob[1] = rs.getString(2);
                ob[2] = rs.getString(3);
                ob[3] = rs.getString(4);
                ob[4] = rs.getString(5);

                modelMENU.addRow(ob);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void selectidmeja() {
        String nomor = (String) cbnomeja.getSelectedItem();
        try {
            java.sql.Connection c = Koneksi.koneksiDb();
            java.sql.Statement st = c.createStatement();
            String sql_idm = "Select idmeja from tbmeja where nomormeja = '" + nomor + "'";
            ResultSet rs = st.executeQuery(sql_idm);

            if (rs.next()) {
                String idm = rs.getString("idmeja");
                txtidmeja.setText(idm);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public final void setJam() {
        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String nol_jam = "", nol_menit = "", nol_detik = "";
                java.util.Date dateTime = new java.util.Date();
                int nilai_jam = dateTime.getHours();
                int nilai_menit = dateTime.getMinutes();
                int nilai_detik = dateTime.getSeconds();
                if (nilai_jam <= 9) {
                    nol_jam = "0";
                }
                if (nilai_menit <= 9) {
                    nol_menit = "0";
                }
                if (nilai_detik <= 9) {
                    nol_detik = "0";
                }
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                lbjam.setText(jam + ":" + menit + ":" + detik + "");
            }
        };
        new Timer(1000, taskPerformer).start();
    }

    public void cetakstruk() {
        try {
            HashMap parameters = new HashMap();
            String a = txtidtransaksi.getText();
            parameters.put("idtransaksi", a);

            String namafile = "src/Aplikasi/struk.jasper";
            File Report = new File(namafile);
            JasperReport jreprt;
            jreprt = (JasperReport) JRLoader.loadObject(Report);
            JasperPrint jprint = JasperFillManager.fillReport(jreprt, parameters, Koneksi.koneksiDb());
            JasperViewer.viewReport(jprint, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Laporan Gagal" + e);
        }
    }

    public void Ubahdata() {
        loaddata();
        loaddatatransaksi();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date tanggal = new Date();
        String jual_tgl = dateFormat.format(tanggal);
        try {
            com.mysql.jdbc.Statement stat = (com.mysql.jdbc.Statement) Koneksi.koneksiDb().createStatement();
            String sql = "Update tbtransaksi set tanggal='" + jual_tgl + "',"
                    + "idpengguna='" + idpengguna + "',"
                    + "idpelanggan='" + idpelanggan + "',"
                    + "idmeja='" + idmeja + "',"
                    + "total='" + totalbayar + "',"
                    + "bayar='" + bayar + "',"
                    + "kembali='" + kembali + "' WHERE idtransaksi="
                    + idtransaksi + "'";
            System.out.println(sql);
            PreparedStatement p = (PreparedStatement) Koneksi.koneksiDb().prepareStatement(sql);
            p.executeUpdate();
            GetData();
            kosongform();
            JOptionPane.showMessageDialog(null, "Data berhasil di rubah");
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }

    public void totalbayar() {
        try {
            java.sql.Connection c = Koneksi.koneksiDb();
            java.sql.Statement st = c.createStatement();
            String sql_total = "SELECT SUM(hargatotal) AS hargatotal FROM tbdetailtransaksi WHERE idtransaksi = '" + idtransaksi + "'";
            ResultSet rs = st.executeQuery(sql_total);

            while (rs.next()) {
                String hargatotal = rs.getString("hargatotal");
                txttotalbayar.setText(hargatotal);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void UpdateStock() {
        int x, y, z;
        x = Integer.parseInt(txtstock.getText());
        y = Integer.parseInt(txtjumlah.getText());
        z = x - y;

        String Barang_ID = this.txtidmenu.getText();
        try
        {
            java.sql.Connection c = Koneksi.koneksiDb();
            String sql = "UPDATE tbmenu set stok = ? Where idmenu = ?";
            PreparedStatement p = (PreparedStatement) c.prepareStatement(sql);
            p.setInt(1,z);
            p.setString(2, Barang_ID);
            p.executeUpdate();
            p.close();
        } catch (SQLException e)
        {
            System.out.println("Terjadi Kesalahan");
        } finally
        {
            JOptionPane.showMessageDialog(this, "Stock barang telah diupdate");
}
}

    public void Update_meja() {
        try {
            Connection c = Koneksi.koneksiDb();
            java.sql.Statement st = c.createStatement();
            String mj = "UPDATE tbmeja set status = 'Terisi' whre idmeja = '" + idmeja + "'";
            PreparedStatement p = (PreparedStatement) Koneksi.koneksiDb().prepareStatement(mj);
            p.executeUpdate();
            GetData();
        } catch (SQLException e) {
            System.out.println("Terjadi Kesalahan");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtpetugas = new javax.swing.JTextField();
        txtidpetugas = new javax.swing.JTextField();
        lbtanggal = new javax.swing.JLabel();
        lbjam = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtidtransaksi = new javax.swing.JTextField();
        txtidpelanggan = new javax.swing.JTextField();
        txtidmeja = new javax.swing.JTextField();
        txttotalbayar = new javax.swing.JTextField();
        txtjumlah = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblmenu = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbltransaksi = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        txtidmenu = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btntambahmenu = new javax.swing.JButton();
        btncarip = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtcarimenu = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtnamamenu = new javax.swing.JTextField();
        txtharga = new javax.swing.JTextField();
        cbnomeja = new javax.swing.JComboBox<>();
        btnproses = new javax.swing.JButton();
        btnselesai = new javax.swing.JButton();
        txtstock = new javax.swing.JTextField();
        btntambahtransaksi1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtbayar = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtkembali = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txthargatotal = new javax.swing.JTextField();
        btncetak = new javax.swing.JButton();
        btnkeluar = new javax.swing.JButton();
        btntambah = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtnamapelanggan = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtiddetail = new javax.swing.JTextField();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable3);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(jTable4);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane6.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 204, 153));

        jLabel1.setFont(new java.awt.Font("Palatino Linotype", 1, 24)); // NOI18N
        jLabel1.setText("FORM TRANSAKSI");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("PETUGAS");

        lbtanggal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbtanggal.setText("Tanggal");

        lbjam.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbjam.setText("Jam");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtidpetugas)
                    .addComponent(jLabel19)
                    .addComponent(txtpetugas, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lbtanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbjam, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtpetugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtidpetugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbtanggal)
                    .addComponent(lbjam))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Id Transaksi");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Id Pelanggan");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Id Meja");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Nama Menu");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Total Bayar");

        txtidtransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidtransaksiActionPerformed(evt);
            }
        });

        txtidpelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidpelangganActionPerformed(evt);
            }
        });

        txtidmeja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidmejaActionPerformed(evt);
            }
        });

        txttotalbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttotalbayarActionPerformed(evt);
            }
        });

        txtjumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtjumlahActionPerformed(evt);
            }
        });
        txtjumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtjumlahKeyReleased(evt);
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

        tbltransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tbltransaksi);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Id Menu");

        txtidmenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidmenuActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("No Meja");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Jumlah");

        btntambahmenu.setText("+");
        btntambahmenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntambahmenuActionPerformed(evt);
            }
        });

        btncarip.setText("Cari Pelanggan");
        btncarip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncaripActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Cari Menu");

        txtcarimenu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcarimenuKeyTyped(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Harga");

        txtnamamenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamamenuActionPerformed(evt);
            }
        });

        cbnomeja.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "=== Pilih No Meja ===", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" }));
        cbnomeja.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbnomejaItemStateChanged(evt);
            }
        });
        cbnomeja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbnomejaActionPerformed(evt);
            }
        });

        btnproses.setText("proses");
        btnproses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprosesActionPerformed(evt);
            }
        });

        btnselesai.setText("Selesai");
        btnselesai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnselesaiActionPerformed(evt);
            }
        });

        txtstock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtstockActionPerformed(evt);
            }
        });

        btntambahtransaksi1.setText("-");
        btntambahtransaksi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntambahtransaksi1ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Bayar");

        txtbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbayarActionPerformed(evt);
            }
        });
        txtbayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbayarKeyReleased(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Kembali");

        txtkembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkembaliActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("Harga Total");

        txthargatotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txthargatotalActionPerformed(evt);
            }
        });

        btncetak.setText("Cetak");
        btncetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncetakActionPerformed(evt);
            }
        });

        btnkeluar.setText("Keluar");
        btnkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkeluarActionPerformed(evt);
            }
        });

        btntambah.setText("Tambah transaksi");
        btntambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntambahActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Nama Pelanggan ");

        txtnamapelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamapelangganActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Id Detail");

        txtiddetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtiddetailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtidpelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtnamamenu, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                            .addComponent(txtjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txthargatotal, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtharga))))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btntambahmenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btntambahtransaksi1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnproses, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnselesai, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txttotalbayar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtbayar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtkembali, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtstock, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtidmenu, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(49, 49, 49)
                                        .addComponent(txtcarimenu, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(58, 58, 58)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtidtransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtiddetail, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btncarip, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(60, 60, 60)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtnamapelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtidmeja, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbnomeja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 783, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(btncetak, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(btnkeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(btntambah, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtiddetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtidtransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)
                        .addComponent(txtidmenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btncarip)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtidpelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(cbnomeja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtcarimenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(txtidmeja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtnamapelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtnamamenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btntambahmenu)
                    .addComponent(btnproses)
                    .addComponent(jLabel10)
                    .addComponent(txttotalbayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(btntambahtransaksi1)
                    .addComponent(btnselesai)
                    .addComponent(jLabel11)
                    .addComponent(txtbayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtkembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txthargatotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtstock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btntambah)
                    .addComponent(btnkeluar)
                    .addComponent(btncetak))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txttotalbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttotalbayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttotalbayarActionPerformed

    private void txtjumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtjumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtjumlahActionPerformed

    private void txtidmenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidmenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidmenuActionPerformed

    private void txtnamamenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamamenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnamamenuActionPerformed

    private void tblmenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblmenuMouseClicked
        // TODO add your handling code here:
        selctdatamenu();
        txtjumlah.setEnabled(true);
        btnproses.setEnabled(true);
        txtstock.setEnabled(false);
        btnselesai.setEnabled(true);
    }//GEN-LAST:event_tblmenuMouseClicked

    private void btncaripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncaripActionPerformed
        // TODO add your handling code here:
        new ftmDataPelanggan().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btncaripActionPerformed

    private void cbnomejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbnomejaActionPerformed
        // TODO add your handling code here:
        cari_meja();
        combo_meja();
    }//GEN-LAST:event_cbnomejaActionPerformed

    private void btnprosesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprosesActionPerformed

        simpandata();
        simpandetail();
        GetDatadetailtransaksi(); // Menampilkan data terbaru ke tabel transaksi
        UpdateStock();
        Update_meja();

        btntambahmenu.setEnabled(true);
        btnkeluar.setEnabled(true);
        btnselesai.setEnabled(true);
    }//GEN-LAST:event_btnprosesActionPerformed

    private void btntambahmenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahmenuActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_btntambahmenuActionPerformed

    private void btnselesaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnselesaiActionPerformed
        // TODO add your handling code here:
        int total = 0;
        for (int i = 0; i < tbltransaksi.getRowCount(); i++) {
            int amount = Integer.parseInt((String) tbltransaksi.getValueAt(i, 5));
            total += amount;
        }
        txttotalbayar.setText("" + total);
        txtbayar.setEnabled(true);
        txtbayar.setText("");
        txtbayar.requestFocus();
        btncetak.setEnabled(true);
    }//GEN-LAST:event_btnselesaiActionPerformed

    private void txtstockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtstockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtstockActionPerformed

    private void btntambahtransaksi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahtransaksi1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btntambahtransaksi1ActionPerformed

    private void txtbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbayarActionPerformed

    private void txtkembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkembaliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkembaliActionPerformed

    private void txthargatotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txthargatotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txthargatotalActionPerformed

    private void btncetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncetakActionPerformed
        // TODO add your handling code here:
        Ubahdata();
        Nonaktif();
        cetakstruk();

        cbnomeja.setEnabled(false);
        cbnomeja.setSelectedIndex(0);
        kosongform();

    }//GEN-LAST:event_btncetakActionPerformed

    private void btnkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkeluarActionPerformed
        // TODO add your handling code here:
        new FrmMenuUtamaApk().setVisible(true);
        this.setVisible(false);

    }//GEN-LAST:event_btnkeluarActionPerformed

    private void btntambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahActionPerformed
        // TODO add your handling code here:
        btncarip.setEnabled(true);
        idotomatis();
        iddetail();
        cbnomeja.setEnabled(true);
        cbnomeja.setSelectedIndex(0);

    }//GEN-LAST:event_btntambahActionPerformed

    private void cbnomejaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbnomejaItemStateChanged
        // TODO add your handling code here:
        selectidmeja();
    }//GEN-LAST:event_cbnomejaItemStateChanged

    private void txtcarimenuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcarimenuKeyTyped
        // TODO add your handling code here:
        carimenu();
    }//GEN-LAST:event_txtcarimenuKeyTyped

    private void txtjumlahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtjumlahKeyReleased
        // TODO add your handling code here:
        try {
            int harga, jumlah, totalharga;
            harga = Integer.parseInt(txtharga.getText());
            jumlah = Integer.parseInt(txtjumlah.getText());
            totalharga = jumlah * harga;
            txthargatotal.setText("" + totalharga);
            txttotalbayar.setText("" + totalharga);
        } catch (Exception e) {

        }
        txtbayar.setEnabled(true);
    }//GEN-LAST:event_txtjumlahKeyReleased

    private void txtidtransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidtransaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidtransaksiActionPerformed

    private void txtidpelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidpelangganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidpelangganActionPerformed

    private void txtidmejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidmejaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidmejaActionPerformed

    private void txtbayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbayarKeyReleased
        // TODO add your handling code here:
        try {
            int totalbayar, bayar, kembalian;
            totalbayar = Integer.parseInt(txttotalbayar.getText());
            bayar = Integer.parseInt(txtbayar.getText());
            kembalian = bayar - totalbayar;
            txtkembali.setText("" + kembalian);
        } catch (Exception e) {

        }
    }//GEN-LAST:event_txtbayarKeyReleased

    private void txtnamapelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamapelangganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnamapelangganActionPerformed

    private void txtiddetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtiddetailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtiddetailActionPerformed

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
            java.util.logging.Logger.getLogger(frmTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btncarip;
    private javax.swing.JButton btncetak;
    private javax.swing.JButton btnkeluar;
    private javax.swing.JButton btnproses;
    private javax.swing.JButton btnselesai;
    private javax.swing.JButton btntambah;
    private javax.swing.JButton btntambahmenu;
    private javax.swing.JButton btntambahtransaksi1;
    public javax.swing.JComboBox<String> cbnomeja;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lbjam;
    private javax.swing.JLabel lbtanggal;
    public javax.swing.JTable tblmenu;
    private javax.swing.JTable tbltransaksi;
    private javax.swing.JTextField txtbayar;
    private javax.swing.JTextField txtcarimenu;
    private javax.swing.JTextField txtharga;
    public javax.swing.JTextField txthargatotal;
    private javax.swing.JTextField txtiddetail;
    private javax.swing.JTextField txtidmeja;
    private javax.swing.JTextField txtidmenu;
    public javax.swing.JTextField txtidpelanggan;
    private javax.swing.JTextField txtidpetugas;
    private javax.swing.JTextField txtidtransaksi;
    public javax.swing.JTextField txtjumlah;
    private javax.swing.JTextField txtkembali;
    private javax.swing.JTextField txtnamamenu;
    public javax.swing.JTextField txtnamapelanggan;
    private javax.swing.JTextField txtpetugas;
    private javax.swing.JTextField txtstock;
    private javax.swing.JTextField txttotalbayar;
    // End of variables declaration//GEN-END:variables

}
