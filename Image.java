package sliceoflife;

import javax.swing.ImageIcon;

/**
 * This class imports all the image resources used in the app.
 * It exists for organization of code.
 */

public class Image {
	ImageIcon bg = new ImageIcon(getClass().getResource("/resources/bg.png"));
	ImageIcon setgoals_text = new ImageIcon(getClass().getResource("/resources/setgoals.png"));
	ImageIcon enterslice_text = new ImageIcon(getClass().getResource("/resources/enterslice.png"));
	ImageIcon category_text = new ImageIcon(getClass().getResource("/resources/category.png"));
	ImageIcon numhours_text = new ImageIcon(getClass().getResource("/resources/numhours.png"));
	ImageIcon letscheckin_text = new ImageIcon(getClass().getResource("/resources/letscheckin.png"));
	ImageIcon checkin_button_img = new ImageIcon(getClass().getResource("/resources/checkinbutton.png"));
	ImageIcon enter_button_img = new ImageIcon(getClass().getResource("/resources/enterbutton.png"));
	ImageIcon set_goals_button_img = new ImageIcon(getClass().getResource("/resources/setbutton.png"));
	ImageIcon reset_button_img = new ImageIcon(getClass().getResource("/resources/resetbutton.png"));
	ImageIcon today_button = new ImageIcon(getClass().getResource("/resources/todaybutton.png"));
	ImageIcon goals_button = new ImageIcon(getClass().getResource("/resources/goalsbutton.png"));
	ImageIcon back_button_img = new ImageIcon(getClass().getResource("/resources/backbutton.png"));
}
