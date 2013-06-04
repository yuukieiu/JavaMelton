package melton.model;

import java.net.URL;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class MeltonCore {
	// このへんはよく知らんおまじない
	private TwitterFactory factory;
	private TwitterStreamFactory streamFactory;
	private static Twitter twitter;
	private static TwitterStream twitterStream;
	private static URL url;				// アイコンのURL
	private boolean fileputflug;	// 画像添付フラグ（ここである必要性？）
	private boolean invisiflug;		// インビジフラグ（ここである(ry）
	private boolean allwaysnewflug;	// 最新ツイート追尾フラグ（こ(ry）
	
	// getter
	public TwitterFactory getfactory() { return factory; }
	public TwitterStreamFactory getstreamFactory() { return streamFactory; }
	public static Twitter gettwitter() { return twitter; }
	public static TwitterStream gettwitterStream() { return twitterStream; }
	public URL geturl() { return url; }
	public boolean getfileputflug() { return fileputflug; }
	public boolean getinvisiflug() { return invisiflug; }
	public boolean getallwaysnewflug() { return allwaysnewflug; }
	
	// setter
	public static void settwitter( Twitter t ) { twitter = t; }
	public static void settwitterStream( TwitterStream ts ) { twitterStream = ts; }
	public static void seturl( URL u ) { url = u; }
	
	public MeltonCore() {
		factory = new TwitterFactory();
		streamFactory = new TwitterStreamFactory();
		twitter = factory.getInstance();
		twitterStream = streamFactory.getInstance();
		fileputflug = false;
		invisiflug = false;
		allwaysnewflug = false;
		// あれ...もしかしてこいつ．．．．．．やることない．．．？
	}
}