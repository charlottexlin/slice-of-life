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
import javax.swing.SwingConstants;
import java.awt.Font;

public class Window {

	private JFrame frame;
	private Image image;
	private Rectangle chart_area;
	
	// Pages of the app
	private JPanel[] pages = new JPanel[4];
	/* 0: today
	 * 1: enter new slice
	 * 2: goals
	 * 3: set goals
	 */
	
	// Pie charts
	private Slice[] today_slices = {new Slice(10, Color.BLUE), new Slice(20, Color.RED), new Slice(30, Color.PINK)};
	private Slice[] goal_slices = {new Slice(20, Color.BLUE), new Slice(30, Color.RED), new Slice(10, Color.PINK)};

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
		
		// Set area for pie chart to be in
		chart_area = new Rectangle(100,200,340,340);
		
		// Initialize GUI
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
		frame.getContentPane().setLayout(null);

		// "Today" navigation button TODO
		JLabel navigate_today = new JLabel("Today");
		navigate_today.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				show_page(0);
			}
		});
		navigate_today.setBounds(70, 140, 190, 50);
		frame.getContentPane().add(navigate_today);
		
		// "Goals" navigation button
		JLabel navigate_goals = new JLabel("Goals");
		navigate_goals.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				show_page(2);
			}
		});
		navigate_goals.setBounds(280, 140, 190, 50);
		frame.getContentPane().add(navigate_goals);
		
		// Initialize all pages of app, and hide all except today page
		pages[0] = init_today_page();
		pages[1] = init_enter_new_slice();
		pages[2] = init_goals_page();
		pages[3] = init_set_goals();
		
		for (int i = 0; i < 4; i++) {
			frame.getContentPane().add(pages[i]);
		}
		
		pages[1].setVisible(false);
				
	}
	
	/**
	 * Initialize the first page of the app, the "today" page
	 */
	private JPanel init_today_page() {
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 534, 661);
		panel.setLayout(null);
		
		// Pie chart
		PieChart chart = new PieChart(today_slices, chart_area);
		chart.setBounds(0, 21, 534, 661);
		panel.add(chart);
		
		// Enter a slice button
		JLabel enter_button = new JLabel("enter a slice");
		enter_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				show_page(1);
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
		
		JLabel title_label = new JLabel("enter a slice");
		title_label.setFont(new Font("Montserrat Light", Font.PLAIN, 46));
		title_label.setHorizontalAlignment(SwingConstants.CENTER);
		title_label.setBounds(105, 200, 324, 49);
		panel.add(title_label);
		
		// Background image
		JLabel background = new JLabel();
		background.setBounds(0, 0, 550, 700);
		background.setIcon(image.bg);
		panel.add(background);
		
		return panel;
	}
	
	/**
	 * Displays the "goals" page, where the user can see their goal pie chart
	 */
	private JPanel init_goals_page() {
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 534, 661);
		panel.setLayout(null);
		
		// Pie chart
		PieChart chart = new PieChart(goal_slices, chart_area);
		chart.setBounds(0, 21, 534, 661);
		panel.add(chart);
		
		// Set goals button
		JLabel set_button = new JLabel("set goals");
		set_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				show_page(3);
			}
		});
		set_button.setBounds(264, 574, 250, 60);
		panel.add(set_button);

		// Background image
		JLabel background = new JLabel();
		background.setBounds(0, 0, 550, 700);
		background.setIcon(image.bg);
		panel.add(background);
				
		return panel;
	}
	
	/**
	 * Displays the "set goals" page, where the user can set their goal percentages
	 */
	private JPanel init_set_goals() {
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 534, 661);
		panel.setLayout(null);
		
		JLabel title_label = new JLabel("set your goals");
		title_label.setFont(new Font("Montserrat Light", Font.PLAIN, 46));
		title_label.setHorizontalAlignment(SwingConstants.CENTER);
		title_label.setBounds(105, 200, 324, 49);
		panel.add(title_label);
		
		// Background image
		JLabel background = new JLabel();
		background.setBounds(0, 0, 550, 700);
		background.setIcon(image.bg);
		panel.add(background);
		
		return panel;
	}
	
	/**
	 * Show the given page and hide all other pages
	 * 
	 * @param page_to_show the index of the page that should be shown
	 */
	private void show_page(int page_to_show) {
		pages[page_to_show].setVisible(true);
		for (int i = 0; i < 4; i++) {
			if (i != page_to_show)
				pages[i].setVisible(false);
		}
	}
}


















