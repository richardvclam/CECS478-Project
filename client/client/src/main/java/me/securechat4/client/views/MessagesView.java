package me.securechat4.client.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import me.securechat4.client.Window;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.views.templates.NavigationView;

public class MessagesView extends View {

	public MessagesView(Controller controller) {
		super(controller);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT));
		
		
		JPanel nav = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		Font labelFont = new Font("Ariel", Font.BOLD, 14);
		
		JButton addContact = new JButton("Add Contact");
		addContact.setForeground(Color.BLACK);
		addContact.setFont(labelFont);
		//gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(15, 15, 15, 15);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		nav.add(addContact, gbc);
		
		JLabel messagesLabel = new JLabel("Messages");
		messagesLabel.setForeground(Color.BLACK);
		messagesLabel.setFont(labelFont);
		//gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(15, 15, 15, 15);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0;
		nav.add(messagesLabel, gbc);
		
		JButton newMessage = new JButton("New Message");
		newMessage.setForeground(Color.BLACK);
		newMessage.setFont(labelFont);
		//gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(15, 15, 15, 15);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 1;
		nav.add(newMessage, gbc);
		
		JPanel messages = new JPanel();
		
		JLabel noContacts = new JLabel("Add a contact to get started.");
		
		messages.add(noContacts);
		
		NavigationView navigationView = new NavigationView(nav, messages);
		
		add(navigationView);
		
	}

}
