package melton.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class Model {
	private TwitterFactory factory;
	private TwitterStreamFactory streamFactory;
	private static Twitter twitter;
	private static TwitterStream twitterStream;
	private static String s;
	private static String pinCode;
	
	public String gets() { return s; }
	public TwitterFactory getfactory() { return factory; }
	public TwitterStreamFactory getstreamFactory() { return streamFactory; }
	public Twitter gettwitter() { return twitter; }
	public TwitterStream gettwitterStream() { return twitterStream; }
	public static String getpinCode() { return pinCode; }
	
	public void setpinCode( String pin) { pinCode = pin; }
	
	public Model( String s ) {
		factory = new TwitterFactory();
		twitter = factory.getInstance();
		streamFactory = new TwitterStreamFactory();
		twitterStream = streamFactory.getInstance();
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
		AccessToken accessToken = loadAccessToken();
		if ( accessToken != null ) {
			MeltonCore.settwitter( new TwitterFactory().getInstance() );
			MeltonCore.gettwitter().setOAuthConsumer("eWgrWCzrtxRvyvP7MJUA", "6fovcZ4qMgv6LkUmReT82o5thWda8bsDRjD99QTaslo");
			MeltonCore.gettwitter().setOAuthAccessToken(accessToken);
			MeltonCore.gettwitterStream().setOAuthAccessToken(accessToken);
			return;
		}
		while ( accessToken == null ) {
			MyLogin login = new MyLogin(requestToken.getAuthorizationURL());
			try {
				accessToken = MeltonCore.gettwitter().getOAuthAccessToken(requestToken, pinCode);
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
			} catch (TwitterException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
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
		s = System.getProperty("user.home") + "/Melton/";
		try {
			s = s + twitter.getScreenName();
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
	
	//アクセストークンを読みこむ
	private static AccessToken loadAccessToken() {
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
			return null;
		} finally {
			if ( is != null ) {
				try {
					is.close();
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			}
		}
	}
}