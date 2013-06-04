import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class StartWindow extends Thread {
	JFrame start;
	JLabel info;
	public void run() {
		start = new JFrame();
		//Image icon = start.getToolkit().getImage("Meltonicon.png");
    	start.setIconImage(MeltonV7XMain.icon);
		JPanel panel = new JPanel();
		info = new JLabel("起動中です…");
		panel.add(info);
		start.setResizable(false);
		start.getContentPane().add(panel);
		start.setTitle("Melton  - 起動中… -");
		start.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
		start.setSize(250,70);
		start.setLocationRelativeTo(null);
		start.setVisible(true);
		start.setAlwaysOnTop(true);
	}
}