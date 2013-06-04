package melton;

import melton.model.*;
import melton.view.*;
import melton.controller.*;

public class Controller {
	public Controller(Model model, View view) {
		// メニュー処理（リスナーとか）
		view.getmenu().getaddAccount().addActionListener(new AddAccountListener(model,view));
		view.getmenu().getchangeAccount().addActionListener(new ChangeAccountListener(model, view));
		view.getmenu().getAllwaysNew().addActionListener(new AllwaysNewListener(model, view));
		view.getmenu().getInvisible().addActionListener(new InvisibleListener(model, view));
		
		// 各ボタンにリスナーセット
		view.getuser().gettweetbutton().addActionListener(new TweetButtonListener(model, view));
		view.getbutton().getReplyButton().addActionListener(new ReplyButtonListener(model, view));
		view.getbutton().getRetweetButton().addActionListener(new RetweetButtonListener(model, view));
		view.getbutton().getUfretweetButton().addActionListener(new UfretweetButtonListener(model, view));
		view.getbutton().getFavoriteButton().addActionListener(new FavoriteButtonListener(model, view));
		view.getbutton().getOpenButton().addActionListener(new OpenButtonListener(model, view));
		view.getbutton().getTalkButton().addActionListener(new TalkButtonListener(model, view));
		view.getbutton().getClearButton().addActionListener(new ClearButtonListener(model, view));
		view.getbutton().getProfileButton().addActionListener(new ProfileButtonListener(model, view));
		view.getbutton().getLinkButton().addActionListener(new LinkButtonListener(model,view));
		view.getbutton().getFileputButton().addActionListener(new FileputButtonListener(model, view));
		view.getbutton().getCopyButton().addActionListener(new CopyButtonListener(model, view));
		view.getbutton().getFavbombButton().addActionListener(new FavbombButtonListener(model, view));
		// モデルの変更をビューに伝えるオブザーバー
		// たとえばTLの内容（更新や消去）
		model.gettll();
			// API残数とかも
	}
}