package me.securechat4.client.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;

import me.securechat4.client.App;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.models.MessagesModel;
import me.securechat4.client.views.templates.MasterDetailView;
import me.securechat4.client.views.templates.NavigationPane;

public class MessagesView extends View {
	
	public static final ImageIcon accountIconNormal = new ImageIcon("img/account_normal.png");
	public static final ImageIcon accountIconPressed = new ImageIcon("img/account_pressed.png");
	public static final ImageIcon newMsgIconNormal = new ImageIcon("img/new_message_button_normal.png");
	public static final ImageIcon newMsgIconPressed = new ImageIcon("img/new_message_button_pressed.png");
	
	private JList list;
	private MasterDetailView mdView;
	private JPanel detailView;

	public MessagesView(Controller controller) {
		super(controller);
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		
		Font labelFont = new Font("Ariel", Font.BOLD, 14);
		
		JButton addContact = new JButton("Add Contact");
		addContact.setBorder(new EmptyBorder(5, 5, 5, 5));
		addContact.setForeground(Color.BLACK);
		addContact.setFont(labelFont);
		addContact.setFocusPainted(false);
		addContact.addActionListener(controller);
		
		JButton account = new JButton(accountIconNormal);
		account.setActionCommand("Account");
		account.setBorder(new EmptyBorder(5, 5, 5, 5));
		account.setForeground(Color.BLACK);
		account.setFont(labelFont);
		account.setFocusPainted(false);
		account.setBorderPainted(false);
		account.setContentAreaFilled(false);
		account.setOpaque(false);
		account.addActionListener(controller);
		account.addMouseListener((MouseListener) controller);
		
		JButton newMessage = new JButton(newMsgIconNormal);
		newMessage.setActionCommand("New Message");
		newMessage.setBorder(new EmptyBorder(5, 5, 5, 5));
		newMessage.setForeground(Color.BLACK);
		newMessage.setFont(labelFont);
		newMessage.setFocusPainted(false);
		newMessage.setBorderPainted(false);
		newMessage.setContentAreaFilled(false);
		newMessage.setOpaque(false);
		newMessage.addActionListener(controller);
		newMessage.addMouseListener((MouseListener) controller);
		
		MessagesModel model = (MessagesModel) getController().getModel();
	
		list = new JList(model.usernames);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBorder(null);
		list.addListSelectionListener((ListSelectionListener) getController());
		
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setBorder(null);
		
		NavigationPane navigationView = new NavigationPane("Messages", listScroller);
		navigationView.setMinimumSize(new Dimension(300, getHeight()));
		//navigationView.getHeader().add(addContact, BorderLayout.WEST, 0);
		navigationView.getHeader().add(account, BorderLayout.WEST);
		navigationView.getHeader().add(newMessage, BorderLayout.EAST);
		
		//left side of the screen is master . right side of the screen is details
		
		detailView = new JPanel();
		detailView.setLayout(new CardLayout());
		detailView.setBackground(Color.WHITE);
		detailView.setBorder(null);

		mdView = new MasterDetailView(navigationView, detailView);
		
		add(mdView);
	}
	
	public void initDetailViews() {
		detailView.add(App.getController("account").getView(), "account");
		detailView.add(App.getController("message").getView(), "message");
		detailView.add(App.getController("addContact").getView(), "addContact");
		
		detailView.add(new NavigationPane(" ", null), "empty");
	}
	
	public void changeDetailView(String panel) {
		CardLayout cardLayout = (CardLayout) detailView.getLayout();
		cardLayout.show(detailView, panel);
	}
	
	public JPanel getDetailView() {
		return detailView;
	}
	
	public JList getList() {
		return list;
	}
	
	public void updateList() {
		list.revalidate();
		list.repaint();
	}
}
