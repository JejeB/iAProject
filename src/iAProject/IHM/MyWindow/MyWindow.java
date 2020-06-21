package iAProject.IHM.MyWindow;

import javax.swing.JFrame;

import iAProject.IHM.Graphics.GraphicsDemo;

public class MyWindow extends JFrame{
	
	
	private GraphicsDemo graphicDemo = new GraphicsDemo("testFile.txt"); //nomFichier a changer
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -172057004406642170L;


	public MyWindow(String name) {
		
		super(name);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(700,800);
		this.setLocationRelativeTo(null);
		
		this.add(graphicDemo);
		
		this.setVisible(true);
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyWindow mywindow = new MyWindow("RythmeGame");
		
		mywindow.setVisible(true);
	}

}
