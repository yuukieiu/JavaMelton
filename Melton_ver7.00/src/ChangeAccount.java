//アカウント切り替えのためのクラス

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;

public class ChangeAccount extends JFrame implements ListSelectionListener {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	DefaultListModel model;
	JLabel label;
	JButton changebutton;
	@SuppressWarnings("rawtypes")
	JList list;
	static TwitterFactory factory = new TwitterFactory();
	static Twitter twitter = factory.getInstance();
	static TwitterStream twitterStream = new TwitterStreamFactory().getInstance();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ChangeAccount() {
		setIconImage(MeltonV7XMain.icon);
		model = new DefaultListModel();
		list = new JList(model);
		label = new JLabel("切り替えるアカウントを選んでください。");
		changebutton = new JButton("切り替え");
		changebutton.addActionListener(new ChangeButtonActionAdapter());
		add(label, BorderLayout.NORTH);
		add(list, BorderLayout.CENTER);
		add(changebutton, BorderLayout.SOUTH);
		list.addListSelectionListener(this);
		FolderFind();
		setBounds(500,300,350,250);
		setTitle("Melton  - アカウント切替 -");
		setVisible(true);
	}
	
	@SuppressWarnings("unchecked")
	private void FolderFind () {
		File dirFile = new File( System.getProperty("user.home") + "/Melton/" );
		File[] subFiles = dirFile.listFiles();
		for ( int i = 0; i < subFiles.length; i++ ) {
			if ( !(subFiles[ i ].getName().matches("default")) ) {
				model.addElement(subFiles[ i ].getName());
			}
		}
	}
	
	private static AccessToken loadAccessToken(String name) {//アクセストークンを読みこむ
		File f = new File(System.getProperty("user.home") + "/Melton/" + name + "/property/accessToken.dat");
		ObjectInputStream is = null;
		try {
			is = new ObjectInputStream(new FileInputStream(f));
			AccessToken accessToken = (AccessToken) is.readObject();
			return accessToken;
		} catch ( IOException e ) {
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("010075");
			return null;
		} catch ( Exception e ) {
			e.printStackTrace();
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("010080");
			return null;
		} finally {
			if ( is != null ) {
				try {
					is.close();
				} catch ( IOException e ) {
					e.printStackTrace();
					@SuppressWarnings("unused")
					ErrorWindow error = new ErrorWindow("010089");
				}
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	class ChangeButtonActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			AccessToken accessToken = loadAccessToken((String) list.getSelectedValue());
			if ( !(MeltonV7XMain.accountchanged) ){
				twitter.setOAuthConsumer("eWgrWCzrtxRvyvP7MJUA", "6fovcZ4qMgv6LkUmReT82o5thWda8bsDRjD99QTaslo");
				twitterStream.setOAuthConsumer("eWgrWCzrtxRvyvP7MJUA", "6fovcZ4qMgv6LkUmReT82o5thWda8bsDRjD99QTaslo");
			}
			for ( int i = 0; i < MeltonV7XMain.loadednamenum; i++ ) {
				if( MeltonV7XMain.loadedname[i].matches((String) list.getSelectedValue()) ) {
					twitter.setOAuthAccessToken(accessToken);
					twitterStream.setOAuthAccessToken(accessToken);
					MeltonV7XMain.twitter = twitter;
					MeltonV7XMain.model0.clear();
					MeltonV7XMain.model1.clear();
					MeltonV7XMain.model2.clear();
					MeltonV7XMain.twitterAPITest();
					MeltonV7XMain.twitterStream.shutdown();
					MeltonV7XMain.twitterStream = new TwitterStreamFactory().getInstance();
					MeltonV7XMain.twitterStream = twitterStream;
					MeltonV7XMain.twitterStream.user();
					try {
						MeltonV7XMain.url = new URL(twitter.verifyCredentials().getProfileImageURL());
					} catch (MalformedURLException e) {
						// TODO 自動生成された catch ブロック
						@SuppressWarnings("unused")
						ErrorWindow error = new ErrorWindow("090126");
						e.printStackTrace();
					} catch (TwitterException e) {
						// TODO 自動生成された catch ブロック
						@SuppressWarnings("unused")
						ErrorWindow error = new ErrorWindow("090131");
						e.printStackTrace();
					}
					MeltonV7XMain.info.setIcon(new ImageIcon(MeltonV7XMain.url));
					dispose();
					return;
				}
			}
			twitter.setOAuthAccessToken(accessToken);
			twitterStream.setOAuthAccessToken(accessToken);
			MeltonV7XMain.twitter = twitter;
			MeltonV7XMain.model0.clear();
			MeltonV7XMain.model1.clear();
			MeltonV7XMain.model2.clear();
			MeltonV7XMain.twitterAPITest();
			MeltonV7XMain.twitterStream.shutdown();
			MeltonV7XMain.twitterStream = new TwitterStreamFactory().getInstance();
			twitterStream.addListener(new MyStatusAdapter());
			MeltonV7XMain.twitterStream = twitterStream;
			MeltonV7XMain.twitterStream.user();
			try {
				MeltonV7XMain.url = new URL(twitter.verifyCredentials().getProfileImageURL());
			} catch (MalformedURLException e) {
				// TODO 自動生成された catch ブロック
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("090156");
				e.printStackTrace();
			} catch (TwitterException e) {
				// TODO 自動生成された catch ブロック
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("090161");
				e.printStackTrace();
			}
			MeltonV7XMain.info.setIcon(new ImageIcon(MeltonV7XMain.url));
			MeltonV7XMain.accountchanged = true;
			dispose();
		}
	}
}