package MyClassPackage;


public class EntryClass{

	static mainController mc_obj;
	static GUI_Manager GUI_obj;
	static Menu_Action_Listener MenuListener_obj;
	
	// it just main function...
	public static void main(String[] args) {
		
		// create objects
		mc_obj = new mainController();
		MenuListener_obj = new Menu_Action_Listener();
		GUI_obj = new GUI_Manager(mc_obj, MenuListener_obj);
		}
}
