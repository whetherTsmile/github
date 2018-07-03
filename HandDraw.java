

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class HandDraw {
	private final int AREA_WIDTH = 500;//画图区宽度
	private final int AREA_HEIGHT = 400;//画图区高度
	private int preX,preY = -1; //上一次鼠标的坐标
	//定义一个右键菜单用来设置画笔颜色
	PopupMenu pmenu = new PopupMenu();
	MenuItem redItem = new MenuItem("红色");
	MenuItem greenItem = new MenuItem("绿色");
	MenuItem blueItem = new MenuItem("蓝色");
	//定义一个bufferedImage对象
	BufferedImage image = new BufferedImage(AREA_WIDTH, AREA_HEIGHT, BufferedImage.TYPE_INT_RGB);
	//获取image对象的graphics
	Graphics g = image.getGraphics();
	DrawCanvas drawCanvas = new DrawCanvas();
	private Frame f = new Frame("简单手绘程序");
	//用于保存画笔颜色
	private Color color = new Color(255, 0, 0);
	
	public void init() {
		ActionListener actionListener = e ->{
			if(e.getActionCommand().equals("红色")) {
				color = new Color(255,0,0);
			}
			if(e.getActionCommand().equals("绿色")) {
				color  = new Color(0,255,0);
			}
			if(e.getActionCommand().equals("蓝色")) {
				color = new Color(0, 0, 255);
			}
		};
		redItem.addActionListener(actionListener);
		greenItem.addActionListener(actionListener);
		blueItem.addActionListener(actionListener);
		pmenu.add(redItem);
		pmenu.add(greenItem);
		pmenu.add(blueItem);
		drawCanvas.add(pmenu);
		//设置画布颜色
		g.fillRect(0, 0, AREA_WIDTH, AREA_HEIGHT);
		drawCanvas.setPreferredSize(new Dimension(AREA_WIDTH, AREA_HEIGHT));
		//监听鼠标移动动作
		drawCanvas.addMouseMotionListener(new MouseMotionAdapter() {
			//实现按下鼠标并拖动的事件处理器
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				if(preX > 0&&preY > 0) {
					g.setColor(color);
					g.drawLine(preX, preY, e.getX(), e.getY());
				}
				preX = e.getX();
				preY = e.getY();
				drawCanvas.repaint();
			}
		});
		
		drawCanvas.addMouseListener(new MouseAdapter() {
			//实现鼠标键松开的事件处理
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.isPopupTrigger()) {
					pmenu.show(drawCanvas, e.getX(), e.getY());
				}
				preX = -1;
				preY = -1;
			}
		});
		
		f.add(drawCanvas);
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
		new HandDraw().init();
	}
	class DrawCanvas extends Canvas{
		@Override
		public void paint(Graphics g) {
			// TODO Auto-generated method stub
			g.drawImage(image, 0, 0, null);
		}
	}
}
