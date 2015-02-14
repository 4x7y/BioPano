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
import java.util.HashMap;


public class SearchToolBar extends JPanel {

    public JSearchTextField jf;
    private static ContactList panelContactor = null;
    private HashMap<String,ContactCard> contacts;

    public SearchToolBar() {

        //this.setPreferredSize(new Dimension(250, 100));
        jf = new JSearchTextField();
        contacts = new HashMap<String,ContactCard>();
        panelContactor = new ContactList();

        setLayout(new BorderLayout(0, 0));
        add(jf, BorderLayout.NORTH);
        jf.setColumns(0);
        jf.setPreferredSize(new Dimension(250,40));

        add(panelContactor, BorderLayout.CENTER);
        panelContactor.addContact("Operon");
        panelContactor.addContact("Gene");
        panelContactor.addContact("Operon");
        panelContactor.addContact("Gene");
        panelContactor.addContact("Operon");
        panelContactor.addContact("Gene");
        panelContactor.addContact("Operon");
        panelContactor.addContact("Gene");
        panelContactor.addContact("Operon");
        panelContactor.addContact("Gene");
        panelContactor.addContact("Operon");
        panelContactor.addContact("Gene");
        panelContactor.addContact("Operon");
        panelContactor.addContact("Gene");
        panelContactor.addContact("Operon");
        panelContactor.addContact("Gene");
        panelContactor.addContact("Operon");
        panelContactor.addContact("Gene");
        panelContactor.addContact("Operon");
        panelContactor.addContact("Gene");
        panelContactor.addContact("Operon");
        panelContactor.addContact("Gene");



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


class ContactList extends JScrollPane {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JPanel jpb;
    private int contactnum = 0;
    private int dimensionY = 400;
    private String focusName = null;
    private HashMap<String,ContactCard> contacts = new HashMap<String,ContactCard>();

    ContactList() {
        jpb = new JPanel();
        jpb.setForeground(new Color(255, 0, 0));
        this.setViewportView(jpb);
        this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jpb.setPreferredSize(new Dimension(190,dimensionY));
        jpb.setLayout(new GridLayout(16, 1));
        jpb.revalidate();
    }

    public void focus(String name) {
        contacts.get(name).setFocus(true);
        if(focusName != null) {
            System.out.println("Set focus false "+focusName);
            contacts.get(focusName).setFocus(false);
        }
        focusName = name;
    }

    public void addContact(String name) {
        contactnum++;
        if(contactnum>10) {
            dimensionY += 40;
            jpb.setPreferredSize(new Dimension(190,dimensionY));
            jpb.setLayout(new GridLayout(contactnum, 1));
            jpb.revalidate();
        }

        contacts.put(name, new ContactCard(name));
        contacts.get(name).setVisible(true);
        jpb.add(contacts.get(name));
        jpb.revalidate();
        System.out.println("ContactList.addContact(): contactnum: " + contactnum );
    }

    public void removeContact(String name) {
        contactnum--;
        jpb.remove(contacts.get(name));
        jpb.revalidate();
        contacts.remove(name);
        System.out.println("remove " + name);
        if(contactnum<=10) {
            dimensionY = 400;
            jpb.setPreferredSize(new Dimension(190,dimensionY));
            jpb.setLayout(new GridLayout(16, 1));

        }
        jpb.repaint();
        jpb.revalidate();
    }
}

class ContactCard extends JPanel implements MouseListener{
    /**
     *
     */
    private static final long serialVersionUID = 4503092787546871531L;
    private String name;
    private JLabel lblName = null;
    private JLabel label = null;
    private Graphics2D g2 = null;

    private final Color c1 = new Color(175,200,244);
    private final Color c2 = new Color(110,135,180);
    private final Color c3 = new Color(223,223,223);
    private final Color c4 = new Color(210,210,210);
    private Color cUp = null;
    private Color cDown = null;

    ContactCard(String name) {
        this.setLayout(null);
        this.name = name;

        cUp = c3; cDown = c4;

        lblName = new JLabel(name);
        lblName.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
        lblName.setBounds(55, 5, 120, 30);
        this.add(lblName);
        addMouseListener(this);

        label = new JLabel();

        ImageIcon icon = new ImageIcon(getClass().getResource("/cn/edu/ustc/biofilm/BioPano/images/actor.png"));
        icon.setImage(icon.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT));
        label.setIcon(icon);
        label.setBounds(6, 5, 33, 33);

        this.add(label);
    }

    protected void paintComponent(Graphics g) {
        g2 = (Graphics2D)g;
        super.paintComponent(g);
        g2.setPaint(new GradientPaint(getWidth()/2,0, cUp,getWidth()/2,getHeight(), cDown));
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setFocus(boolean b) {
        if(b){
            cUp = c1;
            cDown = c2;
        } else {
            cUp = c3;
            cDown = c4;
        }
        this.repaint();
    }
    @Override
    public void mouseClicked(MouseEvent arg0) {
        System.out.println("mouse Clicked panel "+ name);
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {

    }

    @Override
    public void mouseExited(MouseEvent arg0) {

    }

    @Override
    public void mousePressed(MouseEvent arg0) {

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

    }
}

