package me.securechat4.client.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.prompt.PromptSupport;
import org.jdesktop.swingx.prompt.PromptSupport.FocusBehavior;
import org.json.simple.JSONObject;

import me.securechat4.client.Window;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.models.MessageModel;
import me.securechat4.client.views.templates.NavigationPane;

//
//	View for the individual page (Right side of the screen)
//
public class MessageView extends View {
	
	public static final ImageIcon detailsIconNormal = new ImageIcon("img/details_normal.png");
	public static final ImageIcon detailsIconPressed = new ImageIcon("img/details_pressed.png");
	
	private NavigationPane navigationPane;
	private JPanel rootMessagePanel;
	private JTextArea messageTextArea;
	private HashMap<Integer, JPanel> messagePanels; 

	public MessageView(Controller controller) {
		super(controller);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT));
		
		messagePanels = new HashMap<Integer, JPanel>();
		
		rootMessagePanel = new JPanel();
		rootMessagePanel.setLayout(new CardLayout());

		messageTextArea = new JTextArea();
		messageTextArea.setBackground(new Color(250, 250, 250));
		messageTextArea.setLineWrap(true);
		messageTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		messageTextArea.addKeyListener((KeyListener) controller);
		InputMap inputMap = messageTextArea.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap actionMap = messageTextArea.getActionMap();
		
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
		actionMap.put("enter", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String message = messageTextArea.getText();
				
				if (!message.isEmpty()) {
					((MessageModel) controller.getModel()).sendMessage(message);
				}
			}
		});
		
		JPanel messageArea = new JPanel(new BorderLayout());
		
		messageArea.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(199, 199, 203)));
		messageArea.add(messageTextArea, BorderLayout.CENTER);
		
		PromptSupport.setPrompt("Type a message...", messageTextArea);
		PromptSupport.setFocusBehavior(FocusBehavior.HIDE_PROMPT, messageTextArea);

		JButton account = new JButton(detailsIconNormal);
		account.setActionCommand("Details");
		account.setBorder(new EmptyBorder(5, 5, 5, 5));
		account.setForeground(Color.BLACK);
		account.setFocusPainted(false);
		account.setBorderPainted(false);
		account.setContentAreaFilled(false);
		account.setOpaque(false);
		account.addActionListener(controller);
		account.addMouseListener((MouseListener) controller);
		
		navigationPane = new NavigationPane(" ", rootMessagePanel);
		navigationPane.getHeader().add(account, BorderLayout.EAST);
		navigationPane.add(messageArea, BorderLayout.SOUTH);
		
		add(navigationPane);
	}
	
	public NavigationPane getNavPane() {
		return navigationPane;
	}
	
	public JPanel getRootMessagePanel() {
		return rootMessagePanel;
	}
	
	public HashMap<Integer, JPanel> getMessagePanels() {
		return messagePanels;
	}
	
	public JTextArea getMessageTextArea() {
		return messageTextArea;
	}
	
	public void createMessagePanel(int id) {
		if (!messagePanels.containsKey(id)) {
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setBackground(Color.WHITE);
			
			JScrollPane scrollPane = new JScrollPane(panel);
			scrollPane.setBorder(null);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

			rootMessagePanel.add(scrollPane, Integer.toString(id));
			messagePanels.put(id, panel);
		} 
	}
	
	public void createMessagePanels(HashMap<Integer, LinkedList<JSONObject>> allmessages) {
		for (Entry<Integer, LinkedList<JSONObject>> message : allmessages.entrySet()) {
			JPanel panel;
			if (!messagePanels.containsKey(message.getKey())) {
				panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				panel.setBackground(Color.WHITE);
				
				JScrollPane scrollPane = new JScrollPane(panel);
				scrollPane.setBorder(null);
				scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
				scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

				rootMessagePanel.add(scrollPane, message.getKey().toString());
				messagePanels.put(message.getKey(), panel);
			} else {
				panel = messagePanels.get(message.getKey());
			}

			for (JSONObject json : message.getValue()) {
				String sender = (String) json.get("sender");
				String msg = (String) json.get("data"); 
				
				addMessage(panel, sender, msg);
			}		
		}
		
		if (!messagePanels.containsKey(-1)) {
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setBackground(Color.WHITE);
			
			JLabel noContacts = new JLabel("Start a conversation!");
			noContacts.setAlignmentX(Component.CENTER_ALIGNMENT);
			panel.add(noContacts);
			
			rootMessagePanel.add(panel, "empty");
			messagePanels.put(-1, panel);
			
			CardLayout layout = (CardLayout) rootMessagePanel.getLayout();
			layout.show(rootMessagePanel, "empty");
		}
	}
	
	public void addMessage(JPanel panel, String username, String message) {		
		JLabel label = new JLabel(username + ": " + message);
		
		panel.add(label);
		panel.revalidate();
		panel.repaint();
	}

}
