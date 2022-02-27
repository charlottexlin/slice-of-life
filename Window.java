package sliceoflife;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Window {

	private JFrame frame;
	private Image image;
	private Rectangle chart_area;
	
	// Pages of the app
	private JPanel[] pages = new JPanel[6];
	/* 0: today
	 * 1: enter new slice
	 * 2: goals
	 * 3: set goals
	 * 4: today check-in
	 * 5: goals check-in
	 */
	
	// Pie charts and slices
	private ArrayList<Slice> today_slices;
	private ArrayList<Slice> goal_slices;
	private Slice today_fitness, today_study, today_work, today_sleep, today_selfcare, today_hobbies, today_social, today_chores, today_other;
	private Slice goal_fitness, goal_study, goal_work, goal_sleep, goal_selfcare, goal_hobbies, goal_social, goal_chores, goal_other;
	
	// Slice colors
	private Color fitness_color = Color.BLUE, study_color = Color.GREEN, work_color = Color.RED, sleep_color = Color.PINK, selfcare_color = Color.GRAY,
			hobbies_color = Color.ORANGE, social_color = Color.YELLOW, chores_color = Color.CYAN, other_color_1 = Color.WHITE, other_color_2 = Color.LIGHT_GRAY;
	
	// Some text
	private JLabel deviations_text, rec_text, goal_checkin_text;

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
		chart_area = new Rectangle(110,210,320,320);
		
		// Initialize pie chart slice lists
		today_slices = new ArrayList<Slice>();
		goal_slices = new ArrayList<Slice>();
		
		// Set initial values for pie chart slices
		today_fitness = new Slice(0, fitness_color);
		today_study = new Slice(0, study_color);
		today_work = new Slice(0, work_color);
		today_sleep = new Slice(0, sleep_color);
		today_selfcare = new Slice(0, selfcare_color);
		today_hobbies = new Slice(0, hobbies_color);
		today_social = new Slice(0, social_color);
		today_chores = new Slice(0, chores_color);
		today_other = new Slice(24, other_color_1);
		
		goal_fitness = new Slice(0, fitness_color);
		goal_study = new Slice(0, study_color);
		goal_work = new Slice(0, work_color);
		goal_sleep = new Slice(0, sleep_color);
		goal_selfcare = new Slice(0, selfcare_color);
		goal_hobbies = new Slice(0, hobbies_color);
		goal_social = new Slice(0, social_color);
		goal_chores = new Slice(0, chores_color);
		goal_other = new Slice(24, other_color_2);
		
		today_slices.add(today_fitness);
		today_slices.add(today_study);
		today_slices.add(today_work);
		today_slices.add(today_sleep);
		today_slices.add(today_selfcare);
		today_slices.add(today_hobbies);
		today_slices.add(today_social);
		today_slices.add(today_chores);
		today_slices.add(today_other);
		
		goal_slices.add(goal_fitness);
		goal_slices.add(goal_study);
		goal_slices.add(goal_work);
		goal_slices.add(goal_sleep);
		goal_slices.add(goal_selfcare);
		goal_slices.add(goal_hobbies);
		goal_slices.add(goal_social);
		goal_slices.add(goal_chores);
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
		
		// Initialize all pages of app
		pages[0] = init_today_page();
		pages[1] = init_enter_new_slice();
		pages[2] = init_goals_page();
		pages[3] = init_set_goals();
		pages[4] = init_today_checkin();
		pages[5] = init_goals_checkin();
		
		for (int i = 0; i < pages.length; i++) {
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
		// enter a slice button
		JLabel enter_button = new JLabel(image.enter_button_img);
		
		return init_chart_page(today_slices, enter_button, 4, 1);
	}
	
	/**
	 * Creates the "goals" page, where the user can see their goal pie chart
	 * 
	 * @return the goals page's panel
	 */
	private JPanel init_goals_page() {
		// set goals button
		JLabel enter_button = new JLabel(image.set_goals_button_img);
		
		return init_chart_page(goal_slices, enter_button, 5, 3);
	}
	
	/**
	 * Creates a pie chart page (used for today and goals pages)
	 * 
	 * @param slices the pie chart slices to display on the chart
	 * @param chart the pie chart on which to display the slices
	 * @param update_button the button at the bottom of the page
	 * @param checkin_page the index of page that clicking the check-in button should bring the user to
	 * @param button_page the index of page that clicking the update button should bring the user to
	 * @return the created page's panel
	 */
	private JPanel init_chart_page(ArrayList<Slice> slices, JLabel update_button, int checkin_page, int button_page) {
		// Create panel
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 534, 661);
		panel.setLayout(null);
		
		// Navigation buttons
		addNavigationButtons(panel);
		
		// Pie chart
		PieChart chart = new PieChart(slices, chart_area);
		chart.setBounds(0, 30, 534, 661);
		panel.add(chart);
		
		// Check in button
		JLabel checkin_button = new JLabel(image.checkin_button_img);
		checkin_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				set_today_checkin_text();
				set_goal_checkin_text();
				show_page(checkin_page);
			}
		});
		checkin_button.setBounds(22, 580, 150, 60);
		panel.add(checkin_button);
		
		// Update chart button
		update_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				show_page(button_page);
			}
		});
		update_button.setBounds(192, 580, 150, 60);
		panel.add(update_button);
		
		// Reset slices button
		JLabel reset_button = new JLabel(image.reset_button_img);
		reset_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// set all category slices to 0 hours
				for (Slice s : slices)
					s.setValue(0);
				// set other to 24 hours
				slices.get(slices.size()-1).setValue(24);
				chart.repaint();
			}
		});
		reset_button.setBounds(362, 580, 150, 60);
		panel.add(reset_button);

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
		JLabel enter_button = new JLabel(image.enter_button_img);
		
		return init_user_entry(title_label, enter_button, today_slices, 0);
	}
	
	/**
	 * Creates the "set goals" page, where the user can set their goal percentages
	 * 
	 * @return the set goals page's panel
	 */
	private JPanel init_set_goals() {
		JLabel title_label = new JLabel(image.setgoals_text);
		JLabel enter_button = new JLabel(image.set_goals_button_img);
		
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
		
		// Navigation buttons
		addNavigationButtons(panel);
		
		// Title text: "set your goals"
		title_label.setBounds(105, 270, 324, 50);
		panel.add(title_label);
		
		// Text: "category"
		JLabel text1 = new JLabel(image.category_text);
		text1.setBounds(80, 350, 180, 50);
		panel.add(text1);
		
		// Combo box: user can enter category for this slice
		JComboBox<String> category_box = new JComboBox<String>();
		category_box.addItem("Fitness");
		category_box.addItem("Study");
		category_box.addItem("Work");	
		category_box.addItem("Sleep");
		category_box.addItem("Self care");
		category_box.addItem("Hobbies");
		category_box.addItem("Social");
		category_box.addItem("Chores");
		category_box.setBounds(270, 357, 180, 37);
		panel.add(category_box);
		
		// Text: "# of hours"
		JLabel text2 = new JLabel(image.numhours_text);
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
					JOptionPane.showMessageDialog(frame, "Hours must be a whole number.");
				}
				
				// update pie chart based on value entered
				switch (category) {
				case "Fitness":
					updateChart(slices, 0, num_hours);
					break;
				case "Study":
					updateChart(slices, 1, num_hours);
					break;
				case "Work":
					updateChart(slices, 2, num_hours);
					break;
				case "Sleep":
					updateChart(slices, 3, num_hours);
					break;
				case "Self care":
					updateChart(slices, 4, num_hours);
					break;
				case "Hobbies":
					updateChart(slices, 5, num_hours);
					break;
				case "Social":
					updateChart(slices, 6, num_hours);
					break;
				case "Chores":
					updateChart(slices, 7, num_hours);
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
	 * Creates the today check-in page, where the user can see how their current day compares to their goals
	 * 
	 * @return the today check-in page's panel
	 */
	private JPanel init_today_checkin() {
		// Create panel
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 534, 661);
		panel.setLayout(null);
		
		// Navigation buttons
		addNavigationButtons(panel);
		
		// Title text: "let's check in..."
		JLabel title_label = new JLabel(image.letscheckin_text);
		title_label.setBounds(105, 270, 324, 50);
		panel.add(title_label);

		// Text
		JLabel text = new JLabel("Your slices today compared to your goals:");
		text.setBounds(40, 300, 450, 90);
		text.setFont(new Font("Montserrat Medium", Font.PLAIN, 20));
		text.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(text);
		
		deviations_text = new JLabel("", SwingConstants.CENTER);
		deviations_text.setBounds(40, 360, 450, 120);
		deviations_text.setFont(new Font("Montserrat Light", Font.PLAIN, 24));
		panel.add(deviations_text);
		
		rec_text = new JLabel("", SwingConstants.CENTER);
		rec_text.setBounds(40, 445, 450, 120);
		rec_text.setFont(new Font("Montserrat Medium", Font.PLAIN, 20));
		panel.add(rec_text);
		
		set_today_checkin_text();
		
		// Back button
		JLabel back_button = new JLabel("Back");
		back_button.setOpaque(true);
		back_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				show_page(0);
			}
		});
		back_button.setBounds(192, 550, 150, 60);
		panel.add(back_button);

		// Background image
		JLabel background = new JLabel(image.bg);
		background.setBounds(0, 0, 550, 700);
		panel.add(background);
		
		return panel;
	}
	
	/**
	 * Set text on today check-in page
	 */
	private void set_today_checkin_text() {
		// your top 3 deviations
		ArrayList<ArrayList<Integer>> values = new ArrayList<ArrayList<Integer>>();
		
		// get differences between today and goal slices for all categories (except other)
		for (int i = 0; i < today_slices.size()-1; i++) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp.add((int)(today_slices.get(i).getValue() - goal_slices.get(i).getValue()));
			temp.add(i);
			values.add(temp);
		}
		
		// find 3 greatest differences
		values.sort((x, y) -> Integer.compare(Math.abs(x.get(0)), Math.abs(y.get(0))));
		Collections.reverse(values);
		int val1 = values.get(0).get(0);
		int val2 = values.get(1).get(0);
		int val3 = values.get(2).get(0);
		
		// change values to positives
		String s1 = (val1 < 0) ? "less" : "more";
		String s2 = (val2 < 0) ? "less" : "more";
		String s3 = (val3 < 0) ? "less" : "more";
		
		val1 = (val1 < 0) ? -val1 : val1;
		val2 = (val2 < 0) ? -val2 : val2;
		val3 = (val3 < 0) ? -val3 : val3;
		
		// get 3 categories
		String c1 = getCategory(values.get(0).get(1));
		String c2 = getCategory(values.get(1).get(1));
		String c3 = getCategory(values.get(2).get(1));
		
		// update deviations text
		if (today_other.getValue() == 24) {
			deviations_text.setText("<html><div style='text-align: center;'>Start your day off strong by<br/>entering some time slices.<html>");
			// update recommendations text
			rec_text.setText("<html><div style='text-align: center;'>You got this!<html>");
		}
		else if (val1 == 0 && val2 == 0 && val3 == 0) {
			deviations_text.setText("<html><div style='text-align: center;'>You're right on track<br/>with your goals so far!<html>");
			// update recommendations text
			rec_text.setText("<html><div style='text-align: center;'>Keep up the great work.<html>");
		} else {
			deviations_text.setText("<html>1. " + c1 + ": " + val1 + " hrs " + s1 + " than goal<br/>2. " + c2 + ": " + val2 + " hrs " + s2 + " than goal<br/>3. " + c3 + ": " + val3 + " hrs " + s3 + " than goal<html>");
			// update recommendations text
			if (s1.equals("less"))
				rec_text.setText("<html><div style='text-align: center;'>Try to spend a bit more time on " + c1 + " today to reach your goal. You can do it!<html>");
			else
				rec_text.setText("<html><div style='text-align: center;'>You may have spent more time than expected today on " + c1 + ". It's okay, there's always tomorrow!<html>");
		}
	}
	
	/**
	 * Creates the goals check-in page, where the user can see how balanced their goals are
	 * 
	 * @return the goals check-in page's panel
	 */
	private JPanel init_goals_checkin() {
		// Create panel
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 534, 661);
		panel.setLayout(null);
		
		// Navigation buttons
		addNavigationButtons(panel);
		
		// Title text: "let's check in..."
		JLabel title_label = new JLabel(image.letscheckin_text);
		title_label.setBounds(105, 280, 324, 50);
		panel.add(title_label);
		
		// Text: gives a recommendation for your goals
		goal_checkin_text = new JLabel("", SwingConstants.CENTER);
		goal_checkin_text.setBounds(70, 330, 400, 200);
		goal_checkin_text.setFont(new Font("Montserrat", Font.PLAIN, 28));
		panel.add(goal_checkin_text);
		
		// Back button
		JLabel back_button = new JLabel("Back");
		back_button.setOpaque(true);
		back_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				show_page(2);
			}
		});
		back_button.setBounds(192, 530, 150, 60);
		panel.add(back_button);

		// Background image
		JLabel background = new JLabel(image.bg);
		background.setBounds(0, 0, 550, 700);
		panel.add(background);
		
		return panel;
	}
	
	/**
	 * Set text on goal check-in page
	 */
	private void set_goal_checkin_text() {
		// haven't filled in 24 hours yet
		if (goal_slices.get(goal_slices.size()-1).getValue() > 0) {
			goal_checkin_text.setText("<html><div style='text-align: center;'>You haven't finished filling in your daily goals yet. Keep planning your ideal balance!<html>");
		} else {
			// too much or too little
			String str = "";
			for (int i = 0; i < goal_slices.size(); i++) {
				if (goal_slices.get(i).getValue() >= 12) {
					String category = getCategory(i);
					str = "<html><div style='text-align: center;'>You have a lot of time budgeted for " + category + ". Try making time for other things!<html>";
					break;
				} else if (goal_slices.get(i).getValue() < 1) {
					String category = getCategory(i);
					str = "<html><div style='text-align: center;'>You should try to budget some time for " + category +". Start with an hour!<html>";
					break;
				} else {
					// balanced goals
					str = "<html><div style='text-align: center;'>Looking good! Keep working to achieve a healthy balance in your life.<html>";
				}
			}
			goal_checkin_text.setText(str);
		}	
	}
	
	/**
	 * Returns the category based on the index in a slices list
	 * Helper function for initializing check-in pages
	 * 
	 * @param i index of the desired category in the slices list
	 * @return string name of that category
	 */
	private String getCategory(int i) {
		switch (i) {
		case 0: return "fitness";
		case 1: return "study";
		case 2: return "work";
		case 3: return "sleep";
		case 4: return "self care";
		case 5: return "hobbies";
		case 6: return "social";
		case 7: return "chores";
		default: return "other";
		}
	}
	
	/**
	 * Adds the navigation buttons "today" and "goals" to the provided panel
	 * These buttons allow the user to go to different pages of the app
	 * 
	 * @param panel the panel to add the navigation buttons to
	 */
	private void addNavigationButtons(JPanel panel) {
		// "Today" navigation button
		JLabel navigate_today = new JLabel(image.today_button);
		navigate_today.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				show_page(0);
			}
		});
		navigate_today.setBounds(45, 180, 210, 50);
		panel.add(navigate_today);
		
		// "Goals" navigation button
		JLabel navigate_goals = new JLabel(image.goals_button);
		navigate_goals.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				show_page(2);
			}
		});
		navigate_goals.setBounds(285, 180, 210, 50);
		panel.add(navigate_goals);
	}
	
	/**
	 * Update the value of a certain category of a certain pie chart, or show an error if over 24 hours are entered
	 * 
	 * @param slices pie chart slices to update (today or goal)
	 * @param category_id index in the slices array that corresponds to the category to update
	 * @param num_hours number of hours to increase the slice by
	 */
	private void updateChart(ArrayList<Slice> slices, int category_id, int num_hours) {
		slices.get(category_id).updateValue(num_hours);
		slices.get(slices.size()-1).updateValue(-num_hours);
		
		// show error if entered over 24 hours
		if (!checkSliceList(slices)) {
			slices.get(category_id).updateValue(-num_hours);
			slices.get(slices.size()-1).updateValue(num_hours);
			JOptionPane.showMessageDialog(frame, "You've entered over 24 hours!");	
		}
	}
	
	/**
	 * Show the given page and hide all other pages
	 * 
	 * @param page_to_show the index of the page that should be shown
	 */
	private void show_page(int page_to_show) {
		pages[page_to_show].setVisible(true);
		for (int i = 0; i < pages.length; i++) {
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


















