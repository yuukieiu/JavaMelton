package melton.model;

// 初回アカウント認証と二回目以降のキー読み込みのクラス
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// メイン部分
public class MyLogin {
	private Model model;
	private View view;
	
	// getter
	public Model getmodel() { return model; }
	public View getview() { return view; }
	
	public MyLogin( String s ) {
		model = new Model(s);
		view = new View(model);
		// Model.MyLoginDialog();
	}
}

@SuppressWarnings("serial")
class View extends JDialog {
	private JLabel labelpin;
	private JLabel info;
	private JTextField textpin;
	private JButton okbutton;
	private JButton cancelbutton;
	private JPanel panel0;
	private JPanel panel1;
	private JPanel panel2;
	TextObserble to;
	
	public JLabel getlabelpin() { return labelpin; }
	public JLabel getinfo() { return info; }
	public JTextField gettextpin() { return textpin; }
	public JButton getokbutton() { return okbutton; }
	public JButton getcancelbutton() { return cancelbutton; }
	public JPanel getpanel0() { return panel0; }
	public JPanel getpanel1() { return panel1; }
	public JPanel getpanel2() { return panel2; }
	
	public View(Model model) {
		labelpin = new JLabel("PINコード:");
		info = new JLabel("アカウント認証したあと表示されるPINコードを入力してください。");
		textpin =new JTextField(15);
		panel0 = new JPanel();
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel0.add(info);
		panel1.add(labelpin);
		panel2.add(textpin);
		okbutton = new JButton("OK");
		okbutton.addActionListener(new ButtonListener());
		//okbutton.setEnabled(false);
		cancelbutton = new JButton("Cancel");
		cancelbutton.addActionListener(new ButtonListener());
		to = new TextObserble();
		to.addObserver(new TextObserver(model, this));
		panel2.add(okbutton);
		panel2.add(cancelbutton);
		setResizable(false);
		getContentPane().add(panel0);
		getContentPane().add(panel1);
		getContentPane().add(panel2);
		setTitle("Melton  - アカウント追加 -");
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
		setSize(400,150);
		setLocationRelativeTo(null);
		setModal(true);
		setVisible(true);
	}
	
	public void AAcDispose() {
		dispose();
	}
	
	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			AAcDispose();
		}
	}
	
	class TextObserble extends Observable {
		public void notifyObservers() {
			if ( textpin.getText().length() != 0 ) {
				// 入力されたことを知らせる
			}
		}
	}
}

class TextObserver implements Observer {
	Model model;
	View view;
	
	public TextObserver(Model m, View v ) {
		model = m;
		view = v;
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("TextObserber called.");
		model.setpinCode(view.gettextpin().getText());
		view.getokbutton().setEnabled(true);
	}
	
}