package me.securechat4.client.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;

import me.securechat4.client.Window;
import me.securechat4.client.controllers.Controller;

import me.securechat4.client.views.templates.NavigationPane;

public class AddContactView extends View {
	
	private JTextField usernameField;
	private JTextArea keyField;
	private NavigationPane navigationPane;
	private JTextArea publicKeyTextArea;
	//private JPanel rootMessagePanel;
	//private JTextArea messageTextArea;
	//private HashMap<Integer, JPanel> messagePanels; 

	public AddContactView(Controller controller) {
		super(controller);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT));

		Font font = new Font("Ariel", Font.BOLD, 14);
		

		JPanel addContactArea = new JPanel();
		addContactArea.setLayout(new BoxLayout(addContactArea, BoxLayout.Y_AXIS));
		
		
		// Label for Username Input
		JLabel usernameLabel = new JLabel("Receiver's username");
		usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		usernameLabel.setFont(font);
		addContactArea.add(usernameLabel);
		
		// Username Input Field
		usernameField = new JTextField();
		//JTextField usernameField = new JTextField(20);
		usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
		usernameField.setFont(font);
		usernameField.setMaximumSize(new Dimension(300,25));
		//usernameField.addKeyListener((KeyListener) controller);
		addContactArea.add(usernameField);
		
		// Label for Key Input
		
		JLabel keyLabel = new JLabel("Receiver's RSA Key");
		keyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		keyLabel.setFont(font);
		addContactArea.add(keyLabel);
		
		// Key Input Field
		keyField = new JTextArea(300,20);
		keyField.setEditable(true);
		keyField.setLineWrap(true);
		keyField.setFont(font);
		keyField.setMaximumSize(new Dimension(300,15));
		keyField.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(64, 64, 64)),BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		//keyField.addKeyListener((KeyListener) controller);
		addContactArea.add(keyField);		
		
		
		// Add User Button
		JButton addUserButton = new JButton("Add User");
		addUserButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		addUserButton.addActionListener(controller);
		addContactArea.add(addUserButton);
		
		NavigationPane navigationPane = new NavigationPane("Account", addContactArea);
		
		add(navigationPane);
		
	}
	public JTextField getUsernameField() {
		return usernameField;
	}
	public JTextArea getKeyField() {
		return keyField;
	}

}