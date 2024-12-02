package org.example.laboratoire5.control;

import org.example.laboratoire5.view.Application;
import org.example.laboratoire5.view.View;

import javax.swing.*;
import java.io.*;
import java.util.Stack;

public class GestionnaireSauvegarde {
    private Stack<Memento> history;

    public GestionnaireSauvegarde() {
        this.history = new Stack<>();
    }

    public void saveState(View view) {
        Memento memento = new Memento(view);
        history.push(memento);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("savefile.ser"))) {
            out.writeObject(memento);
            System.out.println("State saved.");
        } catch (IOException e) {
            System.err.println("Error saving state: " + e.getMessage());
        }
    }

    public void loadState(View view) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a Saved View File");
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

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(selectedFile))) {
                Memento memento = (Memento) in.readObject();
                // Apply the deserialized state to the View
                view.setImage(memento.getState().getImage());
                view.redraw();
                System.out.println("State loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading state: " + e.getMessage());
            }
        } else {
            Application.Log.warning("File selection canceled.");
        }
    }

    public void undo(View view) {
        if (!history.isEmpty()) {
            Memento memento = history.pop();
            view.setImage(memento.getState().getImage());
            view.redraw();
            System.out.println("Undo successful.");
        } else {
            System.out.println("No previous state to undo.");
        }
    }
}
