/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minimarketku;

//import static com.sun.webkit.perf.WCFontPerfLogger.reset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.config;
import minimarketku.*;

/**
 *
 * @author ASUS
 */
public class admin extends javax.swing.JFrame {

    public Statement st;
    public ResultSet rs;
    public DefaultTableModel tabmodel; 
    
    Connection cn = koneksi.config.Conn();
    
    /**
     * Creates new form admin
     */
    public admin() {
        initComponents();
       
        tampildata();
        reset();
    }

    private void reset(){
        kode.setText(null);
        nama.setText(null);
        harga.setText(null);
        stok.setText(null);
        
        save.setEnabled(true);
        edit.setEnabled(false);
        hapus.setEnabled(false);
    }
    
    public void tampildata(){
        try{
            Object[] baris = {"Kode barang", "Nama", "harga", "stok", "Kategori"};
            tabmodel = new DefaultTableModel(null, baris);
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
            TableBarang.setModel(tabmodel);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        save.setEnabled(true);
        edit.setEnabled(false);
        hapus.setEnabled(false);
        reset.setEnabled(false);
    }
    
    private void find(){
           try{
            Object[] baris = {"Kode barang", "Nama", "harga", "stok", "Kategori"};
            tabmodel = new DefaultTableModel(null, baris);
            st = cn.createStatement();
            rs = st.executeQuery("select * from tb_barang where kode_barang like '%."+
                    cari.getText()+"%' or nama_barang like '%" +
                    cari.getText() + "%'or harga like '%" +
                    cari.getText() + "%'or stok_barang like '%" +
                    cari.getText() + "%'or kategori like '%" +
                    cari.getText() + "%'");
            
            while (rs.next()){
                String id = rs.getString("kode_barang");
                String tgl = rs.getString("nama_barang");
                String barang = rs.getString("harga");
                String ttl = rs.getString("stok_barang");
                String kat = rs.getString("kategori");
                String[] row = {id, tgl, barang, ttl, kat};
                tabmodel.addRow(row);
            }
            TableBarang.setModel(tabmodel);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void auto(){
        try {
            String sql="select * from tb_barang order by kode_barang desc";
            st=cn.createStatement();
            rs=st.executeQuery(sql);
            if (rs.next()) {
                String nofak = rs.getString("kode_barang").substring(1);
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

               kode.setText("F" + Nol + AN);
            } else {
               kode.setText("F0001");
            }

           }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           }
    }
    
    private void simpan(){
        try {
            st = cn.createStatement();
            rs = st.executeQuery("select *from tb_barang where nama_barang = '"+nama.getText()+"'");
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Barang telah terdaftar");
            }else{
            try{
            st = cn.createStatement();
            String sql = "insert into tb_barang values('"+kode.getText()+"',"
                    + " '"+nama.getText()+"',"
                    + " '"+harga.getText()+"',"
                    + " '"+stok.getText()+"', "
                    + "'"+kategori.getSelectedItem()+"')";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.executeUpdate();
            tampildata();
            auto();
            reset();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void update() {                                        
       try{
            st = cn.createStatement();
            String sql = "update tb_barang set nama_barang='"+
                    nama.getText()+"', "
                    + "harga='"+harga.getText()+"', "
                    + "stok_barang='"+stok.getText()+"' "
                    + "where kode_barang='"+kode.getText()+"'";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.executeUpdate();
            tampildata();
            reset();
        } catch (SQLException e){
             e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    private void delete() {                                        
        try{
            int jawab;
            if ((jawab = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data?", 
                    "konfirmasi", JOptionPane.YES_NO_OPTION))==0){
                st = cn.createStatement();
                String sql = "delete from tb_barang where kode_barang='" + kode.getText() + "';";
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.executeUpdate();
                tampildata();
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

        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lId = new javax.swing.JLabel();
        lAkses = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        kategori = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableBarang = new javax.swing.JTable();
        edit = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        save = new javax.swing.JButton();
        hapus = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        kode = new javax.swing.JTextField();
        nama = new javax.swing.JTextField();
        stok = new javax.swing.JTextField();
        cari = new javax.swing.JTextField();
        back = new javax.swing.JButton();
        harga = new javax.swing.JTextField();
        tambahStok = new javax.swing.JButton();
        kodee = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        refresh = new javax.swing.JButton();
        tambah = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(204, 0, 51));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(255, 102, 153));
        jPanel6.setForeground(new java.awt.Color(255, 255, 0));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel11.setText("INPUT BARANG");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(629, Short.MAX_VALUE)
                .addComponent(lId)
                .addGap(41, 41, 41))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(515, 515, 515)
                        .addComponent(lAkses))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(193, 193, 193)
                        .addComponent(jLabel11)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel11)
                .addGap(12, 12, 12)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lAkses)
                    .addComponent(lId))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 100));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 0));
        jLabel12.setText("Nama Barang");
        jPanel5.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 148, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 0));
        jLabel13.setText("Kode Barang");
        jPanel5.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 0));
        jLabel14.setText("Harga");
        jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 186, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 0));
        jLabel15.setText("Kategori");
        jPanel5.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 0));
        jLabel16.setText("Stok Barang");
        jPanel5.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 224, -1, -1));

        kategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Makanan", "Minuman" }));
        kategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kategoriActionPerformed(evt);
            }
        });
        jPanel5.add(kategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 128, -1));

        TableBarang.setModel(new javax.swing.table.DefaultTableModel(
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
        TableBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableBarangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableBarang);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 148, 350, 188));

        edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/gtk-edit.png"))); // NOI18N
        edit.setText("Edit");
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });
        jPanel5.add(edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, 95, -1));

        reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cancel.png"))); // NOI18N
        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        jPanel5.add(reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 400, 95, 34));

        save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/gtk-save-as.png"))); // NOI18N
        save.setText("Simpan");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        jPanel5.add(save, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, -1, -1));

        hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/document_delete.png"))); // NOI18N
        hapus.setText("Hapus");
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });
        jPanel5.add(hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 95, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 51));
        jLabel1.setText("Cari");
        jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(322, 113, -1, -1));

        kode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kodeActionPerformed(evt);
            }
        });
        jPanel5.add(kode, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 128, -1));

        nama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                namaKeyTyped(evt);
            }
        });
        jPanel5.add(nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 148, 128, -1));

        stok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stokActionPerformed(evt);
            }
        });
        stok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                stokKeyTyped(evt);
            }
        });
        jPanel5.add(stok, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 224, 128, -1));

        cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cariKeyReleased(evt);
            }
        });
        jPanel5.add(cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 110, 190, 30));

        back.setText("Back");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });
        jPanel5.add(back, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 400, 70, 30));

        harga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                hargaKeyTyped(evt);
            }
        });
        jPanel5.add(harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 186, 128, -1));

        tambahStok.setText("Tambah Stok");
        tambahStok.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tambahStokMouseClicked(evt);
            }
        });
        tambahStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahStokActionPerformed(evt);
            }
        });
        jPanel5.add(tambahStok, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 400, 110, 30));

        kodee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kodeeMouseClicked(evt);
            }
        });
        kodee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kodeeActionPerformed(evt);
            }
        });
        jPanel5.add(kodee, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 350, 90, 30));

        jPanel1.setBackground(new java.awt.Color(255, 102, 153));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 670, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 670, 30));

        refresh.setText("Refresh");
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });
        jPanel5.add(refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 400, -1, 30));

        tambah.setText("Tambah Data");
        tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahActionPerformed(evt);
            }
        });
        jPanel5.add(tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, 150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void kategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kategoriActionPerformed

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
    update();
        // TODO add your handling code here:

    }//GEN-LAST:event_editActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
    if(kode.getText().trim().equals("") && nama.getText().trim().equals("")
            && harga.getText().trim().equals("") 
            && stok.getText().trim().equals("")){
        JOptionPane.showMessageDialog(null, "Gagal Mereset");
        }else{
        reset();
        auto();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_resetActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        if(kode.getText().trim().equals("")||nama.getText().trim().equals("")||
                harga.getText().trim().equals("")||stok.getText().trim().equals("")){
        JOptionPane.showMessageDialog(null, "isi data dengan Lengkap");
        }else{
        simpan();
        auto();
        }
    }//GEN-LAST:event_saveActionPerformed

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
    delete();
    auto();
    // TODO add your handling code here:
    }//GEN-LAST:event_hapusActionPerformed

    private void kodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kodeActionPerformed

    private void namaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_namaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_namaKeyTyped

    private void stokKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stokKeyTyped
char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_stokKeyTyped

    private void TableBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableBarangMouseClicked
        kode.setText(tabmodel.getValueAt(TableBarang.getSelectedRow(), 0) + "");
        kodee.setText(tabmodel.getValueAt(TableBarang.getSelectedRow(), 0) + "");
        nama.setText(tabmodel.getValueAt(TableBarang.getSelectedRow(), 1) + "");
        harga.setText(tabmodel.getValueAt(TableBarang.getSelectedRow(), 2) + "");
        stok.setText(tabmodel.getValueAt(TableBarang.getSelectedRow(), 3) + "");
        kategori.setSelectedItem(tabmodel.getValueAt(TableBarang.getSelectedRow(), 4));
        
        save.setEnabled(false);
        edit.setEnabled(true);
        hapus.setEnabled(true);
        reset.setEnabled(true);        // TODO add your handling code here:
    }//GEN-LAST:event_TableBarangMouseClicked

    private void stokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stokActionPerformed
        
// TODO add your handling code here:
    }//GEN-LAST:event_stokActionPerformed

    private void cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cariKeyReleased
        find();        // TODO add your handling code here:
    }//GEN-LAST:event_cariKeyReleased

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
          dispose();
        new login().setVisible(true);
        // TODO add your handling code here:
            // TODO add your handling code here:
    }//GEN-LAST:event_backActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setTitle("Input Barang");
        //this.setSize(900,520);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        kode.setEnabled(false);
        auto();
    }//GEN-LAST:event_formWindowOpened

    private void tambahStokMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tambahStokMouseClicked
        if (kodee.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Pilih barang!");
        }else{
            tambah n = new tambah(this, rootPaneCheckingEnabled);
            n.kd = kodee.getText();
            n.show();
        }
    }//GEN-LAST:event_tambahStokMouseClicked

    private void tambahStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahStokActionPerformed

    }//GEN-LAST:event_tambahStokActionPerformed

    private void hargaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hargaKeyTyped
char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_hargaKeyTyped

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        auto();
        tampildata();
        reset();
    }//GEN-LAST:event_refreshActionPerformed

    private void kodeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodeeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kodeeActionPerformed

    private void tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahActionPerformed
        auto();
        tampildata();
    }//GEN-LAST:event_tambahActionPerformed

    private void kodeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kodeeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_kodeeMouseClicked

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
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableBarang;
    private javax.swing.JButton back;
    private javax.swing.JTextField cari;
    private javax.swing.JButton edit;
    private javax.swing.JButton hapus;
    private javax.swing.JTextField harga;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> kategori;
    private javax.swing.JTextField kode;
    private javax.swing.JTextField kodee;
    private javax.swing.JLabel lAkses;
    private javax.swing.JLabel lId;
    private javax.swing.JTextField nama;
    private javax.swing.JButton refresh;
    private javax.swing.JButton reset;
    private javax.swing.JButton save;
    private javax.swing.JTextField stok;
    private javax.swing.JButton tambah;
    private javax.swing.JButton tambahStok;
    // End of variables declaration//GEN-END:variables

    
}
