package MyClassPackage;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.TransferHandler;
import java.io.File;
import java.util.List;

public class DragAndDropHandler extends TransferHandler{

	mainController mc_obj;
	
	@Override
	public boolean canImport(TransferSupport support)
	{
		if(support.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public boolean importData(TransferSupport support)
	{
		if( canImport(support) == false )
		{
			return false;
		}
		
		//****************************************
		//****************************************
		
		try {
				Transferable transferable = support.getTransferable();
				List<File> files = (List<File>)transferable.getTransferData(DataFlavor.javaFileListFlavor);
			
				for(File file : files)
				{
					String filePath = file.getAbsolutePath();
					
					// check file extension
					if(filePath.endsWith(".stl"))
					{
						System.out.println("STL file.");
						mc_obj.add_STL(filePath);
					}
					else
					{
						System.out.println("This is not STL file");
					}
				}
				
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
}// class
