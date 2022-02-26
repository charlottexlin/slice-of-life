package sliceoflife;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

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
	
	// Pie charts and slices
	private ArrayList<Slice> today_slices;
	private ArrayList<Slice> goal_slices;
	private Slice today_fitness, today_study, today_rest, today_other;
	private Slice goal_fitness, goal_study, goal_rest, goal_other;
	
	// Slice colors
	private Color fitness_color = Color.BLUE, study_color = Color.GREEN, rest_color = Color.PINK, other_color = Color.WHITE;

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
	 * Create the application and initialize fields
	 */
	public Window() {
		// Create Image object to import resources
		image = new Image();
		
		// Set area for pie chart to be in
		chart_area = new Rectangle(100,200,340,340);
		
		// Initialize pie chart slice lists
		today_slices = new ArrayList<Slice>();
		goal_slices = new ArrayList<Slice>();
		
		// Set initial values for pie chart slices
		today_fitness = new Slice(0, fitness_color);
		today_study = new Slice(0, study_color);
		today_rest = new Slice(0, rest_color);
		today_other = new Slice(24, other_color);
		
		goal_fitness = new Slice(3, fitness_color);
		goal_study = new Slice(11, study_color);
		goal_rest = new Slice(10, rest_color);
		goal_other = new Slice(0, other_color);
		
		today_slices.add(today_fitness);
		today_slices.add(today_study);
		today_slices.add(today_rest);
		today_slices.add(today_other);
		
		goal_slices.add(goal_fitness);
		goal_slices.add(goal_study);
		goal_slices.add(goal_rest);
		goal_slices.add(goal_other);
		
		// Initialize GUI
		initialize();
	}

	/**
	 * Initialize the contents of the frame
	 */
	private void initialize() {
		// Initialize frame for this window
		frame = new JFrame();
		frame.setBounds(100, 100, 550, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// "Today" navigation button
		JLabel navigate_today = new JLabel("Today");
		navigate_today.setBackground(Color.GRAY);
		navigate_today.setOpaque(true);
		navigate_today.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				show_page(0);
			}
		});
		navigate_today.setBounds(70, 175, 190, 40);
		frame.getContentPane().add(navigate_today);
		
		// "Goals" navigation button
		JLabel navigate_goals = new JLabel("Goals");
		navigate_goals.setBackground(Color.GRAY);
		navigate_goals.setOpaque(true);
		navigate_goals.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				show_page(2);
			}
		});
		navigate_goals.setBounds(280, 175, 190, 40);
		frame.getContentPane().add(navigate_goals);
		
		// Initialize all pages of app
		pages[0] = init_today_page();
		pages[1] = init_enter_new_slice();
		pages[2] = init_goals_page();
		pages[3] = init_set_goals();
		
		for (int i = 0; i < 4; i++) {
			frame.getContentPane().add(pages[i]);
		}
		
		// Hide all pages except today page
		show_page(0);
	}
	
	/**
	 * Creates the "today" page, where the user can see their current pie chart for the day
	 * 
	 * @return the today page's panel
	 */
	private JPanel init_today_page() {
		JLabel enter_button = new JLabel("enter a slice");
		enter_button.setBackground(Color.GRAY);
		enter_button.setOpaque(true);
		return init_chart_page(today_slices, enter_button, 1);
	}
	
	/**
	 * Creates the "goals" page, where the user can see their goal pie chart
	 * 
	 * @return the goals page's panel
	 */
	private JPanel init_goals_page() {
		JLabel enter_button = new JLabel("set goals");
		enter_button.setBackground(Color.GRAY);
		enter_button.setOpaque(true);
		return init_chart_page(goal_slices, enter_button, 3);
	}
	
	/**
	 * Creates a pie chart page (used for today and goals pages)
	 * 
	 * @param slices the pie chart slices to display on the chart
	 * @param chart the pie chart on which to display the slices
	 * @param button the button at the bottom of the page
	 * @param button_page the page that clicking button should bring the user to
	 * @return the created page's panel
	 */
	private JPanel init_chart_page(ArrayList<Slice> slices, JLabel button, int button_page) {
		// Create panel
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 534, 661);
		panel.setLayout(null);
		
		// Pie chart
		PieChart chart = new PieChart(slices, chart_area);
		chart.setBounds(0, 30, 534, 661);
		panel.add(chart);
		
		// Set goals button
		button.setBackground(Color.GRAY);
		button.setOpaque(true);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				show_page(button_page);
			}
		});
		button.setBounds(142, 585, 250, 60);
		panel.add(button);

		// Background image
		JLabel background = new JLabel(image.bg);
		background.setBounds(0, 0, 550, 700);
		panel.add(background);
				
		return panel;
	}
	
	/**
	 * Creates the "enter new slice" page, where the user can update their "today" pie chart slices
	 * 
	 * @return the enter new slice page's page
	 */
	private JPanel init_enter_new_slice() {
		JLabel title_label = new JLabel(image.enterslice_text);
		JLabel enter_button = new JLabel("enter");
		enter_button.setBackground(Color.GRAY);
		enter_button.setOpaque(true);
		
		return init_user_entry(title_label, enter_button, today_slices, 0);
	}
	
	/**
	 * Creates the "set goals" page, where the user can set their goal percentages
	 * 
	 * @return the set goals page's panel
	 */
	private JPanel init_set_goals() {
		JLabel title_label = new JLabel(image.setgoals_text);
		JLabel enter_button = new JLabel("set goal");
		enter_button.setBackground(Color.GRAY);
		enter_button.setOpaque(true);
		
		return init_user_entry(title_label, enter_button, goal_slices, 2);		
	}
	
	/**
	 * Creates a user entry page (used for enter a new slice or set goals pages)
	 * 
	 * @param title_label label to go at the top of this page
	 * @param enter_button button to go at the bottom of this page
	 * @param slices the pie chart slices to update
	 * @param prev_page the page to return to after the user is done entering information
	 * @return the created page's panel
	 */
	private JPanel init_user_entry(JLabel title_label, JLabel enter_button, ArrayList<Slice> slices, int prev_page) {
		// Create panel
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 534, 661);
		panel.setLayout(null);
		
		// Title text: "set your goals"
		title_label.setBounds(105, 270, 324, 50);
		panel.add(title_label);
		
		// Text: "category"
		JLabel text1 = new JLabel("category:");
		text1.setBackground(Color.GRAY);
		text1.setOpaque(true);
		text1.setBounds(80, 350, 180, 50);
		panel.add(text1);
		
		// Combo box: user can enter category for this slice
		JComboBox<String> category_box = new JComboBox<String>();
		category_box.addItem("Fitness");
		category_box.addItem("Study");
		category_box.addItem("Rest");
		category_box.setBounds(270, 357, 180, 37);
		panel.add(category_box);
		
		// Text: "# of hours"
		JLabel text2 = new JLabel("# of hours:");
		text2.setBackground(Color.GRAY);
		text2.setOpaque(true);
		text2.setBounds(80, 420, 180, 50);
		panel.add(text2);
		
		// Typing field: user can enter # of hours
		JTextField hours_field = new JTextField();
		
		hours_field.setBounds(270, 427, 180, 37);
		panel.add(hours_field);
		hours_field.setColumns(10);
		
		// Enter button - retrieves information from the fields and submits it
		enter_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// get category from combo box
				String category = category_box.getSelectedItem().toString();
				
				// get value from hours field
				String str = hours_field.getText();
				int num_hours = 0;
				try {
				    num_hours = Integer.parseInt(str);
				} catch (NumberFormatException number_format_e) {
					JOptionPane.showMessageDialog(frame, "Hours must be a number.");
				}
				
				// update pie chart based on value entered
				switch (category) {
				case "Fitness":
					slices.get(0).updateValue(num_hours);
					slices.get(slices.size()-1).updateValue(-num_hours);
					
					// show error if entered over 24 hours
					if (!checkSliceList(today_slices)) {
						slices.get(0).updateValue(-num_hours);
						slices.get(slices.size()-1).updateValue(num_hours);
						JOptionPane.showMessageDialog(frame, "You've entered over 24 hours!");
					}
					
					break;
				case "Study":
					slices.get(1).updateValue(num_hours);
					slices.get(slices.size()-1).updateValue(-num_hours);
					
					// show error if entered over 24 hours
					if (!checkSliceList(today_slices)) {
						slices.get(1).updateValue(-num_hours);
						slices.get(slices.size()-1).updateValue(num_hours);
						JOptionPane.showMessageDialog(frame, "You've entered over 24 hours!");
					}
					
					break;
				case "Rest":
					slices.get(2).updateValue(num_hours);
					slices.get(slices.size()-1).updateValue(-num_hours);
					
					// show error if entered over 24 hours
					if (!checkSliceList(today_slices)) {
						slices.get(2).updateValue(-num_hours);
						slices.get(slices.size()-1).updateValue(num_hours);
						JOptionPane.showMessageDialog(frame, "You've entered over 24 hours!");
					}
					
					break;
				}
					
				// go back to previous page
				show_page(prev_page);
			}
		});
		enter_button.setBounds(142, 510, 250, 60);
		panel.add(enter_button);
		
		// Background image
		JLabel background = new JLabel(image.bg);
		background.setBounds(0, 0, 550, 700);
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
	
	/**
	 * Ensures the slice list does not have over 24 hours in slice values.
	 * 
	 * @param slices the slice list to check
	 * @return true if the list is sound, false if it has over 24 hours
	 */
	private boolean checkSliceList(ArrayList<Slice> slices) {
		int sum = 0;
		
		// don't count the "other" slices
		for (int i = 0; i < slices.size()-1; i++)
			sum += slices.get(i).getValue();
		
		return sum <= 24;
	}
}


















