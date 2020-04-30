package net.ddns.gngw.qfsmanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public class PathsPanel extends JPanel {
    private JScrollPane pathsPane;
    private ArrayList<Path> pathsList;
    private Properties properties;
    private JPanel pathsPanel;

    // Folder Path Panel
    private JPanel folderPanel;
    private JLabel folderPath;

    public PathsPanel() {
        this.setLayout(new BorderLayout());
        folderPanel = new JPanel(new FlowLayout());
        folderPath = new JLabel("Click Load to Load a settings file or save to create a new one");
        folderPath.setToolTipText("The Settings File");

        pathsList = new ArrayList<Path>();

        folderPanel.add(folderPath);
        folderPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        pathsPanel = new JPanel();
        pathsPanel.setLayout(new BoxLayout(pathsPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < 15; i++) {
            Path path = new Path();
            pathsPanel.add(path);
            pathsList.add(path);
        }

        pathsPane = new JScrollPane(pathsPanel);

        pathsList.get(0).headerSpecial();
        pathsList.get(1).pathSpecial();

        add(folderPanel, BorderLayout.NORTH);
        add(pathsPane, BorderLayout.CENTER);
    }

    public void save(File file) {
        properties = new Properties();
        String extension;
        String path;
        for (int i = 1; i < pathsList.size(); i++) {
            extension = pathsList.get(i).getExt();
            path = pathsList.get(i).getPath();

            if (!extension.isBlank() && !path.isBlank())
                properties.put(extension, path);
        }
        try {
            properties.store(new FileOutputStream(file), "If on Windows, use forward slashes.\nAdd an underscore to remove extension after moving");
            JOptionPane.showMessageDialog(PathsPanel.this, "File Saved", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(PathsPanel.this, "Error Saving File", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    public void setProperties(Properties properties, String path)
    {
        folderPath.setText("QFS Location: " + path);
        int[] count = {1};
            properties.forEach((key,value) ->
            {
                pathsList.get(count[0]).setValues((String) key, (String)value);
                count[0]++;
            });
    }

    private class Path extends JPanel
    {
        private JTextField extensionField;
        private JTextField pathField;
        private JButton browseButton;
        private JButton deleteButton;   
        private JFileChooser folderChooser;

        public Path()
        {
            super(new GridBagLayout());
            folderChooser = new JFileChooser();
            folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            folderChooser.setDialogTitle("Choose Destination Folder");

            GridBagConstraints gc = new GridBagConstraints();
            gc.fill= GridBagConstraints.BOTH;
            gc.anchor = GridBagConstraints.FIRST_LINE_START;

            extensionField = new JTextField(5);
            extensionField.setHorizontalAlignment(JTextField.CENTER);
            extensionField.setToolTipText("Add an underscore at the end to truncate extension after moving");
            gc.gridx = 0;
            gc.gridy = 0;
            gc.weightx = 5;
            add(extensionField, gc);

            pathField = new JTextField(20);
            gc.gridx = 1;
            gc.weightx = 50;
            add(pathField, gc);

            browseButton = new JButton("Browse");
            browseButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent arg0) {
                    folderChooser.showOpenDialog(browseButton);
                    if (folderChooser.getSelectedFile() != null)
                        pathField.setText(folderChooser.getSelectedFile().getAbsolutePath());
                }
                
            });
            gc.gridx = 2;
            gc.weightx =1;
            add(browseButton, gc);
            
            deleteButton = new JButton("Delete");
            deleteButton.setBackground(Color.RED);
            deleteButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    setValues("", "");
                }
            });
            gc.gridx = 3;
            gc.weightx = 1;
            add(deleteButton,gc);
        }

        public void setValues(String ext, String path)
        {
            extensionField.setText(ext);
            pathField.setText(path);
        }

        public String getExt(){return extensionField.getText();}
        public String getPath(){return pathField.getText();}

        public void pathSpecial()
        {
            extensionField.setEnabled(false);
            deleteButton.setEnabled(false);

            pathField.setHorizontalAlignment(JTextField.CENTER);
            setValues("path", "[Folder to be Organized]");
        }

        public void headerSpecial()
        {
            setValues(".extension", "Folder to Save .extension Files to");
            extensionField.setEnabled(false);

            deleteButton.setEnabled(false);
            
            browseButton.setEnabled(false);
            
            pathField.setEnabled(false);
            pathField.setHorizontalAlignment(JTextField.CENTER);
        }
    }
}
