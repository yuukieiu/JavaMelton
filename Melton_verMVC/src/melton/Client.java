package melton;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Client {
	Model model;
	View view;
	Controller ctl;
	
	Client() {
		model = new Model();
		view = new View();
		ctl = new Controller(model, view);
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		Client melton7 = new Client();
	}
}