package org.example.laboratoire5.control;

import org.example.laboratoire5.model.Image;
import org.example.laboratoire5.view.Application;

import javax.swing.*;
import java.io.File;
import java.util.Deque;
import java.util.LinkedList;

public class GestionnaireChargementImage {
    private Deque<Image> imageHistory = new LinkedList<>();

    public void addToImageHistory(Image image){
        imageHistory.push(image);
    }

    public Image removeFromImageHistory() {
        if (!imageHistory.isEmpty())
            return imageHistory.pop();
        return null;
    }

    public Image selectImageFromFileExplorer() {
        JFileChooser fileChooser = new JFileChooser();
        String userHome = System.getenv("USERPROFILE");
        File initialFolder = new File(userHome + File.separator + "Pictures");

        if (initialFolder != null && initialFolder.exists() && initialFolder.isDirectory()) {
            fileChooser.setCurrentDirectory(initialFolder);
        } else {
            Application.Log.warning("Start folder does not exist or is not a directory. Using default.");
        }

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
            System.out.println(fileChooser.getSelectedFile());
            return new Image(selectedFile.getAbsolutePath());
        } else {
            Application.Log.severe("Image selection canceled.");
        }

        return null;
    }
}
