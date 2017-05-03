package me.securechat4.client.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;

import me.securechat4.client.App;
import me.securechat4.client.User;
import me.securechat4.client.Window;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.models.MessagesModel;
import me.securechat4.client.models.NewMessageModel;
import me.securechat4.client.views.templates.NavigationPane;

public class NewMessageView extends View {
	
	public static final ImageIcon addIconNormal = new ImageIcon("img/add_normal.png");
	public static final ImageIcon addIconPressed = new ImageIcon("img/add_pressed.png");
	
	private JList list;
	private HashMap<Integer, User> user;
	private JScrollPane listScroller;

	/*
	 * 	View page for the New Message portion.
	 */
	
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

		
		NewMessageModel model = (NewMessageModel) getController().getModel();
		
		list = new JList(model.contactList);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBorder(null);
		list.addListSelectionListener((ListSelectionListener) getController());
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setBorder(null);
	
		
		NavigationPane navigationPane = new NavigationPane("New Message", listScroller);
		navigationPane.setMinimumSize(new Dimension(300, getHeight()));
		navigationPane.getHeader().add(addContact, BorderLayout.WEST);
		navigationPane.getHeader().add(select, BorderLayout.EAST);
		
		add(navigationPane);
	}
	
	public JList getList() {
		return list;
	}

	public void updateList() {
		list.revalidate();
		list.repaint();
	}

	
}
