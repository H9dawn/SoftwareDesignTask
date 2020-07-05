

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
	//��������
	//chesses[0][0]->chesses[18][18],Ĭ����0����ʾû�����ӣ���ɫ-1����ɫ1
	int[][] chesses = new int[19][19];
	int currentColor = 1;//��ʼ��ɫ����ɫ����־����µ�������ɫ
	String blackMessage = "ʱ��������";//��д����
	String whiteMessage = "ʱ��������";
	int blackTime = 0;//ʣ������ʱ��
	int whiteTime = 0;
	Thread t = new Thread(this);//newһ���߳�
	int undoTimeX;//����ʱ��
	int undoTimeY;
	
	@Override
	public void paint(Graphics g) {// 
		//1. ���ø���paint��ʼ��һЩ����
		super.paint(g);		
		//2. ���ñ���ͼ
		drawBackground(g);		
		//3. ����������֣���Ϸ��Ϣ���ڷ����У�
		drawGameInfo(g);		
		//4. ����������� ���ڷ���ʱ�������� �׷���ʱ�������ƣ�
		drawTime(g);		
		//5. ������(19�����ߺ�19������)
		drawChessBoard(g);		
		//6. ������
		//һ��ʼ������һ������Ҳû�У���һ�£�����һ������
		for (int i = 0;i < 19; i++) {
			for (int j = 0;j < 19; j++) {
				if (chesses[i][j] != 0) {
					if (chesses[i][j]==1) {
						g.setColor(Color.BLACK);
					} else {
						g.setColor(Color.WHITE);
					}
					//���㺯���������ֱ������Ͻǵ�����Ϳ�ߣ���λ����px����
					g.fillOval(10+i*20-16/2, 50+j * 20-16 / 2, 16, 16);
				}
			}
		}
	}

	public void drawBackground(Graphics g) {//������
		Image img = new ImageIcon("background.png").getImage();//ֱ�ӽ���һ��ͼƬ
		g.drawImage(img, 0, 0, null);
	}

	public void drawGameInfo(Graphics g) {//д��Ϸ��Ϣ
		//�������꣺145x40		���壺΢���ź�/����/21
		g.setFont(new Font("΢���ź�", Font.BOLD, 21));
		g.drawString("��Ϸ��Ϣ: �ֵ�" + (currentColor==1?"��":"��") + "��", 145, 40);
	}

	public void drawTime(Graphics g) {//��ʱ������Ҫ��Ϸ�����ü�ʱ�ŵ���ʱ
		//�������꣺50x447 280x447		���壺����/��ͨ/14
		g.setFont(new Font("����", Font.PLAIN, 14));//��������
		g.drawString("�ڷ�: " + blackMessage, 50, 447);//дʱ��
		g.drawString("�׷�: " + whiteMessage, 280, 447);
	}

	public void drawChessBoard(Graphics g) {//������
		//������꣺10x50  ���ӿ�20
		//��19������		
		for (int i = 0; i < 19; i++) {
			g.drawLine(10, 50 + i * 20, 10 + 18 * 20, 50 + i * 20);
		}
		//��19������
		for (int i = 0; i < 19; i++) {
			g.drawLine(10 + i * 20, 50, 10 + i * 20, 50 + 18 * 20);
		}
	}
	boolean gameOver = false;//��Ϸ������־
	@Override
	public void mouseClicked(MouseEvent e) {//������¼�
		
	}
	
	public String currentColorStringfy() {//�ж����һ�����ӵ���ɫ
		return currentColor==1?"��":"��";
	}

	public void playChess(int mouseX,int mouseY) {//
		
	}
	//�ж���Ӯ
	public boolean checkWin(int x, int y) { 
		return checkOneDirection(x,y,-1,0)//�жϺ���
			|| checkOneDirection(x,y,0,-1)//�ж�����
			|| checkOneDirection(x,y,1,-1)//�жϡ�/������
			|| checkOneDirection(x,y,-1,-1);//�ж�"\"����
	}
	
	public boolean checkOneDirection(int x,int y,int e1,int e2) {//����Ƿ�������
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
	
	public void run() {//����
		while (true) {
			//�õ�ʣ��ʱ��
			if (currentColor == 1) {
				blackTime--;
				blackMessage = blackTime/3600 + ":" + blackTime%3600/60 + ":" + blackTime%3600%60; 
			} else {
				whiteTime--;
				whiteMessage = whiteTime/3600 + ":" + whiteTime%3600/60 + ":" + whiteTime%3600%60;
			}
			//��Ϣһ����ˢ�½���
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//ˢ�½���
			repaint();
			
			//���ʱ��û��   �������   ��ĳһ������
			if (blackTime <= 0 || whiteTime <= 0) {
				JOptionPane.showMessageDialog(this, currentColorStringfy() + "����ʱ������!");
				gameOver = true;
				t.stop();
			}
		}
		
	}
	
	//���涼�Ǽ��ĳ�����ܵ�ķ�Χ
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