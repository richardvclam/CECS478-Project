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
	//private JPanel rootMessagePanel;
	//private JTextArea messageTextArea;
	//private HashMap<Integer, JPanel> messagePanels; 

	public AddContactView(Controller controller) {
		super(controller);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT));

		Font font = new Font("Ariel", Font.BOLD, 14);
		components = new HashMap<String, Component>();
				
		JPanel addContactArea = new JPanel(new BorderLayout());
		addContactArea.setBorder(BorderFactory.createLineBorder(new Color(237, 237, 237), 1));
		
		// Label for Username Input
		JLabel usernameLabel = new JLabel("Receiver's username");
		usernameLabel.setBounds(Window.WINDOW_WIDTH/2, 20, 200, 20);	//(x, y, width, height)
		usernameLabel.setForeground(Color.WHITE);
		usernameLabel.setFont(font);
		components.put("usernameLabel", usernameLabel);
		
		// Username Input Field
		JTextField usernameField = new JTextField(20);
		usernameField.setBounds(Window.WINDOW_WIDTH/2, 45, 200, 40);
		usernameField.setFont(font);
		usernameField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		//usernameField.addKeyListener((KeyListener) controller);
		components.put("usernameField", usernameField);
		
		// Label for Key Input
		JLabel keyLabel = new JLabel("Password");
		keyLabel.setBounds(Window.WINDOW_WIDTH/2, 100, 350, 20);
		keyLabel.setForeground(Color.WHITE);
		keyLabel.setFont(font);
		
		// Key Input Field
		JTextField keyField = new JTextField(20);
		keyField.setBounds(Window.WINDOW_WIDTH/2, 125, 350, 40);
		keyField.setFont(font);
		keyField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		//keyField.addKeyListener((KeyListener) controller);
		components.put("keyField", keyField);
		
		//Submit Button
		
		addComponents();
	}
}
