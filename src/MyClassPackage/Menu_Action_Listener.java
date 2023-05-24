package MyClassPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu_Action_Listener implements ActionListener{

	
	
	
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
	}
	// ActionListener method ******************
	
}
