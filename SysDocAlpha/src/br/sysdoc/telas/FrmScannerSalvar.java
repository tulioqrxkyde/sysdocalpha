/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sysdoc.telas;

import br.sysdoc.factories.PathFactory;
import br.sysdoc.model.entidades.DAO;
import br.sysdoc.model.entidades.Funcionario;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author acer
 */
public class FrmScannerSalvar extends javax.swing.JFrame {

    BufferedImage arquivo;
    List<Funcionario> listaFuncionarios;
    DefaultTableModel modelo;
    int indexSelected = 0;

    /**
     * Creates new form FrmScannerSalvar
     */
    public FrmScannerSalvar() {
        initComponents();
        listaFuncionarios = DAO.listarFuncionarios();
        jLabel1.setVisible(false);
        modelo = (DefaultTableModel) jTable1.getModel();
        for (Funcionario func : listaFuncionarios) {
            modelo.addRow(new String[]{func.getName(), func.getCpf()}
            );
        }
        jComboBox1.setEnabled(false);
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                PathFactory path = PathFactory.getInstance();
                Path pathNew = path.newPath(path.getRoot() + PathFactory.getPATH_CONST()
                        + modelo.getValueAt(jTable1.getSelectedRow(), 0) + "/");
                Stream<Path> paths = (Stream) Stream.builder().build();
                try {
                    if (pathNew != null) {
                        paths = Files.list(pathNew);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(FrmScannerSalvar.class.getName()).log(Level.SEVERE, null, ex);
                }
                Iterator<Path> itr = paths.iterator();
                while (itr.hasNext()) {
                    Path pathFolder = itr.next();
                    if (!path.isFile(pathFolder)) {
                        jComboBox1.addItem(pathFolder.getFileName());
                    }
                }
                jComboBox1.setEnabled(true);
            }
        }
        );
    }

    public FrmScannerSalvar(BufferedImage bfImage) {
        this();
        this.arquivo = bfImage;
        jLabel1.setIcon(new ImageIcon(arquivo));
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        tfNome = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jTable1.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Funcionário"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 48, 409, 222);

        jButton1.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jButton1.setText("Salvar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(437, 49, 90, 42);

        jButton4.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jButton4.setText("Sair");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(437, 97, 90, 27);

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel1.add(jTextField1);
        jTextField1.setBounds(10, 22, 410, 20);

        tfNome.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nome:", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel1.add(tfNome);
        tfNome.setBounds(437, 242, 92, 37);

        jLabel1.setText("Arquivo");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel1);
        jLabel1.setBounds(437, 142, 90, 87);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Raíz" }));
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(437, 290, 92, 22);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        PathFactory path = PathFactory.getInstance();
        if (jTable1.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null,
                    "Nenhum funcionário foi selecionado.");
            return;
        }
       // if (arquivo != null) {
            /* TODO -> Salvar arquivo na pasta, saber quantos arquivos
         existem para ir adicionando sequência 1,2,3...*/
        // Path pathNew = Paths.get("C:/teste/teste.txt");

        int index = jComboBox1.getSelectedIndex();
        StringBuilder sb;
        if (index == 0) {
            sb = new StringBuilder(path.getRoot()
                    + PathFactory.getPATH_CONST()
                    + jTable1.getValueAt(jTable1.getSelectedRow(),
                            jTable1.getSelectedColumn()).toString() + "/");
        } else {
            String folder = jTable1.getValueAt(jTable1.getSelectedRow(),
                    jTable1.getSelectedColumn()).toString();

            sb = new StringBuilder(
                    path.getRoot() + PathFactory.getPATH_CONST()
                    + folder + "/" + jComboBox1.getSelectedItem()).append("/");
        }
        String s2 = sb.toString().replaceAll("[/\\\\]", "/");
        Path pathNew = path.newPath(s2);
        sb.append(!tfNome.getText().isEmpty() ? tfNome.getText()
                : path.countFilesInFolder(pathNew));
        s2 = sb.toString().replaceAll("[/\\\\]", "/");
        try {
            pathNew = path.newPath(sb.toString());
            path.createPath(pathNew);
            ImageIO.write(arquivo, "jpg", pathNew.toFile());
        } catch (IOException ex) {
            Logger.getLogger(FrmScannerSalvar.class.getName()).log(Level.SEVERE, null, ex);
        }
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        String valor = jTextField1.getText();
        modelo.setRowCount(0);

        for (Funcionario functionary : listaFuncionarios) {
            if (functionary.getName().regionMatches(true, 0, valor, 0, valor.length())
                    || functionary.getName().contains(" " + valor)
                    || functionary.getCpf().regionMatches(true, 0, valor, 0, valor.length())) {
                if (!modelo.getDataVector().contains(functionary.getName())) {
                    modelo.addRow(new String[]{functionary.getName(),
                        functionary.getCpf()});
                } else {
                    modelo.setRowCount(0);
                }
            }
        }
    }//GEN-LAST:event_jTextField1KeyReleased

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
            java.util.logging.Logger.getLogger(FrmScannerSalvar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmScannerSalvar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmScannerSalvar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmScannerSalvar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmScannerSalvar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField tfNome;
    // End of variables declaration//GEN-END:variables
}
