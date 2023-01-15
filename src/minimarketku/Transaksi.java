package minimarketku;
import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import minimarketku.login;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

//import java.io.InputStream;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;

 /*
 * @author ASUS
 */
public class Transaksi extends javax.swing.JFrame {
    public Statement st;
    public ResultSet rs;
    public DefaultTableModel tabmodel;
    public DefaultTableModel tabmodel2;
    public int stok;

    Connection cn = koneksi.config.Conn();
    JasperReport JasRep;
    JasperPrint JasPri;
    Map param = new HashMap();
    JasperDesign JasDes;
    
   
    /**
     * Creates new form Transaksi
     */
    public Transaksi() {
        initComponents();
        tampil();
        tampiltmp();
        
    }
    
    private void reset(){
         tagihan.setText("");
        nama.setText("");
        harga.setText("");
        jumlah.setText("");
        subtotal.setText("");
        bayar.setText("");
        kembalian.setText("");
    }
    
    public void tampil(){
        try{
            Object[] baris = {"Kode barang", "Nama", "harga", "stok", "Kategori"};
            tabmodel = new DefaultTableModel(null, baris){
                  public boolean isCellEditable(int row, int column){
                return false;
                }
            };
            
            st = cn.createStatement();
            rs = st.executeQuery("select * from tb_barang");
            while (rs.next()){
                String id = rs.getString("kode_barang");
                String tgl = rs.getString("nama_barang");
                String barang = rs.getString("harga");
                String ttl = rs.getString("stok_barang");
                String kat = rs.getString("kategori");
                String[] row = {id, tgl, barang, ttl, kat};
                tabmodel.addRow(row);
            }
            tabelbarang.setModel(tabmodel);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void tampiltmp(){
        try{
            Object[] baris = {"id", "Nama", "harga", "jumlah", "total"};
            tabmodel2 = new DefaultTableModel(null, baris);
            st = cn.createStatement();
            rs = st.executeQuery("select * from tmp_transaksi");
            while (rs.next()){
                String id = rs.getString("no");
                String tgl = rs.getString("nama_barang");
                String barang = rs.getString("harga");
                String ttl = rs.getString("jumlah");
                String kat = rs.getString("total");
                String[] row = {id, tgl, barang, ttl, kat};
                tabmodel2.addRow(row);
            }
            tabeldaftar.setModel(tabmodel2);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
     public String status="";
    public void auto(){
    try {
            st=cn.createStatement();
            rs=st.executeQuery("select * from tmp_transaksi order by no desc");
            if (rs.next()) {
                String nofak = rs.getString("no").substring(1);
                String AN = "" + (Integer.parseInt(nofak) + 1);
                String Nol = "";

                if(AN.length()==1)
                {Nol = "000";}
                else if(AN.length()==2)
                {Nol = "00";}
                else if(AN.length()==3)
                {Nol = "0";}
                else if(AN.length()==4)
                {Nol = "";}

               kode.setText("T" + Nol + AN);
            } else {
               kode.setText("T0001");
            }

           }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           }
}

    public void find(){
        try{
            Object[] baris = {"Kode barang", "Nama", "harga", "stok", "Kategori"};
            tabmodel = new DefaultTableModel(null, baris);
            st = cn.createStatement();
            rs = st.executeQuery("select * from tb_barang where kode_barang like '%."
                    +txtcariBarang.getText()+"%' or nama_barang like '%" 
                    +txtcariBarang.getText() + "%'or harga like '%" 
                    +txtcariBarang.getText() + "%'or stok_barang like '%" 
                    +txtcariBarang.getText() + "%'or kategori like '%" 
                    +txtcariBarang.getText() + "%'");
            while (rs.next()){
                String id = rs.getString("kode_barang");
                String tgl = rs.getString("nama_barang");
                String barang = rs.getString("harga");
                String ttl = rs.getString("stok_barang");
                String kat = rs.getString("kategori");
                String[] row = {id, tgl, barang, ttl, kat};
                tabmodel.addRow(row);
            }
            tabelbarang.setModel(tabmodel);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void findd(){
        try{
            Object[] baris = {"id", "Nama", "harga", "jumlah", "total"};
            tabmodel2 = new DefaultTableModel(null, baris);
            st = cn.createStatement();
            rs = st.executeQuery("select * from tmp_transaksi where nama_barang like '%"
                    +txtcari.getText()+"%' or no like '%" 
                    +txtcari.getText() + "%'or harga like '%" 
                    +txtcari.getText() + "%'or jumlah like '%" 
                    +txtcari.getText() + "%'or total like '%" 
                    +txtcari.getText() + "%'");
            while (rs.next()){
                String id = rs.getString("no");
                String tgl = rs.getString("nama_barang");
                String barang = rs.getString("harga");
                String ttl = rs.getString("jumlah");
                String kat = rs.getString("total");
                String[] row = {id, tgl, barang, ttl, kat};
                tabmodel2.addRow(row);
            }
            tabeldaftar.setModel(tabmodel2);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
    private void delete() {                                        
        try{
            int jawab;
            if ((jawab = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data?", "konfirmasi", JOptionPane.YES_NO_OPTION))==0){
                st = cn.createStatement();
                String sql = "delete from tmp_transaksi where no='" + kode_bar.getText() + "';";
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.executeUpdate();
                tampiltmp();
                reset();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    } 

  //  public String status = "";
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        nama = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelbarang = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        txtcariBarang = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jumlah = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        harga = new javax.swing.JTextField();
        tambah = new javax.swing.JButton();
        kode_bar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        subtotal = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        reset = new javax.swing.JButton();
        kode = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabeldaftar = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        hapus = new javax.swing.JButton();
        tagihan = new javax.swing.JTextField();
        JmlBalik = new javax.swing.JTextField();
        nama2 = new javax.swing.JTextField();
        keluar = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        kembalian = new javax.swing.JTextField();
        newt = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        bayar = new javax.swing.JTextField();
        btnBayar = new javax.swing.JButton();
        cetak = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 51));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 153, 204));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(5, 62, 74));
        jLabel13.setText("TRANSAKSI");

        jPanel5.setBackground(new java.awt.Color(255, 0, 51));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Total Tagihan");
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, -1, 20));

        jPanel7.setBackground(new java.awt.Color(255, 0, 51));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Harga ");
        jPanel7.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 90, -1, 20));
        jPanel7.add(nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 60, 180, -1));

        jPanel8.setBackground(new java.awt.Color(255, 0, 51));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelbarang.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelbarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelbarangMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabelbarang);

        jPanel8.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 350, 150));

        jLabel10.setForeground(new java.awt.Color(255, 255, 51));
        jLabel10.setText("Nama Barang");
        jPanel8.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, -1, 20));
        jPanel8.add(jTextField19, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 80, -1));
        jPanel8.add(jTextField20, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 170, -1));

        jLabel12.setForeground(new java.awt.Color(255, 255, 51));
        jLabel12.setText("Kode Transaksi");
        jPanel8.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, -1, 20));

        jPanel6.setBackground(new java.awt.Color(255, 0, 51));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtcariBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcariBarangKeyReleased(evt);
            }
        });
        jPanel6.add(txtcariBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 270, -1));

        jLabel4.setForeground(new java.awt.Color(255, 255, 51));
        jLabel4.setText("Nama Barang");
        jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, -1, 20));
        jPanel6.add(jTextField13, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 80, -1));

        jLabel5.setForeground(new java.awt.Color(255, 255, 51));
        jPanel6.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 14, -1, -1));
        jPanel6.add(jTextField14, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 170, -1));

        jLabel6.setForeground(new java.awt.Color(255, 255, 51));
        jLabel6.setText("Kode Transaksi");
        jPanel6.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, -1, 20));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Cari Barang");
        jPanel6.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        jPanel8.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, -1));

        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, -1));

        jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jumlahActionPerformed(evt);
            }
        });
        jumlah.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jumlahFocusGained(evt);
            }
        });
        jumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jumlahKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jumlahKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jumlahKeyTyped(evt);
            }
        });
        jPanel7.add(jumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 120, 180, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Jumlah");
        jPanel7.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 120, -1, 20));

        harga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hargaActionPerformed(evt);
            }
        });
        jPanel7.add(harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 90, 180, -1));

        tambah.setText("Tambah");
        tambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tambahMouseClicked(evt);
            }
        });
        tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahActionPerformed(evt);
            }
        });
        jPanel7.add(tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 190, 80, 30));

        kode_bar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kode_barActionPerformed(evt);
            }
        });
        jPanel7.add(kode_bar, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, 80, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Kode Transaksi");
        jPanel7.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, -1, 30));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Nama Barang");
        jPanel7.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 60, -1, 20));

        subtotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subtotalActionPerformed(evt);
            }
        });
        subtotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                subtotalKeyReleased(evt);
            }
        });
        jPanel7.add(subtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 150, 180, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Subtotal");
        jPanel7.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 150, -1, 20));

        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        jPanel7.add(reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 190, 70, 30));

        kode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kodeActionPerformed(evt);
            }
        });
        jPanel7.add(kode, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, 90, 30));

        jPanel5.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        tabeldaftar.setModel(new javax.swing.table.DefaultTableModel(
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
        tabeldaftar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabeldaftarMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tabeldaftar);

        jPanel5.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 720, 100));

        jLabel15.setForeground(new java.awt.Color(255, 255, 51));
        jLabel15.setText("Harga Barang");
        jPanel5.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 130, -1, 20));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Daftar pembelian");
        jPanel5.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, 20));

        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcariKeyReleased(evt);
            }
        });
        jPanel5.add(txtcari, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 240, 180, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Cari Barang");
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 240, -1, 20));

        hapus.setText("Hapus");
        hapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hapusMouseClicked(evt);
            }
        });
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });
        jPanel5.add(hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 410, -1, -1));

        tagihan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tagihanActionPerformed(evt);
            }
        });
        tagihan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tagihanKeyReleased(evt);
            }
        });
        jPanel5.add(tagihan, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 400, 160, -1));

        JmlBalik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmlBalikActionPerformed(evt);
            }
        });
        jPanel5.add(JmlBalik, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 410, -1, -1));

        nama2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nama2ActionPerformed(evt);
            }
        });
        jPanel5.add(nama2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 410, -1, -1));

        keluar.setText("Keluar");
        keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keluarActionPerformed(evt);
            }
        });

        jLabel18.setForeground(new java.awt.Color(204, 255, 204));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/005-cashier-1.png"))); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 153, 204));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(5, 62, 74));
        jLabel30.setText("Kembalian");

        kembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kembalianActionPerformed(evt);
            }
        });

        newt.setText("New Transaksi");
        newt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newtMouseClicked(evt);
            }
        });
        newt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(newt, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(newt))
        );

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(5, 62, 74));
        jLabel29.setText("Cash");

        bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                bayarKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                bayarKeyReleased(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bayarKeyPressed(evt);
            }
        });

        btnBayar.setText("Bayar");
        btnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarActionPerformed(evt);
            }
        });

        cetak.setText("Cetak bukti pembayaran");
        cetak.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cetakMouseClicked(evt);
            }
        });
        cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addGap(31, 31, 31)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(keluar)
                .addGap(23, 23, 23))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel29)
                        .addGap(31, 31, 31)
                        .addComponent(bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(btnBayar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel13))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(keluar)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 6, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnBayar)))
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cetak)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtcariBarangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariBarangKeyReleased
        find();
    }//GEN-LAST:event_txtcariBarangKeyReleased

    private void txtcariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyReleased
        findd();
    }//GEN-LAST:event_txtcariKeyReleased

    private void hargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hargaActionPerformed

    private void subtotalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_subtotalKeyReleased
        // TODO add your handling code here:        
    }//GEN-LAST:event_subtotalKeyReleased
/*public void auto(){
    try {
            st=cn.createStatement();
            rs=st.executeQuery("select * from tb_transaksi order by kd_transaksi desc");
            if (rs.next()) {
                String nofak = rs.getString("kd_transaksi").substring(1);
                String AN = "" + (Integer.parseInt(nofak) + 1);
                String Nol = "";

                if(AN.length()==1)
                {Nol = "000";}
                else if(AN.length()==2)
                {Nol = "00";}
                else if(AN.length()==3)
                {Nol = "0";}
                else if(AN.length()==4)
                {Nol = "";}

               kode_bar.setText("T" + Nol + AN);
            } else {
               kode_bar.setText("T0001");
            }

           }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           }
}*/
public void total_tmp(){
    int total=0;
        try {
            st = cn.createStatement();
            rs = st.executeQuery("select * from tmp_transaksi");
            while(rs.next()){
                total = total +Integer.parseInt(rs.getString("total"));
            }
            tagihan.setText("RP."+String.valueOf(total));
        } catch (Exception e) {
            e.printStackTrace();
        }
}
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
       nama.disable();
        harga.disable();
        subtotal.disable();
        kode_bar.disable();
        tagihan.disable();
        auto();
        kembalian.disable();
        hapus.setEnabled(true);
         nama2.setVisible(false);
        JmlBalik.setVisible(false);
        total_tmp();
        this.setTitle("Transaksi");
        this.setSize(820,730);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }//GEN-LAST:event_formWindowOpened

    private void jumlahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jumlahKeyReleased
        if (jumlah.getText().equals("")||jumlah.getText().equals(0)) {
            subtotal.setText("0");
        }else{
        subtotal.setText(String.valueOf(Integer.parseInt(harga.getText())*Integer.parseInt(jumlah.getText())));
        }
    }//GEN-LAST:event_jumlahKeyReleased

    private void jumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jumlahKeyPressed
        
    }//GEN-LAST:event_jumlahKeyPressed

    private void jumlahFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jumlahFocusGained
        
    }//GEN-LAST:event_jumlahFocusGained

    private void jumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jumlahKeyTyped
        char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_jumlahKeyTyped

    private void tabelbarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelbarangMouseClicked
        nama.setText(tabmodel.getValueAt(tabelbarang.getSelectedRow(), 1).toString());        
        harga.setText(tabmodel.getValueAt(tabelbarang.getSelectedRow(), 2).toString());
        kode_bar.setText(tabmodel.getValueAt(tabelbarang.getSelectedRow(), 0).toString());
        
        
                 
    }//GEN-LAST:event_tabelbarangMouseClicked

    private void tambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tambahMouseClicked
//  auto();
  if (status=="") {
        if (jumlah.getText().equals("") || jumlah.getText().equals("0") 
               || nama.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Pilih dan Masukan jumlah barang!");
        }else{
       
            try {
             st = cn.createStatement();
             rs = st.executeQuery("select * from tb_barang where kode_barang='"+
                     tabmodel.getValueAt(tabelbarang.getSelectedRow(), 0).toString()+"'");
                if (rs.next()) {
                    if (Integer.parseInt(rs.getString("stok_barang"))<Integer.parseInt(jumlah.getText())) {
                  
                        st = cn.createStatement();
                            st.executeUpdate("insert into tmp_transaksi (no,nama_barang,harga,jumlah,total)"
                                    + " values('"+kode_bar.getText()+"','"+nama.getText()+"',"
                                    + "'"+Integer.parseInt(harga.getText())+"',"
                                    + "'"+Integer.parseInt(jumlah.getText())+"',"
                                    + "'"+Integer.parseInt(subtotal.getText())+"')");
                            tampiltmp();
                            tampil();
                            total_tmp();
                            nama.setText("");
                            harga.setText("");
                            jumlah.setText("");
                            subtotal.setText("");
                       
                    }else{
                        try {
                            st = cn.createStatement();
                            st.executeUpdate("insert into tmp_transaksi (no,nama_barang,harga,jumlah,total) "
                                    + "values('"+kode_bar.getText()+"','"+nama.getText()+"',"
                                    + "'"+Integer.parseInt(harga.getText())+"',"
                                    + "'"+Integer.parseInt(jumlah.getText())+"',"
                                    + "'"+Integer.parseInt(subtotal.getText())+"')");
                            tampiltmp();
                            tampil();
                            total_tmp();
                            nama.setText("");
                            harga.setText("");
                            jumlah.setText("");
                            subtotal.setText("");
                           
                        } catch (Exception e) {
                            e.printStackTrace();
                        }                    
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "hah");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }else{
            int cek = JOptionPane.showConfirmDialog(null, "Print struk pembelian sebelumnya?");
            if (cek==JOptionPane.YES_OPTION) {
                try {
                    reset();
                    tampil();
                    tampiltmp();
                    auto();
                    total_tmp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    status="";
                    st = cn.createStatement();
                    st.executeUpdate("truncate tmp_transaksi");
                    reset();
                    tampil();
                    total_tmp();
                    auto();
                    tampiltmp();
                } catch (Exception e) {
                    e.printStackTrace();
                } 
            }
        }	

        
    }//GEN-LAST:event_tambahMouseClicked

    private void tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahActionPerformed
        // TODO add your handling code here:
       //auto();
       //tampil();
        //tampiltmp();
              try {
            st = cn.createStatement();
            rs = st.executeQuery("select * from tb_barang where nama_barang = '"+nama.getText()+"'");
            while (rs.next()) {
                int a = Integer.parseInt(rs.getString("stok_barang"));
                int j = Integer.parseInt(jumlah.getText());
                
                int rj = a-j;
                
                try {
                    st.executeUpdate("update tb_barang set stok_barang ='"+rj+"' where nama_barang='"+nama.getText()+"'");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Gagal Update  Stock");
                }
            }
        } catch (Exception e) {
        }

     
    }//GEN-LAST:event_tambahActionPerformed

    private void bayarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bayarKeyTyped
        // TODO add your handling code here:
        char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_bayarKeyTyped

    private void bayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bayarKeyReleased
        
    }//GEN-LAST:event_bayarKeyReleased

    private void bayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bayarKeyPressed
//        int kembalian = 0;
//        kembalian =Integer.parseInt(bayar.getText()) - Integer.parseInt(tagihan.getText().substring(3));
//        if (kembalian<0) {
//            this.kembalian.setText("Uang kurang");
//        }else{
//        this.kembalian.setText(String.valueOf(kembalian));
//        }
    }//GEN-LAST:event_bayarKeyPressed

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarActionPerformed
        if (!bayar.getText().equals("")) {
        int kembalian = 0;
        kembalian =Integer.parseInt(bayar.getText()) - Integer.parseInt(tagihan.getText().substring(3));
        if (kembalian<0) {
            JOptionPane.showMessageDialog(null, "Maaf! Uang anda kurang");
        }else{
            int total = Integer.parseInt(tagihan.getText().substring(3));
            try {
          
         //  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
           //LocalDate localDate = LocalDate.now();
            //st = cn.createStatement();
           //st.executeUpdate("insert into transaksi values('"+kode.getText()+"','"+localDate+"','"+total+"')");
            
            JOptionPane.showMessageDialog(null, "Terimakasih sudah membayar");
            status = "ada";
            this.kembalian.setText(String.valueOf(kembalian));
            } catch (Exception e) {
            e.printStackTrace();
            }
            
        }
        }else{
            JOptionPane.showMessageDialog(null, "isi bayar");
        }
    }//GEN-LAST:event_btnBayarActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        reset();
    }//GEN-LAST:event_resetActionPerformed

    private void cetakMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cetakMouseClicked
     /*   if (status=="") {
            JOptionPane.showMessageDialog(null, "transaksi belum terjadi");
        }else{
            try {
                    Desktop.getDesktop().browse(new URL ("src/minimarketku/transaksi.jrxml?kode="
                            +kode.getText()).toURI());
                    status="";
              st = cn.createStatement();
                    st.executeUpdate("truncate tmp_transaksi");
                    reset();
                    tampil();
                    tampiltmp();
                    auto();
                    total_tmp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }*/
    }//GEN-LAST:event_cetakMouseClicked

    private void tabeldaftarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabeldaftarMouseClicked
  JmlBalik.setText(tabmodel2.getValueAt(tabeldaftar.getSelectedRow(), 3).toString());
        nama2.setText(tabmodel2.getValueAt(tabeldaftar.getSelectedRow(), 1).toString());      // TODO add your handling code here:
    }//GEN-LAST:event_tabeldaftarMouseClicked

    private void hapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hapusMouseClicked
        try {
            st =cn.createStatement();
            st.executeUpdate("delete from tmp_transaksi where no ='"+tabmodel2.getValueAt
        (tabeldaftar.getSelectedRow(),0)+"'");
            reset();
            tampil();
            tampiltmp();
            total_tmp();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_hapusMouseClicked

    private void keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keluarActionPerformed
        login n  = new login();
        n.show();
        this.hide();
    }//GEN-LAST:event_keluarActionPerformed

    private void cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetakActionPerformed
        // TODO add your handling code here:
    
      //try{
    //   JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("Laporan.jasper"), null, koneksi.config.getConection());
     //JasperViewer.viewReport(jp, false);
      //} catch(Exception e){
        //  JOptionPane.showMessageDialog(rootPane, e);
      //}
      
          try {
        File report = new File("src/minimarketku/transaksi.jrxml");
        JasDes = JRXmlLoader.load(report);
       param.put("no", kode.getText());
        param.put("TOTAL", tagihan.getText());
        param.put("BAYAR", bayar.getText());
        param.put("KEMBALIAN", kembalian.getText());
        JasRep = JasperCompileManager.compileReport(JasDes);
        JasPri = JasperFillManager.fillReport(JasRep, param, cn);
        JasperViewer.viewReport(JasPri, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }//GEN-LAST:event_cetakActionPerformed

    private void jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jumlahActionPerformed

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        // TODO add your handling code here:
        
         try {
            st = cn.createStatement();
            rs = st.executeQuery("select * from tb_barang where nama_barang = '"+nama2.getText()+"'");
            while (rs.next()) {
                int a = Integer.parseInt(rs.getString("stok_barang"));
                int j = Integer.parseInt(JmlBalik.getText());
                
                int rj = j+a;
                
                try {
                    st.executeUpdate("update tb_barang set stok_barang ='"+rj+"' where nama_barang='"+nama2.getText()+"'");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Gagal Update  Stock");
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_hapusActionPerformed

    private void tagihanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tagihanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tagihanActionPerformed

    private void tagihanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tagihanKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tagihanKeyReleased

    private void JmlBalikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmlBalikActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JmlBalikActionPerformed

    private void nama2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nama2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nama2ActionPerformed

    private void kode_barActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kode_barActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kode_barActionPerformed

    private void kembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kembalianActionPerformed

    private void subtotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subtotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_subtotalActionPerformed

    private void kodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kodeActionPerformed

    private void newtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newtMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_newtMouseClicked

    private void newtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newtActionPerformed
        // TODO add your handling code here:
          new Transaksi().setVisible(true);
        this.hide();
    }//GEN-LAST:event_newtActionPerformed

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
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JmlBalik;
    private javax.swing.JTextField bayar;
    private javax.swing.JButton btnBayar;
    private javax.swing.JButton cetak;
    private javax.swing.JButton hapus;
    private javax.swing.JTextField harga;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jumlah;
    private javax.swing.JButton keluar;
    private javax.swing.JTextField kembalian;
    private javax.swing.JTextField kode;
    private javax.swing.JTextField kode_bar;
    private javax.swing.JTextField nama;
    private javax.swing.JTextField nama2;
    private javax.swing.JButton newt;
    private javax.swing.JButton reset;
    private javax.swing.JTextField subtotal;
    private javax.swing.JTable tabelbarang;
    private javax.swing.JTable tabeldaftar;
    private javax.swing.JTextField tagihan;
    private javax.swing.JButton tambah;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtcariBarang;
    // End of variables declaration//GEN-END:variables
}
