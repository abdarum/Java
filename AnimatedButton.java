package button_2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import java.lang.Math;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;

import java.util.Timer;
import java.util.TimerTask;

//import javax.swing.Timer;


public class AnimatedButton {
	
    private static JButton b1 = new JButton("Play");
    private static JFrame f = new JFrame();
    private static JPanel p = new JPanel();
    private static Random randomnumber = new Random();

	private static int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	private static int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	private static int buttonHeight = 64;//b1.getHeight();
	private static int buttonWidth = 128;//b1.getWidth();
	private static int finalButtonX = 0;
	private static int finalButtonY = 0;
    private static int animationStep=250;
	private static int fps = 30;
	
	private static int currentButtonX;
	private static int currentButtonY;
	private static int vectorX;
	private static int vectorY;
	

	
	public static void main(String[]args) {
		
		p.setBackground(new Color(0,0,0,0));
		f.setUndecorated(true);
		f.setVisible(true);
		f.setBackground(new Color(0,0,0,0)); //or 0.0f
		
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p.add(b1);
		f.add(p);
		b1.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				currentButtonX = b1.getLocation().x;
				currentButtonY = b1.getLocation().y;
            	
				//System.out.println("X "+currentButtonX+" Y "+currentButtonY);
				f.repaint();
				finalButtonX = randomnumber.nextInt(screenWidth-buttonWidth);
	        	finalButtonY = randomnumber.nextInt(screenHeight-buttonHeight);

	        	vectorX = finalButtonX - currentButtonX;
        		vectorY = finalButtonY - currentButtonY;
        		
        		System.out.println("X "+vectorX + " Y "+vectorY);
        		double scaleVector = (float) ((float) Math.pow(animationStep,2) / (Math.pow(vectorX, 2)+Math.pow(vectorY, 2)));
        		vectorX = (int) (vectorX*scaleVector);
        		vectorY = (int) (vectorY*scaleVector);
        		System.out.println("X "+vectorX + " Y "+vectorY);
        		
        		Timer timer = new Timer();
		    		timer.scheduleAtFixedRate(new TimerTask() {
		    			@Override
		    			public void run() {

							if(Math.abs((finalButtonX - currentButtonX)) < Math.abs(vectorX) || Math.abs((finalButtonY - currentButtonY)) < Math.abs(vectorY) ) {
					            b1.setLocation(finalButtonX ,finalButtonY);		 
					            timer.cancel();
					            timer.purge();
				        	}
				        	else {
				        		b1.setLocation(currentButtonX+vectorX, currentButtonY+vectorY);	        		
				        	}
			        		currentButtonX = b1.getLocation().x;
							currentButtonY = b1.getLocation().y;	
							f.repaint();
		    			}
		    		}
		    		,100, (1000/fps));
		    		
        		       		
		}});
		
	}
}
