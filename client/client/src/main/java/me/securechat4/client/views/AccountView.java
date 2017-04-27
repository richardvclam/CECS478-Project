package me.securechat4.client.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Base64;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import me.securechat4.client.App;
import me.securechat4.client.Window;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.views.templates.NavigationPane;

public class AccountView extends View {
	
	JLabel usernameLabel;
	JTextArea publicKeyTextArea;

	public AccountView(Controller controller) {
		super(controller);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT));
		
		Font labelFont = new Font("Ariel", Font.BOLD, 14);
		
		JPanel profileArea = new JPanel();
		profileArea.setLayout(new BoxLayout(profileArea, BoxLayout.Y_AXIS));
		
		usernameLabel = new JLabel();
		usernameLabel.setFont(labelFont);
		usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		profileArea.add(usernameLabel);
		
		JLabel publicKeyLabel = new JLabel("RSA-2048 Bit Public Key");
		publicKeyLabel.setFont(labelFont);
		publicKeyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		profileArea.add(publicKeyLabel);
		
		Border outsideMargin = new EmptyBorder(5, 10, 10, 10);
		Border border = BorderFactory.createLineBorder(new Color(237, 237, 237), 1);
		Border insideMargin = new EmptyBorder(5, 5, 5, 5);
		
		publicKeyTextArea = new JTextArea("Public key not found!");
		publicKeyTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
		publicKeyTextArea.setEditable(false);
		publicKeyTextArea.setLineWrap(true);
		publicKeyTextArea.setBorder(new CompoundBorder(outsideMargin, new CompoundBorder(border, insideMargin)));
		
		profileArea.add(publicKeyTextArea);
		
		JButton generateKey = new JButton("Generate Key");
		generateKey.setAlignmentX(Component.CENTER_ALIGNMENT);
		generateKey.addActionListener(controller);
		profileArea.add(generateKey);
		
		JButton emailKey = new JButton("Email Key");
		emailKey.setAlignmentX(Component.CENTER_ALIGNMENT);
		emailKey.addActionListener(controller);
		profileArea.add(emailKey);
		
		NavigationPane navigationPane = new NavigationPane("Account", profileArea);
		
		add(navigationPane);
	}
	
	public void init() {
		usernameLabel.setText(App.getUsername());
		
		updatePublicKey();
	}
	
	public void updatePublicKey() {
		if (App.getUserKeys().getPublicKey() != null) {
			publicKeyTextArea.setText(Base64.getEncoder().encodeToString(App.getUserKeys().getPublicKey().getEncoded()));
		}
	}

}
