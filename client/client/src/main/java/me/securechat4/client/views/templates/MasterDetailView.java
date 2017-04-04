package me.securechat4.client.views.templates;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JSplitPane;

import me.securechat4.client.Window;
import me.securechat4.client.views.View;

public class MasterDetailView extends JSplitPane {

	public MasterDetailView(View masterView, View detailView) {
		super(JSplitPane.HORIZONTAL_SPLIT, masterView, detailView);
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT));
		
		setBorder(null);
		setDividerSize(1);
		setDividerLocation(Window.WINDOW_WIDTH / 2);
		setContinuousLayout(true);
		setEnabled(false);
	}

}
