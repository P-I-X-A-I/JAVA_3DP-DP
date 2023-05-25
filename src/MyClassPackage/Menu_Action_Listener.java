package MyClassPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu_Action_Listener implements ActionListener{

	mainController mc_obj;
	
	// constructor
	public Menu_Action_Listener(mainController m)
	{
		mc_obj = m;
	}
	
	
	// ActionListener method ******************
	public void actionPerformed(ActionEvent e)
	{
		String actionCommand = e.getActionCommand();
		
		if( actionCommand.equals("menu_open")) 
		{
			System.out.println("menu_open_selected");
		}
		else if(actionCommand.equals("print_stgs"))
		{
			System.out.println("printer setting selected");
		}
		else if(actionCommand.equals("BT_LOAD"))
		{
			System.out.println("Load button");
		}
		else if(actionCommand.equals("BT_DELETE"))
		{
			System.out.println("Delete button");
		}
		else if(actionCommand.equals("plus_45"))
		{
			System.out.println("+45d");
		}
		else if(actionCommand.equals("minus_45"))
		{
			System.out.println("-45d");
		}
		else if(actionCommand.equals("radio_x"))
		{
			System.out.println("radio_x");
		}
		else if(actionCommand.equals("radio_y"))
		{
			System.out.println("radio_y");
		}
		else if(actionCommand.equals("radio_z"))
		{
			System.out.println("radio_z");
		}
		else if(actionCommand.equals("convert_button"))
		{
			System.out.println("convert button");
		}
	}
	// ActionListener method ******************
	
}
