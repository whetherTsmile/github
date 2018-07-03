package study.work.work11;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.Timer;

/**
* @author UnoySmile
* @date 2018年7月3日 下午5:11:25
* @description 
*/
public class PinBall {
	private final int TABLE_WIDTH = 300;//桌面宽度
	private final int TABLE_HEIGHT = 400;//桌面高度
	private final int RACKET_Y = 340;//球拍垂直高度
	private final int RACKET_HEIGHT = 20;//球拍的高度和宽度
	private final int RACKET_WIDTH = 60;
	private final int BALL_SIZE = 16;
	private Frame f  = new Frame("弹球游戏");
	Random rand = new Random();
	private int ySpeed = 10; //小球纵向的运行速度
	private double xyRate = rand.nextDouble() - 0.5;//返回一个-0.5~0.5的比率，用于控制小球的方向
	private int xSpeed = (int)(ySpeed * xyRate * 2);//小球横向的运行速度
	//ballx和bally代表小球坐标
	private int ballX = rand.nextInt(200)+20;
	private int ballY = rand.nextInt(10)+20;
	private int racketX = rand.nextInt(200);//球拍水平位置
	private MyCanvas tableArea = new MyCanvas();
	Timer timer;
	private boolean isLose = false;//游戏是否结束
	
	public void init() {
		//初始化桌面小球
		tableArea.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));//设置桌面区域最佳大小
		f.add(tableArea);
		KeyAdapter keyProcessor = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_LEFT) {
					if(racketX>0) {
						racketX = racketX - 10;
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if(racketX<TABLE_WIDTH-RACKET_WIDTH) {
						racketX = racketX + 10;
					}
				}
			}
		};
		
		f.addKeyListener(keyProcessor);
		tableArea.addKeyListener(keyProcessor);
		
		ActionListener taskPerformer = e -> {
			if(ballX <= 0 || ballX >= TABLE_WIDTH - BALL_SIZE) {
				xSpeed = -xSpeed;
			}
			if(ballY >= RACKET_Y-BALL_SIZE && 
				(ballX < racketX || ballX > racketX + RACKET_WIDTH)) {
				timer.stop();
				isLose = true;
				tableArea.repaint();
			}
			else if(ballY<=0 || (ballY >= RACKET_Y-BALL_SIZE && ballX > racketX && ballX <= racketX + RACKET_WIDTH)) {
				ySpeed = -ySpeed;
			}
			//小球坐标增加
			ballY = ballY + ySpeed;
			ballX = ballX + xSpeed;
			tableArea.repaint();
		};
		timer = new Timer(100, taskPerformer);
		timer.start();
		f.pack();
		f.setVisible(true);
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
	}
	public static void main(String[] args) {
		new PinBall().init();
		Image i = new BufferedImage(13, 13, BufferedImage.TYPE_INT_RGB);
		System.out.println(i.getGraphics());
	}
	
	class MyCanvas extends Canvas{
		@Override
		public void paint(Graphics g) {
			// TODO Auto-generated method stub
			if(isLose) {
				g.setColor(new Color(255, 0, 0));
				g.setFont(new Font("Times", Font.BOLD, 30));
				g.drawString("游戏已结束", 50, 200);
			}
			else
			{
				g.setColor(new Color(240, 240, 80));
				g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);
				g.setColor(new Color(80, 80, 200));
				g.fillRect(racketX, RACKET_Y, RACKET_WIDTH, RACKET_HEIGHT);
			}
		}
	}
}
