package melton.view;
//「なにかがおかしいよ」のやつ

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ErrorWindow extends JDialog implements ActionListener{
	private static final long serialVersionUID = 1L;
	JLabel label;
	JButton button;
	JDialog dialog;
	
	public ErrorWindow (String code) {//コンストラクタ
		setIconImage(MeltonV6XMain.icon);
		JPanel panel0 = new JPanel();
		JPanel panel1 = new JPanel();
		label = new JLabel("<html>なにかがおかしいよ<br>エラーコード:" + code + "</html>");
		button = new JButton("OK");
		button.addActionListener(this);
		panel0.add(label);
		panel1.add(button);
		dialog = new JDialog();
		dialog.setResizable(false);
		dialog.getContentPane().setLayout(new FlowLayout());
		dialog.getContentPane().add(panel0);
		dialog.getContentPane().add(panel1);
		dialog.setTitle("Melton  - エラー -");
		dialog.setSize(250,150);
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if ( e.getSource() == button ) {
			dialog.dispose();
		}
	}
}