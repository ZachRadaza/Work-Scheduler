package resources;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.List;

import javax.swing.TransferHandler;

import display.NewPage.New;
import display.NewPage.Template;

public class FileDropHandler extends TransferHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int i = 0;
	
	public FileDropHandler(int i){
		this.i = i;
	}
	
    @Override
    public boolean canImport(TransferSupport support) {
        // Accept only file drops
        return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) return false;

        try {
            Transferable t = support.getTransferable();
            @SuppressWarnings("unchecked")
			List<File> droppedFiles = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);

            StringBuilder sb = new StringBuilder("");
            for (File file : droppedFiles) {
                sb.append(file.getAbsolutePath()).append("\n");
            }
            
            if(i == 0) ((Template) New.getAllPanel(3)).setFilePathStation(sb.toString());
            else ((Template) New.getAllPanel(3)).setFilePathEmp(sb.toString());
            
            ((Template) New.getAllPanel(3)).setPanelFileAdded(this.i);
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}