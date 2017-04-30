package me.securechat4.client.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.security.PublicKey;

import javax.crypto.SecretKey;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import me.securechat4.client.Window;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.crypto.CryptoUtil;
import me.securechat4.client.views.templates.NavigationPane;

public class UserDetailsView extends View {
	
	private JTextArea publicKeyTextArea;
	private JTextArea aesKeyTextArea;
	private JTextArea hmacKeyTextArea;
	private NavigationPane navigationPane;

	public UserDetailsView(Controller controller) {
		super(controller);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT));
		
		Font labelFont = new Font("Ariel", Font.BOLD, 14);
		
		JPanel profileArea = new JPanel();
		profileArea.setBackground(Color.WHITE);
		profileArea.setMinimumSize(new Dimension(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT));
		profileArea.setLayout(new BoxLayout(profileArea, BoxLayout.Y_AXIS));
		
		// RSA Public Key Label
		JLabel publicKeyLabel = new JLabel("RSA 2048-Bit Public Key");
		publicKeyLabel.setFont(labelFont);
		publicKeyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		profileArea.add(publicKeyLabel);
		
		Border outsideMargin = new EmptyBorder(5, 10, 10, 10);
		Border border = BorderFactory.createLineBorder(new Color(237, 237, 237), 1);
		Border insideMargin = new EmptyBorder(5, 5, 5, 5);
		CompoundBorder compoundBorder = new CompoundBorder(outsideMargin, new CompoundBorder(border, insideMargin));
		
		publicKeyTextArea = new JTextArea("Public key not found!");
		publicKeyTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
		publicKeyTextArea.setEditable(false);
		publicKeyTextArea.setLineWrap(true);
		publicKeyTextArea.setBorder(compoundBorder);
		
		profileArea.add(publicKeyTextArea);
		
		JLabel aesKeyLabel = new JLabel("AES 256-Bit Key");
		aesKeyLabel.setFont(labelFont);
		aesKeyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		profileArea.add(aesKeyLabel);
		
		aesKeyTextArea = new JTextArea("AES key not found!");
		aesKeyTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
		aesKeyTextArea.setEditable(false);
		aesKeyTextArea.setLineWrap(true);
		aesKeyTextArea.setBorder(compoundBorder);
		profileArea.add(aesKeyTextArea);
		
		JLabel hmacKeyLabel = new JLabel("HMAC 256-Bit Key");
		hmacKeyLabel.setFont(labelFont);
		hmacKeyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		profileArea.add(hmacKeyLabel);
		
		hmacKeyTextArea = new JTextArea("HMAC key not found!");
		hmacKeyTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
		hmacKeyTextArea.setEditable(false);
		hmacKeyTextArea.setLineWrap(true);
		hmacKeyTextArea.setBorder(compoundBorder);
		profileArea.add(hmacKeyTextArea);
		
		JButton exchangeKey = new JButton("Exchange Diffie-Hellman Keys");
		exchangeKey.setActionCommand("Exchange");
		exchangeKey.setAlignmentX(Component.CENTER_ALIGNMENT);
		exchangeKey.addActionListener(controller);
		profileArea.add(exchangeKey);
		
		JButton emailKey = new JButton("Email Public Key");
		emailKey.setActionCommand("Email");
		emailKey.setAlignmentX(Component.CENTER_ALIGNMENT);
		emailKey.addActionListener(controller);
		profileArea.add(emailKey);
		
		JScrollPane scrollPane = new JScrollPane(profileArea);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(null);
		scrollPane.setBackground(Color.WHITE);
		
		Font buttonFont = new Font("Ariel", Font.PLAIN, 14);
		
		JButton back = new JButton("Back");
		back.setBorder(new EmptyBorder(5, 5, 5, 5));
		back.setForeground(new Color(0, 122, 255));
		back.setFont(buttonFont);
		back.setBorderPainted(false);
		back.setContentAreaFilled(false);
		back.setFocusPainted(false);
		back.addActionListener(controller);
		
		JButton edit = new JButton("Edit");
		edit.setBorder(new EmptyBorder(5, 5, 5, 5));
		edit.setForeground(new Color(0, 122, 255));
		edit.setFont(buttonFont);
		edit.setBorderPainted(false);
		edit.setContentAreaFilled(false);
		edit.setFocusPainted(false);
		edit.addActionListener(controller);
		
		navigationPane = new NavigationPane("Details", profileArea);
		navigationPane.getHeader().add(back, BorderLayout.WEST);
		navigationPane.getHeader().add(edit, BorderLayout.EAST);
		
		add(navigationPane);
	}
	
	public void update(String username, PublicKey rsaKey, SecretKey aesKey, SecretKey hmacKey) {
		navigationPane.getHeaderLabel().setText(username + "'s Details");
		if (rsaKey != null) {
			publicKeyTextArea.setText(CryptoUtil.encodeKeyToString(rsaKey));
		}
		if (aesKey != null) {
			aesKeyTextArea.setText(CryptoUtil.encodeKeyToString(aesKey));
		}
		if (hmacKey != null) {
			hmacKeyTextArea.setText(CryptoUtil.encodeKeyToString(hmacKey));
		}
	}

}
