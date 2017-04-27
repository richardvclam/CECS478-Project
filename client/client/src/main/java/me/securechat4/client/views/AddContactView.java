package me.securechat4.client.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonModel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.prompt.PromptSupport;
import org.jdesktop.swingx.prompt.PromptSupport.FocusBehavior;
import org.json.simple.JSONObject;

import me.securechat4.client.App;
import me.securechat4.client.Window;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.models.MessageModel;
import me.securechat4.client.models.MessagesModel;
import me.securechat4.client.views.templates.NavigationPane;

public class AddContactView extends View {
	
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
		JTextField usernameField = new JTextField(20);
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
		JTextArea keyField = new JTextArea(300,20);
		keyField.setEditable(true);
		keyField.setLineWrap(true);
		keyField.setFont(font);
		keyField.setMaximumSize(new Dimension(300,300));
		keyField.setBorder(new CompoundBorder(BorderFactory.createLineBorder(new Color(237, 237, 237)),BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		//keyField.addKeyListener((KeyListener) controller);
		addContactArea.add(keyField);		
		
		
		//Submit Button
		
		
		NavigationPane navigationPane = new NavigationPane("Account", addContactArea);
		
		add(navigationPane);
		
		
	}
}