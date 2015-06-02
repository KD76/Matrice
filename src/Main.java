
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Matrice m = new Matrice(3, 1); // Création d'un nouvel objet "Matrice" vide, ayant 3 lignes et 1 colonne
		double[][] test = {{1,2},{1,2}}; // On crée un tableau de doubles correspondant aux nombres de notre matrice
		MatriceCarree m2 = new MatriceCarree(test); // Création d'une matrice carrée à partir du tableau précédent
		m2.display(); // On affiche la matrice carrée.
        //m2.display();
        //MatriceCarree metTest = new MatriceCarree(test);
        //MatriceCarree product = metTest.multiply(metTest).toMatriceCarree();
        //product.display();
        long startTime = System.currentTimeMillis(); // HP - On récupère le temps à la milliseconde près
        double retTEST = m2.det(); // On calcule et on stocke la valeur du déterminant
        long endTime = System.currentTimeMillis(); // HP - On récupère le temps à la milliseconde près
		System.out.println(retTEST); // On affiche la valeur du déterminant
        System.out.println("Temps d'execution:"+(endTime-startTime)+"ms"); // On affiche le temps d'exécution
        //m2.display();
        /*MatriceCarree invertedTest = m2.getInverted();
        invertedTest.display();*/
	}

}
