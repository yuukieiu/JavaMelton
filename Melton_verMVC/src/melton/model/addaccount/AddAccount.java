package melton.model.addaccount;

//アカウント追加機能
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import melton.model.*;

// メイン部分
public class AddAccount extends MyLogin{

	public AddAccount(String s) {
		super(s);
		// TODO 自動生成されたコンストラクター・スタブ
	}

}

class AddModel extends Model{
	
	public AddModel(String s) {
		super(s);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	//アカウント認証のためのダイアログ
	public static void MyLoginDialog() {
		MeltonCore.gettwitter().setOAuthConsumer("eWgrWCzrtxRvyvP7MJUA", "6fovcZ4qMgv6LkUmReT82o5thWda8bsDRjD99QTaslo");
		MeltonCore.gettwitterStream().setOAuthConsumer("eWgrWCzrtxRvyvP7MJUA", "6fovcZ4qMgv6LkUmReT82o5thWda8bsDRjD99QTaslo");
		RequestToken requestToken = null;
		try {
			requestToken = MeltonCore.gettwitter().getOAuthRequestToken();
		} catch (TwitterException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		AccessToken accessToken = null;
		while ( accessToken == null ) {
			MyLogin login = new MyLogin(requestToken.getAuthorizationURL());
			try {
				accessToken = MeltonCore.gettwitter().getOAuthAccessToken(requestToken, getpinCode());
				MeltonCore.gettwitterStream().setOAuthAccessToken(accessToken);
			} catch ( TwitterException te ) {
				if ( te.getStatusCode() == 401 ) {
					//エラー処理するの...？
				} else {
					te.printStackTrace();
				}
			}
			
			try {
				storeAccessToken(MeltonCore.gettwitter().verifyCredentials().getId(), accessToken);
			} catch (IllegalStateException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (TwitterException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		//MeltonCore.settwitter( twitter );
		// ここでTL初期化
		// 初期読み込み
		MeltonCore.gettwitterStream().shutdown();
		//MeltonCore.settwitterStream( twitterStream );
		// twitterStreamにListenerセット
		MeltonCore.gettwitterStream().user();
		try {
			MeltonCore.seturl( new URL(MeltonCore.gettwitter().verifyCredentials().getProfileImageURL()) );
		} catch (MalformedURLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		// アイコンを変える（URLにオブザーバーつけて反応させる？）
		return;
	}
	
	//アカウント情報をファイルに保存する
	private static void storeAccessToken(long l, AccessToken accessToken) {
		File f = null;
		try {
			f = createAccessTokenFileName();
		} catch (IllegalStateException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		File d = f.getParentFile();
		if ( !d.exists() ) { d.mkdirs(); }
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(new FileOutputStream(f));
			os.writeObject(accessToken);
		} catch ( IOException e ) {
			e.printStackTrace();
		} finally {
			if ( os != null ) {
				try {
					os.close();
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//アカウント情報を保存するファイルの名前を生成する
	static File createAccessTokenFileName() {
		String s;
		s = System.getProperty("user.home") + "/Melton/";
		try {
			s = s + MeltonCore.gettwitter().getScreenName();
		} catch (IllegalStateException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		s = s + "/property/accessToken.dat";
		return new File(s);
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