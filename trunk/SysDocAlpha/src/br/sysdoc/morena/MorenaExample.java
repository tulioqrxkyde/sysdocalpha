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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import eu.gnome.morena.Camera;
import eu.gnome.morena.Configuration;
import eu.gnome.morena.Device;
import eu.gnome.morena.Manager;
import eu.gnome.morena.Scanner;

/**
 * MorenaExample demonstrates basic use of the Morena Framework in an
 * application environment. Process of scanning is asynchronous and application
 * is provided with the file containing an image.
 * 
 * Requirements: 1. Java 1.5 or newer 2. Morena7 for image acquisition
 * 
 */

public class MorenaExample {
  static Manager manager;

  public static void main(String args[]) {
    MorenaExample example = new MorenaExample();
    System.out.println("MorenaExample("+Arrays.toString(args)+") ... started at "+new Date()); // Loads native library and initialize logging
    try {
      Configuration.setLogLevel(Level.ALL);               // setting max log detail
//      Configuration.addDeviceType(".*fficejet.*", true);  // workaround for HP scanners
      manager=Manager.getInstance();
      if (args.length == 0)
        example.simpleScan();
      else if (args[0].equalsIgnoreCase("batch"))
        example.batchScan();
    } catch (Throwable ex) {
      ex.printStackTrace();
    }
    finally {
      manager.close();
    }
    System.out.println("Finished.");
  }

  /**
   * This example method shows how to scan single image from selected device and
   * default functional unit (flatbed)
   * 
   * @throws Exception
   */
  private void simpleScan() throws Exception {
    // Selecting a device (1st device available is selected)
    List<? extends Device> devices = manager.listDevices();
    if (devices.size() > 0) {
      Device device = devices.get(0);

      if (device != null) {

        // for scanner device set the scanning parameters
        if (device instanceof Scanner) {
          Scanner scanner = (Scanner) device;
          scanner.setMode(Scanner.RGB_8);
          scanner.setResolution(75);
          scanner.setFrame(0, 0, 622, 874);   // A4 8.3 x 11.7 ( 622 x 877 at 75 DPI) (for Lide110 - 622 x 874)
        } else
        if (device instanceof Camera) {
          // Camera specific settings
        }

        // start scan using default (0) functional unit
        BufferedImage bimage = SynchronousHelper.scanImage(device);
        System.out.println("scanned image info: size=(" + bimage.getWidth() + ", " + bimage.getHeight() + ")   bit mode=" + bimage.getColorModel().getPixelSize());
        // do image processing if necessary
        // ...
        Thread.sleep(30000);
      }
    } else
        System.out.println("No device connected!!!");
  }

  
  /**
   * This example method shows how to scan multiple images from selected device with ADF (automatic document feeder).
   * If ADF unit is not found or recognized the default unit (0) is used.
   * Scanned image files are converted to jpeg format and placed in temporary directory (System.getProperty("java.io.tmpdir"))
   * 
   * @throws Exception
   */
  private void batchScan() throws Exception {
    List<? extends Device> devices = manager.listDevices();
    List<File> filesToDelete = new ArrayList<File>();
    if (devices.size() > 0) {
      Device device = devices.get(0);

      if (device != null) {

        // for scanner device set the scanning parameters
        if (device instanceof Scanner) {
          Scanner scanner = (Scanner) device;
          scanner.setMode(Scanner.RGB_8);
          scanner.setResolution(200);
          // find feeder unit
          int feederUnit=scanner.getFeederFunctionalUnit();
          System.out.println("Feeder unit : "+(feederUnit>=0?feederUnit:"none found - trying 0"));
          if (feederUnit<0)
            feederUnit=0; // 0 designates a default unit
          if (scanner.isDuplexSupported())
            scanner.setDuplexEnabled(true);

          int pageNo=1;
          ScanSession session=new ScanSession();
          // start batch scan
          try {
              session.startSession(device, feederUnit);
              File file=null; 
              while (null!=(file=session.getImageFile())) {
                filesToDelete.add(file);
                // image processing - image file is transformed to JPEG format
                BufferedImage image = ImageIO.read(file);
                System.out.println("scanned image "+file.getPath()+" : size=(" + image.getWidth() + ", " + image.getHeight() + ")   bit mode=" + image.getColorModel().getPixelSize());
                if (!"jpg".equalsIgnoreCase(ScanSession.getExt(file))) { // convert to jpeg if not already in jpeg format
                  File jpgFile=new File(file.getParent(), "Morena_example_img_"+(pageNo++)+".jpg");
                  FileOutputStream fout=new FileOutputStream(jpgFile);
                  ImageIO.write(image, "jpeg", fout);
                  fout.close();
                  filesToDelete.add(jpgFile);
                }
              }

          } catch (Exception ex) { // check if error is related to empty ADF
            if (session.isEmptyFeeder())
              System.out.println("No more sheets in the document feeder");
            else
              ex.printStackTrace();
          }
        }
      }
      System.out.println("Scanning completed - check the images ... waiting 120s");
      Thread.sleep(120000);

      // all files are deleted on the exit
      for (File file : filesToDelete)
      { file.deleteOnExit();
      }
    } else
        System.out.println("No device connected!!!");
  }
  
}
