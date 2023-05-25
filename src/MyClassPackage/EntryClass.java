package MyClassPackage;


public class EntryClass{

	static mainController mc_obj;
	static GUI_Manager GUI_obj;
	static Menu_Action_Listener MenuListener_obj;
	
	// it just main function...
	public static void main(String[] args) {
		
		// create objects
		mc_obj = new mainController();
		MenuListener_obj = new Menu_Action_Listener(mc_obj);
		GUI_obj = new GUI_Manager(mc_obj, MenuListener_obj);
		
		// set object to mainController
		mc_obj.set_GUI_obj(GUI_obj);
		mc_obj.set_MenuAction_obj(MenuListener_obj);
		
	}
}
