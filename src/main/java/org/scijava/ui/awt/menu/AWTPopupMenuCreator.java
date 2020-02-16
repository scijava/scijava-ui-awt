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

package org.scijava.ui.awt.menu;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.PopupMenu;

import org.scijava.menu.ShadowMenu;

/**
 * Populates an AWT {@link PopupMenu} with menu items from a {@link ShadowMenu}.
 * <p>
 * Unfortunately, the {@link AWTMenuBarCreator}, {@link AWTPopupMenuCreator} and
 * {@link AWTPopupMenuCreator} classes must all exist and replicate some code,
 * because {@link MenuBar}, {@link MenuItem} and {@link PopupMenu} do not share
 * a common interface for operations such as {@link Menu#add}.
 * </p>
 * <p>
 * This class is called {@code AWTPopupMenuCreator} rather than simply
 * {@code PopupMenuCreator} for consistency with other UI implementations such
 * as {@link AWTMenuCreator}.
 * </p>
 * 
 * @author Curtis Rueden
 */
public class AWTPopupMenuCreator extends AbstractAWTMenuCreator<PopupMenu> {

	@Override
	protected void addLeafToTop(final ShadowMenu shadow, final PopupMenu target) {
		addLeafToMenu(shadow, target);
	}

	@Override
	protected Menu
		addNonLeafToTop(final ShadowMenu shadow, final PopupMenu target)
	{
		return addNonLeafToMenu(shadow, target);
	}

	@Override
	protected void addSeparatorToTop(final PopupMenu target) {
		addSeparatorToMenu(target);
	}

}
