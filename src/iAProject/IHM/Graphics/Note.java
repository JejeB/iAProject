package iAProject.IHM.Graphics;

public class Note {
	private int x;
	private int y;
	private int num;
	private String Nname;
	private String touche;
	
	
	public Note(String name,int n) {
		
		this.Nname = name;
		this.y = 0;
		this.num = n;
		
		if(this.Nname.contentEquals("DO")) { //Initialiser this.x
			this.x = 25;
			this.touche = "A";
		}else if(this.Nname.contentEquals("RE")) {
			this.x = 125;
			this.touche = "Z";
		}else if(this.Nname.contentEquals("MI")) {
			this.x = 225;
			this.touche = "E";
		}else if(this.Nname.contentEquals("FA")) {
			this.x = 325;
			this.touche = "R";
		}else if(this.Nname.contentEquals("SOL")) {
			this.x = 425;
			this.touche = "T";
		}else if(this.Nname.contentEquals("LA")) {
			this.x = 525;
			this.touche = "Y";
		}else if(this.Nname.contentEquals("SI")) {
			this.x = 625;
			this.touche = "U";
		}
			
		
		
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getNname() {
		return Nname;
	}
	public void setNname(String Nname) {
		this.Nname = Nname;
	}

	public String getTouche() {
		return touche;
	}

	public void setTouche(String touche) {
		this.touche = touche;
	}
	
	
}
