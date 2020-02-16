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

import java.awt.Color;

import org.scijava.util.ColorRGB;
import org.scijava.util.ColorRGBA;

/**
 * Translates ImageJ {@link ColorRGB}s into AWT {@link Color}s.
 * 
 * @author Curtis Rueden
 * @author Lee Kamentsky
 */
public final class AWTColors {

	private AWTColors() {
		// prevent instantiation of utility class
	}

	/**
	 * Gets the AWT {@link Color} corresponding to the given ImageJ
	 * {@link ColorRGB}.
	 */
	public static Color getColor(final ColorRGB c) {
		if (c == null) return null;
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
	}

	/**
	 * Get the AWT {@link Color} corresponding to the given ImageJ
	 * {@link ColorRGB} plus explicit alpha component (0-255).
	 * 
	 * @param c RGB color
	 * @param alpha alpha value (0-255)
	 * @return AWT color
	 */
	public static Color getColor(final ColorRGB c, final int alpha) {
		if (c == null) return null;
		if (alpha < 0 || alpha > 255) {
			throw new IllegalArgumentException("Alpha value of " + alpha +
				" is out of range (0-255)");
		}
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);
	}

	/**
	 * Gets the ImageJ {@link ColorRGB} corresponding to the given AWT
	 * {@link Color}.
	 * 
	 * @param color {@link Color} to be translated
	 * @return the corresponding ImageJ RGB color
	 */
	public static ColorRGB getColorRGB(final Color color) {
		if (color == null) return null;
		return new ColorRGB(color.getRed(), color.getGreen(), color.getBlue());
	}

	/**
	 * Get the ImageJ {@link ColorRGBA} corresponding to the given AWT
	 * {@link Color}, including alpha component.
	 * 
	 * @param color {@link Color} to be translated
	 * @return the corresponding ImageJ RGBA color
	 */
	public static ColorRGBA getColorRGBA(final Color color) {
		if (color == null) return null;
		return new ColorRGBA(color.getRed(), color.getGreen(), color.getBlue(),
			color.getAlpha());
	}

}
