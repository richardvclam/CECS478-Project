package me.securechat4.client.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;

import me.securechat4.client.Window;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.views.templates.NavigationPane;

public class NewMessageView extends View {
	
	public static final ImageIcon addIconNormal = new ImageIcon("img/add_normal.png");
	public static final ImageIcon addIconPressed = new ImageIcon("img/add_pressed.png");
	
	private JList list;

	public NewMessageView(Controller controller) {
		super(controller);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT));
		
		Font buttonFont = new Font("Ariel", Font.PLAIN, 14);	
		
		JPanel contactsArea = new JPanel();
		contactsArea.setLayout(new BoxLayout(contactsArea, BoxLayout.Y_AXIS));
		contactsArea.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JButton addContact = new JButton(addIconNormal);
		addContact.setActionCommand("Add Contact");
		addContact.setBorder(new EmptyBorder(5, 5, 5, 5));
		addContact.setForeground(Color.BLACK);
		addContact.setFocusPainted(false);
		addContact.setBorderPainted(false);
		addContact.setContentAreaFilled(false);
		addContact.setOpaque(false);
		addContact.addActionListener(controller);
		addContact.addMouseListener((MouseListener) controller);
		
		JButton select = new JButton("Select");
		select.setBorder(new EmptyBorder(5, 5, 5, 5));
		select.setForeground(new Color(0, 122, 255));
		select.setFont(buttonFont);
		select.setBorderPainted(false);
		select.setContentAreaFilled(false);
		select.setFocusPainted(false);
		select.addActionListener(controller);
		
		NavigationPane navigationPane = new NavigationPane("New Message", contactsArea);
		navigationPane.getHeader().add(addContact, BorderLayout.WEST);
		navigationPane.getHeader().add(select, BorderLayout.EAST);
		
		add(navigationPane);
	}
	
	public JList getList() {
		return list;
	}

}
