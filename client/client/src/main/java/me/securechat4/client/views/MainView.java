package me.securechat4.client.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JSplitPane;

import me.securechat4.client.Window;
import me.securechat4.client.controllers.Controller;

public class MainView extends View {

	public MainView(Controller controller) {
		super(controller);

		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(Window.WINDOW_WIDTH + 300, Window.WINDOW_HEIGHT));
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new MessagesView(null), new MessageView(null));

		splitPane.setMinimumSize(new Dimension(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT));
		splitPane.setBorder(null);
		splitPane.setDividerSize(1);
		splitPane.setDividerLocation(Window.WINDOW_WIDTH / 2);
		splitPane.setContinuousLayout(true);
		splitPane.setEnabled(false);
		
		add(splitPane);
	}

}
