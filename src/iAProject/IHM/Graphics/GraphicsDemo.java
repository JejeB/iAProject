package iAProject.IHM.Graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GraphicsDemo extends JPanel implements ActionListener, KeyListener{
	
	Timer timer = new Timer(1,this);
	
	private int nbNote = 0;
	private int etatFin = 0;
	
	private long tmr = 0;
	private long Tn;
	private long Tsn = 0;
	private long Tdn = 0;
	private int TmpDn = 4;
	
	private List<Note> notes = new ArrayList<>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8446512944272666694L;

	public GraphicsDemo(String fichier){
		try {
			this.fileReaderInit(fichier);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		timer.start();
		
	}
	
	public void fileReaderInit(String fichier) throws IOException{
		
		int i = 0;
		
		BufferedReader in = new BufferedReader(new FileReader(fichier));
		String line = in.readLine();
		this.Tn = Integer.parseInt(line);
		
		
		while ((line = in.readLine()) != null)
		{
			Note note = new Note(line,i);
			this.notes.add(note);
			i++;
		}
		in.close();
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		this.setBackground(new Color(0,255,255));
		
		Graphics2D g2D = (Graphics2D) g;
		
		
		
		if(this.etatFin == 0) {
			
			for(int i = 0; i < this.getNbNote();i++) {
				
				if(this.notes.get(i).getX() > -1 && this.notes.get(i).getX() < 700 && this.notes.get(i).getY() > -1 && this.notes.get(i).getX() < 800) {
					g2D.setColor(new Color(255,50,50));
					g2D.fillRoundRect(this.notes.get(i).getX(), this.notes.get(i).getY(), 50, 100, 10, 10);
					g2D.setColor(new Color(0,0,0));
					g2D.setStroke(new BasicStroke(2f) );
					g2D.drawRoundRect(this.notes.get(i).getX(), this.notes.get(i).getY(), 50, 100, 10, 10);
				}
				
			}
			if(nbNote == 0) {
				if(this.notes.get(0).getX() > -1 && this.notes.get(0).getX() < 700 && this.notes.get(0).getY() > -1 && this.notes.get(0).getX() < 800) {
					g2D.setColor(new Color(255,50,50));
					g2D.fillRoundRect(this.notes.get(0).getX(), this.notes.get(0).getY(), 50, 100, 10, 10);
					g2D.setColor(new Color(0,0,0));
					g2D.setStroke(new BasicStroke(2f) );
					g2D.drawRoundRect(this.notes.get(0).getX(), this.notes.get(0).getY(), 50, 100, 10, 10);
				}
			}
		}
		g2D.setColor(new Color(255,255,255));
		g2D.setStroke(new BasicStroke(2f) );
		g2D.drawLine(0, 500, 700, 500);
		g2D.drawLine(0, 650, 700, 650);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(etatFin == 0) {
			
		
			if(tmr == 0 && nbNote == 0) {
				
				Tdn = 4;
				nbNote = nbNote+1;
				this.Tsn = this.Tsn + this.Tn; 
				
			}
			
			if(tmr == 1000) {
				System.out.println("Tn = " + Tn + " ms ");
				for(int i = 0; i < this.getNotes().size();i++) {
					System.out.println("note" + i + " : " + notes.get(i).getNname() + ", " + notes.get(i).getNum() + ", " + notes.get(i).getTouche() + ", " + notes.get(i).getX() + ", " + notes.get(i).getY());
				}
			}
			
			if(tmr == Tdn) {
				
				for(int i = 0;i<this.getNbNote();i++) {
					int y = this.notes.get(i).getY();
					y = y+1;
					this.notes.get(i).setY(y);
				}
				
				Tdn = Tdn + TmpDn;
				
			}
			
			if(tmr == this.Tsn && this.nbNote < this.notes.size()) {
				nbNote = nbNote+1;
				this.Tsn = this.Tsn + this.Tn; 
				
			}
			
			tmr = tmr+1;
		}
		repaint();
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public long getTn() {
		return Tn;
	}

	public void setTn(long tn) {
		Tn = tn;
	}

	public long getTsn() {
		return Tsn;
	}

	public void setTsn(long tsn) {
		Tsn = tsn;
	}

	public long getTdn() {
		return Tdn;
	}

	public void setTdn(long tdn) {
		Tdn = tdn;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getNbNote() {
		return nbNote;
	}

	public void setNbNote(int nbNote) {
		this.nbNote = nbNote;
	}

	public int getEtatFin() {
		return etatFin;
	}

	public void setEtatFin(int etatFin) {
		this.etatFin = etatFin;
	}

	public long getTmr() {
		return tmr;
	}

	public void setTmr(long tmr) {
		this.tmr = tmr;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		rythmeGame(e);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		rythmeGame(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		rythmeGame(e);
		
	}
	
	public void rythmeGame(KeyEvent e) {
		
		char c =  e.getKeyChar();
		System.out.println(c);
		
		
		if(c =='a' || c == 'A') {
			
			System.out.println("A");
			
		}else if(c =='z' || c == 'Z') {
			
			System.out.println("Z");
			
		}else if(c =='e' || c == 'E') {
			
			System.out.println("E");
			
		}else if(c =='r' || c == 'R') {
			
			System.out.println("R");
			
		}else if(c =='t' || c == 'T') {
			
			System.out.println("T");
			
		}else if(c =='y' || c == 'Y') {
			
			System.out.println("Y");
			
		}else if(c =='u' || c == 'U') {
			
			System.out.println("U");
			
		}
		
	}

}
