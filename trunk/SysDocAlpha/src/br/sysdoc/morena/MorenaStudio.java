package br.sysdoc.morena;

/*
 * Morena 7 - Image Acquisition Framework
 *
 * Copyright (c) 1999-2011 Gnome spol. s r.o. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Gnome spol. s r.o. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Gnome.
 */
/**
 * SimpleExample demonstrates use of the Morena Framework in both application
 * and applet environment. Upload action cant be used if it is invoked from
 * local filesystem.
 *
 * Requirements: 1. Java2 1.5 or newer 2. Morena7 for image acquisition
 *
 */
import br.sysdoc.telas.FrmScannerSalvar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;

import eu.gnome.morena.Configuration;
import eu.gnome.morena.Device;
import eu.gnome.morena.DeviceListChangeListener;
import eu.gnome.morena.Manager;
import eu.gnome.morena.Scanner;
import eu.gnome.morena.TransferListener;

@SuppressWarnings("serial")
public class MorenaStudio extends JApplet {
    
    static private Manager manager;
    
    static {
        System.err.println("MorenaStudio started at " + (new Date()));
    }
    
    public static class MainPanel extends JPanel implements DeviceListChangeListener {
        
        private JTextField status = new JTextField();
        private ImagePanel selected = null;
        private SaveImageAction saveImageAction;
        private CancelAction cancelAction;
        private UploadImageAction uploadImageAction;
        private MouseListener mouseListener = new MouseListener();
        private boolean hasServer = false;
        private URL documentBase = null;
        private Scanner scanner = null;
        
        public class RemoveAllAction extends AbstractAction implements Runnable {
            
            RemoveAllAction() {
                super("Remover todos");
            }
            
            public synchronized void actionPerformed(ActionEvent event) {
                new Thread(this).start();
            }
            
            public synchronized void run() {
                removeAll();
                select(null);
                repaint();
            }
        }
        
        private class AcquireImageAction extends AbstractAction implements TransferListener {
            
            AcquireImageAction() {
                super("Digitalizar");
            }
            
            public synchronized void actionPerformed(ActionEvent event) {
                try {
                    status.setText("Processando ...");
                    Device device = manager.selectDevice(MainPanel.this);
                    if (device != null) {
                        if (device instanceof Scanner) {
                            scanner = (Scanner) device;
                            if (scanner.setupDevice(MainPanel.this)) {
                                setEnabled(false);
                                cancelAction.setEnabled(true);
                                scanner.startTransfer(this);
                            }
                        } else {
                            scanner = null;
                            device.startTransfer(this);
                        }
                        status.setText("Selected " + device + "  ...");
                    } else {
                        status.setText("Falha, Nenhum dispositivo conectado ...");
                    }
                } catch (Throwable exception) {
                    JOptionPane.showMessageDialog(MainPanel.this, exception.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                    exception.printStackTrace();
                    status.setText("Falhou, tente novamente ...");
                    setEnabled(true);
                    cancelAction.setEnabled(false);
                }
            }
            
            public void transferDone(File file) {
                try {
                    BufferedImage image = ImageIO.read(file);
                    if (image != null) {
                        ImagePanel imagePanel = new ImagePanel(image);
                        MainPanel.this.add(imagePanel);
                        select(imagePanel);
                        int size = (int) Math.round(Math.sqrt(getComponentCount()));
                        setLayout(new GridLayout(size, size));
                        status.setText("Done [" + file.getAbsolutePath() + "]...");
                        validate();
                    } else {
                        status.setText("Done [" + file.getAbsolutePath() + "] - can not display this image type");
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                setEnabled(true);
                cancelAction.setEnabled(false);
            }
            
            public void transferFailed(int code, String message) {
                status.setText(message + " [0x" + Integer.toHexString(code) + "]");
                setEnabled(true);
                cancelAction.setEnabled(false);
            }
            
            public void transferProgress(int percent) {
                status.setText(percent + "%");
            }
        }
        
        private class CancelAction extends AbstractAction {
            
            CancelAction() {
                super("Cancelar");
                setEnabled(false);
            }
            
            public synchronized void actionPerformed(ActionEvent event) {
                scanner.cancelTransfer();
            }
        }
        
        private class SaveImageAction extends AbstractAction implements Runnable {
            
            private class Filter extends FileFilter {
                
                String type;
                
                Filter(String type) {
                    this.type = type;
                }
                
                public boolean accept(File file) {
                    return file.getName().endsWith(type);
                }
                
                public String getDescription() {
                    return type + " Files";
                }
            }
            
            SaveImageAction() {
                super("Salvar arquivo");
            }
            
            public void actionPerformed(ActionEvent event) {
                new Thread(this).start();
            }
            
            public synchronized void run() {
                try {
                    status.setText("Processando ...");
                    BufferedImage bufferedImage = selected.getImage();
                    new FrmScannerSalvar(bufferedImage).setVisible(true);
                } catch (Throwable exception) {
                    JOptionPane.showMessageDialog(MainPanel.this, exception.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                    exception.printStackTrace();
                    status.setText("Falha, tente novamente ...");
                }
            }
            
            public boolean isEnabled() {
                return selected != null;
            }
        }
        
        private class UploadImageAction extends AbstractAction implements Runnable {
            
            UploadImageAction() {
                super("Fazer Upload");
                
            }
            
            public void actionPerformed(ActionEvent event) {
                new Thread(this).start();
            }
            
            public synchronized void run() {
                try {
                    status.setText("Working ...");
                    BufferedImage bufferedImage = selected.getImage();
                    ByteArrayOutputStream tmp = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, "jpg", tmp);
                    tmp.close();
                    int contentLength = tmp.size();
                    if (contentLength > 1024 * 1024) {
                        throw new Exception("Image is too big to upload");
                    }
                    URL uploadURL = new URL(documentBase, "upload.php");
                    HttpURLConnection connection = (HttpURLConnection) uploadURL.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);
                    connection.setDefaultUseCaches(false);
                    connection.setRequestProperty("content-type", "img/jpeg");
                    connection.setRequestProperty("content-length", String.valueOf(contentLength));
                    OutputStream out = connection.getOutputStream();
                    out.write(tmp.toByteArray());
                    out.close();
                    InputStream in = connection.getInputStream();
                    int c;
                    while ((c = in.read()) != -1) {
                        System.err.write(c);
                    }
                    in.close();
                    URL imageURL = new URL(documentBase, connection.getHeaderField("file-name"));
                    status.setText("Done - image is uploaded to " + imageURL + " (for at least 5 minutes) ...");
                } catch (Throwable exception) {
                    JOptionPane.showMessageDialog(MainPanel.this, exception.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                    exception.printStackTrace();
                    status.setText("Failed, try again ...");
                }
            }
            
            public boolean isEnabled() {
                return hasServer && selected != null;
            }
        }
        
        private class MouseListener extends MouseAdapter {
            
            public void mouseClicked(MouseEvent event) {
                select((ImagePanel) event.getComponent());
            }
        }
        
        private class ImagePanel extends JPanel {
            
            private BufferedImage image;
            int imageWidth;
            int imageHeight;
            
            ImagePanel(BufferedImage image) {
                this.setBackground(Color.WHITE);
                this.setForeground(Color.WHITE);
                super.setBackground(Color.WHITE);
                super.setForeground(Color.WHITE);
                this.image = image;
                imageWidth = image.getWidth();
                imageHeight = image.getHeight();
                addMouseListener(mouseListener);
            }
            
            public BufferedImage getImage() {
                return image;
            }
            
            public void paint(Graphics g) {
                super.paint(g);
                int panelWidth = getWidth() - 6;
                int panelHeight = getHeight() - 6;
                double horizontalRatio = (double) panelWidth / imageWidth;
                double verticalRatio = (double) panelHeight / imageHeight;
                if (horizontalRatio > verticalRatio) {
                    g.drawImage(image, (int) (panelWidth - imageWidth * verticalRatio) / 2 + 3, 3, (int) (imageWidth * verticalRatio), (int) (imageHeight * verticalRatio), this);
                } else {
                    g.drawImage(image, 3, 3, (int) (imageWidth * horizontalRatio), (int) (imageHeight * horizontalRatio), this);
                }
            }
            
        }
        
        private class ToolBar extends JToolBar {
            
            ToolBar() {
//        List<Device> devices=manager.listDevices();
//        add(deviceCombo = new JComboBox<Device>(devices.toArray(new Device[devices.size()])));
                addSeparator();
                add(new AcquireImageAction());
                addSeparator();
                add(cancelAction = new CancelAction());
                addSeparator();
                add(saveImageAction = new SaveImageAction());
                saveImageAction.setEnabled(false);
                addSeparator();
                add(uploadImageAction = new UploadImageAction());
                uploadImageAction.setEnabled(false);
                addSeparator();
                add(new RemoveAllAction());
                setMargin(new Insets(4, 2, 2, 2));
            }
        }
        
        void select(ImagePanel image) {
            if (selected != null) {
                selected.setBorder(null);
            }
            selected = image;
            if (selected != null) {
                selected.setBorder(new LineBorder(Color.blue, 1));
                saveImageAction.setEnabled(true);
                uploadImageAction.setEnabled(hasServer);
            } else {
                saveImageAction.setEnabled(false);
                uploadImageAction.setEnabled(false);
            }
        }
        
        public void listChanged() {
            // deprecated      
        }

//    @Override
        public void deviceConnected(Device device) {
            status.setText("dispositivo conectado : " + device);
//      deviceCombo.addItem(device);
        }

//    @Override
        public void deviceDisconnected(Device device) {
            status.setText("dispositivo desconectado : " + device);
//      deviceCombo.removeItem(device);
        }
        
        MainPanel(Container container, URL documentBase) {
            this.documentBase = documentBase;
            status.setEditable(false);
            hasServer = documentBase != null && documentBase.getProtocol().indexOf("http") != -1;
            ToolBar t = new ToolBar();
            t.setForeground(Color.WHITE);
            t.setBackground(Color.WHITE);
            container.add(t, BorderLayout.NORTH);
            container.add(this, BorderLayout.CENTER);
            container.add(status, BorderLayout.SOUTH);
            
            setLayout(new GridLayout(1, 1));
            setForeground(Color.WHITE);
            setBackground(Color.WHITE);
            container.setForeground(Color.WHITE);
            
            container.setBackground(Color.WHITE);
            manager.addDeviceListChangeListener(this);
        }
        
    }
    
    public void init() {
        manager = Manager.getInstance();
        setBackground(Color.WHITE);
        setForeground(Color.WHITE);
        this.getAppletContext().getApplets().nextElement().setBackground(Color.WHITE);
        this.getAppletContext().getApplets().nextElement().setForeground(Color.WHITE);
        new MainPanel(getContentPane(), getDocumentBase());
    }
    
    @Override
    public void start() {
        System.err.println("Morena is available " + manager.available());
    }
    
    @Override
    public void stop() {
        manager.close();
    }
    
    public static void main(String args[]) {
        boolean nativeUI = false;
        if (args != null && args.length > 0) {
            if (args.length >= 1) {
                nativeUI = Boolean.parseBoolean(args[0]);
            }
        }
        System.err.println("Configuration: native UI - " + nativeUI);
        JFrame frame = new JFrame("SysDoc - Virtualização de Documento");
// -- Configuration settings      
        Configuration.setLogLevel(Level.ALL);
        Configuration.addDeviceType(".*fficejet.*", true);
        if (nativeUI) {
            Configuration.setMode(Configuration.MODE_NATIVE_UI);
        }

        // -- Manager instantiation    
        manager = Manager.getInstance();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                manager.close();
            }
        });
        new MainPanel(frame.getContentPane(), null);
        frame.setBounds(100, 100, 600, 400);
        frame.setVisible(true);
        frame.setBackground(Color.WHITE);
        frame.setForeground(Color.WHITE);
        frame.getContentPane().setForeground(Color.WHITE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLocationRelativeTo(null);
    }
}
