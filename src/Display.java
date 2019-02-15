import java.awt.Color;

import javax.swing.JFrame;

public class Display {

	private JFrame frame;
	private String title;
	public int width, height;
	private Gameplay gameplay;

	public Display(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;

		createDisplay();
	}

	private void createDisplay() {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setBackground(Color.BLACK);

		gameplay = new Gameplay();
		frame.add(gameplay);

		frame.setVisible(true);
	}

	public JFrame getFrame() {
		return frame;
	}

}
