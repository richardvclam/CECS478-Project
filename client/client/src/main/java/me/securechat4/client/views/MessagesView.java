package me.securechat4.client.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import me.securechat4.client.App;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.controllers.MessageController;
import me.securechat4.client.models.MessagesModel;
import me.securechat4.client.views.templates.MasterDetailView;
import me.securechat4.client.views.templates.NavigationPane;

public class MessagesView extends View {
	
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
		
		JButton newMessage = new JButton("New Message");
		newMessage.setBorder(new EmptyBorder(5, 5, 5, 5));
		newMessage.setForeground(Color.BLACK);
		newMessage.setFont(labelFont);
		newMessage.setFocusPainted(false);
		
		MessagesModel model = (MessagesModel) getController().getModel();
	
		list = new JList(model.usernames);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBorder(null);
		list.addListSelectionListener((ListSelectionListener) getController());
		
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setBorder(null);
		
		NavigationPane navigationView = new NavigationPane("Messages", listScroller);
		navigationView.setMinimumSize(new Dimension(300, getHeight()));
		navigationView.getHeader().add(addContact, BorderLayout.WEST);
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
		detailView.add(App.getControllers().get("message").getView(), "message");
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
