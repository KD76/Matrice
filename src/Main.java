
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Matrice m = new Matrice(3, 1);
		double[][] test = {{1,2},{1,2}};
		MatriceCarree m2 = new MatriceCarree(test);
		m2.display();
        //m2.display();
        //MatriceCarree metTest = new MatriceCarree(test);
        //MatriceCarree product = metTest.multiply(metTest).toMatriceCarree();
        //product.display();
        long startTime = System.currentTimeMillis();
        double retTEST = m2.det();
        long endTime = System.currentTimeMillis();
		System.out.println(retTEST);
        System.out.println("Temps d'execution:"+(endTime-startTime)+"ms");
        //m2.display();
        /*MatriceCarree invertedTest = m2.getInverted();
        invertedTest.display();*/
	}

}
