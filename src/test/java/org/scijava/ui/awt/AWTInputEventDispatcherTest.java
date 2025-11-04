/*
 * #%L
 * SciJava Common shared library for SciJava software.
 * %%
 * Copyright (C) 2009 - 2025 SciJava developers.
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

import org.junit.Test;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.*;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.scijava.display.Display;
import org.scijava.display.event.input.KyPressedEvent;
import org.scijava.event.EventService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests {@link AWTInputEventDispatcher}.
 * 
 * @author Curtis Rueden
 */
public class AWTInputEventDispatcherTest {

	@Test
	public void testKeyPressedEvent() {
		AWTInputEventDispatcher dispatcher = dispatcher();

		// Dispatch an AWT KeyEvent.
		KeyEvent awtEvent = new KeyEvent(new Panel(),
			KeyEvent.KEY_PRESSED, 0,
			InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK,
			KeyEvent.VK_0, '0');
		dispatcher.keyPressed(awtEvent);

		// Verify that KeyEvent was published as a KyEvent.
		KyPressedEvent sjEvent = extractEvent(KyPressedEvent.class);
		System.out.println(sjEvent.getAccelerator());
		assertEquals("control shift NUM0", sjEvent.getAccelerator().toString());
	}

	private AWTInputEventDispatcher dispatcher() {
		return new AWTInputEventDispatcher(mock(Display.class), mock(EventService.class));
	}

	private static class MethodCall {
		Object obj;
		Method method;
		Object[] args;
		MethodCall(Object obj, Method method, Object[] args) {
			this.obj = obj;
			this.method = method;
			this.args = args;
		}
	}

	private final List<MethodCall> calls = new ArrayList<>();

	@SuppressWarnings("unchecked")
	private <T> T mock(Class<T> iface) {
		return (T) Proxy.newProxyInstance(
			getClass().getClassLoader(),
			new Class<?>[] {iface},
			(obj, method, args) -> {
				calls.add(new MethodCall(obj, method, args));
				return null;
			}
		);
	}

	private <T> T extractEvent(Class<T> eventClass) {
		assertFalse(calls.isEmpty());
		MethodCall call = calls.get(0);
		assertNotNull(call);
		assertNotNull(call.args);
        assertEquals(1, call.args.length);
		Object arg = call.args[0];
		assertTrue(eventClass.isInstance(arg));
		@SuppressWarnings("unchecked")
		T typedArg = (T) arg;
		calls.clear();
		return typedArg;
	}

	public static void main(String... args) {
		EventQueue.invokeLater(() -> {
			Frame f = new Frame();
			Panel p = new Panel();
			f.add(p);

			f.addWindowListener(new WindowAdapter() {
				@Override public void windowClosing(WindowEvent e) { f.dispose(); }
			});

			KeyListener keys = new KeyListener() {
				@Override public void keyTyped(KeyEvent e) { System.out.println(e); }
				@Override public void keyPressed(KeyEvent e) { System.out.println(e); }
				@Override public void keyReleased(KeyEvent e) { System.out.println(e); }
			};
			MouseListener mouse = new MouseListener() {
				@Override public void mouseClicked(MouseEvent e) { System.out.println(e); }
				@Override public void mousePressed(MouseEvent e) { System.out.println(e); }
				@Override public void mouseReleased(MouseEvent e) { System.out.println(e); }
				@Override public void mouseEntered(MouseEvent e) { System.out.println(e); }
				@Override public void mouseExited(MouseEvent e) { System.out.println(e); }
			};
			MouseMotionListener mouseMotion = new MouseMotionListener() {
				@Override public void mouseDragged(MouseEvent e) { System.out.println(e); }
				@Override public void mouseMoved(MouseEvent e) { System.out.println(e); }
			};
			MouseWheelListener mouseWheel = System.out::println;

			f.addKeyListener(keys);
			f.addMouseListener(mouse);
			f.addMouseMotionListener(mouseMotion);
			f.addMouseWheelListener(mouseWheel);

			p.addKeyListener(keys);
			p.addMouseListener(mouse);
			p.addMouseMotionListener(mouseMotion);
			p.addMouseWheelListener(mouseWheel);

			f.setBounds(200, 200, 400, 400);
			f.setVisible(true);
		});
	}
}
