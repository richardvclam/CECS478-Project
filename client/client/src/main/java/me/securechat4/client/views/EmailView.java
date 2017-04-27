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
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import me.securechat4.client.Window;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.views.templates.NavigationPane;

public class EmailView extends View {
	
	private JTextField usernameField;

	public EmailView(Controller controller) {
		super(controller);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT));
		
		Font labelFont = new Font("Ariel", Font.BOLD, 12);	
		Font fieldFont = new Font("Ariel", Font.PLAIN, 12);
		
		JPanel emailArea = new JPanel();
		emailArea.setLayout(new BoxLayout(emailArea, BoxLayout.Y_AXIS));
		emailArea.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JLabel usernameLabel = new JLabel("Recipient Username");
		usernameLabel.setFont(labelFont);
		usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		emailArea.add(usernameLabel);
		
		usernameField = new JTextField();
		usernameField.setFont(fieldFont);
		usernameField.setMaximumSize(new Dimension(300, 40));
		usernameField.setBorder(new CompoundBorder(
				BorderFactory.createLineBorder(new Color(237, 237, 237)),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		emailArea.add(usernameField);
		
		JButton emailKey = new JButton("Send");
		emailKey.setAlignmentX(Component.CENTER_ALIGNMENT);
		emailKey.addActionListener(controller);
		emailArea.add(emailKey);
		
		NavigationPane navigationPane = new NavigationPane("Email Public Key", emailArea);
		
		add(navigationPane);
	}
	
	public JTextField getUsernameField() {
		return usernameField;
	}

}
