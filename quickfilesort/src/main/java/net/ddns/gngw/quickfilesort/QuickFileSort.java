package net.ddns.gngw.quickfilesort;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;



public class QuickFileSort {
    private static Properties properties;
    private static File logFile = new File("log.txt");
    private static PrintWriter writer;
    public static void main(String[] args) {
        setProperties();
        startLogger();
        String path = properties.getProperty("path");

        //If The path is not Found Quit
        if (path == null){
            System.out.println("Path not set in Config File, Open Conf and set path=\"Directory\"");
            return;
        }

        //Gets a List of the Files
        File pathDir = new File(path);
        System.out.println("Working From Path: " + pathDir.getAbsolutePath());
        log("Working From Path: " + pathDir.getAbsolutePath());
        File[] directory = pathDir.listFiles();
        for (File file: directory)
        {
            String fileName = file.getName();
            int lastDot = fileName.lastIndexOf('.');

            //If There is No extension, Stop
            if (lastDot == -1)
                System.out.println("No Extension Found for: " + fileName + "\n");
            else{
                String extension = fileName.substring(lastDot, fileName.length());
                String destination = properties.getProperty(extension);

                //If The Extension is binded to a destination, continue
                if(destination != null)
                {
                    //If The File Terminates in an '_', it will rename the file to Remove the Extension  
                    if (fileName.charAt(fileName.length()-1) == '_') {
                        fileName = fileName.substring(0, lastDot);
                    }
                
                //The full path of the destination is Set
                destination += "/" + fileName;
                String response = move(file, destination);
                log(response);
                System.out.println(response);
            }
            else
                System.out.printf("No Extension Directory for: %s; (extension %s not found)\n\n", file.getName(), extension);
            }
        }

        if (writer != null)
            writer.close();
    }
    public static String move(File file, String destination)
    {
        try {
            File newLocation = new File(Paths.get(destination) + "");
            Files.move(file.toPath(), newLocation.toPath());
            return String.format("Success: %s Moved to %s", file.getAbsolutePath(), destination);
        } catch (IOException e) {
            e.printStackTrace();
            return String.format("Error: Could not move %s to %s :: file may already exist", file.getAbsolutePath(), destination);
        }  
    }

    public static void setProperties()
    {
        properties = new Properties();
        try {
            properties.load(new FileReader(new File("settings.conf")));
        } catch (FileNotFoundException e) {
            System.out.println(log("Settings not Found,\n Save a 'settings.conf' file with necessary format in same path as jar"));
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(log("Error Reading settings.conf"));
            e.printStackTrace();
        }
    }

    public static String log(String log)
    {
        if (writer != null)
        writer.println(log);
        return log;
    }

    //Only Logs the last Run
    public static void startLogger()
    {
        try {
            writer = new PrintWriter(logFile);
        } catch (IOException e) {
            System.out.println("Error Creating Log File");
            e.printStackTrace();
        }
    }


}
