package iAProject.OudinetPackage;

public class SonBPM extends Son {

	private float bpm;
	private int point_note;
	private int numero_note;
	float tab_point[];

	/*
	 * private int taille_echantillon; private float freq_echantillon;
	 */

	public SonBPM(float bpm, String nomFichier) {
		super(nomFichier);
		this.bpm = bpm;
		int taille_echantillon = donnees().length;
		float freq_echantillon = frequence();
		float point_note;
		point_note = ((float) 60.0 / bpm) * (freq_echantillon);
		this.point_note = (int)point_note;
		System.out.println(point_note);
	}
	
	
	public float[] getDonneeNote(int numero_note) {
		float tab[] = new float[point_note];
		int min = numero_note*point_note  > donnees().length ? donnees().length : numero_note*point_note ;
		int max = (numero_note+1)*point_note > donnees().length ? donnees().length :(numero_note+1)*point_note ;
		int j=0;
		for (int i = min; i< max  ; i++) {
			
			tab[j] = donnees[i];
			j++;
			}
		return tab;
	}

	

	/*
	 * public float getNote() {
	 * 
	 * }
	 */

	public static void main(String[] args) {

		SonBPM sonBPM = new SonBPM(130, args[0]);
		
		float tab[];
		tab= sonBPM.getDonneeNote(1);
		System.out.println("OK");
		
		
	}
}
