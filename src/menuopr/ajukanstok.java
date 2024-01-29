/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package menuopr;
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
public class ajukanstok extends javax.swing.JFrame {
    DefaultTableModel table = new DefaultTableModel();

    /**
     * Creates new form inventory
     */
    public ajukanstok() {
        initComponents();
        setLocationRelativeTo(this);
        tanggal();
        
        koneksi conn = new koneksi();
        koneksi.getKoneksi();
        
        tb_ajukanstok.setModel(table);
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
    
    private void tampilData(){
        //untuk mengahapus baris setelah input
        int row = tb_ajukanstok.getRowCount();
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
//                    String stok1 = rslt.getString("stok1");
                    String totalawal = rslt.getString("totalawal");
                    String tanggal = rslt.getString("tanggal");
                    
                    
                //masukan semua data kedalam array
                String[] data = {id,supplier,alamat,kontak,namabarang,hargaawal,harga,stokbarang,totalawal,tanggal};
                //menambahakan baris sesuai dengan data yang tersimpan diarray
                table.addRow(data);
            }
                //mengeset nilai yang ditampung agar muncul di table
                tb_ajukanstok.setModel(table);
            
        }catch(SQLException e){
            System.out.println(e);
        }
       
    }
    private void clear(){
        txt_supplier.setText(null);
        txt_alamat.setText(null);
        txt_kontak.setText(null);
        txt_namabarang.setText(null);
        txt_hargaawal.setText(null);
        txt_harga.setText(null);
        txt_stokbarang.setText(null);
        txt_totalawal.setText(null);
        txt_tanggal.setDate(null);
        txt_search.setText(null);
        
    }
    private void tambahData(){
        
        String supplier = txt_supplier.getText();
        String alamat = txt_alamat.getText();
        String kontak = txt_kontak.getText();
        String nama = txt_namabarang.getText();
        String hargaawal = txt_hargaawal.getText();
        String harga = txt_harga.getText();
        String stokbarang = txt_stokbarang.getText();
        String stok1 = txt_stokbarang.getText();
        String totalawal = txt_totalawal.getText();
        
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String tanggal = date.format(txt_tanggal.getDate());
        
        //panggil koneksi
        Connection connect = koneksi.getKoneksi();
        //query untuk memasukan data
        String query = "INSERT INTO tb_validasistok VALUES (null, '"+supplier+"', '"+alamat+"', '"+kontak+"', '"+nama+"', '"+hargaawal+"', '"+harga+"', '"+stokbarang+"', '"+stok1+"', '"+totalawal+"', '"+tanggal+"')";
        
        try{
        //menyiapkan statement untuk di eksekusi
        PreparedStatement ps = (PreparedStatement) connect.prepareStatement(query);
        ps.executeUpdate(query);
        JOptionPane.showMessageDialog(null,"Data Berhasil Disimpan");
          
        }catch(SQLException | HeadlessException e){
            System.out.println(e);
            JOptionPane.showMessageDialog(null,"Data Gagal Disimpan");
            
        }finally{
            tampilData();
            clear();
        }
    }
    public void tanggal(){
        Date now = new Date();  
        txt_tanggal.setDate(now);    
    }
    private void hapusData(){
        //ambill data no pendaftaran
        int i = tb_ajukanstok.getSelectedRow();
        
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
                clear();
            }
    	else if (returnValue == JOptionPane.NO_OPTION){
                JOptionPane.showMessageDialog(null, "Data Gagal Dihapus");
            this.setVisible(true);
        }
    }
    
    private void editData(){
        
        int i = tb_ajukanstok.getSelectedRow();
        
        String id = table.getValueAt(i, 0).toString();
        String supplier = txt_supplier.getText();
        String alamat = txt_alamat.getText();
        String kontak = txt_kontak.getText();
        String nama = txt_namabarang.getText();
        String hargaawal = txt_hargaawal.getText();
        String harga = txt_harga.getText();
        String stokbarang = txt_stokbarang.getText();
        String stok1 = txt_stokbarang.getText();
        
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String tanggal = String.valueOf(date.format(txt_tanggal.getDate()));
        
        String totalawal = txt_totalawal.getText();
        
        Connection connect = koneksi.getKoneksi();
        
        String query = "UPDATE `tb_validasistok` SET `supplier` = '"+supplier+"', `alamat` = '"+alamat+"', `kontak` = '"+kontak+"', `namabarang` = '"+nama+"', `hargaawal` = '"+hargaawal+"', `harga` = '"+harga+"', `stokbarang` = '"+stokbarang+"', `stok1` = '"+stok1+"', `tanggal` = '"+tanggal+"', `totalawal` = '"+totalawal+"'"
                + "WHERE `tb_validasistok`.`idbarang` = '"+id+"';";
        
        try{
            PreparedStatement ps = (PreparedStatement) connect.prepareStatement(query);
            ps.executeUpdate(query);
            JOptionPane.showMessageDialog(null , "Data Update");
        }catch(SQLException | HeadlessException e){
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Gagal Update");
        }finally{
            tampilData();
            clear();
        }
    }
    private void cari(){
        int row = tb_ajukanstok.getRowCount();
        for(int a = 0 ; a < row ; a++){
            table.removeRow(0);
        }
        
        String cari = txt_search.getText();
        
        String query = "SELECT * FROM `tb_validasistok` WHERE `idbarang` LIKE '%"+cari+"%' OR `supplier`  LIKE '%"+cari+"%' OR `alamat`  LIKE '%"+cari+"%' OR `kontak`  LIKE '%"+cari+"%' OR `namabarang` LIKE '%"+cari+"%' ";
                
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
                    String nama = rslt.getString("namabarang");
                    String hargaawal = rslt.getString("hargaawal");
                    String harga = rslt.getString("harga");
                    String stokbarang = rslt.getString("stokbarang");
                    String stok1 = rslt.getString("stok1");
                    String totalawal = rslt.getString("totalawal");
                    String tanggal = rslt.getString("tanggal");
                    
                //masukan semua data kedalam array
                String[] data = {id,supplier,alamat,kontak,nama,hargaawal,harga,stokbarang,stok1,totalawal,tanggal};
                //menambahakan baris sesuai dengan data yang tersimpan diarray
                table.addRow(data);
            }
                //mengeset nilai yang ditampung agar muncul di table
                tb_ajukanstok.setModel(table);
           
        
    }catch(SQLException e){
           System.out.println(e);
    }
    }

    private void total() {
        String harga = txt_hargaawal.getText();
        String jumlah = txt_stokbarang.getText();

        int hargaa = Integer.parseInt(harga);
        try {
            int jumlahh = Integer.parseInt(jumlah);

            int total = hargaa * jumlahh;
            String total_harga = Integer.toString(total);

            txt_totalawal.setText(total_harga);
        } catch (Exception e) {
            txt_stokbarang.setText(null);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btn_refresh = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        txt_namabarang = new javax.swing.JTextField();
        txt_supplier = new javax.swing.JTextField();
        btn_back = new javax.swing.JButton();
        btn_clear = new javax.swing.JButton();
        btn_search = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btn_edit = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txt_hargaawal = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_harga = new javax.swing.JTextField();
        txt_tanggal = new com.toedter.calendar.JDateChooser();
        txt_stokbarang = new javax.swing.JTextField();
        txt_alamat = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_kontak = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_ajukanstok = new javax.swing.JTable();
        txt_totalawal = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btn_print = new javax.swing.JButton();
        txt_search = new javax.swing.JTextField();
        btn_ajukanstok = new javax.swing.JButton();

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
        jLabel1.setText("A J U K A N   S T O K   B A R A N G");
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
                .addContainerGap(1195, Short.MAX_VALUE)
                .addComponent(minimize, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(402, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addContainerGap(403, Short.MAX_VALUE)))
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

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("N A M A    B A R A N G");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("S U P P L I E R");

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

        txt_namabarang.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_namabarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namabarangActionPerformed(evt);
            }
        });
        txt_namabarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_namabarangKeyPressed(evt);
            }
        });

        txt_supplier.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_supplierActionPerformed(evt);
            }
        });
        txt_supplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_supplierKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_supplierKeyTyped(evt);
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

        btn_clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/clear.png"))); // NOI18N
        btn_clear.setText("C L E A R");
        btn_clear.setIconTextGap(8);
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
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

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("S T O K   B A R A N G");

        btn_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/edit.png"))); // NOI18N
        btn_edit.setText("E D I T");
        btn_edit.setIconTextGap(8);
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("H A R G A   A W A L");
        jLabel3.setFocusable(false);

        txt_hargaawal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_hargaawal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hargaawalActionPerformed(evt);
            }
        });
        txt_hargaawal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_hargaawalKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_hargaawalKeyTyped(evt);
            }
        });

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("H A R G A    J U A L");
        jLabel8.setFocusable(false);

        txt_harga.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_harga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_hargaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_hargaKeyTyped(evt);
            }
        });

        txt_stokbarang.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_stokbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_stokbarangActionPerformed(evt);
            }
        });
        txt_stokbarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_stokbarangKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_stokbarangKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_stokbarangKeyTyped(evt);
            }
        });

        txt_alamat.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_alamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_alamatKeyPressed(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("A L A M A T ");

        txt_kontak.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_kontak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_kontakKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_kontakKeyTyped(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("K O N T A K");

        tb_ajukanstok.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_ajukanstok.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_ajukanstokMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tb_ajukanstok);

        txt_totalawal.setEditable(false);
        txt_totalawal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_totalawal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_totalawal.setDisabledTextColor(new java.awt.Color(0, 153, 51));
        txt_totalawal.setEnabled(false);
        txt_totalawal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalawalActionPerformed(evt);
            }
        });
        txt_totalawal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_totalawalKeyPressed(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("T O T A L    H A R G A    A W A L");

        btn_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/print.png"))); // NOI18N
        btn_print.setText("P R I N T");
        btn_print.setIconTextGap(2);
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        txt_search.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btn_ajukanstok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/submit-32.png"))); // NOI18N
        btn_ajukanstok.setText("A J U K A N   S T O K   B A R A N G");
        btn_ajukanstok.setFocusPainted(false);
        btn_ajukanstok.setIconTextGap(10);
        btn_ajukanstok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ajukanstokActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btn_search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_hargaawal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                            .addComponent(txt_supplier, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txt_alamat, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_harga, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_kontak, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_stokbarang, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txt_search))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_namabarang, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_totalawal, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btn_ajukanstok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(18, 18, 18)
                            .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_namabarang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_kontak, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_hargaawal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_stokbarang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_totalawal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txt_tanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_ajukanstok, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(143, 143, 143))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1280, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        hapusData();
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void txt_namabarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namabarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namabarangActionPerformed

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        // TODO add your handling code here:
        int returnValue = 0;
    	returnValue = JOptionPane.showConfirmDialog(null, "Are you sure you want to EXIT?", "Caution EXIT !!", JOptionPane.YES_NO_OPTION);
    		
    	if (returnValue == JOptionPane.YES_OPTION){
            new menuopr.stokbarang().setVisible(true);
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

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        // TODO add your handling code here:       
//      txt_kodebarang.setText(null);
        txt_supplier.setText(null);
        txt_alamat.setText(null);
        txt_kontak.setText(null);
        txt_namabarang.setText(null);
        txt_hargaawal.setText(null);
        txt_harga.setText(null);
        txt_stokbarang.setText(null);
        txt_totalawal.setText(null);
//      txt_tanggal.setDate(null);
        clear();
    }//GEN-LAST:event_btn_clearActionPerformed

    private void txt_supplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_supplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_supplierActionPerformed

    private void txt_hargaawalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hargaawalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hargaawalActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        // TODO add your handling code here:
        editData();
    }//GEN-LAST:event_btn_editActionPerformed

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

    private void txt_stokbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_stokbarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_stokbarangActionPerformed

    private void tb_ajukanstokMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_ajukanstokMouseClicked
        // TODO add your handling code here:
        
        int baris = tb_ajukanstok.getSelectedRow();

        String supplier = table.getValueAt(baris,1).toString();
        txt_supplier.setText(supplier);

        String alamat = table.getValueAt(baris,2).toString();
        txt_alamat.setText(alamat);

        String kontak = table.getValueAt(baris,3).toString();
        txt_kontak.setText(kontak);

        String nama = table.getValueAt(baris,4).toString();
        txt_namabarang.setText(nama);

        String hargaawal = table.getValueAt(baris, 5).toString();
        txt_hargaawal.setText(hargaawal);

        String harga = table.getValueAt(baris, 6).toString();
        txt_harga.setText(harga);

        String stok = table.getValueAt(baris, 7).toString();
        txt_stokbarang.setText(stok);

        String totalawal = table.getValueAt(baris, 8).toString();
        txt_totalawal.setText(totalawal);

        String tanggal = table.getValueAt(baris, 9).toString();

        Date convert = null;
        try{
            convert = new SimpleDateFormat("yyyy-MM-dd").parse(tanggal);
        }catch(Exception e){
            System.out.println(e);
        }
        txt_tanggal.setDate(convert);
    }//GEN-LAST:event_tb_ajukanstokMouseClicked

    private void txt_supplierKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_supplierKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
                txt_alamat.requestFocus();
            }
    }//GEN-LAST:event_txt_supplierKeyPressed

    private void txt_alamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_alamatKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            txt_kontak.requestFocus();
        }
    }//GEN-LAST:event_txt_alamatKeyPressed

    private void txt_kontakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_kontakKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            txt_namabarang.requestFocus();
        }
    }//GEN-LAST:event_txt_kontakKeyPressed

    private void txt_namabarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_namabarangKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            txt_hargaawal.requestFocus();
        }
    }//GEN-LAST:event_txt_namabarangKeyPressed

    private void txt_hargaawalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_hargaawalKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            txt_harga.requestFocus();
        }
    }//GEN-LAST:event_txt_hargaawalKeyPressed

    private void txt_hargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_hargaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            txt_stokbarang.requestFocus();
        }
    }//GEN-LAST:event_txt_hargaKeyPressed

    private void txt_stokbarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_stokbarangKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            btn_ajukanstok.requestFocus();
        }
    }//GEN-LAST:event_txt_stokbarangKeyPressed

    private void txt_totalawalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalawalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalawalActionPerformed

    private void txt_totalawalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_totalawalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalawalKeyPressed

    private void txt_stokbarangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_stokbarangKeyReleased
        // TODO add your handling code here:
        total();
    }//GEN-LAST:event_txt_stokbarangKeyReleased

    private void txt_supplierKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_supplierKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(Character.isLetter(c)||Character.isWhitespace(c)||Character.isISOControl(c)){
            txt_supplier.setEditable(true);
        }else{
            txt_supplier.setEditable(false);
            JOptionPane.showMessageDialog(null, "Hanya Masukkan Huruf");
        }
    }//GEN-LAST:event_txt_supplierKeyTyped

    private void txt_kontakKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_kontakKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(Character.isDigit(c)||Character.isWhitespace(c)||Character.isISOControl(c)){
            txt_kontak.setEditable(true);
        }else{
            txt_kontak.setEditable(false);
            JOptionPane.showMessageDialog(null, "Hanya Masukkan Angka");
        }
    }//GEN-LAST:event_txt_kontakKeyTyped

    private void txt_hargaawalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_hargaawalKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(Character.isDigit(c)||Character.isWhitespace(c)||Character.isISOControl(c)){
            txt_hargaawal.setEditable(true);
        }else{
            txt_hargaawal.setEditable(false);
            JOptionPane.showMessageDialog(null, "Hanya Masukkan Angka");
        }
    }//GEN-LAST:event_txt_hargaawalKeyTyped

    private void txt_hargaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_hargaKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(Character.isDigit(c)||Character.isWhitespace(c)||Character.isISOControl(c)){
            txt_harga.setEditable(true);
        }else{
            txt_harga.setEditable(false);
            JOptionPane.showMessageDialog(null, "Hanya Masukkan Angka");
        }
    }//GEN-LAST:event_txt_hargaKeyTyped

    private void txt_stokbarangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_stokbarangKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(Character.isDigit(c)||Character.isWhitespace(c)||Character.isISOControl(c)){
            txt_stokbarang.setEditable(true);
        }else{
            txt_stokbarang.setEditable(false);
            JOptionPane.showMessageDialog(null, "Hanya Masukkan Angka");
        }
    }//GEN-LAST:event_txt_stokbarangKeyTyped

    private void btn_ajukanstokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ajukanstokActionPerformed
        // TODO add your handling code here:
        tambahData();
    }//GEN-LAST:event_btn_ajukanstokActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        // TODO add your handling code here:
        txt_supplier.setText(null);
        txt_alamat.setText(null);
        txt_kontak.setText(null);
        txt_namabarang.setText(null);
        txt_hargaawal.setText(null);
        txt_harga.setText(null);
        txt_stokbarang.setText(null);
        txt_totalawal.setText(null);
        txt_search.setText(null);
        txt_supplier.setText(null);
        tampilData();
    }//GEN-LAST:event_btn_refreshActionPerformed

    @SuppressWarnings("unchecked")
    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        // TODO add your handling code here:
        try {
            String file = "/report/ajukanstok.jasper";

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
                new ajukanstok().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_ajukanstok;
    private javax.swing.JButton btn_back;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_print;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_search;
    private javax.swing.JLabel exit;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel minimize;
    private javax.swing.JTable tb_ajukanstok;
    private javax.swing.JTextField txt_alamat;
    private javax.swing.JTextField txt_harga;
    private javax.swing.JTextField txt_hargaawal;
    private javax.swing.JTextField txt_kontak;
    private javax.swing.JTextField txt_namabarang;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_stokbarang;
    private javax.swing.JTextField txt_supplier;
    private com.toedter.calendar.JDateChooser txt_tanggal;
    private javax.swing.JTextField txt_totalawal;
    // End of variables declaration//GEN-END:variables

    private void dispose(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
