package MyClassPackage;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

// window
import javax.swing.*;
//import javax.swing.JFrame;
//import javax.swing.JMenu;
//import javax.swing.JMenuBar;
//import javax.swing.JMenuItem;
//import javax.swing.Timer;
//import javax.swing.JComboBox;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
//import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.awt.GLJPanel;


// need implement eventListener
public class GUI_Manager implements GLEventListener, ActionListener{

	public JFrame mainWindow;
	public GLJPanel glCanvas;
	public Timer timer_obj;
	
	// GUI variables
	public JComboBox<String> combo_obj;
	
	
	// constructor
	public GUI_Manager(mainController m, Menu_Action_Listener MAL)
	{
		System.out.println("GUI_Manager init");
			
		
		// setup openGL ***********************************
		System.out.println("*** setup opengl (in GUI_Manager)***");
		GLProfile profile = GLProfile.get(GLProfile.GL4);
		GLCapabilities caps = new GLCapabilities(profile);
		
		// setup canvas from capabilities
		System.out.println("create canvas");
		glCanvas = new GLJPanel(caps);
		glCanvas.addGLEventListener(this);
		glCanvas.setPreferredSize(new Dimension(200, 200));
		glCanvas.setAutoSwapBufferMode(false);
		
		
		// Create main window *********************************
		mainWindow = new JFrame("3DP-DP");
		//mainWindow.setSize(800, 600);
		mainWindow.setBounds(200, 200, 1000, 650);
		mainWindow.setResizable(false);
		// layout
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setVisible(true);
		//****************************************************
		
		// create menu ****************************
		JMenuBar menuBar = new JMenuBar();
//.................................
		JMenu fileMenu = new JMenu("file");
		JMenuItem openItem = new JMenuItem("Open");
		fileMenu.add(openItem);
//................................
		JMenu settingMenu = new JMenu("Setting");
		JMenuItem settingItem = new JMenuItem("Printer Settings");
		settingMenu.add(settingItem);
//................................
		
		// set callback
		openItem.setActionCommand("menu_open");
		openItem.addActionListener(MAL);
		settingItem.setActionCommand("print_stgs");
		settingItem.addActionListener(MAL);
		
		// add to menubar
		menuBar.add(fileMenu);
		menuBar.add(settingMenu);
		
		
		mainWindow.setJMenuBar(menuBar);
		//******************************************
		
		// get content pane *****************************
		Container mainWin_Pane = mainWindow.getContentPane();
		//contentPane.setLayout(new GridLayout(2, 2));
		// set layoutmanager to null
		mainWin_Pane.setLayout(null);
		
		// add glCanvas to window
		mainWin_Pane.add(glCanvas);
		glCanvas.setBounds(200, 0, 800, 650);
		
		
		
		// setup other GUI
		JLabel selectPrinter_Label = new JLabel("1.Select Printer");
		mainWin_Pane.add(selectPrinter_Label);
		selectPrinter_Label.setBounds(10, 10, 150, 20);
		
		String[] options = {"printer-1", "printer-2", "printer-3"};
		combo_obj = new JComboBox<>(options);
		mainWin_Pane.add(combo_obj);
		combo_obj.setBounds(10, 40, 170, 20);
//..........................
		JLabel loadSTL_Label = new JLabel("2.Load STL files");
		mainWin_Pane.add(loadSTL_Label);
		loadSTL_Label.setBounds(10, 85, 150, 20);
		
		JButton load_bt = new JButton("Load");
		JButton delete_bt = new JButton("Delete");
		mainWin_Pane.add(load_bt);
		mainWin_Pane.add(delete_bt);
		load_bt.setBounds(10, 110, 80, 20);
		delete_bt.setBounds(100, 110, 80, 20);
		
		load_bt.setActionCommand("BT_LOAD");
		delete_bt.setActionCommand("BT_DELETE");
		load_bt.addActionListener(MAL);
		delete_bt.addActionListener(MAL);
		
	//.................................
		DefaultListModel<String>listModel = new DefaultListModel<>();
		listModel.addElement("data_1.stl");
		listModel.addElement("data2.stl");
		listModel.addElement("data_3.stl");
		
        JList<String> listView = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(listView);
        
        mainWin_Pane.add(scrollPane);
        scrollPane.setBounds(10, 140, 170, 200);
        
 //..............................................
        JLabel rotate_label = new JLabel("rotate object");
        mainWin_Pane.add(rotate_label);
        rotate_label.setBounds(10, 360, 170, 20);
        
        JButton p45_bt = new JButton("+45");
        JButton m45_bt = new JButton("-45");
        p45_bt.setActionCommand("plus_45");
        m45_bt.setActionCommand("minus_45");
        p45_bt.addActionListener(MAL);
        m45_bt.addActionListener(MAL);
        
        mainWin_Pane.add(p45_bt);
        mainWin_Pane.add(m45_bt);
        p45_bt.setBounds(10, 390, 60, 20);
        m45_bt.setBounds(80, 390, 60, 20);
        
        JRadioButton xAxis_rd = new JRadioButton("x");
        JRadioButton yAxis_rd = new JRadioButton("y");
        JRadioButton zAxis_rd = new JRadioButton("z");
        ButtonGroup bt_group = new ButtonGroup();
        
        bt_group.add(xAxis_rd);
        bt_group.add(yAxis_rd);
        bt_group.add(zAxis_rd);
        xAxis_rd.setSelected(true);
        
        mainWin_Pane.add(xAxis_rd);
        mainWin_Pane.add(yAxis_rd);
        mainWin_Pane.add(zAxis_rd);
        xAxis_rd.setActionCommand("radio_x");
        yAxis_rd.setActionCommand("radio_y");
        zAxis_rd.setActionCommand("radio_z");
        xAxis_rd.addActionListener(MAL);
        yAxis_rd.addActionListener(MAL);
        zAxis_rd.addActionListener(MAL);
        xAxis_rd.setBounds(10, 420, 50, 20);
        yAxis_rd.setBounds(60, 420, 50, 20);
        zAxis_rd.setBounds(120, 420, 50, 20);
        
        
        // convert file button
        JButton convert_bt = new JButton("convert");
        convert_bt.setActionCommand("convert_button");
        convert_bt.addActionListener(MAL);
        mainWin_Pane.add(convert_bt);
        convert_bt.setBounds(10, 500, 150, 40);
        
        // timer setup ***************************************
		timer_obj = new Timer(1000, e->glCanvas.repaint());
		timer_obj.start();
	}
	
	
	// GLEventListener method *******************
	public void display(GLAutoDrawable arg0)
	{
		System.out.println("display");
		GL4 gl = arg0.getGL().getGL4();
		
		// get random float
		float[] col = new float[4];
		Random randVal = new Random();
		col[0] = randVal.nextFloat();
		col[1] = randVal.nextFloat();
		col[2] = randVal.nextFloat();
		col[3] = 1.0f;
		
		
		gl.glClearColor(col[0], col[1], col[2], col[3]);
		gl.glClear(GL4.GL_COLOR_BUFFER_BIT);
		gl.glFinish();
		arg0.swapBuffers();
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
		gl.glClear(GL4.GL_COLOR_BUFFER_BIT);
		gl.glFinish();
	}
	
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4)
	{
		//System.out.printf("reshape %d, %d, %d, %d", arg1, arg2, arg3, arg4);
	}
	// EventListener method *******************
	
	
	
	// ActionListener method ******************
	public void actionPerformed(ActionEvent e)
	{
	}
	// ActionListener method ******************
	
}
