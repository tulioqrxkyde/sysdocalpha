/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sysdoc.telas;

import br.sysdoc.factories.PathFactory;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author tulio.xcrtf
 */
public class FrmFiles extends javax.swing.JFrame implements MouseListener {

    List<String> labelsNames;
    List<JLabel> labels;
    JLabel label = new JLabel();
    int x = 10, y = 5, countPathsLast, countPathsNext;
    List<File> file;
    String[] types = {"jpg", "png", "gif"};
    Path path;
    List<Path> lastPaths, nextPath;

    /**
     * Creates new form FrmFiles
     */
    public FrmFiles() {
        initComponents();
        jButton3.setEnabled(false);
        jButton4.setEnabled(false);
    }

    public FrmFiles(Path path) {
        this();
        this.path = path;
        file = new ArrayList<>();
        lastPaths = new ArrayList<>();
        nextPath = new ArrayList<>();
        createFileExplorer(path);
    }

    public void createFileExplorer(Path path) {
        this.path = path;
        labels = new ArrayList<>();
        labelsNames = new ArrayList<>();
        file = new ArrayList<>();
        Stream<Path> paths = (Stream) Stream.builder().build();
        try {
            if (path != null) {
                if (!PathFactory.getInstance().isFile(path)) {
                    if (Files.list(path) != null) {
                        int size = (int) Files.list(path).count();
                        if (size > 0) {
                            paths = Files.list(path);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FrmFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        Iterator<Path> itr = paths.iterator();
        int count = 0, countFileInLines = 0;
        PathFactory pathFactory = PathFactory.getInstance();
        while (itr.hasNext()) {
            label = new JLabel();
            label.addMouseListener(this);
            Path pathReceived = itr.next();
            if (count == 7) {
                x = 10;
                y += 90;
                count = 0;
                countFileInLines++;
            }
            if (countFileInLines > 0) {
                jPanel1.setSize(jPanel1.getWidth(), jPanel1.getHeight() + 86);
                jPanel1.setPreferredSize(new Dimension(jPanel1.getWidth(),
                        jPanel1.getHeight()));
                countFileInLines = 0;
            }
            if (pathReceived != null) {
                if (pathFactory.isFile(pathReceived)) {
                    if (pathReceived.toString().endsWith(types[0])
                            || pathReceived.toString().endsWith(types[1])
                            || pathReceived.toString().endsWith(types[2])) {
                        label.setBounds(x, y, 100, 100);
                        label.setIcon(new ImageIcon(getClass()
                                .getResource("/br/sysdoc/icones/imagem_icon.png")));
                        String txt = pathReceived.getFileName().toString();
                        if (txt.length() >= 12) {
                            label.setText(txt.substring(0, 11) + "...");
                        } else {
                            label.setText(pathReceived.getFileName().toString());
                        }
                        label.setHorizontalTextPosition(SwingConstants.CENTER);
                        label.setVerticalTextPosition(SwingConstants.BOTTOM);
                        labelsNames.add("jpg");
                        labels.add(label);
                        file.add(pathReceived.toFile());
                    }
                } else {
                    label.setBounds(x, y, 100, 100);
                    label.setIcon(new ImageIcon(getClass()
                            .getResource("/br/sysdoc/icones/imagem_folder_icon.png")));
                    String name = pathReceived.getFileName().toString();
                    if (name.length() >= 12) {
                        label.setText(name.substring(0, 11) + "...");
                    } else {
                        label.setText(pathReceived.getFileName().toString());
                    }
                    label.setHorizontalTextPosition(SwingConstants.CENTER);
                    label.setVerticalTextPosition(SwingConstants.BOTTOM);
                    labelsNames.add(name);
                    labels.add(label);
                    file.add(pathReceived.toFile());
                }
            }
            count++;
            x += 80;
            jPanel1.add(label);
        }
        x = 10;
        y = 15;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sysdoc - File Explorer");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(702, 455));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 702, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 455, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("<");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText(">");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (lastPaths != null) {
            jButton4.setEnabled(true);
            if (countPathsLast > 0) {
                countPathsLast = countPathsLast == 1
                        ? countPathsLast - 1
                        : lastPaths.size() - 1;
            }

            if (countPathsLast == 0) {
                jButton3.setEnabled(false);
            }
            countPathsNext++;
            nextPath.add(path);
            openFolder(lastPaths.get(countPathsLast).toString());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (nextPath != null) {

            openFolder(nextPath.get(countPathsNext > 0
                    ? countPathsNext - 1 : countPathsNext).toString());
            countPathsNext--;
            if (countPathsNext == 0) {
                jButton4.setEnabled(false);
                nextPath.removeAll(nextPath);
            }
            jButton3.setEnabled(true);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(FrmFiles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmFiles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmFiles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmFiles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmFiles().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        Iterator<String> iterator = labelsNames.iterator();
        String name = "";
        try {
            for (int k = 0; iterator.hasNext(); k++) {
                name = iterator.next();
                if (me.getSource().equals(labels.get(k))) {
                    if (me.getClickCount() > 1) {
                        if (name.endsWith(types[0]) || name.endsWith(types[1])
                                || name.endsWith(types[2])) {
                            Desktop.getDesktop().open(file.get(k));
                        } else {
                            if (!lastPaths.contains(path)) {
                                lastPaths.add(path);
                            }
                            countPathsLast++;
                            jButton3.setEnabled(true);
                            openFolder(path.toString().concat("/").concat(name));
                        }
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FrmFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    public void openFolder(String folderName) {
        jPanel1.removeAll();
        jPanel1.setSize(jPanel1.getWidth(), jPanel1.getHeight() - 8600);
        jPanel1.setPreferredSize(new Dimension(jPanel1.getWidth(),
                jPanel1.getHeight()));
        String temp = folderName.replaceAll("[/\\\\]", "/");
        createFileExplorer(Paths.get(temp));
    }
}
