package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JInternalFrame;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;

public class MainLayout extends JFrame {
	private JTextField frameTitle;
	
	private JLabel labelTextTimestamp = new JLabel("Timestamp");
	private JLabel labelValTimestamp = new JLabel("");
	private JLabel labelTextCity = new JLabel("City");
	private JLabel labelValCity = new JLabel("");
	private JLabel labelTextTemp = new JLabel("Temperature");
	private JLabel labelValTemp = new JLabel("");
	private JLabel labelTextHumidity = new JLabel("Humidity");
	private JLabel labelValHumidity = new JLabel("");
	public JButton buttonDownloadData = new JButton("Pobierz");
		
	public void setTimestamp(String time) {
		labelValTimestamp.setText(time);
	}
	
	public void setCity(String city) {
		labelValCity.setText(city);
	}
	
	public void setTemp(String temp) {
		labelValTemp.setText(temp);
	}
	
	public void setHumidity(String scattered) {
		labelValHumidity.setText(scattered);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainLayout frame = new MainLayout();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainLayout() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 360, 191);
		getContentPane().setLayout(null);
		
		frameTitle = new JTextField();
		frameTitle.setHorizontalAlignment(SwingConstants.CENTER);
		frameTitle.setText("Parse data from API");
		frameTitle.setBounds(21, 11, 300, 20);
		getContentPane().add(frameTitle);
		frameTitle.setColumns(10);
		
		//JLabel labelTextTimestamp = new JLabel("Timestamp");
		labelTextTimestamp.setBounds(10, 42, 67, 14);
		getContentPane().add(labelTextTimestamp);
		
		//JLabel labelValTimestamp = new JLabel("");
		labelValTimestamp.setBounds(108, 42, 240, 14);
		getContentPane().add(labelValTimestamp);
		
		//JLabel labelTextCity = new JLabel("City");
		labelTextCity.setBounds(10, 64, 46, 14);
		getContentPane().add(labelTextCity);
		
		//JLabel labelValCity = new JLabel("");
		labelValCity.setBounds(108, 64, 126, 14);
		getContentPane().add(labelValCity);
		
		//JLabel labelTextTemp = new JLabel("Temperature");
		labelTextTemp.setBounds(10, 89, 88, 14);
		getContentPane().add(labelTextTemp);
		
		//JLabel labelValTemp = new JLabel("");
		labelValTemp.setBounds(108, 89, 126, 14);
		getContentPane().add(labelValTemp);
		
		//JLabel labelTextHumidity = new JLabel("Humidity");
		labelTextHumidity.setBounds(10, 114, 67, 14);
		getContentPane().add(labelTextHumidity);
		
		//JLabel labelValHumidity = new JLabel("");
		labelValHumidity.setBounds(108, 114, 126, 14);
		getContentPane().add(labelValHumidity);
		
		buttonDownloadData.setBounds(232, 110, 89, 23);
		getContentPane().add(buttonDownloadData);
	}
}
