package MyClassPackage;

import java.awt.Dimension;

// window
import javax.swing.JFrame;


import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.awt.GLJPanel;

// need implement eventListener
public class GUI_Manager implements GLEventListener{

	public JFrame mainWindow;
	
	// constructor
	public GUI_Manager()
	{
		System.out.println("GUI_Manager init");
		
		// setup opengl
		System.out.println("setup opengl");
		GLProfile profile = GLProfile.get(GLProfile.GL4);
		GLCapabilities caps = new GLCapabilities(profile);
		
		// setup canval from capabilities
		System.out.println("create canvas");
		//GLCanvas glCanvas = new GLCanvas(caps); // cause error
		GLJPanel glCanvas = new GLJPanel(caps);
		glCanvas.addGLEventListener(this);
		glCanvas.setPreferredSize(new Dimension(200, 200));
		
		// Create main window
		mainWindow = new JFrame("3DP-DP");
		mainWindow.setSize(800, 600);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setVisible(true);
		
		
		// add glCanvas to window
		System.out.println("add to window");
		mainWindow.getContentPane().add(glCanvas);
		
	}
	
	
	// EventListener method *******************
	public void display(GLAutoDrawable arg0)
	{
		System.out.println("display");
	}
	
	public void dispose(GLAutoDrawable arg0)
	{
		System.out.println("dispose");
	}
	
	public void init(GLAutoDrawable arg0)
	{
		System.out.println("init");
		
		GL4 gl = arg0.getGL().getGL4();
		gl.glClearColor((float)1.0, (float)1.0, (float)0.0, (float)1.0);
	
	}
	
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4)
	{
		System.out.printf("reshape %d, %d, %d, %d", arg1, arg2, arg3, arg4);
	}
	// EventListener method *******************

}
