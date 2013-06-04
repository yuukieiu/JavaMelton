//リストのボーダーの定義

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicGraphicsUtils;

public class DotBorder extends LineBorder {
	private static final long serialVersionUID = 1L;
	public DotBorder(Color color, int thickness) {
        super(color, thickness);
    }
    @Override public boolean isBorderOpaque() {return true;}
    @Override public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
        Graphics2D g2 = (Graphics2D)g;
        g2.translate(x,y);
        g2.setPaint(getLineColor());
        BasicGraphicsUtils.drawDashedRect(g2, 0, 0, w, h);
        g2.translate(-x,-y);
    }
}