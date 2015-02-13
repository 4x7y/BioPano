package cn.edu.ustc.biofilm.BioPano.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;


import java.awt.event.*;
import java.awt.*;



public class SearchToolBar extends JPanel {

    public JSearchTextField jf;

    public SearchToolBar() {

        //this.setPreferredSize(new Dimension(250, 100));
        jf = new JSearchTextField();

        setLayout(new BorderLayout(0, 0));
        add(jf, BorderLayout.NORTH);
        jf.setColumns(0);
    }



}


class JSearchTextField extends JIconTextField implements FocusListener {

    private String textWhenNotFocused;
    private ImageIcon icon;
    public JSearchTextField() {
        super();
        this.textWhenNotFocused = "Search...";
        this.addFocusListener(this);
        icon = new ImageIcon(getClass().getResource("/cn/edu/ustc/biofilm/BioPano/images/find.png"));
        this.setIcon(icon);
    }

    public String getTextWhenNotFocused() {
        return this.textWhenNotFocused;
    }

    public void setTextWhenNotFocused(String newText) {
        this.textWhenNotFocused = newText;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!this.hasFocus() && this.getText().equals("")) {
            int width = this.getWidth();
            int height = this.getHeight();
            Font prev = g.getFont();
            Font italic = prev.deriveFont(Font.ITALIC);
            Color prevColor = g.getColor();
            g.setFont(italic);
            g.setColor(UIManager.getColor("textInactiveText"));
            int h = g.getFontMetrics().getHeight();
            int textBottom = (height - h) / 2 + h - 4;
            int x = this.getInsets().left;
            Graphics2D g2d = (Graphics2D) g;
            RenderingHints hints = g2d.getRenderingHints();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.drawString(textWhenNotFocused, x, textBottom);
            g2d.setRenderingHints(hints);
            g.setFont(prev);
            g.setColor(prevColor);
        }

    }

    //FocusListener implementation:
    public void focusGained(FocusEvent e) {
        this.repaint();
    }

    public void focusLost(FocusEvent e) {
        this.repaint();
    }
}

class JIconTextField extends JTextField{

    private Icon icon;
    private Insets dummyInsets;

    public JIconTextField(){
        super();
        this.icon = null;
        /*
        Border border = UIManager.getBorder("TextField.border");

        this.dummyInsets = border.getBorderInsets(dummy);
        */
        JTextField dummy = new JTextField();
        Border line = BorderFactory.createLineBorder(Color.lightGray);
        Border empty = new EmptyBorder(5,28,5, 5);
        CompoundBorder border = new CompoundBorder(line, empty);
        this.dummyInsets = border.getBorderInsets(dummy);
        this.setBorder(border);
    }

    public void setIcon(Icon icon){
        this.icon = icon;
    }

    public Icon getIcon(){
        return this.icon;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int textX = 2;

        if(this.icon!=null){
            int iconWidth = icon.getIconWidth();
            int iconHeight = icon.getIconHeight();
            int x = dummyInsets.left-20;//this is our icon's x
            textX = x+iconWidth+2; //this is the x where text should start
            int y = (this.getHeight() - iconHeight)/2;
            icon.paintIcon(this, g, 3, y);
        }


        this.setMargin(new Insets(0, 0, 0, 0));


    }

}