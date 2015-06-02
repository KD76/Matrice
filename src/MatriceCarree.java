import java.math.BigDecimal;

/**
 * Classe complémentant la gestion des matrices carrées.
 *
 * @author Douaa Elkhalfi, Julien Guittat, Thomas Brenière, Kévin Dumanoir
 */
public class MatriceCarree extends Matrice {

    /**
     * Crée une matrice carrée vide d'ordre order.
     *
     * @param order Ordre de la matrice
     */
    public MatriceCarree(int order) {
        super(order, order);
    }

    /**
     * Crée une matrice carrée à partir d'un double tableau de double.
     *
     * @param fillData double tableau de données
     */
    public MatriceCarree(double[][] fillData) {
        if (fillData.length != fillData[0].length) {
            int minrow = Math.min(fillData.length, fillData[0].length);
            double[][] newdata = new double[minrow][minrow];
            for (int i = 0; i < minrow; i++) {
                System.arraycopy(fillData[i], 0, newdata[i], 0, newdata.length);
            }
            fillData = newdata;
        }
        data = copyArray(fillData);
        sepMaxLength = new int[fillData.length];
    }

    /**
     * Crée une matrice identité d'ordre order
     *
     * @param order Ordre de l'itentité
     * @return Matrice identité
     */
    public static MatriceCarree getIdentity(int order) {
        double[][] data = new double[order][order];
        for (int i = 0; i < order; i++) {
            data[i][i] = 1;
        }
        return new MatriceCarree(data);
    }

    /**
     * Vérifie si la matrice est inversible.
     *
     * @return Vrai si la matrice est inversible, faux sinon.
     */
    public boolean hasInvert() {
        MatriceCarree Identity = MatriceCarree.getIdentity(order());
        MatriceCarree testInvert = new MatriceCarree(getData());
        testInvert.gaussJordan();
        testInvert.display();
        return testInvert.equals(Identity);
    }

    /**
     * Calcule l'inverse de la matrice en cours.
     *
     * @return Matrice inverse.
     */
    public MatriceCarree getInverted() {
        if (!hasInvert()) {
            System.out.println("[INFO] La Matrice n'est pas inversible.");
            return MatriceCarree.getIdentity(1);
        }
        MatriceCarree Identity = MatriceCarree.getIdentity(order());
        Matrice joinedMat = join(Identity, false);
        joinedMat.gaussJordan();
        //joinedMat.display();
        Matrice[] convertedMatrices = joinedMat.split(order(), false);
        return convertedMatrices[1].toMatriceCarree();
    }

    /**
     * Calcule le déterminant de la matrice.
     *
     * @return Déterminant
     */
    public double det() {
        double[][] echelon = echelonnage();
        double det = 1;
        for (int i = 0; i < order(); i++) {
            det *= echelon[i][i];
        }
        return new BigDecimal(Double.toString(det)).setScale(7, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /*public double det() {
        double det = 0;
        int order = getOrder();
        int colPos = 0;
        while (order != 2) {
            for(int i=colPos; i<order; i++) {
                if (data[colPos][i] != 0) {

                }
            }
            colPos++;
        }
    }*/

    /**
     * @return Ordre de la matrice.
     */
    public int order() {
        return data.length;
    }
}
