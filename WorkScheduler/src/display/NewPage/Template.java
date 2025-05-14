package display.NewPage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import display.MainFrame;
import main.FileRead;
import resources.Button;
import resources.FileDropHandler;

public class Template extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel panelCenter;
	private JPanel[] fileSelectWrapper;
	private String filePathEmp;
	private String filePathStation;
	private LinkedList<Button> buttonPanelTemplate;
	
	private int days;

	public Template(int days){
		panelCenter = new JPanel();
		fileSelectWrapper = new JPanel[2];
		filePathEmp = "";
		filePathStation = "";
		buttonPanelTemplate = new LinkedList<>();
		this.days = days;
		
		setTemplate();
	}
	
	private void setTemplate(){
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		
		this.add(New.setHeader("Add Templates"), BorderLayout.NORTH);
		this.add(New.setFooter(buttonPanelTemplate), BorderLayout.SOUTH);
		
		panelCenter.setOpaque(false);
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		
		//adds instructions
		JPanel insHolder = new JPanel();
		insHolder.setBackground(MainFrame.darkMidBgColor);
		insHolder.setLayout(new BoxLayout(insHolder, BoxLayout.X_AXIS));
		insHolder.setPreferredSize(new Dimension(580, 30));
				
		JLabel ins = new JLabel("Add Stations and Employee Availability through the use of the");
		ins.setForeground(MainFrame.brightBgColor);
		ins.setOpaque(false);
		ins.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 15));
		ins.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0)); //aligns it with button
				
		buttonPanelTemplate.add(new Button("Downloadable Templates ", 15, 2, true));
		buttonPanelTemplate.get(2).setMaximumSize(new Dimension(185, 40));
				
		insHolder.add(ins);
		insHolder.add(buttonPanelTemplate.get(2));
		panelCenter.add(insHolder);
				
		//adds options to add files
		panelFileSection(0);
		panelFileSection(1);
				
		//for preferred size
		Dimension dimension = panelCenter.getPreferredSize();
		dimension.width = 750;
		panelCenter.setPreferredSize(dimension);
		this.add(panelCenter, BorderLayout.CENTER);
				
		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}
	
	//adds separate sections for employee and station template
	private void panelFileSection(int i){ //i==0 is station, i == 1 is employee		
		fileSelectWrapper[i] = new JPanel();
		panelFileSectionWrapper(i);
		
		JPanel fileSelectBorder = new JPanel();
		fileSelectBorder.setOpaque(false);
		fileSelectBorder.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		fileSelectBorder.add(fileSelectWrapper[i]);
		panelCenter.add(fileSelectBorder);
	}
	
	//for the options of the wrapper with the choose files, creates it
	private void panelFileSectionWrapper(int i){
		//for borders
		fileSelectWrapper[i].removeAll(); //for when the x is pressed on the added file
		String[] title = {"Template for Station", "Template for Employees"};
		fileSelectWrapper[i].setOpaque(false);
		fileSelectWrapper[i].setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(MainFrame.darkBgColor, 2), title[i], 2, 0, new Font("Microsoft JhengHei", Font.PLAIN, 20), MainFrame.brightBgColor));
			
		JPanel fileSelectHolder = new JPanel();
		fileSelectHolder.setOpaque(false);
		fileSelectHolder.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		fileSelectHolder.setLayout(new BoxLayout(fileSelectHolder, BoxLayout.Y_AXIS));
	
		buttonPanelTemplate.add(new Button("Choose Files", 20, 3 + i));
			
		JPanel or = new JPanel();
		or.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, MainFrame.darkBgColor), "OR", 2, 0, new Font("Microsoft JhengHei", Font.PLAIN, 15), MainFrame.brightBgColor));
		or.setOpaque(false);

		JPanel dragDropPanel = new JPanel();
		dragDropPanel.setBorder(BorderFactory.createDashedBorder(null, 1f, 5f, 5f, true));
		dragDropPanel.setOpaque(false);
		dragDropPanel.setForeground(MainFrame.brightBgColor);
		dragDropPanel.setPreferredSize(new Dimension(700, 150));
		JTextArea dragDrop = new JTextArea("Drag and Drop Files");
		dragDrop.setOpaque(false);
		dragDrop.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
		dragDrop.setForeground(MainFrame.brightBgColor);
		dragDrop.setDropTarget(null);
		dragDrop.setTransferHandler(new FileDropHandler(i));
		dragDrop.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
		dragDropPanel.add(dragDrop);
			
		fileSelectHolder.add(buttonPanelTemplate.get(3 + i));
		fileSelectHolder.add(or);
		fileSelectHolder.add(dragDropPanel);
			
		fileSelectWrapper[i].add(fileSelectHolder);
		fileSelectWrapper[i].revalidate();
		fileSelectWrapper[i].repaint();
	}
	
	//for when a file is added into the section
	private void panelTemplateFileAdded(int i){//i==0 is station, i == 1 is employee
		fileSelectWrapper[i].removeAll();
			
		JPanel fileBorder = new JPanel();
		fileBorder.setOpaque(false);
		fileBorder.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
		JPanel fileHolder = new JPanel();
		fileHolder.setOpaque(false);
		fileHolder.setBorder(BorderFactory.createLineBorder(MainFrame.midBgColor, 1));
		fileHolder.setLayout(new BorderLayout());
			
		JLabel fileName = new JLabel();
		fileName.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 15));
		fileName.setForeground(MainFrame.brightBgColor);
		if(i == 0) fileName.setText(filePathStation);
		else fileName.setText(filePathEmp);
			
		JLabel labelIcon = new JLabel();
		labelIcon.setIcon(new ImageIcon(new ImageIcon("media/taskBar/homeIcon.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
		labelIcon.setAlignmentX(CENTER_ALIGNMENT);
		labelIcon.setAlignmentY(CENTER_ALIGNMENT);
		labelIcon.setOpaque(false);
		labelIcon.setVisible(true);
		labelIcon.revalidate();
		labelIcon.repaint();
			
		buttonPanelTemplate.add(new Button("x", 15, 5 + i));
		buttonPanelTemplate.get(5 + i).setAlignmentX(RIGHT_ALIGNMENT);
			
		fileHolder.add(fileName, BorderLayout.SOUTH);
		fileHolder.add(labelIcon, BorderLayout.CENTER);
		fileHolder.add(buttonPanelTemplate.get(5 + i), BorderLayout.EAST);
			
		fileBorder.add(fileHolder);
		fileSelectWrapper[i].add(fileBorder);
		fileSelectWrapper[i].revalidate();
		fileSelectWrapper[i].repaint();
	}
	
	//to download files
	private void copyLocalFile() {
		String[] sourcePath = {"src/resources/StationTemplate.txt", "src/resources/EmployeeTemplate.txt", "src/resources/StationExample.txt", "src/resources/EmployeeExample.txt"};
		String[] fileName = {"StationTemplate.txt", "EmployeeTemplate.txt", "StationExample.txt", "EmployeeExample.txt"};
			
		String downloadsPath = System.getProperty("user.home") + File.separator + "Downloads";
		String[] destinationPath = new String[4];
		for(int i = 0; i < destinationPath.length; i++) destinationPath[i] = downloadsPath + File.separator + fileName[i];
		    
	    try {
	        for(int i = 0; i < sourcePath.length; i++) Files.copy(Paths.get(sourcePath[i]), Paths.get(destinationPath[i]), StandardCopyOption.REPLACE_EXISTING);
	        JOptionPane.showMessageDialog(null, "File Donwloaded Successfully", "File Donwload", JOptionPane.INFORMATION_MESSAGE);
	            
	    } catch (IOException ex) {
	        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "File Download", JOptionPane.INFORMATION_MESSAGE);
	    }
	}
		
	private void chooseFile(int file){ //file == 0 is stations file path, int == 1 is emp file path
		JFileChooser fileChooser = new JFileChooser();
		int response = fileChooser.showOpenDialog(null);
		if(response == JFileChooser.APPROVE_OPTION){
			if(file == 0) filePathStation = fileChooser.getSelectedFile().getAbsolutePath();
			else filePathEmp = fileChooser.getSelectedFile().getAbsolutePath();
			
			panelTemplateFileAdded(file);
		} else if(response == JFileChooser.ERROR_OPTION){
			JOptionPane.showMessageDialog(null, "An Error has ocurred while choosing a file");
		}
	}
	
	private boolean validateStationPopUp(){
		if(!FileRead.readFileStore(filePathStation)){
			JOptionPane.showMessageDialog(null, "Invalid Information Inputted on Station's Data on the Tempalte", "Data Misinput", JOptionPane.INFORMATION_MESSAGE);
			FileRead.reset();
			return false;
		} else {
			FileRead.reset();
			return true;
		}
	}
	
	private boolean validateEmpPopUp(){
		if(!FileRead.readFileEmp(filePathEmp)){
			JOptionPane.showMessageDialog(null, "Invalid Information Inputted on Employee's Data on the Template", "Data Misinput", JOptionPane.INFORMATION_MESSAGE);
			FileRead.reset();
			return false;
		} else {
			FileRead.reset();
			return true;
		}
	}
	
	public void setFilePathStation(String filePath){
		filePathStation = filePath.trim();
	}
		
	public void setFilePathEmp(String filePath){
		filePathEmp = filePath.trim();
	}
	//for abstraction
	public void setPanelFileAdded(int i){
		panelTemplateFileAdded(i);
	}
	
	public void buttonPressed(int buttonNumber){
		if(buttonNumber == 0){ //back
			New.setPanelLevel(1);
			New.footerPress(buttonNumber);
		} else if(buttonNumber == 1){ //next
			//validate and generate schedules
			if(validateStationPopUp()){
				if(validateEmpPopUp()){
					New.setAllPanels(4, new Schedule(days, filePathStation, filePathEmp));
					New.footerPress(buttonNumber);
				}
			}
		} else if(buttonNumber == 2){ //download
			copyLocalFile();
		} else if(buttonNumber == 3){ //choose files for station
			chooseFile(0);
		} else if(buttonNumber == 4){ //choose files for employee
			chooseFile(1);
		} else if(buttonNumber == 5){ //removes files for station
			panelFileSectionWrapper(0);
		} else{ //removes files for employee
			panelFileSectionWrapper(1);
		}
	}
}