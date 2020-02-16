/*
 * #%L
 * SciJava UI components for Java AWT.
 * %%
 * Copyright (C) 2010 - 2020 Board of Regents of the University of
 * Wisconsin-Madison, Broad Institute of MIT and Harvard, and Max Planck
 * Institute of Molecular Cell Biology and Genetics.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package org.scijava.ui.awt;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.scijava.Context;
import org.scijava.InstantiableException;
import org.scijava.app.StatusService;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.PluginInfo;
import org.scijava.tool.Tool;
import org.scijava.tool.ToolService;
import org.scijava.ui.ToolBar;
import org.scijava.ui.UIService;

/**
 * Button bar with selectable tools, similar to ImageJ 1.x.
 * 
 * @author Curtis Rueden
 */
public class AWTToolBar extends Panel implements ToolBar {

	private final Map<String, Button> toolButtons;

	@Parameter
	private StatusService statusService;

	@Parameter
	private ToolService toolService;

	@Parameter
	private UIService uiService;

	@Parameter
	private LogService log;

	public AWTToolBar(final Context context) {
		context.inject(this);

		toolButtons = new HashMap<String, Button>();
		setLayout(new FlowLayout());
		populateToolBar();
	}

	// -- Helper methods --

	private void populateToolBar() {
		Tool lastTool = null;
		for (final Tool tool : toolService.getTools()) {
			try {
				final Button button = createButton(tool);
				toolButtons.put(tool.getInfo().getName(), button);
				add(button);

				// add a separator between tools where applicable
				if (toolService.isSeparatorNeeded(tool, lastTool)) {
					add(new Label(" "));
				}
				lastTool = tool;
			}
			catch (final InstantiableException e) {
				log.warn("Invalid tool: " + tool.getInfo(), e);
			}
		}
	}

	private Button createButton(final Tool tool) throws InstantiableException {
		final PluginInfo<?> info = tool.getInfo();
		final String name = info.getName();
		final String label = info.getLabel();
		final URL iconURL = info.getIconURL();
		final Image iconImage = loadImage(iconURL);
		final boolean enabled = info.isEnabled();
		final boolean visible = info.isVisible();

		final Button button = new Button() {

			@Override
			public void paint(final Graphics g) {
				super.paint(g);
				if (iconImage == null) return;
				final int buttonWidth = getWidth();
				final int buttonHeight = getHeight();
				final int iconWidth = iconImage.getWidth(this);
				final int iconHeight = iconImage.getHeight(this);
				g.drawImage(iconImage, (buttonWidth - iconWidth) / 2,
					(buttonHeight - iconHeight) / 2, this);
			}
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(32, 32);
			}
		};
		if (iconURL == null) {
			if (label != null && !label.isEmpty()) button.setLabel(label);
			else button.setLabel(name);
			log.warn("Invalid icon for tool: " + tool);
		}

		// display description on mouseover
		button.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(final MouseEvent evt) {
				statusService.showStatus(tool.getDescription());
			}

			@Override
			public void mouseExited(final MouseEvent evt) {
				statusService.clearStatus();
			}

			@Override
			public void mousePressed(final MouseEvent evt) {
				if (evt.getButton() == MouseEvent.NOBUTTON) return;
				if (evt.getButton() == MouseEvent.BUTTON1) return;
				tool.configure();
			}
		});

		// activate tool when button pressed
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				toolService.setActiveTool(tool);
			}
		});

		button.setEnabled(enabled);
		button.setVisible(visible);

		return button;
	}

	private Image loadImage(final URL iconURL) {
		return Toolkit.getDefaultToolkit().createImage(iconURL);
	}

}
