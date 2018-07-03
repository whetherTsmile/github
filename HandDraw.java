

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
	private final int AREA_WIDTH = 500;//��ͼ�����
	private final int AREA_HEIGHT = 400;//��ͼ���߶�
	private int preX,preY = -1; //��һ����������
	//����һ���Ҽ��˵��������û�����ɫ
	PopupMenu pmenu = new PopupMenu();
	MenuItem redItem = new MenuItem("��ɫ");
	MenuItem greenItem = new MenuItem("��ɫ");
	MenuItem blueItem = new MenuItem("��ɫ");
	//����һ��bufferedImage����
	BufferedImage image = new BufferedImage(AREA_WIDTH, AREA_HEIGHT, BufferedImage.TYPE_INT_RGB);
	//��ȡimage�����graphics
	Graphics g = image.getGraphics();
	DrawCanvas drawCanvas = new DrawCanvas();
	private Frame f = new Frame("���ֻ����");
	//���ڱ��滭����ɫ
	private Color color = new Color(255, 0, 0);
	
	public void init() {
		ActionListener actionListener = e ->{
			if(e.getActionCommand().equals("��ɫ")) {
				color = new Color(255,0,0);
			}
			if(e.getActionCommand().equals("��ɫ")) {
				color  = new Color(0,255,0);
			}
			if(e.getActionCommand().equals("��ɫ")) {
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
		//���û�����ɫ
		g.fillRect(0, 0, AREA_WIDTH, AREA_HEIGHT);
		drawCanvas.setPreferredSize(new Dimension(AREA_WIDTH, AREA_HEIGHT));
		//��������ƶ�����
		drawCanvas.addMouseMotionListener(new MouseMotionAdapter() {
			//ʵ�ְ�����겢�϶����¼�������
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
			//ʵ�������ɿ����¼�����
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
