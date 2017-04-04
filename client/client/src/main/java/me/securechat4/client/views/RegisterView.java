package me.securechat4.client.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
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

public class RegisterView extends View {
	
	public RegisterView(Controller controller) {
		super(controller);
		setLayout(null);
		setBackground(View.BLUE);
		
		components = new HashMap<String, Component>();
		
		int offset = 10;
		
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
		components.put("passwordField", passwordField);
		
		JLabel confirmPasswordLabel = new JLabel("Confirm Password");
		confirmPasswordLabel.setBounds((Window.WINDOW_WIDTH - 350)/ 2, offset + 180, 350, 20);
		confirmPasswordLabel.setForeground(Color.WHITE);
		confirmPasswordLabel.setFont(labelFont);
		components.put("confirmPasswordLabel", confirmPasswordLabel);
		
		JPasswordField confirmPasswordField = new JPasswordField(20);
		confirmPasswordField.setBounds((Window.WINDOW_WIDTH - 350)/ 2, offset + 205, 350, 40);
		confirmPasswordField.setFont(fieldFont);
		confirmPasswordField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		components.put("confirmPasswordField", confirmPasswordField);
		
		JButton registerButton = new JButton("Register");
		registerButton.addActionListener(controller);
		registerButton.setBounds((Window.WINDOW_WIDTH - 350)/ 2, offset + 270, 350, 50);
		registerButton.setBorderPainted(false);
		registerButton.setFocusPainted(false);
		registerButton.setBackground(new Color(232, 232, 232));
		registerButton.getModel().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ButtonModel model = (ButtonModel) e.getSource();
				if (model.isRollover()) {
					registerButton.setBackground(new Color(207, 207, 207));
				} else {
					registerButton.setBackground(new Color(216, 216, 216));
				}
			}
		});
		components.put("registerButton", registerButton);
		
		JLabel loginLabel = new JLabel("Already have an account?");
		loginLabel.setBounds((Window.WINDOW_WIDTH - 350)/ 2, offset + 330, 150, 20);
		loginLabel.setForeground(Color.WHITE);
		components.put("loginLabel", loginLabel);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(controller);
		loginButton.setBounds(((Window.WINDOW_WIDTH - 350)/ 2) + 115, offset + 330, 100, 20);
		loginButton.setForeground(Color.WHITE);
		loginButton.setFocusPainted(false);
		loginButton.setMargin(new Insets(0, 0, 0, 0));
		loginButton.setContentAreaFilled(false);
		loginButton.setBorderPainted(false);
		loginButton.setOpaque(false);
		loginButton.getModel().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ButtonModel model = (ButtonModel) e.getSource();
				if (model.isRollover()) {
					Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
					fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
					loginButton.setFont(loginLabel.getFont().deriveFont(fontAttributes));
				} else {
					loginButton.setFont(loginLabel.getFont());
				}
		
			}
		});
		components.put("loginButton", loginButton);
		
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
	
	public void displayInvalidUsername() {
		JLabel usernameLabel = ((JLabel) getComponent("usernameLabel"));
		usernameLabel.setText("Username (Username is already registered.)");
		usernameLabel.setForeground(View.RED);
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
	
	public void displayInvalidPassword() {
		JLabel passwordLabel = ((JLabel) getComponent("passwordLabel"));
		passwordLabel.setText("Password (Password does not match.)");
		passwordLabel.setForeground(View.RED);	
	}
	
	public void displayEmptyConfirmPasswordLabel() {
		JLabel cPasswordLabel = ((JLabel) getComponent("confirmPasswordLabel"));
		cPasswordLabel.setText("Confirm Password (This field is required!)");
		cPasswordLabel.setForeground(View.RED);
	}
	
	public void displayNormalConfirmPasswordLabel() {
		JLabel cPasswordLabel = ((JLabel) getComponent("confirmPasswordLabel"));
		cPasswordLabel.setText("Confirm Password");
		cPasswordLabel.setForeground(Color.WHITE);
	}
	
	public void displayInvalidConfirmPasswordLabel() {
		JLabel cPasswordLabel = ((JLabel) getComponent("confirmPasswordLabel"));
		cPasswordLabel.setText("Confirm Password (Passwords must match!)");
		cPasswordLabel.setForeground(View.RED);
	}

}
