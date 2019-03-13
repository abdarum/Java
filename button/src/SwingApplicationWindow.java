package button;

import javax.swing.*;

import java.awt.Toolkit;
import java.awt.event.*;
//import java.awt.*;
import java.util.*;
import java.awt.Color;


import static java.awt.GraphicsDevice.WindowTranslucency.*;

public class SwingApplicationWindow {
    private static JButton b1 = new JButton("Play");
    private static JFrame f = new JFrame();
    private static JPanel p = new JPanel();
    private static Random randomnumber = new Random();
    private static int location1=50;
	private static int location2=20;
	private static int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	private static int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	private static int buttonHeight = b1.getHeight();
	private static int buttonWidth = b1.getWidth();
    public static void main(String[]args){
        

    	//f.setOpacity((float) 1);
        p.setBackground(new Color(0,0,0,0));
        //f.setOpacity((float) 0.10);
    	f.setUndecorated(true);
        f.setVisible(true);
    	//b1.setBackground(new Color(255,0,0,255));
    	f.setBackground(new Color(0.0f,0.0f,0.0f,0.0f));

        
        //f.setSize(JFrame.MAXIMIZED_BOTH,JFrame.MAXIMIZED_BOTH);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//f.setUndecorated(true); 
		//f.setLayout(null); 
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           
        p.add(b1);
        f.add(p);
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) { 
            	f.repaint();
                b1.setLocation(randomnumber.nextInt(screenWidth-buttonWidth),randomnumber.nextInt(screenHeight-buttonWidth));
                System.out.println(b1.getLocation());
            }
        });
    }
}