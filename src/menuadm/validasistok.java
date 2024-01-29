/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package menuadm;
import loginmetodelama.loginadm;
import koneksi.koneksi;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
//
//new
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author alwia
 */
public class validasistok extends javax.swing.JFrame {
    DefaultTableModel table = new DefaultTableModel();

    /**
     * Creates new form inventory
     */
    public validasistok() {
        initComponents();
        setLocationRelativeTo(this);
        
        koneksi conn = new koneksi();
        koneksi.getKoneksi();
        
        tb_validasi.setModel(table);
        table.addColumn("ID");
        table.addColumn("SUPPLIER");
        table.addColumn("ALAMAT");
        table.addColumn("KONTAK");
        table.addColumn("NAMA BARANG");
        table.addColumn("HARGA AWAL");
        table.addColumn("HARGA JUAL");
        table.addColumn("STOK");
        table.addColumn("TOTAL HARGA AWAL");
        table.addColumn("TANGGAL");
 
        tampilData();
    }
    
    public void showData(String jdate1, String jdate2){
        Connection con = getconnection();
        PreparedStatement st;
        ResultSet rs;
        
        try {
            if(jdate1.equals("")||jdate2.equals(""))
            {
                st = con.prepareStatement("SELECT * FROM `tb_validasistok` ");
            }else
           {
                st = con.prepareStatement("SELECT * FROM `tb_validasistok` WHERE `tanggal`BETWEEN '"+jdate1+"' AND '"+jdate2+"'");
                st.setString(1, jdate1);
                st.setString(2, jdate2);
            }
            
            rs = st.executeQuery();
            tb_validasi.setModel(table);
            
            Object[]row;
            
            while(rs.next()){
                
                row = new Object[10];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);
                row[8] = rs.getString(9);
                row[9] = rs.getString(10);
                
            }
                    
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void tampilData(){
        //untuk mengahapus baris setelah input
        int row = tb_validasi.getRowCount();
        for(int a = 0 ; a < row ; a++){
            table.removeRow(0);
        }
        
        String query = "SELECT * FROM `tb_validasistok` ";
        
        try{
            Connection connect = koneksi.getKoneksi();//memanggil koneksi
            Statement sttmnt = connect.createStatement();//membuat statement
            ResultSet rslt = sttmnt.executeQuery(query);//menjalanakn query
            
            while (rslt.next()){
                //menampung data sementara
                
                    String id = rslt.getString("idbarang");
                    String supplier = rslt.getString("supplier");
                    String alamat = rslt.getString("alamat");
                    String kontak = rslt.getString("kontak");
                    String namabarang = rslt.getString("namabarang");
                    String hargaawal = rslt.getString("hargaawal");
                    String harga = rslt.getString("harga");
                    String stokbarang = rslt.getString("stokbarang");
                //    String stok1 = rslt.getString("stok1");
                    String totalawal = rslt.getString("totalawal");
                    String tanggal = rslt.getString("tanggal");
                    
                    
                //masukan semua data kedalam array
                String[] data = {id,supplier,alamat,kontak,namabarang,hargaawal,harga,stokbarang,totalawal,tanggal};
                //menambahakan baris sesuai dengan data yang tersimpan diarray
                table.addRow(data);
            }
                //mengeset nilai yang ditampung agar muncul di table
                tb_validasi.setModel(table);
            
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
    private void tambahData(){
        
        Connection connect = koneksi.getKoneksi();
        String cari = txt_search.getText();
        String date1 = "2020-01-01";
        String date2 = "2050-01-01";
        if(jDate1.getDate() != null && jDate2.getDate() != null){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            date1 = df.format(jDate1.getDate());
            date2 = df.format(jDate2.getDate());            
        }            
        
        String query = "INSERT INTO `tb_stokbarang` (supplier, alamat, kontak, namabarang, hargaawal, harga, stokbarang, stok1, totalawal, tanggal) "
                + "SELECT supplier, alamat, kontak, namabarang, hargaawal, harga, stokbarang, stok1, totalawal, tanggal FROM `tb_validasistok` WHERE "
                + "(`supplier` LIKE '%"+cari+"%' OR"
                + "`idbarang`  LIKE '%"+cari+"%' OR "
                + "`namabarang` LIKE '%"+cari+"%' OR"
                + "`tanggal` LIKE '%"+cari+"%' ) "
                + "AND `tanggal` BETWEEN '"+date1+"' AND '"+date2+"'";
                
        int returnValue = 0;
    	returnValue = JOptionPane.showConfirmDialog(null, "Are you sure you want to VALIDATE THIS DATA?", "VALIDATE Data !!", JOptionPane.YES_NO_OPTION);
    		
    	if (returnValue == JOptionPane.YES_OPTION)
            try{
                PreparedStatement ps = (PreparedStatement) connect.prepareStatement(query);
                ps.execute();
                JOptionPane.showMessageDialog(null , "Data Berhasil Di- VALIDASI");
            }catch(SQLException | HeadlessException e){
                System.out.println(e);
            }finally{
                tampilData();
                cari();
            }
    	else if (returnValue == JOptionPane.NO_OPTION){
                JOptionPane.showMessageDialog(null, "Data Gagal Di- VALIDASI");
            this.setVisible(true);
        }
        
//        int returnValue = 0;
    	returnValue = JOptionPane.showConfirmDialog(null, "Are you sure you want to DELETE?", "DELETE Data !!", JOptionPane.YES_NO_OPTION);
    		
    	if (returnValue == JOptionPane.YES_OPTION)
            try{
                String clear = "TRUNCATE `tb_validasistok`";
//                Connection connect = koneksi.getKoneksi();
                PreparedStatement ps = (PreparedStatement) connect.prepareStatement(clear);
                ps.execute();
                JOptionPane.showMessageDialog(null , "Data Berhasil Dihapus");
            }catch(SQLException | HeadlessException e){
                System.out.println(e);
            }finally{
                tampilData();
            }
    	else if (returnValue == JOptionPane.NO_OPTION){
                JOptionPane.showMessageDialog(null, "Data Gagal Dihapus");
            this.setVisible(true);
        }
            
////        ambill data no 
//        int i = tb_validasi.getSelectedRow();
////        
////        String id = table.getValueAt(i, 0).toString();
//        Connection connect = koneksi.getKoneksi();
//        
//        String query = "INSERT INTO `tb_stokbarang` (supplier, alamat, kontak, namabarang, hargaawal, harga, stokbarang, stok1, totalawal, tanggal) "
//                + "SELECT supplier, alamat, kontak, namabarang, hargaawal, harga, stokbarang, stok1, totalawal, tanggal "
//                + "FROM `tb_validasistok` ";
//        
//        int returnValue = 0;
//    	returnValue = JOptionPane.showConfirmDialog(null, "Are you sure you want to VALIDATE THIS DATA?", "VALIDATE Data !!", JOptionPane.YES_NO_OPTION);
//    		
//    	if (returnValue == JOptionPane.YES_OPTION)
//            try{
//                PreparedStatement ps = (PreparedStatement) connect.prepareStatement(query);
//                ps.execute();
//                JOptionPane.showMessageDialog(null , "Data Berhasil Di- VALIDASI");
//            }catch(SQLException | HeadlessException e){
//                System.out.println(e);
//            }finally{
//                tampilData();
//                cari();
//            }
//    	else if (returnValue == JOptionPane.NO_OPTION){
//                JOptionPane.showMessageDialog(null, "Data Gagal Di- VALIDASI");
//            this.setVisible(true);
//        }
    }
    
    private void hapusData(){
//        ambill data no pendaftaran
//        int i = tb_validasi.getSelectedRow();
//        
//        String id = table.getValueAt(i, 0).toString();
//        
//        Connection connect = koneksi.getKoneksi();
//        
//        String query = "DELETE FROM `tb_validasistok` WHERE `tb_validasistok`.`idbarang` = "+id+" ";
//        
//        int returnValue = 0;
//    	returnValue = JOptionPane.showConfirmDialog(null, "Are you sure you want to DELETE?", "DELETE Data !!", JOptionPane.YES_NO_OPTION);
//    		
//    	if (returnValue == JOptionPane.YES_OPTION)
//            try{
//                String clear = "TRUNCATE `tb_validasistok`";
//                Connection connect = koneksi.getKoneksi();
//                PreparedStatement ps = (PreparedStatement) connect.prepareStatement(clear);
//                ps.execute();
//                JOptionPane.showMessageDialog(null , "Data Berhasil Dihapus");
//            }catch(SQLException | HeadlessException e){
//                System.out.println(e);
//            }finally{
//                tampilData();
//            }
//    	else if (returnValue == JOptionPane.NO_OPTION){
//                JOptionPane.showMessageDialog(null, "Data Gagal Dihapus");
//            this.setVisible(true);
//        }

        int i = tb_validasi.getSelectedRow();
        
        String id = table.getValueAt(i, 0).toString();
        
        Connection connect = koneksi.getKoneksi();
        
        String query = "DELETE FROM `tb_validasistok` WHERE `tb_validasistok`.`idbarang` = "+id+" ";
        
        int returnValue = 0;
    	returnValue = JOptionPane.showConfirmDialog(null, "Are you sure you want to DELETE?", "DELETE Data !!", JOptionPane.YES_NO_OPTION);
    		
    	if (returnValue == JOptionPane.YES_OPTION)
            try{
                PreparedStatement ps = (PreparedStatement) connect.prepareStatement(query);
                ps.execute();
                JOptionPane.showMessageDialog(null , "Data Berhasil Dihapus");
            }catch(SQLException | HeadlessException e){
                System.out.println(e);
            }finally{
                tampilData();
            }
    	else if (returnValue == JOptionPane.NO_OPTION){
                JOptionPane.showMessageDialog(null, "Data Gagal Dihapus");
            this.setVisible(true);
        }
    }
    
    private void cari(){
        int row = tb_validasi.getRowCount();
        for(int a = 0 ; a < row ; a++){
            table.removeRow(0);
        }
            String cari = txt_search.getText();
            String date1 = "2020-01-01";
            String date2 = "2050-01-01";
            if(jDate1.getDate() != null && jDate2.getDate() != null){
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                date1 = df.format(jDate1.getDate());
                date2 = df.format(jDate2.getDate());            
            }            
        
        String query = "SELECT * FROM `tb_validasistok` WHERE "
                + "(`supplier` LIKE '%"+cari+"%' OR"
                + "`idbarang`  LIKE '%"+cari+"%' OR "
                + "`namabarang` LIKE '%"+cari+"%' OR"
                + "`tanggal` LIKE '%"+cari+"%' ) "
                + "AND `tanggal` BETWEEN '"+date1+"' AND '"+date2+"'";
                
        try{
            tb_validasi.setModel(new DefaultTableModel(null, new Object[]{"id","supplier","namabarang","stok1","hargaawal","totalawal","tanggal"}));
            Connection connect = koneksi.getKoneksi();//memanggil koneksi
            Statement sttmnt = connect.createStatement();//membuat statement
            ResultSet rslt = sttmnt.executeQuery(query);//menjalanakn query
           
           while (rslt.next()){
                //menampung data sementara
                   
                    String id = rslt.getString("idbarang");
                    String supplier = rslt.getString("supplier");
                    String alamat = rslt.getString("alamat");
                    String kontak = rslt.getString("kontak");
                    String nama = rslt.getString("namabarang");
                    String hargaawal = rslt.getString("hargaawal");
                    String hargajual = rslt.getString("harga");
                    String stokbarang = rslt.getString("stokbarang");
                //    String stok1 = rslt.getString("stok1");
                    String totalawal = rslt.getString("totalawal");
                    String tanggal = rslt.getString("tanggal");
                    
                //masukan semua data kedalam array
                String[] data = {id,supplier,alamat,kontak,nama,hargaawal,hargajual,stokbarang,totalawal,tanggal};
                //menambahakan baris sesuai dengan data yang tersimpan diarray
                table.addRow(data);
            }
                //mengeset nilai yang ditampung agar muncul di table
                tb_validasi.setModel(table);
           
    }catch(Exception e){
           System.out.println("Error cari : "+e);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        minimize = new javax.swing.JLabel();
        exit = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btn_refresh = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_back = new javax.swing.JButton();
        btn_search = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_validasi = new javax.swing.JTable();
        btn_print = new javax.swing.JButton();
        txt_search = new javax.swing.JTextField();
        btn_validate = new javax.swing.JButton();
        jDate1 = new com.toedter.calendar.JDateChooser();
        jDate2 = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(25, 25, 69));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("V A L I D A S I   S T O K   B A R A N G");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        minimize.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/minimize.png"))); // NOI18N
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                minimizeMousePressed(evt);
            }
        });

        exit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/close.png"))); // NOI18N
        exit.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                exitMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(1198, Short.MAX_VALUE)
                .addComponent(minimize, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(386, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addContainerGap(387, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minimize, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 50));

        jPanel2.setBackground(new java.awt.Color(235, 236, 240));
        jPanel2.setPreferredSize(new java.awt.Dimension(1280, 554));

        btn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/refresh.png"))); // NOI18N
        btn_refresh.setText("R E F R E S H");
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/delete.png"))); // NOI18N
        btn_delete.setText("D E L E T E");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/back.png"))); // NOI18N
        btn_back.setText("B A C K");
        btn_back.setIconTextGap(8);
        btn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backActionPerformed(evt);
            }
        });

        btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/search.png"))); // NOI18N
        btn_search.setText("S E A R C H");
        btn_search.setFocusPainted(false);
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        tb_validasi.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_validasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_validasiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tb_validasi);

        btn_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/print.png"))); // NOI18N
        btn_print.setText("P R I N T");
        btn_print.setIconTextGap(2);
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        txt_search.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btn_validate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/ceklis 32.png"))); // NOI18N
        btn_validate.setText("V A L I D A S I   S T O K   B A R A N G");
        btn_validate.setFocusPainted(false);
        btn_validate.setIconTextGap(10);
        btn_validate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_validateActionPerformed(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("A W A L");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("A K H I R");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_validate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jDate1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDate2, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(txt_search)
                        .addGap(18, 18, 18)
                        .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jDate2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_validate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1280, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        hapusData();
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        // TODO add your handling code here:
        int returnValue = 0;
    	returnValue = JOptionPane.showConfirmDialog(null, "Are you sure you want to EXIT?", "Caution EXIT !!", JOptionPane.YES_NO_OPTION);
    		
    	if (returnValue == JOptionPane.YES_OPTION){
            new menuadm.stokbarang().setVisible(true);
            this.dispose();
        }
        else if (returnValue == JOptionPane.NO_OPTION){
            this.setVisible(true);
        }
    }//GEN-LAST:event_btn_backActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        // TODO add your handling code here:
        cari();
    }//GEN-LAST:event_btn_searchActionPerformed

    private void minimizeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMousePressed
        // TODO add your handling code here:
        this.setExtendedState(stokbarang.ICONIFIED);
    }//GEN-LAST:event_minimizeMousePressed

    private void exitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMousePressed
        // TODO add your handling code here:
        int returnValue = 0;
    	returnValue = JOptionPane.showConfirmDialog(null, "Are you sure you want to EXIT?", "Caution EXIT !!", JOptionPane.YES_NO_OPTION);
    		
    	if (returnValue == JOptionPane.YES_OPTION){
        System.exit(0);
        }
        else if (returnValue == JOptionPane.NO_OPTION){
            this.setVisible(true);
        }
    }//GEN-LAST:event_exitMousePressed
    int xy,xx;
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_formMouseDragged

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        // TODO add your handling code here:
        setOpacity((float)1.0);
    }//GEN-LAST:event_formMouseReleased

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        setOpacity((float)0.8);
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void tb_validasiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_validasiMouseClicked
        // TODO add your handling code here:
        
        int baris = tb_validasi.getSelectedRow();

        String supplier = table.getValueAt(baris,0).toString();

        String alamat = table.getValueAt(baris,1).toString();

        String kontak = table.getValueAt(baris,2).toString();

        String nama = table.getValueAt(baris,3).toString();

        String hargaawal = table.getValueAt(baris, 4).toString();

        String harga = table.getValueAt(baris, 5).toString();

        String stok = table.getValueAt(baris, 6).toString();

        String totalawal = table.getValueAt(baris, 7).toString();

        String tanggal = table.getValueAt(baris, 8).toString();

        Date convert = null;
        try{
            convert = new SimpleDateFormat("yyyy-MM-dd").parse(tanggal);
        }catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_tb_validasiMouseClicked

    private void btn_validateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_validateActionPerformed
        // TODO add your handling code here:
        tambahData();
//        hapusData();
    }//GEN-LAST:event_btn_validateActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        // TODO add your handling code here:
        txt_search.setText(null);
        jDate1.setDate(null);
        jDate2.setDate(null);
        tampilData();
    }//GEN-LAST:event_btn_refreshActionPerformed

    @SuppressWarnings("unchecked")
    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        // TODO add your handling code here:
        try {
            String file = "/report/validasistok.jasper";

            Class.forName("com.mysql.jdbc.Driver").newInstance();

            HashMap parameter = new HashMap();
            parameter.put("search",txt_search.getText());
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream(file),parameter,koneksi.getKoneksi());
            JasperViewer.viewReport(print, false);

        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException | JRException e){
            System.out.println(e);
        }
    }//GEN-LAST:event_btn_printActionPerformed

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
            java.util.logging.Logger.getLogger(stokbarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(stokbarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(stokbarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(stokbarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new validasistok().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_back;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_print;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_search;
    private javax.swing.JButton btn_validate;
    private javax.swing.JLabel exit;
    private com.toedter.calendar.JDateChooser jDate1;
    private com.toedter.calendar.JDateChooser jDate2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel minimize;
    private javax.swing.JTable tb_validasi;
    private javax.swing.JTextField txt_search;
    // End of variables declaration//GEN-END:variables

    private void dispose(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private Connection getconnection() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
