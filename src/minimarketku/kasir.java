package minimarketku;

import java.awt.Desktop;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import minimarketku.login;

public class kasir extends javax.swing.JFrame {
    public Statement st;
    public ResultSet rs;
    public DefaultTableModel tabmodel;
    public DefaultTableModel tabmodel2; 

    Connection cn = koneksi.config.Conn();
    public kasir() {
        initComponents();
        tampil();
        tampiltmp();
        reset();
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
            tblDaftarBarang.setModel(tabmodel);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampiltmp(){
        try{
            Object[] baris = {"id", "Nama", "harga", "jumlah", "subtotal"};
            tabmodel2 = new DefaultTableModel(null, baris);
            st = cn.createStatement();
            rs = st.executeQuery("select * from tmp_transaksi where no='"+kode.getText()+"'");
            while (rs.next()){
                String id = rs.getString("no");
                String tgl = rs.getString("nama_barang");
                String barang = rs.getString("harga");
                String ttl = rs.getString("jumlah");
                String kat = rs.getString("subtotal");
                String[] row = {id, tgl, barang, ttl, kat};
                tabmodel2.addRow(row);
            }
            tblDaftarPembelian.setModel(tabmodel2);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        hapus.setEnabled(true);
        
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
    
    private void find(){
           try{
            Object[] baris = {"Kode barang", "Nama", "harga", "stok", "Kategori"};
            tabmodel = new DefaultTableModel(null, baris);
            st = cn.createStatement();
            rs = st.executeQuery("select * from tb_barang where kode_barang like '%."+txtCari.getText()+"%' or nama_barang like '%" +txtCari.getText() + "%'or harga like '%" +txtCari.getText() + "%'or stok_barang like '%" +txtCari.getText() + "%'or kategori like '%" +txtCari.getText() + "%'");
            while (rs.next()){
                String id = rs.getString("kode_barang");
                String tgl = rs.getString("nama_barang");
                String barang = rs.getString("harga");
                String ttl = rs.getString("stok_barang");
                String kat = rs.getString("kategori");
                String[] row = {id, tgl, barang, ttl, kat};
                tabmodel.addRow(row);
            }
            tblDaftarBarang.setModel(tabmodel);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    private void findbeli(){
           try{
            Object[] baris = {"id", "Nama", "harga", "jumlah", "subtotal"};
            tabmodel = new DefaultTableModel(null, baris);
            st = cn.createStatement();
            rs = st.executeQuery("select * from tmp_transaksi where no like '%."+txtCariBeli.getText()+"%' or nama_barang like '%" +txtCariBeli.getText() + "%'or harga like '%" +txtCariBeli.getText() + "%'or jumlah like '%" +txtCariBeli.getText() + "%'or subtotal like '%" +txtCariBeli.getText() + "%'");
            while (rs.next()){
                String no = rs.getString("no");
                String nm = rs.getString("nama_barang");
                String har = rs.getString("harga");
                String jml = rs.getString("jumlah");
                String subtot = rs.getString("subtotal");
                String[] row = {no, nm, har, jml, subtot};
                tabmodel.addRow(row);
            }
            tblDaftarPembelian.setModel(tabmodel);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }



public void total_tmp(){
    int subtotal=0;
        try {
            st = cn.createStatement();
            rs = st.executeQuery("select * from tmp_transaksi");
            while(rs.next()){
                subtotal = subtotal +Integer.parseInt(rs.getString("subtotal"));
            }
            tagihan.setText("RP."+String.valueOf(subtotal));
        } catch (Exception e) {
            e.printStackTrace();
        }
}
private void delete() {                                        
        try{
            int jawab;
            if ((jawab = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data?", "konfirmasi", JOptionPane.YES_NO_OPTION))==0){
                st = cn.createStatement();
                String sql = "delete from tmp_transaksi where no='" + kode.getText() + "';";
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.executeUpdate();
                tampiltmp();
                reset();
            }
        }catch(Exception e){
            e.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        btnCariBeli = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDaftarBarang = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDaftarPembelian = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        kode = new javax.swing.JTextField();
        nama = new javax.swing.JTextField();
        harga = new javax.swing.JTextField();
        jumlah = new javax.swing.JTextField();
        subtotal = new javax.swing.JTextField();
        reset = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtCariBeli = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tagihan = new javax.swing.JTextField();
        hapus = new javax.swing.JButton();
        btnbaru = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        keluar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        kembalian = new javax.swing.JTextField();
        bayar = new javax.swing.JTextField();
        btnBayar = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 153, 204));

        btnCariBeli.setBackground(new java.awt.Color(255, 0, 51));

        jLabel2.setBackground(new java.awt.Color(255, 255, 0));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Cari Barang");

        txtCari.setBackground(new java.awt.Color(204, 255, 153));
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });

        tblDaftarBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kode Barang", "Nama", "Harga", "Stok", "Kategori"
            }
        ));
        tblDaftarBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDaftarBarangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDaftarBarang);

        tblDaftarPembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nama", "Harga", "Jumlah", "Subtotal"
            }
        ));
        jScrollPane2.setViewportView(tblDaftarPembelian);

        jLabel3.setBackground(new java.awt.Color(255, 255, 0));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Daftar Pembelian");

        jLabel4.setBackground(new java.awt.Color(255, 255, 0));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Kode Transaksi");

        jLabel5.setBackground(new java.awt.Color(255, 255, 0));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Nama Barang");

        jLabel6.setBackground(new java.awt.Color(255, 255, 0));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Harga");

        jLabel7.setBackground(new java.awt.Color(255, 255, 0));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Jumlah");

        jLabel8.setBackground(new java.awt.Color(255, 255, 0));
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Subtotal");

        jumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jumlahKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jumlahKeyTyped(evt);
            }
        });

        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        btnTambah.setText("Tambah");
        btnTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTambahMouseClicked(evt);
            }
        });
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(255, 255, 0));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Cari Barang");

        txtCariBeli.setBackground(new java.awt.Color(204, 255, 153));
        txtCariBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariBeliKeyReleased(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(255, 255, 0));
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Total Tagihan");

        tagihan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tagihanActionPerformed(evt);
            }
        });

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

        btnbaru.setText("BARU");
        btnbaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbaruActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btnCariBeliLayout = new javax.swing.GroupLayout(btnCariBeli);
        btnCariBeli.setLayout(btnCariBeliLayout);
        btnCariBeliLayout.setHorizontalGroup(
            btnCariBeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnCariBeliLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(btnCariBeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(btnCariBeliLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(tagihan, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(hapus))
                    .addComponent(jLabel3)
                    .addGroup(btnCariBeliLayout.createSequentialGroup()
                        .addGroup(btnCariBeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(btnCariBeliLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(btnCariBeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(btnCariBeliLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(btnCariBeliLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(harga, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(btnCariBeliLayout.createSequentialGroup()
                                .addGroup(btnCariBeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(btnCariBeliLayout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnCariBeliLayout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(25, 25, 25)))
                                .addGroup(btnCariBeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(kode, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(btnCariBeliLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(btnCariBeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnCariBeliLayout.createSequentialGroup()
                                        .addComponent(btnbaru, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(reset)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnTambah)
                                        .addGap(19, 19, 19))
                                    .addGroup(btnCariBeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(btnCariBeliLayout.createSequentialGroup()
                                            .addComponent(jLabel9)
                                            .addGap(18, 18, 18)
                                            .addComponent(txtCariBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(subtotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addComponent(jScrollPane2))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        btnCariBeliLayout.setVerticalGroup(
            btnCariBeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnCariBeliLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnCariBeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(btnCariBeliLayout.createSequentialGroup()
                        .addGroup(btnCariBeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3))
                    .addGroup(btnCariBeliLayout.createSequentialGroup()
                        .addGroup(btnCariBeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(kode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(btnCariBeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(btnCariBeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(btnCariBeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(btnCariBeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(btnCariBeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTambah)
                            .addGroup(btnCariBeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(reset)
                                .addComponent(btnbaru)))
                        .addGap(27, 27, 27)
                        .addGroup(btnCariBeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtCariBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(btnCariBeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(tagihan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hapus))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Sejahtera/logo-trans.png"))); // NOI18N
        jLabel1.setText("TRANSAKSI");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        keluar.setText("Keluar");
        keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keluarActionPerformed(evt);
            }
        });

        jLabel11.setBackground(new java.awt.Color(255, 255, 0));
        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Cash");

        jLabel12.setBackground(new java.awt.Color(255, 255, 0));
        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("Kembalian");

        kembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kembalianActionPerformed(evt);
            }
        });

        btnBayar.setText("Bayar");
        btnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarActionPerformed(evt);
            }
        });
        btnBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                btnBayarKeyTyped(evt);
            }
        });

        jButton6.setText("Cetak Bukti Pembayaran");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/minimarketku/005-cashier-1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnCariBeli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnBayar)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6)
                        .addGap(78, 78, 78))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(64, 64, 64)
                .addComponent(keluar)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(keluar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)))
                .addComponent(btnCariBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBayar))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addGap(65, 65, 65))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        if(kode.getText().trim().equals("") && nama.getText().trim().equals("") && harga.getText().trim().equals("") &&
                jumlah.getText().trim().equals("")){
        JOptionPane.showMessageDialog(null, "Gagal Mereset");
        }else{
        reset();
        auto();
     }  
    }//GEN-LAST:event_resetActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        nama.disable();
        harga.disable();
        subtotal.disable();
        kode.disable();
        tagihan.disable();
        auto();
        kembalian.disable();
        hapus.setEnabled(true);
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

    private void jumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jumlahKeyTyped
        char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_jumlahKeyTyped

    private void tblDaftarBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDaftarBarangMouseClicked
        nama.setText(tabmodel.getValueAt(tblDaftarBarang.getSelectedRow(), 1).toString());        
        harga.setText(tabmodel.getValueAt(tblDaftarBarang.getSelectedRow(), 2).toString());
    }//GEN-LAST:event_tblDaftarBarangMouseClicked

    private void btnTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahMouseClicked
        if (status=="") {
        if (jumlah.getText().equals("") || jumlah.getText().equals("0") || nama.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Pilih dan Masukan jumlah barang!");
        }else{
       
            try {
             st = cn.createStatement();
             rs = st.executeQuery("select * from tb_barang where kode_barang='"+tabmodel.getValueAt
        (tblDaftarBarang.getSelectedRow(), 0).toString()+"'");
                if (rs.next()) {
                    if (Integer.parseInt(rs.getString("stok_barang"))<Integer.parseInt(jumlah.getText())) {
                        JOptionPane.showMessageDialog(null, "Maaf, barang habis!");
                    }else{
                        try {
                            st = cn.createStatement();
                            st.executeUpdate("insert into tmp_transaksi (no,nama_barang,harga,jumlah,subtotal)"
                                    + " values('"+kode.getText()+"','"+nama.getText()+"','"
                                    +Integer.parseInt(harga.getText())+"','"+Integer.parseInt(jumlah.getText())
                                    +"','"+Integer.parseInt(subtotal.getText())+"')");
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
                    Desktop.getDesktop().browse(new URL ("http://localhost/transaksi/laporan/laporan2.php?kode="+
                            kode.getText()).toURI());
                    status="";
//                    st = cn.createStatement();
//                    st.executeUpdate("truncate tmp_transaksi");
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
    }//GEN-LAST:event_btnTambahMouseClicked

    private void btnBayarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBayarKeyTyped
        char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
        
    }//GEN-LAST:event_btnBayarKeyTyped

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarActionPerformed
        int kembalian = 0;
        kembalian = Integer.parseInt(bayar.getText())- Integer.parseInt(tagihan.getText().substring(3));
        if(kembalian<0){
            JOptionPane.showMessageDialog(null, "Maaf! Uang anda kurang");
            this.kembalian.setText("Uang kurang");
        }else{
            this.kembalian.setText("Rp."+String.valueOf(kembalian));;
       }
    }//GEN-LAST:event_btnBayarActionPerformed

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        find();
    }//GEN-LAST:event_txtCariKeyReleased

    private void keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keluarActionPerformed
       login n  = new login();
        n.show();
        this.hide();
    }//GEN-LAST:event_keluarActionPerformed

    private void txtCariBeliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariBeliKeyReleased
        findbeli();
    }//GEN-LAST:event_txtCariBeliKeyReleased

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        
    }//GEN-LAST:event_hapusActionPerformed

    private void hapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hapusMouseClicked
        try{
            st = cn.createStatement();
            st.executeUpdate("delete from tmp_transaksi where no='"+tabmodel2.getValueAt
        (tblDaftarPembelian.getSelectedRow(),0)+"'");
            reset();
            tampil();
            tampiltmp();
            total_tmp();
        }catch(Exception e){
        }
    }//GEN-LAST:event_hapusMouseClicked

    private void kembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kembalianActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        try{
            st = cn.createStatement();
            rs = st.executeQuery("select * from tb_barang where nama_barang='"+nama.getText()+"'");
            while(rs.next()){
                int a = Integer.parseInt(rs.getString("stok_barang"));
                int j = Integer.parseInt(jumlah.getText());
                
                int rj = a-j;
                
                try{
                    st.executeUpdate("update tb_barang set stok_barang ='"+rj+"' where nama_barang='"+nama.getText()+"'");
                    
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Gagal Update stok");
                }
            }
        }catch(Exception e){
            
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnbaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbaruActionPerformed
        new kasir().setVisible(true);
        this.hide();
    }//GEN-LAST:event_btnbaruActionPerformed

    private void tagihanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tagihanActionPerformed
        reset();
    }//GEN-LAST:event_tagihanActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    public void reset(){
        tagihan.setText("");
        nama.setText("");
        harga.setText("");
        jumlah.setText("");
        subtotal.setText("");
        bayar.setText("");
        kembalian.setText("");
    }
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
            java.util.logging.Logger.getLogger(kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new kasir().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bayar;
    private javax.swing.JButton btnBayar;
    private javax.swing.JPanel btnCariBeli;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnbaru;
    private javax.swing.JButton hapus;
    private javax.swing.JTextField harga;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jumlah;
    private javax.swing.JButton keluar;
    private javax.swing.JTextField kembalian;
    private javax.swing.JTextField kode;
    private javax.swing.JTextField nama;
    private javax.swing.JButton reset;
    private javax.swing.JTextField subtotal;
    private javax.swing.JTextField tagihan;
    private javax.swing.JTable tblDaftarBarang;
    private javax.swing.JTable tblDaftarPembelian;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtCariBeli;
    // End of variables declaration//GEN-END:variables
}
