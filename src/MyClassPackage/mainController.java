package MyClassPackage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;


public class mainController {

	// variables
	
	// printer size ************
	static final int A_X = 180;
	static final int A_Y = 100;
	static final int A_Z = 110;
	
	static final int B_X = 300;
	static final int B_Y = 300;
	static final int B_Z = 450;
	
	static final int C_X = 220;
	static final int C_Y = 160;
	static final int C_Z = 230;
	//**************************
	
	GUI_Manager GUI_obj;
	Menu_Action_Listener MenuListen_obj;
	
	ArrayList<STL_Class> stl_Array;
	ArrayList<String> stl_Names;
	
	int Selected_Printer_ID = 0;
	
	public mainController()
	{
		stl_Array = new ArrayList<STL_Class>();
		stl_Names = new ArrayList<String>();
	}
	
	void set_GUI_obj(GUI_Manager o)
	{
		GUI_obj = o;
	}
	
	void set_MenuAction_obj(Menu_Action_Listener o)
	{
		MenuListen_obj = o;
	}
	
	void mc_print()
	{
		System.out.println("mc test print");
	}
	
	// interfaces to other class
	JFrame get_main_window()
	{
		return GUI_obj.mainWindow;
	}
	
	//*****************************************
	//*****************************************
	// from GUI
	public void add_STL(String filePath) throws IOException
	{
		STL_Class tempSTL = new STL_Class(filePath);
		
		// add to stl array
		stl_Array.add(tempSTL);
		
		// add to name array
		stl_Names.add(new String(tempSTL.file_name));
		
		// update STL list view
		GUI_obj.ListModel.addElement(tempSTL.file_name);
	}
	public void remove_STL()
	{
		// check index of selected list item
		int selectedID = GUI_obj.ListView_STL.getSelectedIndex();
		
		
		// remove items
		if( selectedID != -1)
		{
		stl_Array.remove(selectedID);
		stl_Names.remove(selectedID);
		GUI_obj.ListModel.remove(selectedID);
		}
	}
	
}
