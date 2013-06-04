package melton;

import melton.model.*;
import melton.model.addaccount.AddAccount;
import melton.model.changeaccount.ChangeAccount;
import melton.model.favbomb.FavBomb;
import melton.model.link.Link;
import melton.model.login.Login;
import melton.model.meltonerror.MeltonError;
import melton.model.notifier.Notifier;
import melton.model.open.Open;
import melton.model.profile.Profile;
import melton.model.talk.Talk;

public class Model {
	// privateで宣言
	private MeltonCore core;	// ここで主にtwitter4jとの連携とユーザーのいろいろを...
	private TimeLineList tll;	// タイムラインだけど見えてない
	private AddAccount ada;		// アカウント追加機能
	private ChangeAccount cha;	// アカウント切替機能
	private MeltonError me;		// エラー表示機能
	private FavBomb fb;			// ふぁぼ爆機能
	private Notifier n;			// 各種通知機能
	private Link link;			// リンク
	private Login login;		// オートログイン
	private Open op;			// いずれ消えるであろう開く機能...
	private Profile pro;		// プロフィール表示機能
	private Talk talk;			// 会話表示機能
	// getterつくる
	public MeltonCore getcore() { return core; }
	public TimeLineList gettll() { return tll; }
	public AddAccount getada() { return ada; }
	public ChangeAccount getcha() { return cha; }
	public MeltonError getme() { return me; }
	public FavBomb getfb() { return fb; }
	public Notifier getn() { return n; }
	public Link getlink() { return link; }
	public Login getlogin() { return login; }
	public Open getop() { return op; }
	public Profile getpro() { return pro; }
	public Talk gettalk() { return talk; }
	
	// Meltonのモデル生成
	public Model() {
		core = new MeltonCore();
		tll = new TimeLineList();
		ada = new AddAccount("dummy");
		cha = new ChangeAccount();
		me = new MeltonError();
		fb = new FavBomb();
		n = new Notifier();
		link = new Link();
		login = new Login();
		op = new Open();
		pro = new Profile();
		talk = new Talk();
	}
	
	// 起動処理
	public void startUp(){
		// アカウントデータ読みこむやつ
		// loginからcoreのtwitterを生成的な...
		// TLを読みこむ
	}
	
	// 終了処理
	public void shutDoun() {
		// ウィンドウ閉じる以外で終了する必要性（）
	}
	
	// リセット（いるかどうかわからない）
	public void reset() {
		// メモリ解放のためにある程度までTLが溜まったら捨てる...？
	}
}