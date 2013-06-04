import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.URLEntity;

public class LinkWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	JFrame linkwindow;
	String link;
	@SuppressWarnings("rawtypes")
	DefaultListModel model = new DefaultListModel();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JList list = new JList(model);
	JScrollPane scroll;
	@SuppressWarnings("unchecked")
	public LinkWindow ( Status status ) {
		linkwindow = new JFrame();
		setIconImage(MeltonV7XMain.icon);
		URLEntity[] urls = null;
		MediaEntity[] medias = null;
		urls = status.getURLEntities();
		list.setCellRenderer(new TextAreaRenderer());
		list.setFixedCellHeight(50);
		list.setLayoutOrientation(JList.VERTICAL);
		scroll = new JScrollPane(list);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(7);
		for ( int i = 0; urls.length > i; i++ ) {
			link = urls[i].getExpandedURL();
			model.addElement(link);
		}
		medias = status.getMediaEntities();
		for ( int i = 0; medias.length > i; i++ ){
			link = medias[i].getExpandedURL();
			model.addElement(link);
		}
		add(scroll);
		setTitle("Melton  - リンク -");
		list.addMouseListener(new MouseAdapter());
		setBounds(500,300,350,250);
		setVisible(true);
		setAlwaysOnTop(true);
	}
	
	class MouseAdapter implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO 自動生成されたメソッド・スタブ

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO 自動生成されたメソッド・スタブ
			list.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO 自動生成されたメソッド・スタブ

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO 自動生成されたメソッド・スタブ
			try{
				Desktop desktop = Desktop.getDesktop();
				desktop.browse(new URI((String) list.getSelectedValue()));
			}catch(IOException ioe) {
				ioe.printStackTrace();
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("080085");
			}catch(URISyntaxException use) {
				use.printStackTrace();
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("080089");
			}
			setVisible(false);
			linkwindow.dispose();
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO 自動生成されたメソッド・スタブ

		}

	}
}
