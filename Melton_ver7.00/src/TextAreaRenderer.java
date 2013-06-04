//リストのデザインを決めるレンダラー

import java.awt.Color;
import java.awt.Component;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

import twitter4j.TwitterException;

@SuppressWarnings("rawtypes")
public class TextAreaRenderer extends JLabel implements ListCellRenderer {
	private static final long serialVersionUID = 1L;
	private Border focusBorder;
	private static final Border NOMAL_BORDER = BorderFactory.createEmptyBorder(2,2,2,2);
	private static final Color EVEN_COLOR = new Color(230,255,230);
	private URL url = null;
	private ImageIcon icon = null;
	@Override public Component getListCellRendererComponent(JList list, Object object, int index, boolean isSelected, boolean cellHasFocus) {
		Color mentionc = new Color(Integer.parseInt("99ffcc",16));
		Color rtcolor = new Color(Integer.parseInt("66ff66",16));
		if ( focusBorder == null ) {
			focusBorder = new DotBorder( new Color(~list.getSelectionBackground().getRGB()),2);
		}
		if ( object instanceof TimeLineData ) {
			if ( isSelected ) {
				setBackground(Color.gray);
				setForeground(list.getSelectionForeground());
			} else if ( ((TimeLineData) object).getStatus().isRetweet() ) {
				setBackground(rtcolor);
			} else
				try {
					if ( ((TimeLineData) object).getStatus().getInReplyToUserId() == MeltonV7XMain.twitter.getId() ) {
						setBackground(mentionc);
					} else {
						setBackground(index%2 == 0 ? EVEN_COLOR : list.getBackground());
						setForeground(list.getForeground());
					}
				} catch (IllegalStateException e1) {
					// TODO 自動生成された catch ブロック
					@SuppressWarnings("unused")
					ErrorWindow error = new ErrorWindow("1000");
					e1.printStackTrace();
				} catch (TwitterException e1) {
					// TODO 自動生成された catch ブロック
					@SuppressWarnings("unused")
					ErrorWindow error = new ErrorWindow("1001");
					e1.printStackTrace();
				}
			try {
				url = new URL(((TimeLineData) object).getImageURL());
			} catch (MalformedURLException e) {
				// TODO 自動生成された catch ブロック
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("1002");
				e.printStackTrace();
			}
			icon = new ImageIcon(url);
			setBorder(cellHasFocus ? focusBorder : NOMAL_BORDER);
			setIcon(icon);
			if ( ((TimeLineData) object).getStatus().getUser().isProtected() ) {
				setText("<html><b>" + ((TimeLineData) object).getScreenName() + "</b> -" + ((TimeLineData) object).getUserName() + "- <b>Protected</b><br>" + ((TimeLineData) object).getTweetText() + "<br><I>" + ((TimeLineData) object).getDate() + "</I><br>via " + ((TimeLineData) object).getCliantname() + "</html>");
			} else {
				setText("<html><b>" + ((TimeLineData) object).getScreenName() + "</b> -" + ((TimeLineData) object).getUserName() + "-<br>" + ((TimeLineData) object).getTweetText() + "<br><I>" + ((TimeLineData) object).getDate() + "</I><br>via " + ((TimeLineData) object).getCliantname() + "</html>");
			}
			if ( ((TimeLineData) object).getStatus().isRetweet() ) {
				try {
					url = new URL(((TimeLineData) object).getStatus().getRetweetedStatus().getUser().getProfileImageURL());
				} catch (MalformedURLException e) {
					// TODO 自動生成された catch ブロック
					@SuppressWarnings("unused")
					ErrorWindow error = new ErrorWindow("1003");
					e.printStackTrace();
				}
				icon = new ImageIcon(url);
				setIcon(icon);
				setText("<html><b>" + ((TimeLineData) object).getStatus().getRetweetedStatus().getUser().getScreenName() + "</b> -" + ((TimeLineData) object).getStatus().getRetweetedStatus().getUser().getName() + "-<br>" + ((TimeLineData) object).getStatus().getRetweetedStatus().getText() + "<br><I>" + ((TimeLineData) object).getStatus().getRetweetedStatus().getCreatedAt() + "</I><br>Retweeted by  " + ((TimeLineData) object).getUserName() + "</html>");
				//((TimeLineData) object).setScreenName(((TimeLineData) object).getStatus().getRetweetedStatus().getUser().getScreenName());
				//((TimeLineData) object).setUserName(((TimeLineData) object).getStatus().getRetweetedStatus().getUser().getName());
				//((TimeLineData) object).setTweetText(((TimeLineData) object).getStatus().getRetweetedStatus().getText());
				//((TimeLineData) object).setStatus(((TimeLineData) object).getStatus().getRetweetedStatus());
			}
			setOpaque(true);
		} else if ( object instanceof String ) {
			if ( isSelected ) {
				setBackground(Color.gray);
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(index%2 == 0 ? EVEN_COLOR : list.getBackground());
				setForeground(list.getForeground());
			}
			setText("<html><a Href = " + object +">"  + object + "</a></html>");
		}
		return this;
	}
	
	  /*private static String htmlEscape(String strVal){
		  StringBuffer strResult=new StringBuffer();
		  for(int i=0;i<strVal.length();i++){
		    switch(strVal.charAt(i)){
		    case '&' :
		      strResult.append("&amp;");
		      break;
		    case '<' :
		      strResult.append("&lt;");
		      break;
		    case '>' :
		      strResult.append("&gt;");
		      break;
		    default :
		      strResult.append(strVal.charAt(i));
		      break;
		    }
		  }
		  return strResult.toString();
	  }*/
}