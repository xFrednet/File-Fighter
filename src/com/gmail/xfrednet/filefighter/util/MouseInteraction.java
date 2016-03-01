package com.gmail.xfrednet.filefighter.util;

/**
 * Created by xFrednet on 22.02.2016.
 */
public interface MouseInteraction {
	
	void mouseEntered(int x, int y);
	void mouseExited(int x, int y);
	
	void mousePressed(int x, int y, int button);
	void mouseReleased(int x, int y, int button);
	
	void mouseWaits(int x, int y, int time);
	
	void mouseMoved(int x, int y);
	
}
