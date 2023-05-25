package MyClassPackage;

public class mainController {

	// variables
	GUI_Manager GUI_obj;
	Menu_Action_Listener MenuListen_obj;
	
	public mainController()
	{
		
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
}
