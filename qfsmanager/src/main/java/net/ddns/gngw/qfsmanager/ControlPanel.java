package net.ddns.gngw.qfsmanager;

import javax.swing.JButton;
import javax.swing.JPanel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

public class ControlPanel extends JPanel {
    //Use annonymous inner classes that call controls methods  

    private Controllable controls;
    private JButton save;
    private JButton load;
    private JButton help;

    ControlPanel() {
        super(new FlowLayout());
        save = new JButton("Save");
        load = new JButton("Load");
        help = new JButton("Help");

        save.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0) {controls.save();}
        });

        load.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0) {controls.load();}
        });

        help.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                controls.help();

            }
            
        });

        add(save);
        add(load);
        add(help);

    }

	public void setControls(Controllable controls) {
        this.controls = controls;
	}



}
