
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Matrice m = new Matrice(3, 1); // Cr�ation d'un nouvel objet "Matrice" vide, ayant 3 lignes et 1 colonne
		double[][] test = {{1,2},{1,2}}; // On cr�e un tableau de doubles correspondant aux nombres de notre matrice
		MatriceCarree m2 = new MatriceCarree(test); // Cr�ation d'une matrice carr�e � partir du tableau pr�c�dent
		m2.display(); // On affiche la matrice carr�e.
        //m2.display();
        //MatriceCarree metTest = new MatriceCarree(test);
        //MatriceCarree product = metTest.multiply(metTest).toMatriceCarree();
        //product.display();
        long startTime = System.currentTimeMillis(); // HP - On r�cup�re le temps � la milliseconde pr�s
        double retTEST = m2.det(); // On calcule et on stocke la valeur du d�terminant
        long endTime = System.currentTimeMillis(); // HP - On r�cup�re le temps � la milliseconde pr�s
		System.out.println(retTEST); // On affiche la valeur du d�terminant
        System.out.println("Temps d'execution:"+(endTime-startTime)+"ms"); // On affiche le temps d'ex�cution
        //m2.display();
        /*MatriceCarree invertedTest = m2.getInverted();
        invertedTest.display();*/
	}

}
