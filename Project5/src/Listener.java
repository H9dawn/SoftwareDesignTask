

//写的时候，缺啥就加啥
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Listener {

	public static void main(String[] args) {
		JFrame frame = new JFrame();//实例化jframe，构造一个初始时不可见的新窗体
		frame.setTitle("Listener");//设置窗体标题
		frame.setSize(400,400);//设置窗体大小
		frame.setLocationRelativeTo(null);//使窗口显示在屏幕中央
		frame.setDefaultCloseOperation(3);
		//DISPOSE_ON_CLOSE，当点击关闭窗口时,它会仅仅关闭本窗口，而不会对应用程序下创建的其他窗口产生影响。
		
		MyPanel panel = new MyPanel();//new一个面板，为了画棋盘。
		
		panel.addMouseListener(panel);//为面板加入监听器，为了使用一些方法
		
		frame.add(panel);//把面板加入窗体中
		
		frame.setVisible(true);
		//数据模型已构造好，允许JVM可以根据数据模型执行paint方法开始画图并显示到屏幕上了
	}
}
class MyPanel extends JPanel implements MouseListener{
//创建我的面板，定义一些方法，
//继承了JPanel类和MouseListener接口，下面的方法都是内定的
	@Override
	public void mouseClicked(MouseEvent e) {     //点击并松开事件
		
	}

	@Override
	public void mousePressed(MouseEvent e) {     //点击不松开事件
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {     //释放鼠标事件
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {     //下面两个不用管了
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {     //
		
		
	}
	
}
