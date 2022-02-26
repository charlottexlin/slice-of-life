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
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

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
	private PieChart today_chart;
	private PieChart goal_chart;
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
	 * Create the application.
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
		goal_rest = new Slice(9, rest_color);
		goal_other = new Slice(1, other_color);
		
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
		
		// Initialize all pages of app, and hide all except today page
		pages[0] = init_today_page();
		pages[1] = init_enter_new_slice();
		pages[2] = init_goals_page();
		pages[3] = init_set_goals();
		
		for (int i = 0; i < 4; i++) {
			frame.getContentPane().add(pages[i]);
		}
		
		show_page(0);
				
	}
	
	/**
	 * Initialize the first page of the app, the "today" page
	 */
	private JPanel init_today_page() {
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 534, 661);
		panel.setLayout(null);
		
		// Pie chart
		today_chart = new PieChart(today_slices.toArray(new Slice[4]), chart_area);
		today_chart.setBounds(0, 30, 534, 661);
		panel.add(today_chart);
		
		// Enter a slice button
		JLabel enter_button = new JLabel("enter a slice");
		enter_button.setBackground(Color.GRAY);
		enter_button.setOpaque(true);
		enter_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				show_page(1);
			}
		});
		enter_button.setBounds(142, 585, 250, 60);
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
		
		// Title text: "enter a slice"
		JLabel title_label = new JLabel(image.enterslice_text);
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
		JLabel enter_button = new JLabel("enter");
		enter_button.setBackground(Color.GRAY);
		enter_button.setOpaque(true);
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
					JOptionPane.showMessageDialog(frame, "# of hours must be a number.");
				}
				
				// save current list separately in case we need to revert
				ArrayList<Slice> previous_slices = new ArrayList<Slice>();
				for (int i = 0; i < today_slices.size(); i++) {
					Slice old = new Slice(today_slices.get(i).getValue(), today_slices.get(i).getColor());
					previous_slices.add(old);
				}
				
				// update pie chart based on value entered
				switch (category) {
				case "Fitness":
					today_fitness.updateValue(num_hours);
					today_other.updateValue(today_other.getValue() - num_hours);
					break;
				case "Study":
					today_study.updateValue(num_hours);
					today_other.updateValue(today_other.getValue() - num_hours);
					break;
				case "Rest":
					today_rest.updateValue(num_hours);
					today_other.updateValue(today_other.getValue() - num_hours);
					break;
				}

				// show error and revert if this adds up to over 24 hours
				if (!checkSliceList(today_slices)) {
					today_slices = previous_slices;
					JOptionPane.showMessageDialog(frame, "You've entered over 24 hours!");
				}
					
				// go back to today page
				show_page(0);
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
	 * Displays the "goals" page, where the user can see their goal pie chart
	 */
	private JPanel init_goals_page() {
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 534, 661);
		panel.setLayout(null);
		
		// Pie chart
		goal_chart = new PieChart(goal_slices.toArray(new Slice[0]), chart_area);
		goal_chart.setBounds(0, 30, 534, 661);
		panel.add(goal_chart);
		
		// Set goals button
		JLabel set_button = new JLabel("set goals");
		set_button.setBackground(Color.GRAY);
		set_button.setOpaque(true);
		set_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				show_page(3);
			}
		});
		set_button.setBounds(142, 585, 250, 60);
		panel.add(set_button);

		// Background image
		JLabel background = new JLabel(image.bg);
		background.setBounds(0, 0, 550, 700);
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
		
		// Title text: "set your goals"
		JLabel title_label = new JLabel(image.setgoals_text);
		title_label.setBounds(105, 230, 324, 50);
		panel.add(title_label);
		
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


















