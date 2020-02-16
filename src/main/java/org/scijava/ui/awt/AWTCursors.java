package org.scijava.ui.awt;
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



import java.awt.Cursor;

import org.scijava.input.MouseCursor;

/**
 * Translates ImageJ {@link MouseCursor}s into AWT {@link Cursor}s.
 * 
 * @author Grant Harris
 * @author Curtis Rueden
 */
public final class AWTCursors {

	private AWTCursors() {
		// prevent instantiation of utility class
	}

	/**
	 * Gets the AWT {@link Cursor} corresponding to the given ImageJ
	 * {@link MouseCursor}.
	 */
	public static Cursor getCursor(final MouseCursor cursorCode) {
		return Cursor.getPredefinedCursor(getCursorCode(cursorCode));
	}

	/**
	 * Gets the AWT cursor code corresponding to the given ImageJ
	 * {@link MouseCursor}.
	 */
	public static int getCursorCode(final MouseCursor cursorCode) {
		switch (cursorCode) {
			case DEFAULT:
				return Cursor.DEFAULT_CURSOR;
			case OFF:
				return Cursor.CUSTOM_CURSOR;
			case HAND:
				return Cursor.HAND_CURSOR;
			case CROSSHAIR:
				return Cursor.CROSSHAIR_CURSOR;
			case MOVE:
				return Cursor.MOVE_CURSOR;
			case TEXT:
				return Cursor.TEXT_CURSOR;
			case WAIT:
				return Cursor.WAIT_CURSOR;
			case N_RESIZE:
				return Cursor.N_RESIZE_CURSOR;
			case S_RESIZE:
				return Cursor.S_RESIZE_CURSOR;
			case W_RESIZE:
				return Cursor.W_RESIZE_CURSOR;
			case E_RESIZE:
				return Cursor.E_RESIZE_CURSOR;
			case NW_RESIZE:
				return Cursor.NW_RESIZE_CURSOR;
			case NE_RESIZE:
				return Cursor.NE_RESIZE_CURSOR;
			case SW_RESIZE:
				return Cursor.SW_RESIZE_CURSOR;
			case SE_RESIZE:
				return Cursor.SE_RESIZE_CURSOR;
			default:
				return Cursor.DEFAULT_CURSOR;
		}
	}

}
