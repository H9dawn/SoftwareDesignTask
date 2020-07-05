

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ChessPanel extends JPanel implements MouseListener ,Runnable{
	//棋子数组
	//chesses[0][0]->chesses[18][18],默认是0，表示没有棋子，白色-1，黑色1
	int[][] chesses = new int[19][19];
	int currentColor = 1;//初始颜色：黑色，标志最近下的棋子颜色
	String blackMessage = "时间无限制";//就写个字
	String whiteMessage = "时间无限制";
	int blackTime = 0;//剩余落子时间
	int whiteTime = 0;
	Thread t = new Thread(this);//new一个线程
	int undoTimeX;//撤销时间
	int undoTimeY;
	
	@Override
	public void paint(Graphics g) {// 
		//1. 调用父类paint初始化一些东西
		super.paint(g);		
		//2. 设置背景图
		drawBackground(g);		
		//3. 画上面的文字（游戏信息：黑方先行）
		drawGameInfo(g);		
		//4. 画下面的文字 （黑方：时间无限制 白方：时间无限制）
		drawTime(g);		
		//5. 画棋盘(19条横线和19条纵线)
		drawChessBoard(g);		
		//6. 画棋子
		//一开始，上面一个棋子也没有，点一下，出来一个棋子
		for (int i = 0;i < 19; i++) {
			for (int j = 0;j < 19; j++) {
				if (chesses[i][j] != 0) {
					if (chesses[i][j]==1) {
						g.setColor(Color.BLACK);
					} else {
						g.setColor(Color.WHITE);
					}
					//画点函数，参数分别是左上角的坐标和宽高，单位都是px像素
					g.fillOval(10+i*20-16/2, 50+j * 20-16 / 2, 16, 16);
				}
			}
		}
	}

	public void drawBackground(Graphics g) {//画背景
		Image img = new ImageIcon("background.png").getImage();//直接借用一张图片
		g.drawImage(img, 0, 0, null);
	}

	public void drawGameInfo(Graphics g) {//写游戏信息
		//文字坐标：145x40		字体：微软雅黑/粗体/21
		g.setFont(new Font("微软雅黑", Font.BOLD, 21));
		g.drawString("游戏信息: 轮到" + (currentColor==1?"黑":"白") + "了", 145, 40);
	}

	public void drawTime(Graphics g) {//计时器，需要游戏里设置计时才倒计时
		//文字坐标：50x447 280x447		字体：宋体/普通/14
		g.setFont(new Font("宋体", Font.PLAIN, 14));//设置字体
		g.drawString("黑方: " + blackMessage, 50, 447);//写时间
		g.drawString("白方: " + whiteMessage, 280, 447);
	}

	public void drawChessBoard(Graphics g) {//画棋盘
		//起点坐标：10x50  格子宽：20
		//画19条横线		
		for (int i = 0; i < 19; i++) {
			g.drawLine(10, 50 + i * 20, 10 + 18 * 20, 50 + i * 20);
		}
		//画19条纵线
		for (int i = 0; i < 19; i++) {
			g.drawLine(10 + i * 20, 50, 10 + i * 20, 50 + 18 * 20);
		}
	}
	boolean gameOver = false;//游戏结束标志
	@Override
	public void mouseClicked(MouseEvent e) {//鼠标点击事件
		
	}
	
	public String currentColorStringfy() {//判断最近一次落子的颜色
		return currentColor==1?"白":"黑";
	}

	public void playChess(int mouseX,int mouseY) {//
		
	}
	//判断输赢
	public boolean checkWin(int x, int y) { 
		return checkOneDirection(x,y,-1,0)//判断横向
			|| checkOneDirection(x,y,0,-1)//判断纵向
			|| checkOneDirection(x,y,1,-1)//判断“/”方向
			|| checkOneDirection(x,y,-1,-1);//判断"\"方向
	}
	
	public boolean checkOneDirection(int x,int y,int e1,int e2) {//检查是否五子连
		int i = 1;
		int count = 1;
		while (
				x + e1 * i <= 18
			&&	y + e2 * i <= 18
			&&	y + e2 * i >= 0
			&&  x + e1 * i >= 0
			&&	chesses[x+e1*i][y+e2*i] != 0 
			&&  chesses[x+e1*i][y+e2*i]== chesses[x][y]) { 
			count++;
			i++;
		}
		i = 1;
		while (
				x - e1 * i >= 0
			&&	y - e2 * i >= 0
			&&  x - e1 * i <= 18
			&&	y - e2 * i <= 18
			&&	chesses[x-e1*i][y-e2*i] != 0 
			&&  chesses[x-e1*i][y-e2*i]== chesses[x][y]) { 
			count++;
			i++;
		}
		if (count >= 5) {
			return true;
		}
		return false;
	}
	
	public void run() {//运行
		while (true) {
			//拿到剩余时间
			if (currentColor == 1) {
				blackTime--;
				blackMessage = blackTime/3600 + ":" + blackTime%3600/60 + ":" + blackTime%3600%60; 
			} else {
				whiteTime--;
				whiteMessage = whiteTime/3600 + ":" + whiteTime%3600/60 + ":" + whiteTime%3600%60;
			}
			//休息一秒再刷新界面
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//刷新界面
			repaint();
			
			//如果时间没了   程序结束   且某一方输了
			if (blackTime <= 0 || whiteTime <= 0) {
				JOptionPane.showMessageDialog(this, currentColorStringfy() + "方超时，输了!");
				gameOver = true;
				t.stop();
			}
		}
		
	}
	
	//下面都是检查某个功能点的范围
	public boolean isChessBoardRange (int mouseX, int mouseY) {
		return mouseX>=10&&mouseX<=370&&mouseY>=50&&mouseY<=410;
	}

	public boolean isStartGameRange (int mouseX, int mouseY) {
		return mouseX>=401&&mouseX<=470&&mouseY>=51&&mouseY<=80;
	}

	public boolean isSettingRange (int mouseX, int mouseY) {
		return mouseX>=401&&mouseX<=470&&mouseY>=102&&mouseY<=130;
	}

	public boolean isGameBriefRge (int mouseX, int mouseY) {
		return mouseX>=401&&mouseX<=470&&mouseY>=151&&mouseY<=180;
	}
	
	public boolean isDefeat (int mouseX, int mouseY) {
		return mouseX>=401&&mouseX<=470&&mouseY>=200&&mouseY<=228;
	}

	public boolean isGiveUpRange (int mouseX, int mouseY) {
		return mouseX>=401&&mouseX<=470&&mouseY>=252&&mouseY<=280;
	}

	public boolean isAboutRange (int mouseX, int mouseY) {
		return mouseX>=401&&mouseX<=470&&mouseY>=302&&mouseY<=330;
	}

	public boolean isExitRange (int mouseX, int mouseY) {
		return mouseX>=401&&mouseX<=470&&mouseY>=351&&mouseY<=380;
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}