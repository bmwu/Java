package com.bmwu.events;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

public class EventSourceProxy {
	static {
		System.loadLibrary("listener");
	}

	private static Hashtable _table = new Hashtable();
	
	public static EventSourceProxy get(int handleEventSource) {
		if (0 == handleEventSource) {
			return null;
		}

		Object obj = _table.get(new Integer(handleEventSource));
	
		if (obj instanceof EventSourceProxy) {
			return (EventSourceProxy)obj;
		}
		
		EventSourceProxy proxy = new EventSourceProxy(handleEventSource);
		_table.put(new Integer(handleEventSource), proxy);

		return proxy;
	}
	
	private int _handleEventSource = 0;
	private int _handleNativeListener = 0;
	private HashSet _set = new HashSet();

	private EventSourceProxy(int handleEventSource) {
		_handleEventSource = handleEventSource;
	}

	public void addMouseDownListener(IJMouseDownListener listener) {
		if (null == listener) {
			return;
		}
		
		_set.add(listener);
		
		if (1 == _set.size()) {
			_handleNativeListener = registerListener(_handleEventSource, "fireMouseDownEvent", this.getClass());
		}
	}

	public void removeMouseDownListener(IJMouseDownListener listener) {
		if (null == listener) {
			return;
		}

		_set.remove(listener);
		
		if (0 == _set.size()) {
			unregisterListener(_handleEventSource, _handleNativeListener);
		}
	}

	private void fireMouseDownEvent(int hPos, int vPos) {
		for (Iterator iter = _set.iterator(); iter.hasNext();) {
			IJMouseDownListener listener = (IJMouseDownListener) iter.next();
			listener.onMouseDown(hPos, vPos);
		}
	}
	
	private native int registerListener(int handleEventSource, String methodName, Class jclass);
	private native int unregisterListener(int handleEventSource, int handleNativeListener);
}
