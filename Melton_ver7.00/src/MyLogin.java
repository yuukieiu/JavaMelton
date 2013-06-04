//初回アカウント認証と二回目以降のキー読み込みのクラス

import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class MyLogin extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	JLabel labelpin;
	JTextField textpin;
	JButton okbutton;
	JButton cancelbutton;
	JDialog dialog;
	JLabel info;
	static String s;
	
	public MyLogin ( String text ) {//コンストラクタ
		dialog = new JDialog();
		dialog.setIconImage(MeltonV7XMain.icon);
		JPanel panel0 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		info = new JLabel("ブラウザでアカウント認証したあと表示されるPINコードを入力してください。");
		try{
			Desktop desktop = Desktop.getDesktop();
			desktop.browse(new URI(text));
		}catch(IOException ioe) {
			ioe.printStackTrace();
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("0A0050");
		}catch(URISyntaxException use) {
			use.printStackTrace();
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("0A0054");
		}
		//adress.setLineWrap(true);
		labelpin = new JLabel("PINコード:");
		textpin =new JTextField(15);
		panel0.add(info);
		panel2.add(labelpin);
		panel3.add(textpin);
		okbutton = new JButton("OK");
		cancelbutton = new JButton("Cancel");
		okbutton.addActionListener(this);
		cancelbutton.addActionListener(this);
		panel3.add(okbutton);
		panel3.add(cancelbutton);
		dialog.setResizable(false);
		dialog.getContentPane().add(panel0);
		dialog.getContentPane().add(panel2);
		dialog.getContentPane().add(panel3);
		dialog.setTitle("Melton  - アカウント認証 -");
		dialog.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
		dialog.setSize(500,170);
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
	}
	
	public String getPinCode() {
		return textpin.getText();
	}
	
	public static void MyLoginDialog() {
		MeltonV7XMain.twitter.setOAuthConsumer("eWgrWCzrtxRvyvP7MJUA", "6fovcZ4qMgv6LkUmReT82o5thWda8bsDRjD99QTaslo");
		MeltonV7XMain.twitterStream.setOAuthConsumer("eWgrWCzrtxRvyvP7MJUA", "6fovcZ4qMgv6LkUmReT82o5thWda8bsDRjD99QTaslo");
		RequestToken requestToken = null;
		try {
			requestToken = MeltonV7XMain.twitter.getOAuthRequestToken();
		} catch (TwitterException e) {
			// TODO 自動生成された catch ブロック
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("0A0093");
			e.printStackTrace();
		}
		AccessToken accessToken = loadAccessToken();
		if ( accessToken != null ) {
			MeltonV7XMain.twitter = new TwitterFactory().getInstance();
			MeltonV7XMain.twitter.setOAuthConsumer("eWgrWCzrtxRvyvP7MJUA", "6fovcZ4qMgv6LkUmReT82o5thWda8bsDRjD99QTaslo");
			MeltonV7XMain.twitter.setOAuthAccessToken(accessToken);
			MeltonV7XMain.twitterStream.setOAuthAccessToken(accessToken);
			return;
		}
		while ( accessToken == null ) {
			MeltonV7XMain.start.start.dispose();
			MyLogin login = new MyLogin(requestToken.getAuthorizationURL());
			String pin = login.getPinCode();
			try {
				if ( pin.length() > 0 ) {
					accessToken = MeltonV7XMain.twitter.getOAuthAccessToken(requestToken, pin);
					MeltonV7XMain.twitterStream.setOAuthAccessToken(accessToken);
				} else {
					accessToken = MeltonV7XMain.twitter.getOAuthAccessToken();
					MeltonV7XMain.twitterStream.setOAuthAccessToken(accessToken);
				}
			} catch ( TwitterException te ) {
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("0A0118");
				if ( te.getStatusCode() == 401 ) {
					//エラー処理するの…？
				} else {
					te.printStackTrace();
				}
			}
			try {
				storeAccessToken(MeltonV7XMain.twitter.verifyCredentials().getId(), accessToken);
			} catch (TwitterException e) {
				// TODO 自動生成された catch ブロック
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("0A0130");
				e.printStackTrace();
			}
		}
		return;
	}
	
	private static AccessToken loadAccessToken() {//アクセストークンを読みこむ
		File f = createAccessTokenFileName();
		ObjectInputStream is = null;
		try {
			is = new ObjectInputStream(new FileInputStream(f));
			AccessToken accessToken = (AccessToken) is.readObject();
			return accessToken;
		} catch ( IOException e ) {
			//@SuppressWarnings("unused")
			//ErrorWindow error = new ErrorWindow("0B05");
			return null;
		} catch ( Exception e ) {
			e.printStackTrace();
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("0A0151");
			return null;
		} finally {
			if ( is != null ) {
				try {
					is.close();
				} catch ( IOException e ) {
					e.printStackTrace();
					@SuppressWarnings("unused")
					ErrorWindow error = new ErrorWindow("0A0160");
				}
			}
		}
	}
	
	private static void storeAccessToken(long l, AccessToken accessToken) {
		//アカウント情報を保存する
		File f = createAccessTokenFileName();
		File d = f.getParentFile();
		if ( !d.exists() ) { d.mkdirs(); }
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(new FileOutputStream(f));
			os.writeObject(accessToken);
		} catch ( IOException e ) {
			e.printStackTrace();
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("0A0178");
		} finally {
			if ( os != null ) {
				try {
					os.close();
				} catch ( IOException e ) {
					e.printStackTrace();
					@SuppressWarnings("unused")
					ErrorWindow error = new ErrorWindow("0A0186");
				}
			}
		}
		s = System.getProperty("user.home") + "/Melton/";
		try {
			s = s + MeltonV7XMain.twitter.getScreenName();
		} catch (IllegalStateException e) {
			// TODO 自動生成された catch ブロック
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("0A0196");
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO 自動生成された catch ブロック
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("0A0201");
			e.printStackTrace();
		}
		s = s + "/property/accessToken.dat";
		f = new File(s);
		d = f.getParentFile();
		if ( !d.exists() ) { d.mkdirs(); }
		try {
			os = new ObjectOutputStream(new FileOutputStream(f));
			os.writeObject(accessToken);
		} catch ( IOException e ) {
			e.printStackTrace();
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("0A0214");
		} finally {
			if ( os != null ) {
				try {
					os.close();
				} catch ( IOException e ) {
					e.printStackTrace();
					@SuppressWarnings("unused")
					ErrorWindow error = new ErrorWindow("0A0222");
				}
			}
		}
	}
	
	static File createAccessTokenFileName() {
		//アカウント情報を保存するためのファイルの名前を生成する
		s = System.getProperty("user.home") + "/Melton/";
		try {
			s = s + "default";
		} catch (IllegalStateException e) {
			// TODO 自動生成された catch ブロック
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("0A0236");
			e.printStackTrace();
		}
		s = s + "/property/accessToken.dat";
		return new File(s);
	}
	
	public void actionPerformed(ActionEvent e) {
		if ( e.getSource() == okbutton ) {
			dialog.dispose();
		} else if ( e.getSource() == cancelbutton ) {
			System.exit(0);
		}
	}
}