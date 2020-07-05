

import javax.swing.JFrame;

public class ChessGame {
	public static void main(String[] args) {
		JFrame frame = new JFrame();//下面同监听器
		frame.setTitle("五子棋");
		frame.setSize(500,510);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(3);
		ChessPanel panel = new ChessPanel();
		panel.addMouseListener(panel);
		frame.add(panel);
		
		frame.setVisible(true);
	}
}
