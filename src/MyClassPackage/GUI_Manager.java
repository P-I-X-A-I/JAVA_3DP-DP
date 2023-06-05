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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
//import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.awt.GLJPanel;

import com.jogamp.opengl.util.Animator;

// need implement eventListener
public class GUI_Manager{

	JFrame mainWindow;
	JFrame paramWindow;
	GLJPanel glCanvas;
	GL_Draw GLDraw_obj;
	Timer timer_obj;
	DragAndDropHandler DAD_obj;
	
	// GUI variables
	// labels (main Window)
	JLabel LB_select_printer;
	JLabel LB_load_stl;
	JComboBox<String> combo_obj;
	JLabel LB_rotate;
	// labels (parameter Window)
	JLabel LB_layer_height;
	JLabel LB_perimeter;
	JLabel LB_infill;
	JLabel LB_pattern;
	JLabel LB_temperature;
	
	// button
	JButton BT_load_stl;
	JButton BT_delete_stl;
	JButton BT_plus_45;
	JButton BT_minus_45;
	JButton BT_convert;
	
	// radio button
    JRadioButton xAxis_rd;
    JRadioButton yAxis_rd;
    JRadioButton zAxis_rd;
    ButtonGroup bt_group;
    JRadioButton RAD_infill_grid;
    JRadioButton RAD_infill_gyroid;
    JRadioButton RAD_infill_3dhc;
    ButtonGroup infill_group;
    
    // checkbox
    JCheckBox CHK_support;
    JCheckBox CHK_raft;
    
	// Slider
	JSlider SLI_layer_height;
	JSlider SLI_perimeter;
	JSlider SLI_infill;
	JSlider SLI_temperature;
	
	// stl list view
	DefaultListModel<String> ListModel;
	JList<String> ListView_STL;
	JScrollPane Scroll_STL;
	
	
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

		// create GLobj and set to canvas
		GLDraw_obj = new GL_Draw();
		GLDraw_obj.mc_obj = m;
		glCanvas.addGLEventListener(GLDraw_obj);
		//glCanvas.addGLEventListener(this);
		//glCanvas.setPreferredSize(new Dimension(200, 200));
		glCanvas.setAutoSwapBufferMode(false);
		
		
		// Create main window *********************************
		mainWindow = new JFrame("3DP-DP");
		mainWindow.setSize(1000, 650);
		mainWindow.setBounds(300, 100, 1000, 650);
		mainWindow.setResizable(false);
		// layout
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setLayout(null);
		mainWindow.setVisible(true);
		
		// set DragAndDrop to mainWindow
		DAD_obj = new DragAndDropHandler();
		DAD_obj.mc_obj = m;
		mainWindow.setTransferHandler(DAD_obj);
		
// add glCanvas to window *****************************
		mainWindow.getContentPane().add(glCanvas);
		glCanvas.setBounds(200, 0, 800, 650);
		
		
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
		// the timing call "setVisible" is important to draw window GUI correctly.
		mainWindow.setVisible(true);
				

		
		
		// setup other GUI
		// 1.select printer
		LB_select_printer = new JLabel("1.Select Printer");
		mainWindow.getContentPane().add(LB_select_printer);
		LB_select_printer.setBounds(10, 10, 150, 20);
		
		// printer combobox
		String[] options = {"printer-1", "printer-2", "printer-3"};
		combo_obj = new JComboBox<>(options);
		mainWindow.getContentPane().add(combo_obj);
		combo_obj.setBounds(10, 40, 170, 20);
		combo_obj.setActionCommand("printer_combo");
		combo_obj.addActionListener(MAL);

		// 2. load stl label
		LB_load_stl = new JLabel("2.Load STL files");
		mainWindow.getContentPane().add(LB_load_stl);
		LB_load_stl.setBounds(10, 85, 150, 20);
		
		BT_load_stl = new JButton("Load");
		BT_delete_stl = new JButton("Delete");
		mainWindow.getContentPane().add(BT_load_stl);
		mainWindow.getContentPane().add(BT_delete_stl);
		BT_load_stl.setBounds(10, 110, 80, 20);
		BT_delete_stl.setBounds(100, 110, 80, 20);
		
		BT_load_stl.setActionCommand("BT_LOAD");
		BT_delete_stl.setActionCommand("BT_DELETE");
		BT_load_stl.addActionListener(MAL);
		BT_delete_stl.addActionListener(MAL);
		
		//STL list view ***********************
		ListModel = new DefaultListModel<>();
		
        ListView_STL = new JList<>(ListModel);
        ListView_STL.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        Scroll_STL = new JScrollPane(ListView_STL);
        
        mainWindow.getContentPane().add(Scroll_STL);
        Scroll_STL.setBounds(10, 140, 170, 200);
        /*
        ListModel.removeAllElements();
        ListModel.addElement("testSTL.stl");
        ListModel.addElement("parts.stl");
        */
        
 //..............................................
        LB_rotate = new JLabel("rotate object");
        mainWindow.getContentPane().add(LB_rotate);
        LB_rotate.setBounds(10, 360, 170, 20);
        
        BT_plus_45 = new JButton("+45");
        BT_minus_45 = new JButton("-45");
        BT_plus_45.setActionCommand("plus_45");
        BT_minus_45.setActionCommand("minus_45");
        BT_plus_45.addActionListener(MAL);
        BT_minus_45.addActionListener(MAL);
        
        mainWindow.getContentPane().add(BT_plus_45);
        mainWindow.getContentPane().add(BT_minus_45);
        BT_plus_45.setBounds(10, 390, 60, 20);
        BT_minus_45.setBounds(80, 390, 60, 20);
        
        
        // rotation radio button
        xAxis_rd = new JRadioButton("x");
        yAxis_rd = new JRadioButton("y");
        zAxis_rd = new JRadioButton("z");
        bt_group = new ButtonGroup();
        
        bt_group.add(xAxis_rd);
        bt_group.add(yAxis_rd);
        bt_group.add(zAxis_rd);
        xAxis_rd.setSelected(true);
        
        mainWindow.getContentPane().add(xAxis_rd);
        mainWindow.getContentPane().add(yAxis_rd);
        mainWindow.getContentPane().add(zAxis_rd);
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
        BT_convert = new JButton("convert");
        BT_convert.setActionCommand("convert_button");
        BT_convert.addActionListener(MAL);
        mainWindow.getContentPane().add(BT_convert);
        BT_convert.setBounds(10, 500, 150, 40);
        
//*******************************************      
// Create parameter window*******************
//*******************************************
     	paramWindow = new JFrame("Parameter Settings");
     	paramWindow.setBounds(20, 100, 250, 400);
     	paramWindow.setResizable(false);
     	paramWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
     	paramWindow.setLayout(null);
     	paramWindow.setVisible(true);
     		
     	// setup parameter window GUI
     	// Layer height
     	LB_layer_height = new JLabel("Layer height : 0.3mm");
     	paramWindow.getContentPane().add(LB_layer_height);
     	LB_layer_height.setBounds(10, 10, 240, 20);
     		
     	SLI_layer_height = new JSlider(0, 5, 2);
     	SLI_layer_height.setPaintTicks(true);
     	SLI_layer_height.setSnapToTicks(true);
     	SLI_layer_height.setMajorTickSpacing(1);
     	SLI_layer_height.setMinorTickSpacing(1);
     	SLI_layer_height.addChangeListener( new ChangeListener() {
     	public void stateChanged(ChangeEvent e) {
     		int sli_val = SLI_layer_height.getValue();
     		String mmStr = String.format("Layer height : %1.2fmm", 0.2+sli_val*0.05);
     		LB_layer_height.setText(mmStr);
     		}
     	});
     	paramWindow.getContentPane().add(SLI_layer_height);
     	SLI_layer_height.setBounds(10, 40, 210, 20);
     		
     	// perimeter
     	LB_perimeter = new JLabel("Perimeters : 2");
     	paramWindow.getContentPane().add(LB_perimeter);
     	LB_perimeter.setBounds(10, 60, 200, 20);
     		
     	SLI_perimeter = new JSlider(0, 4, 1);
     	SLI_perimeter.setPaintTicks(true);
     	SLI_perimeter.setSnapToTicks(true);
     	SLI_perimeter.setMajorTickSpacing(1);
     	SLI_perimeter.setMinorTickSpacing(1);
     	SLI_perimeter.addChangeListener(new ChangeListener() {
     		public void stateChanged(ChangeEvent e) {
     			int sli_val = SLI_perimeter.getValue();
     			LB_perimeter.setText("Perimeters : " + (sli_val+1));
     		}
     	});
     	paramWindow.getContentPane().add(SLI_perimeter);
     	SLI_perimeter.setBounds(10, 90, 210, 20);
        
     	
     	// infill density
     	LB_infill = new JLabel("Infill density : 10%");
     	paramWindow.getContentPane().add(LB_infill);
     	LB_infill.setBounds(10, 110, 200, 20);
     	
     	SLI_infill = new JSlider(0, 20, 2);
     	SLI_infill.setPaintTicks(true);
     	SLI_infill.setSnapToTicks(true);
     	SLI_infill.setMajorTickSpacing(1);
     	SLI_infill.setMinorTickSpacing(1);
     	SLI_infill.addChangeListener(new ChangeListener() {
     		public void stateChanged(ChangeEvent e) {
     			int sli_val = SLI_infill.getValue();
     			LB_infill.setText("Infill density : "+(sli_val*5) + "%");
     		}
     	});
     	paramWindow.getContentPane().add(SLI_infill);
     	SLI_infill.setBounds(10, 140, 210, 20);
     	
     	// infill pattern radio button
     	LB_pattern = new JLabel("Infill pattern");
     	paramWindow.getContentPane().add(LB_pattern);
     	LB_pattern.setBounds(10, 175, 200, 20);
     	
     	RAD_infill_grid = new JRadioButton("grid");
     	RAD_infill_gyroid = new JRadioButton("gyroid");
     	RAD_infill_3dhc = new JRadioButton("3D-hc");
     	infill_group = new ButtonGroup();
     	
     	infill_group.add(RAD_infill_grid);
     	infill_group.add(RAD_infill_gyroid);
     	infill_group.add(RAD_infill_3dhc);
     	RAD_infill_3dhc.setSelected(true);
     	
     	paramWindow.getContentPane().add(RAD_infill_grid);
     	paramWindow.getContentPane().add(RAD_infill_gyroid);
     	paramWindow.getContentPane().add(RAD_infill_3dhc);
     	RAD_infill_grid.setActionCommand("infill_grid");
     	RAD_infill_gyroid.setActionCommand("infill_gyroid");
     	RAD_infill_3dhc.setActionCommand("infill_3dhc");
     	RAD_infill_grid.addActionListener(MAL);
     	RAD_infill_gyroid.addActionListener(MAL);
     	RAD_infill_3dhc.addActionListener(MAL);
     	RAD_infill_grid.setBounds(10, 200, 60, 20);
     	RAD_infill_gyroid.setBounds(70, 200, 60, 20);
     	RAD_infill_3dhc.setBounds(140, 200, 80, 20);
     	
// support, raft checkbox
     	CHK_support = new JCheckBox("support");
     	CHK_support.setSelected(false);
     	CHK_support.setActionCommand("check_support");
     	CHK_support.addActionListener(MAL);
     	paramWindow.getContentPane().add(CHK_support);
     	CHK_support.setBounds(10, 240, 200, 20);
     	
     	CHK_raft = new JCheckBox("raft");
     	CHK_raft.setSelected(false);
     	CHK_raft.setActionCommand("check_raft");
     	CHK_raft.addActionListener(MAL);
     	paramWindow.getContentPane().add(CHK_raft);
     	CHK_raft.setBounds(10, 265, 200, 20);
     	
// temperature slider
     	LB_temperature = new JLabel("Temperature : 215");
     	paramWindow.getContentPane().add(LB_temperature);
     	LB_temperature.setBounds(10, 300, 200, 20);
     	
     	SLI_temperature = new JSlider(0, 10, 3);
     	SLI_temperature.setPaintTicks(true);
     	SLI_temperature.setSnapToTicks(true);
     	SLI_temperature.setMajorTickSpacing(2);
     	SLI_temperature.setMinorTickSpacing(1);
     	SLI_temperature.addChangeListener(new ChangeListener() {
     		public void stateChanged(ChangeEvent e) {
     			int sli_val = SLI_temperature.getValue();
     			LB_temperature.setText("Temperature : "+((sli_val*5)+190));
     		}
     	});
     	paramWindow.getContentPane().add(SLI_temperature);
     	SLI_temperature.setBounds(10, 330, 210, 20);
     	
     	
// timer setup ***************************************
		timer_obj = new Timer(1000, arg->glCanvas.repaint());
		timer_obj.start();
		
		//Animator anim = new Animator(glCanvas);
		//anim.start();
		
	}
	
	
	
	// ActionListener method ******************
	//public void actionPerformed(ActionEvent e)
	//{
	//}
	// ActionListener method ******************
	
}
