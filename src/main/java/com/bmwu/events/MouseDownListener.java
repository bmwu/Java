package com.bmwu.events;

public class MouseDownListener {
	EventSourceProxy _proxy;
	IJMouseDownListener _listener;
	
	public MouseDownListener(int handleEventSource) {
		_proxy = EventSourceProxy.get(handleEventSource);
		
		if (null == _proxy) {
			return;
		}
		
		_listener = new IJMouseDownListener() {
			public void onMouseDown(int hPos, int vPos) {
				System.out.println("In MouseDownListener.onMouseDown");
				System.out.println("	X = " + String.valueOf(hPos));
				System.out.println("	Y = " + String.valueOf(vPos));
			}
		};
		
		_proxy.addMouseDownListener(_listener);
	}
	
	public void release() {
		_proxy.removeMouseDownListener(_listener);
		_listener = null;
		_proxy = null;
	}
	
	public static void main(String[] argv) {
		MouseDownListener listener = new MouseDownListener(0);
	}
}
