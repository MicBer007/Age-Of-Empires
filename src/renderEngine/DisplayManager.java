package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	private static int FpsCap = 120;
	private static int Width = 1240;
	private static int Height = 720;
	
	public static int getFPS_CAP() {
		return FpsCap;
	}
	
	public static void setFPS_CAP(int fPS_CAP) {
		FpsCap = fPS_CAP;
	}
	
	public static int getWidth() {
		return Width;
	}
	
	public static void setWidth(int width) {
		Width = width;
	}
	
	public static int getHeight() {
		return Height;
	}
	
	public static void setHeight(int height) {
		Height = height;
	}
	
	public static void createDisplay (String title) {
		
		ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);
		
		try {
			
			Display.setDisplayMode(new DisplayMode(Width,Height));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle(title);
			
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, Width, Height);
		
	}
	
	public static void updateDisplay() {
		
		Display.sync(FpsCap);
		Display.update();
		
	}
	
	public static void closeDisplay() {
		
		Display.destroy();
		
	}
	
}
