import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import twitter4j.Status;


public class InfomationNotice extends JFrame {
	private static final long serialVersionUID = 1L;
	JFrame infonotice;
	String[] cliantname = new String[2];
	JLabel label;
	JPanel panel;
	JTextArea noticetext;
	public InfomationNotice ( Status status, int type ){//コンストラクタ
		infonotice = new JFrame();
		infonotice.setIconImage(MeltonV7XMain.icon);
		cliantname = status.getSource().split(">");
		if ( !(cliantname[0].equals("web")) ) {
			cliantname = cliantname[1].split("<");
		}
		switch(type){
		case 1:
			infonotice.setTitle("リプライが届きました");
			break;
		case 2:
			infonotice.setTitle("お気に入りに追加されました");
			break;
		case 3:
			infonotice.setTitle("リツイートされました");
			break;
		case 4:
			infonotice.setTitle("フォローされました");
			break;
		default:
			infonotice.setTitle("Melton  - Information -");
			break;
		}
		noticetext = new JTextArea( status.getUser().getScreenName() + " -" + status.getUser().getName() + "-\n" + status.getText() + "\n" + status.getCreatedAt() + "\nvia " + cliantname[0] );
		noticetext.setEditable(false);
		JScrollPane scroll = new JScrollPane(noticetext);
		JProgressBar noticetime = new JProgressBar(0,5000);
		noticetext.setLineWrap(true);
		infonotice.setResizable(false);
		infonotice.getContentPane().setLayout(new BorderLayout());
		infonotice.getContentPane().add(BorderLayout.CENTER,scroll);
		infonotice.getContentPane().add(BorderLayout.NORTH,noticetime);
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		// 変数desktopBoundsにデスクトップ領域を表すRectangleが代入される
		Rectangle desktopBounds = env.getMaximumWindowBounds();
		infonotice.setBounds(5,desktopBounds.height - 205,400,200);
		infonotice.setVisible(true);
		infonotice.setAlwaysOnTop(true);
		infonotice.setDefaultCloseOperation(HIDE_ON_CLOSE);
		for ( int i = 0; i <= 5000; i++ ) {
			try {
				Thread.sleep(1);
			} catch ( InterruptedException e ) {
				//System.out.println(e);
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("060065");
			}
			noticetime.setValue(i);
		}
		infonotice.setVisible(false);
		dispose();
	}
}