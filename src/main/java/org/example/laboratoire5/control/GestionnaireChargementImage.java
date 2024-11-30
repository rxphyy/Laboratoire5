package org.example.laboratoire5.control;

import javax.swing.*;
import java.io.File;

public class GestionnaireChargementImage {

    public void selectImageFromFileExplorer() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                }
                String name = file.getName().toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".png");
            }

            @Override
            public String getDescription() {
                return "Image Files (*.jpg, *.png, *.gif)";
            }
        });

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Picked new image: " + selectedFile.getAbsolutePath());
        } else {
            System.out.println("Image selection canceled.");
        }
    }

}
