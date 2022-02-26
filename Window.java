package sliceoflife;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class Window {

	private JFrame frame;
	private Image image;
	
	// Pages of the app
	private JPanel today_page, enter_new_slice_page;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		// Create Image object to import resources
		image = new Image();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Initialize frame for this window
		frame = new JFrame();
		frame.setBounds(100, 100, 550, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Initial pie chart
		Slice[] slices = {new Slice(10, Color.BLUE), new Slice(20, Color.RED), new Slice(30, Color.PINK)};
		frame.getContentPane().setLayout(null);
		
		PieChart chart = new PieChart(slices, new Rectangle(100,200,340,340));
		chart.setBounds(0, 21, 534, 661);
		
		frame.getContentPane().add(chart);
		
		// Initialize all pages of app, and hide all except today page
		today_page = init_today_page();
		enter_new_slice_page = init_enter_new_slice();
		frame.getContentPane().add(today_page);
		frame.getContentPane().add(enter_new_slice_page);
		enter_new_slice_page.setVisible(false);
		
		
		// TODO mouse listeners will toggle visibility of various panels
	}
	
	/**
	 * Initialize the first page of the app, the "today" page
	 */
	private JPanel init_today_page() {
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 534, 661);
		panel.setLayout(null);
		
		// Enter a slice button
		JLabel enter_button = new JLabel("enter a slice");
		enter_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				today_page.setVisible(false);
				enter_new_slice_page.setVisible(true);
			}
		});
		enter_button.setBounds(264, 574, 250, 60);
		panel.add(enter_button);
		
		// Background image
		JLabel background = new JLabel();
		background.setBounds(0, 0, 550, 700);
		background.setIcon(image.bg);
		panel.add(background);
		
		return panel;
	}
	
	/**
	 * Displays the "enter new slice" page, where the user can update their "today" pie chart slices
	 */
	private JPanel init_enter_new_slice() {
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 534, 661);
		panel.setLayout(null);
		
		JLabel enter_button = new JLabel("hola");
		enter_button.setBounds(264, 574, 250, 60);
		panel.add(enter_button);
		return panel;
	}
}


















