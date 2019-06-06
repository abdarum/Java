import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener {

    public static final int B_WIDTH = 300;
    public static final int B_HEIGHT = 300;
    public static final int DOT_SIZE = 10;
    public static final int ALL_DOTS = 900;

    private int currentDelay = 300;
    private int finalDelay = 20;
    private int delayChangesSpeed = 15;
    
    private int apple_x;
    private int apple_y;

    private boolean inGame = true;
    
    private JLabel Scoreboard;
    private JLabel Scoreboard2;
    private JLabel Scoreboard3;
    
    private Timer timer;
    private Image ball;
    private Image ball2;
    private Image apple;
    private Image head;
    private Image head2;
    
    public Food food;
    public Snake snake;
    public Snake snake2;
    
    public boolean multiplayerMode = true;
    
    public Board() {
    	
    	food = new Food();
    	snake = new Snake();
    	if (multiplayerMode) {
    		snake2 = new Snake();
    	}
        initBoard();
    }
    
    private void initBoard() {
//    	getStartDelayFromUser();
//    	getFinalDelayFromUser();
//    	getChangingDelaySpeedFromUser();
    	
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {

        ImageIcon iid = new ImageIcon("Images/dot.png");
        ball = iid.getImage();
        
        ImageIcon iid2 = new ImageIcon("Images/dot2.png");
        ball2 = iid2.getImage();

        ImageIcon iia = new ImageIcon("Images/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("Images/head.png");
        head = iih.getImage();
        
        ImageIcon iih2 = new ImageIcon("Images/head2.png");
        head2 = iih2.getImage();

    }

    private void initGame() {

    	snake.setLength(3);
    	if(multiplayerMode) {
    		snake2.setLength(3);
    	}

        Scoreboard = new JLabel("Scoreboard: ");
        Scoreboard.setBounds(100, 100, 100, 100);
        add(Scoreboard);
        
        Scoreboard2 = new JLabel("0");
        Scoreboard2.setBounds(120, 100, 100, 100);
        add(Scoreboard2);
        
        if (multiplayerMode) {
        	Scoreboard3 = new JLabel("0");
            Scoreboard3.setBounds(120, 100, 100, 100);
            add(Scoreboard3);
        }
        
        snake.initSnakeLocalisation(50,50);
        snake.printPositions("Snake");
        if(multiplayerMode) {
        	snake2.initSnakeLocalisation(50,250);
        	snake2.printPositions("Snake2");
        }
        
        apple_x = food.locateApple_x(apple_x);
        apple_y = food.locateApple_y(apple_y);
        
        timer = new Timer(currentDelay, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        
        if (inGame) {

            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < snake.getLength(); z++) {
                if (z == 0) {
                    g.drawImage(head, snake.x[z], snake.y[z], this);
                } else {
                    g.drawImage(ball, snake.x[z], snake.y[z], this);
                }
            }
            
            if(multiplayerMode) {
                for (int z = 0; z < snake2.getLength(); z++) {
                    if (z == 0) {
                        g.drawImage(head2, snake2.x[z], snake2.y[z], this);
                    } else {
                        g.drawImage(ball2, snake2.x[z], snake2.y[z], this);
                    }
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }        
    }

    private void gameOver(Graphics g) {
        
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }



    private void checkCollision() {
    	boolean test1 = !snake.checkCollisionSnake();
    	if (multiplayerMode) {
    		boolean test2 = !snake2.checkCollisionSnake();
    		boolean test3 = !snake.checkCollisionForOtherSnake(snake2);
    		boolean test4 = !snake2.checkCollisionForOtherSnake(snake);
    		inGame = test1 && test2 && test3 && test4;
//    		System.out.println("test1: "+test1+"test2: "+test2+"test13: "+test3+"test4: "+test4+"\t inGame: "+inGame);
    		} else {
    		inGame = test1;
    	}

        
        if (!inGame) {
            timer.stop();
        }
    }
    
    public void setStartDelay(int delay) {
    	currentDelay = delay;
    }
    
    public void getStartDelayFromUser() {
    	System.out.println("Enter value between 1 and 60(default 3)");
    	System.out.print("Enter start FPS value:");
    	int default_var = 3;
    	int fps = default_var;
    	int delay = 0;
    	try {
	    	Scanner in = new Scanner(System.in);
	    	fps = in.nextInt();
	    	if (fps<1 || fps>60) {
	    		fps = default_var;
	    		throw new IllegalArgumentException("Argument not in range");
	    	}
    	} catch (Exception e) {
    		System.out.println("Wrong value, set default: "+fps);
    	}
    	delay = 1000/fps;
    	setStartDelay(delay);
    }
    
    public void setFinalDelay(int delay) {
    	finalDelay = delay;
    }
    
    public void getFinalDelayFromUser() {
    	System.out.println("Enter value between 1 and 60(default 20)");
    	System.out.print("Enter final FPS value:");
    	int default_var = 20;
    	int fps = default_var;
    	int delay = 0;
    	try {
	    	Scanner in = new Scanner(System.in);
	    	fps = in.nextInt();
	    	if (fps<1 || fps>60) {
	    		fps = default_var;
	    		throw new IllegalArgumentException("Argument not in range");
	    	}
    	} catch (Exception e) {
    		System.out.println("Wrong value, set default: "+fps);
    	}
    	delay = 1000/fps;
    	setFinalDelay(delay);
    }
    
    public void setChangingDelaySpeed(int speed) {
    	delayChangesSpeed = speed;
    }
    
    public void getChangingDelaySpeedFromUser() {
    	System.out.println("Enter value between 1(slow) and 30(fast)(default 15)");
    	System.out.print("Enter final FPS value:");
    	int default_var = 15;
    	int speed = default_var;
    	try {
	    	Scanner in = new Scanner(System.in);
	    	speed  = in.nextInt();
	    	if (speed<1 || speed>30) {
	    		speed = default_var;
	    		throw new IllegalArgumentException("Argument not in range");
	    	}
    	} catch (Exception e) {
    		System.out.println("Wrong value, set default: "+speed);
    	}
    	speed = 31 - speed;
    	setChangingDelaySpeed(speed);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	

        if (inGame) {

            if(food.checkApple(apple_x, apple_y, snake.x, snake.y)) {
            	apple_x = food.locateApple_x(apple_x);
            	apple_y = food.locateApple_x(apple_y);
            	snake.incrementLength();
            	String tmp = Scoreboard2.getText();
            	int score = Integer.parseInt(tmp);
            	score++;
            	String str = Integer.toString(score);
            	Scoreboard2.setText(str);
            	changeDelay();
            	}
//            checkCollision();
//            snake.move();
        
	        if (multiplayerMode) {
	            if(food.checkApple(apple_x, apple_y, snake2.x, snake2.y)) {
	            	apple_x = food.locateApple_x(apple_x);
	            	apple_y = food.locateApple_x(apple_y);
	            	snake2.incrementLength();
	            	String tmp = Scoreboard3.getText();
	            	int score = Integer.parseInt(tmp);
	            	score++;
	            	String str = Integer.toString(score);
	            	Scoreboard3.setText(str);
	            	changeDelay(); 
//	            	snake2.move();
	            	}
	        }
	        snake2.move();
            snake.move();
            checkCollision();	        
        }
        repaint();
    }

    private void changeDelay() {
    	currentDelay = currentDelay - (currentDelay - finalDelay)/delayChangesSpeed;
    	
    	System.out.println(currentDelay);
    	timer.stop();
    	timer = new Timer(currentDelay, (ActionListener) this);
    	timer.start(); 
    }
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!snake.getRightDirection())) {
                snake.setLeftDirection(true);
                snake.setUpDirection(false);
                snake.setDownDirection(false);
            }

            if ((key == KeyEvent.VK_RIGHT) && (!snake.getLeftDirection())) {
            	snake.setRightDirection(true);
                snake.setUpDirection(false);
                snake.setDownDirection(false);
            }

            if ((key == KeyEvent.VK_UP) && (!snake.getDownDirection())) {
            	snake.setUpDirection(true);
                snake.setRightDirection(false);
                snake.setLeftDirection(false);
            }

            if ((key == KeyEvent.VK_DOWN) && (!snake.getUpDirection())) {
            	snake.setDownDirection(true);
                snake.setRightDirection(false);
                snake.setLeftDirection(false);
            }
            
            if ((key == KeyEvent.VK_A) && (!snake2.getRightDirection())) {
                snake2.setLeftDirection(true);
                snake2.setUpDirection(false);
                snake2.setDownDirection(false);
            }

            if ((key == KeyEvent.VK_D) && (!snake2.getLeftDirection())) {
            	snake2.setRightDirection(true);
                snake2.setUpDirection(false);
                snake2.setDownDirection(false);
            }

            if ((key == KeyEvent.VK_W) && (!snake2.getDownDirection())) {
            	snake2.setUpDirection(true);
                snake2.setRightDirection(false);
                snake2.setLeftDirection(false);
            }

            if ((key == KeyEvent.VK_S) && (!snake2.getUpDirection())) {
            	snake2.setDownDirection(true);
                snake2.setRightDirection(false);
                snake2.setLeftDirection(false);
            }
        }
    }
}