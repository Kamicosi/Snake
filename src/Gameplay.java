import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -553705930811916348L;
	private int[] snakexLength = new int[750];
	private int[] snakeyLength = new int[750];

	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;

	private boolean gameOver = false;

	private ImageIcon snakeBody, food;

	private int moves = 0;

	private int lengthOfSnake = 3;
	private int score = lengthOfSnake - 3;
	private int startingxPosition, startingyPosition, foodxPosition, foodyPosition;

	private Timer timer;
	private int delay = 65;

	public URL FoodURL, SnakeURL;

	public Gameplay() {
		generateFoodPosition();
		generateStartingPosition();

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();

		FoodURL = Gameplay.class.getResource("Img/food.png");
		SnakeURL = Gameplay.class.getResource("Img/snake.png");
	}

	@Override
	public void paint(Graphics g) {

		if (moves == 0) {
			snakexLength[2] = startingxPosition - 50;
			snakexLength[1] = startingxPosition - 25;
			snakexLength[0] = startingxPosition;

			snakeyLength[2] = startingyPosition;
			snakeyLength[1] = startingyPosition;
			snakeyLength[0] = startingyPosition;
		}

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1000, 800);

		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN, 30));
		g.drawString("Score: " + score, 800, 50);

		for (int i = 0; i < lengthOfSnake; i++) {
			snakeBody = new ImageIcon(SnakeURL);
			snakeBody.paintIcon(this, g, snakexLength[i], snakeyLength[i]);
		}

		food = new ImageIcon(FoodURL);
		food.paintIcon(this, g, foodxPosition, foodyPosition);

		for (int i = 1; i < lengthOfSnake; i++) {
			if (snakexLength[i] == snakexLength[0] && snakeyLength[i] == snakeyLength[0]) {
				gameOver = true;
			}
		}

		if (gameOver) {
			right = false;
			left = false;
			up = false;
			down = false;

			g.drawString("You have big die", 500, 300);
			g.drawString("Final  Score: " + score, 500, 500);
			g.drawString("press space to restart", 300, 400);
		}

		if (moves == 0) {
			g.drawString("use the arrow keys to start", 300, 400);
			g.setFont(new Font("arial", Font.PLAIN, 200));
			g.drawString("Snake", 200, 300);
		}

		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();

		if (right) {
			for (int i = lengthOfSnake - 1; i >= 0; i--) {
				snakeyLength[i + 1] = snakeyLength[i];
			}
			for (int i = lengthOfSnake - 1; i >= 0; i--) {
				if (i == 0) {
					snakexLength[i] = snakexLength[i] + 25;
				} else {
					snakexLength[i] = snakexLength[i - 1];
				}

				if (snakexLength[i] >= 1000) {
					snakexLength[i] = 0;
				}
			}

			repaint();
		}
		if (left) {
			for (int i = lengthOfSnake - 1; i >= 0; i--) {
				snakeyLength[i + 1] = snakeyLength[i];
			}
			for (int i = lengthOfSnake - 1; i >= 0; i--) {
				if (i == 0) {
					snakexLength[i] = snakexLength[i] - 25;
				} else {
					snakexLength[i] = snakexLength[i - 1];
				}

				if (snakexLength[i] < 0) {
					snakexLength[i] = 975;
				}
			}

			repaint();
		}
		if (up) {
			for (int i = lengthOfSnake - 1; i >= 0; i--) {
				snakexLength[i + 1] = snakexLength[i];
			}
			for (int i = lengthOfSnake - 1; i >= 0; i--) {
				if (i == 0) {
					snakeyLength[i] = snakeyLength[i] - 25;
				} else {
					snakeyLength[i] = snakeyLength[i - 1];
				}

				if (snakeyLength[i] < 0) {
					snakeyLength[i] = 750;
				}
			}

			repaint();
		}
		if (down) {
			for (int i = lengthOfSnake - 1; i >= 0; i--) {
				snakexLength[i + 1] = snakexLength[i];
			}
			for (int i = lengthOfSnake - 1; i >= 0; i--) {
				if (i == 0) {
					snakeyLength[i] = snakeyLength[i] + 25;
				} else {
					snakeyLength[i] = snakeyLength[i - 1];
				}

				if (snakeyLength[i] >= 775) {
					snakeyLength[i] = 0;
				}
			}

			repaint();
		}
//eat food
		if (snakexLength[0] == foodxPosition && snakeyLength[0] == foodyPosition) {
			lengthOfSnake++;
			score++;
			generateFoodPosition();

		}

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_SPACE && gameOver) {
			moves = 0;
			score = 0;
			lengthOfSnake = 3;
			repaint();
			generateFoodPosition();
			generateStartingPosition();
			gameOver = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT && !gameOver && !snakeNeckRightOfHead()) {
			moves++;

			right = true;
			left = false;
			down = false;
			up = false;

		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT && !gameOver && !snakeNeckLeftOfHead()) {
			if (moves != 0) {
				moves++;

				right = false;
				left = true;
				down = false;
				up = false;

			}
		}

		if (e.getKeyCode() == KeyEvent.VK_UP && !gameOver && !snakeNeckTopOfHead()) {
			moves++;

			right = false;
			left = false;
			down = false;
			up = true;

		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN && !gameOver && !snakeNeckBottomOfHead()) {
			moves++;

			right = false;
			left = false;
			down = true;
			up = false;

		}
	}

	public void generateFoodPosition() {
		foodxPosition = ((int) ((Math.random() * 37) + 3)) * 25;
		foodyPosition = ((int) (Math.random() * 31)) * 25;
	}

	public void generateStartingPosition() {
		startingxPosition = (int) ((Math.random() * 37) + 3) * 25;
		startingyPosition = (int) (Math.random() * 31) * 25;
	}

	public boolean snakeNeckRightOfHead() {
		return snakexLength[1] == snakexLength[0] + 25 && snakeyLength[1] == snakeyLength[0];
	}

	public boolean snakeNeckLeftOfHead() {
		return snakexLength[1] == snakexLength[0] - 25 && snakeyLength[1] == snakeyLength[0];
	}

	public boolean snakeNeckTopOfHead() {
		return snakexLength[1] == snakexLength[0] && snakeyLength[1] == snakeyLength[0] - 25;
	}

	public boolean snakeNeckBottomOfHead() {
		return snakexLength[1] == snakexLength[0] && snakeyLength[1] == snakeyLength[0] + 25;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
