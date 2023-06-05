package MyClassPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

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
			// print setting
			this.printer_setting();

		}
		
		if( actionCommand.equals("printer_combo"))
		{
			/*
			JComboBox src = (JComboBox) e.getSource();
			int ID = src.getSelectedIndex();
			System.out.println("combo : "+ID);
			mc_obj.Selected_Printer_ID = ID;
			*/
		}
		
		if(actionCommand.equals("BT_LOAD"))
		{
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filt = new FileNameExtensionFilter("STL file", "stl");
			fc.setFileFilter(filt);
			
			int result = fc.showOpenDialog(mc_obj.get_main_window());
			
			if(result == fc.APPROVE_OPTION)
			{
				// get file path
				String filePath = fc.getSelectedFile().getAbsolutePath();
				
				// create STL object
				try {
					mc_obj.add_STL(filePath);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else
			{ // do nothing
			}
			
			
		}
		else if(actionCommand.equals("BT_DELETE"))
		{
			mc_obj.remove_STL();
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
		
		
		if(actionCommand.equals("infill_grid"))
		{
			System.out.println("infill grid");
		}
		else if(actionCommand.equals("infill_gyroid"))
		{
			System.out.println("infill gyroid");
		}
		else if(actionCommand.equals("infill_3dhc"))
		{
			System.out.println("infill 3D honeycomb");
		}
		
		if(actionCommand.equals("check_support"))
		{
			System.out.println("checkbox support");
		}
		else if(actionCommand.equals("check_raft"))
		{
			System.out.println("checkbox raft");
		}
		
	}
	// ActionListener method ******************
	
	
	
	void printer_setting()
	{
		//open Dialog ***************************
		JFrame mainWin = mc_obj.get_main_window();
		
		// create dialog window
		JDialog dialogWindow = new JDialog(mainWin, "printer settings", true);
		dialogWindow.setSize(500, 500);
		dialogWindow.setLocationRelativeTo(mainWin);
		dialogWindow.getContentPane().setLayout(null);

		
		// add GUI to dialog
		JTextField xsize_TF = new JTextField();
		JTextField ysize_TF = new JTextField();
		JTextField zsize_TF = new JTextField();
		dialogWindow.getContentPane().add(xsize_TF);
		dialogWindow.getContentPane().add(ysize_TF);
		dialogWindow.getContentPane().add(zsize_TF);
		xsize_TF.setBounds(10, 100, 80, 30);
		ysize_TF.setBounds(110, 100, 80, 30);
		zsize_TF.setBounds(210, 100, 80, 30);
		
		JButton OK_bt = new JButton("Apply");
		dialogWindow.getContentPane().add(OK_bt);
		OK_bt.setBounds(400, 420, 80, 30);
		
		// set method to button
		OK_bt.addActionListener(arg->dialogWindow.dispose());
		
		// show dialog window
		dialogWindow.setModal(true);
		dialogWindow.setVisible(true);
	}
	
}
