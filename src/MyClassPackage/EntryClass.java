package MyClassPackage;


public class EntryClass{

	static mainController mc_obj;
	static GUI_Manager GUI_obj;
	
	
	// it just main function...
	public static void main(String[] args) {
		// create objects
		mc_obj = new mainController();
		GUI_obj = new GUI_Manager(mc_obj);
		
		/*
		System.out.println("3DP-DP mainController init");
		
		// setup opengl
		GLProfile profile = GLProfile.get(GLProfile.GL4);
		GLCapabilities caps = new GLCapabilities(profile);
		
		// setup canvas
		GLJPanel glCanvas = new GLJPanel(caps);
		EntryClass ec = new EntryClass();
		glCanvas.addGLEventListener(ec);
		
		// create mainwindow
		JFrame mainWindow = new JFrame("3DP-DP");
		mainWindow.setSize(800, 600);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// create menu
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("file");
		JMenuItem openItem = new JMenuItem("Open");
		
		fileMenu.add(openItem);
		menuBar.add(fileMenu);
		mainWindow.setJMenuBar(menuBar);
		
		// get content pane & add canvas
		Container cp = mainWindow.getContentPane();
		cp.add(glCanvas);
		
		mainWindow.setVisible(true);
		*/
		}
}
