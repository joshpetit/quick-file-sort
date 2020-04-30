package net.ddns.gngw.qfsmanager;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame {
    private ControlPanel controlPanel;
    private PathsPanel pathsPanel;

    public MainFrame() {
        super("Quick File Sort :: Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(600, 550);
        
        controlPanel = new ControlPanel();
        controlPanel.setControls(new Controls());

        pathsPanel = new PathsPanel();

        add(controlPanel, BorderLayout.SOUTH);
        add(pathsPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private class Controls implements Controllable {
        @Override
        public void save() {
            JFileChooser fileChooser = new JFileChooser();
            JOptionPane.showMessageDialog(MainFrame.this, "Make Sure to Save in the same path as a quickfilesort Jar", "Info", JOptionPane.INFORMATION_MESSAGE);
            fileChooser.setDialogTitle("Select Location to Save (settings.conf)");
            fileChooser.setSelectedFile(new File("settings.conf"));

            fileChooser.showSaveDialog(fileChooser);
            if (fileChooser.getSelectedFile() != null){
                pathsPanel.save(fileChooser.getSelectedFile());
            }
        }

        @Override
        public void load() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Properties File");
            fileChooser.showOpenDialog(fileChooser);
            if (fileChooser.getSelectedFile() != null) {
                Properties props = new Properties();
                try {
                    props.load(new FileInputStream(fileChooser.getSelectedFile()));
                    pathsPanel.setProperties(props, fileChooser.getSelectedFile().getAbsolutePath());
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Error Reading File", "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void help() {
            JOptionPane.showMessageDialog(MainFrame.this, "The first field defines the extension, the second field defines where files ending in that extension will go.\nAdd an underscore at the end to have the program remove the extension after moving\n(i.e giving the extension .txt.folder1_ so it may go to a certain folder and removes .folder1_"
            , "Help", JOptionPane.INFORMATION_MESSAGE);
            

        }
        
    }
}