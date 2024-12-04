package org.example.laboratoire5.control;

import org.example.laboratoire5.view.Application;
import org.example.laboratoire5.view.View;
import org.example.laboratoire5.view.ViewMenuWrapper;

import javax.swing.*;
import java.io.*;
import java.util.Stack;

public class GestionnaireSauvegarde {
    private Stack<Memento> history;

    public GestionnaireSauvegarde() {
        this.history = new Stack<>();
    }

    public void saveState(ViewMenuWrapper view) {
        Memento memento = new Memento(view);
        history.push(memento);

        File selectedFile;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Location to Save the File");
        fileChooser.setSelectedFile(new File("serializedObject.ser"));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() || file.getName().toLowerCase().endsWith(".ser");
            }

            @Override
            public String getDescription() {
                return "Serialized Files (*.ser)";
            }
        });

        String userHome = System.getenv("USERPROFILE");
        File initialFolder = new File(userHome + File.separator + "Documents");
        if (initialFolder.exists() && initialFolder.isDirectory()) {
            fileChooser.setCurrentDirectory(initialFolder);
        } else {
            Application.Log.warning("Default folder does not exist. Using system default.");
        }

        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();

            // Ensure the file has the ".ser" extension
            if (!selectedFile.getName().toLowerCase().endsWith(".ser")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".ser");
            }

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(selectedFile))) {
                out.writeObject(memento);
                System.out.println("State saved to: " + selectedFile.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error saving state: " + e.getMessage());
            }
        } else {
            Application.Log.warning("Save operation canceled.");
        }
    }
}
