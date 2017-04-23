package me.securechat4.client.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.KeyListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import me.securechat4.client.Window;
import me.securechat4.client.controllers.Controller;

public class LoginView extends View {
	
	public LoginView(Controller controller) {
		super(controller);
		setLayout(null);
		setBackground(View.BLUE);
		
		components = new HashMap<String, Component>();
		
		int offset = 50;
		
		Font labelFont = new Font("Arial Bold", Font.PLAIN, 13);
		Font fieldFont = new Font("Ariel", Font.PLAIN, 14);

		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setBounds((Window.WINDOW_WIDTH - 350)/ 2, offset + 20, 350, 20);
		usernameLabel.setForeground(Color.WHITE);
		usernameLabel.setFont(labelFont);
		components.put("usernameLabel", usernameLabel);
		
		JTextField usernameField = new JTextField(20);
		usernameField.setBounds((Window.WINDOW_WIDTH - 350)/ 2, offset + 45, 350, 40);
		usernameField.setFont(fieldFont);
		usernameField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		usernameField.addKeyListener((KeyListener) controller);
		components.put("usernameField", usernameField);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds((Window.WINDOW_WIDTH - 350)/ 2, offset + 100, 350, 20);
		passwordLabel.setForeground(Color.WHITE);
		passwordLabel.setFont(labelFont);
		components.put("passwordLabel", passwordLabel);
		
		JPasswordField passwordField = new JPasswordField(20);
		passwordField.setBounds((Window.WINDOW_WIDTH - 350)/ 2, offset + 125, 350, 40);
		passwordField.setFont(fieldFont);
		passwordField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		passwordField.addKeyListener((KeyListener) controller);
		components.put("passwordField", passwordField);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(controller);
		loginButton.setBounds((Window.WINDOW_WIDTH - 350)/ 2, offset + 190, 350, 60);
		loginButton.setBorderPainted(false);
		loginButton.setFocusPainted(false);
		loginButton.setBackground(new Color(232, 232, 232));
		loginButton.getModel().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ButtonModel model = (ButtonModel) e.getSource();
				if (model.isRollover()) {
					loginButton.setBackground(new Color(207, 207, 207));
				} else {
					loginButton.setBackground(new Color(216, 216, 216));
				}
			}
		});
		components.put("loginButton", loginButton);
		
		JLabel registerLabel = new JLabel("Need an account?");
		registerLabel.setBounds((Window.WINDOW_WIDTH - 350)/ 2, offset + 260, 150, 20);
		registerLabel.setForeground(Color.WHITE);
		components.put("registerLabel", registerLabel);
		
		JButton registerButton = new JButton("Register");
		registerButton.addActionListener(controller);
		registerButton.setBounds(((Window.WINDOW_WIDTH - 350)/ 2) + 80, offset + 260, 100, 20);
		registerButton.setForeground(Color.WHITE);
		registerButton.setFocusPainted(false);
		registerButton.setMargin(new Insets(0, 0, 0, 0));
		registerButton.setContentAreaFilled(false);
		registerButton.setBorderPainted(false);
		registerButton.setOpaque(false);
		registerButton.getModel().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ButtonModel model = (ButtonModel) e.getSource();
				if (model.isRollover()) {
					Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
					fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
					registerButton.setFont(registerLabel.getFont().deriveFont(fontAttributes));
				} else {
					registerButton.setFont(registerLabel.getFont());
				}
		
			}
		});
		components.put("registerButton", registerButton);
		
		addComponents();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	public void displayEmptyUsernameLabel() {
		JLabel usernameLabel = ((JLabel) getComponent("usernameLabel"));
		usernameLabel.setText("Username (This field is required!)");
		usernameLabel.setForeground(View.RED);
	}
	
	public void displayNormalUsernameLabel() {
		JLabel usernameLabel = ((JLabel) getComponent("usernameLabel"));
		usernameLabel.setText("Username");
		usernameLabel.setForeground(Color.WHITE);
	}
	
	public void displayEmptyPasswordLabel() {
		JLabel passwordLabel = ((JLabel) getComponent("passwordLabel"));
		passwordLabel.setText("Password (This field is required!)");
		passwordLabel.setForeground(View.RED);	
	}

	public void displayNormalPasswordLabel() {
		JLabel passwordLabel = ((JLabel) getComponent("passwordLabel"));
		passwordLabel.setText("Password");
		passwordLabel.setForeground(Color.WHITE);
	}
	
	public void displayInvalidUsername() {
		JLabel usernameLabel = ((JLabel) getComponent("usernameLabel"));
		usernameLabel.setText("Username (Username does not exist.)");
		usernameLabel.setForeground(View.RED);
	}
	
	public void displayInvalidPassword() {
		JLabel passwordLabel = ((JLabel) getComponent("passwordLabel"));
		passwordLabel.setText("Password (Password does not match.)");
		passwordLabel.setForeground(View.RED);	
	}
	
}
