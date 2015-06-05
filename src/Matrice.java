import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Classe permettant de créer un objet matriciel en java.
 *
 * @author Douaa Elkhalfi, Julien Guittat, Thomas Brenière, Kévin Dumanoir
 */
public class Matrice {

    /**
     * Tableau de valeurs de la matrice.
     */
    protected double[][] data;

    /**
     * Tableau contenant la longueur maximal du séparateur en fonction du n° de colonne.
     */
    protected int[] sepMaxLength;

    /**
     * Initialise une matrice vide, de rows lignes et de columns colonnes.
     *
     * @param rows    Nombre de lignes.
     * @param columns Nombre de colonnes.
     */
    public Matrice(int rows, int columns) {
        data = new double[rows][columns]; // Création d'un "tableau de tableau de double" (définition du double tableau) dans l'ordre suivant : tableau de (rows) tableau de (columns) nombres.
        // EXPLICATION : Dans la matrice, on gère les nombres de la façon suivante : On utilise un double tableau qui prend en 1er index le numéro de ligne, puis le numéro de colonne en second paramètre.
        // Ainsi, lorsque l'on créera un tableau pour notre matrice, on devra faire un tableau de LIGNES, et pas un tableau de COLONNES (Voir fichier Main.java pour avoir un exemple)

        sepMaxLength = new int[columns]; // On crée un tableau qui contiendra la taille du nombre le plus "large" (donc, les grands nombres, mais aussi ceux avec plein de décimales ! Le procédé est expliqué après.)
    }

    /**
     * Initialise une matrice depuis le double tableau fillData.
     *
     * @param fillData Double tableau "source".
     */
    public Matrice(double[][] fillData) {
        data = copyArray(fillData); // On copie le tableau d'entrée afin de ne pas avoir de problèmes de modification extérieure de tableau.
        // En effet, l'élément double[][] n'est pas le double-tableau en lui-même, mais uniquement son pointeur ! De ce fait, si copie juste le pointeur,
        // il suffit de modifier le double tableau à l'extérieur de la matrice pour altérer son contenu ! Et ça, c'est pas cool !

        sepMaxLength = new int[fillData[0].length]; // Idem que pour la méthode précédente.
    }

    protected Matrice() { // HORS PROGRAMME - Fonctionnement secondaire du programme, rien d'important.
    }

    /**
     * Récupère un élément de la matrice suivant la ligne et la colonne demandée.
     * @param row    Ligne demandée (commence à partir de 1 !)
     * @param column Colonne demandée (idem, même précautions que pour les lignes.)
     * @return Element de la matrice à la position row,column
     */
    public double get(int row, int column) {
        if (row < 1 || row > data.length || column < 1 || column > data[0].length) { // Si le numéro de ligne/colonne est en dehors des limites de la matrice,
            System.err.println("[ERREUR] La ligne/colonne demandée est hors-matrice. Zéro retourné."); // On affiche un message d'erreur...
            return 0; // Et on retourne 0.
        }

        return data[row - 1][column - 1]; // Sinon, on retourne le nombre contenu dans le double-tableau de données de la matrice.
    }

    /**
     * Attribue à l'élément de coordonnées row,column la valeur value.
     *
     * @param row    Ligne demandée (commence à partir de 1 !)
     * @param column Colonne demandée (idem, même précautions que pour les lignes.)
     * @param value  Valeur à insérer dans la matrice.
     */
    public void set(int row, int column, double value) {
        if (row < 1 || row > data.length || column < 1 || column > data[0].length) { // Pareil que pour en haut...
            System.err.println("[ERREUR] La ligne/colonne demandée est hors-matrice.");
            return;
        }

        data[row - 1][column - 1] = value; // ... sauf que cette fois, on ne retourne pas le nombre contenu, on le remplace.
        updateSeparator(value, (column - 1)); // On met à jour le système d'alignement des nombres.
    }

    /**
     * @return Double tableau des valeurs de la matrice.
     */
    public double[][] getData() {
        return copyArray(data); // Fait une copie du tableau de la matrice et le renvoie. Cela permet, en toute circonstance, de préserver les données de la matrice, c'est à dire d'empêcher les modifications extérieures non sollicitées.
    }

    /**
     * @return Nombre de ligne de la matrice.
     */
    public int rowsCount() {
        return data.length; // Renvoie le nombre de tableaux contenus dans le double tableau, soit le nombre de lignes.
    }

    /**
     * @return Nombre de colonnes de la matrice.
     */
    public int columnsCount() {
        return data[0].length; // Renvoie le nombre de "nombres" contenus dans le premier tableau du double tableau, ce qui est le nombre de colonnes.
    }

    /**
     * Ajoute un scalaire à la matrice.
     *
     * @param scalaire Valeur scalaire réelle.
     * @return Matrice en cours d'exploitation.
     */
    public Matrice add(double scalaire) {
        for (int i = 0; i < data.length; i++) { // Pour chaque sous tableau du double-tableau...
            for (int j = 0; j < data[0].length; j++) { // Pour chaque valeur du sous-tableau en cours...
                data[i][j] += scalaire; // On ajoute le scalaire.
                updateSeparator(data[i][j], j); // On met à jour le système d'alignement des nombres.
            }
        }
        return this;
    }

    /**
     * Ajoute une matrice à la matrice en cours d'exploitation
     *
     * @param m Matrice à ajouter.
     * @return Matrice en cours d'exploitation.
     */
    public Matrice add(Matrice m) {
        if (data.length != m.rowsCount() || data[0].length != m.columnsCount()) { // Si les matrices n'ont pas le même nombre de lignes ou de colonnes,
            System.err.println("[ERREUR] Les matrices sont incompatibles. Matrice Id1 retournée."); // Alors on affiche une erreur,
            return (new Matrice(1, 1)).add(1); // Et on renvoit une matrice 1x1 contenant un 1 (c'est la "Matrice d'erreur")
        }
        double[][] fromMat = m.getData(); // On récupère le double tableau de nombres de la matrice à ajouter
        int columnLength = fromMat[0].length; // On stocke le nombre de colonnes du double tableau
        for (int i = 0; i < fromMat.length; i++) { // Pour chaque sous-tableau du double tableau...
            for (int j = 0; j < columnLength; j++) { // Pour chaque valeur du sous-tableau en cours...
                data[i][j] += fromMat[i][j]; // On ajoute à notre matrice la valeur de la matrice M aux mêmes coordonnées.
                updateSeparator(data[i][j], j); // On met à jour le système d'alignement des nombres.
            }
        }

        return this;
    }

    /**
     * Multiplie la matrice en cours avec une matrice m.
     *
     * @param m Matrice à multiplier (à droite)
     * @return Nouvelle matrice issue de la multiplication.
     */
    public Matrice multiply(Matrice m) {
        if (data[0].length != m.rowsCount()/* || data.length < m.columnsCount()*/) { // Condition à vérifier. - Au final, elle n'est pas nécessaire et fausse même le calcul matriciel.
            System.err.println("[ERREUR] Les matrices sont incompatibles. Matrice Id1 retournée.");
            return (new Matrice(1, 1)).add(1);
        }

        double[][] newData = new double[data.length][m.columnsCount()];
        double[][] m2Data = m.getData();

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < m.columnsCount(); j++) {
                for (int k = 0; k < data[0].length; k++) {
                    newData[i][j] += data[i][k] * m2Data[k][j];
                }
            }
        }

        return new Matrice(newData);
    }

    /**
     * Multiplie la matrice en cours par un scalaire.
     *
     * @param scalaire Sclaire à multiplier
     * @return Matrice en cours, multipliée par scalaire.
     */
    public Matrice multiply(double scalaire) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                data[i][j] *= scalaire;
                updateSeparator(data[i][j], j);
            }
        }
        return this;
    }

    /**
     * Génère une copie d'un double tableau de doubles.
     *
     * @param from Double tableau à copier.
     * @return Nouvel objet contenant les valeurs du double tableau from
     */
    protected double[][] copyArray(double[][] from) {
        double[][] data = new double[from.length][from[0].length];
        for (int i = 0; i < from.length; i++) {
            data[i] = copySimpleArray(from[i]);
        }
        return data;
    }

    /**
     * Génère une copie d'un tableau de double.
     *
     * @param from Tableau à copier
     * @return Nouveau tableau contenant les valeurs du tableau from
     */
    private double[] copySimpleArray(double[] from) {
        double[] data = new double[from.length];
        System.arraycopy(from, 0, data, 0, from.length);
        return data;
    }

    /**
     * Affiche une matrice (avec alignement des colonnes)
     */
    public void display() {
        String[] display = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            display[i] = "| ";
            for (int j = 0; j < data[0].length; j++) {
                display[i] += data[i][j] + getSeparator(data[i][j], j);
            }
            display[i] += "|";
        }
        System.out.println(generateBarre(display[0]));
        for (String line : display) {
            System.out.println(line);
        }
        System.out.println(generateBarre(display[0]));
    }

    /**
     * Génère une barre de longueur égale à la ligne passée en paramètre.
     *
     * @param display Ligne de longueur à considérer.
     * @return Barre.
     */
    private String generateBarre(String display) {
        String barre = "";
        for (int i = 0; i < display.length(); i++) {
            barre += "-";
        }
        return barre;
    }

    /**
     * Génère le séparateur entre le nombre et celui de la colonne suivante.
     * Le séparateur est généré de telle sorte à ce que les nombres de chaque colonne soient alignés sur la gauche.
     *
     * @param number Nombre à considérer
     * @param column Numéro de la colonne
     * @return Séparateur adapté.
     */
    private String getSeparator(double number, int column) {
        String separator = "";

        for (int i = 0; i < getMaxLength(column) - Double.toString(number).length(); i++) {
            separator += " ";
        }

        return separator;
    }

    /**
     * Génère la longueur maximale que peut avoir la colonne column, séparateur compris.
     *
     * @param column Colonne étudiée
     * @return Longueur maximale de la colonne.
     */
    private int getMaxLength(int column) {
        if (sepMaxLength[column] == 0) {
            int max = 0;
            for (double[] line : data) {
                int testVal = Double.toString(line[column]).length();
                if (testVal > max) max = testVal;
            }
            sepMaxLength[column] = max + 1;
        }
        return sepMaxLength[column];
    }

    /**
     * Applique l'algorithme de Gauss-Jordan à la matrice.
     * Il permet, lorsqu'il est appliqué à une matrice jointe à l'identité (A|In) de récupérer "le possible inverse" avec l'identité (In|A-1).
     */
    public void gaussJordan() {
        int r = -1;
        for (int i = 0; i < rowsCount(); i++) {
            int k = getPosMaxCol(i, r + 1);
            double pivot = data[k][i];
            if (pivot != 0) {
                r++;
                if (pivot != 1) {
                    for (int j = 0; j < columnsCount(); j++) {
                        data[k][j] /= pivot;
                        data[k][j] += 0.0;
                    }
                }
                if (r != k) {
                    switchLines(r, k);
                }
                for (int j = 0; j < rowsCount(); j++) {
                    if (j != r) {
                        addLineTo(data[j], data[r], BigDecimal.valueOf(-1 * data[j][i]));
                    }
                }
            }
            //display();
        }

        //----- "BONUS" : Utilisation de la classe BigDecimal pour obtenir le résultat "exact" -----\\
        round(15);
        //----- FIN "BONUS" -----\\
        sepMaxLength = new int[data[0].length];
    }

    /**
     * Détermine la matrice échelonnée correspondante, avec la même valeur de déterminant.
     *
     * @return Tableau échelonné
     */
    public double[][] echelonnage() {
        double[][] echelon = copyArray(data);
        int max = Math.min(echelon.length, echelon[0].length);
        for (int i = 0; i < max; i++) {
            BigDecimal pivot = new BigDecimal(Double.toString(echelon[i][i]));
            for (int j = i + 1; j < max; j++) {
                BigDecimal a = new BigDecimal(-1 * echelon[j][i]);
                addLineTo(echelon[j], echelon[i], a.divide(pivot, 30, BigDecimal.ROUND_HALF_UP));
            }
        }
        return echelon;
    }

    /**
     * Détermine la position de la valeur maximale d'une colonne à partir d'une ligne spécifiée.
     *
     * @param column Colonne considérée
     * @param start  Ligne de départ
     * @return Posiion de la valeur max
     */
    private int getPosMaxCol(int column, int start) {
        double max = Double.NEGATIVE_INFINITY;
        int pos = -1;
        for (int i = start; i < data.length; i++) {
            if (Math.abs(data[i][column]) > max) {
                max = Math.abs(data[i][column]);
                pos = i;
            }
        }
        return pos;
    }

    /**
     * Met à jour le système d'alignement des nombres de la même colonne.
     *
     * @param value Valeur à exploiter pour la mise à jour
     * @param col   Numéro de la colonne à mettre à jour
     */
    private void updateSeparator(double value, int col) {
        if (col >= sepMaxLength.length || col < 0)
            return;
        // (Optionnel, plutôt difficile) On récupère la longueur du nombre stocké...
        int valueStringLength = Double.toString(value).length(); // (Optionnel, plutôt difficile) On récupère la longueur du nombre stocké...
        if (sepMaxLength[col] != 0 && sepMaxLength[col] < valueStringLength + 1) // ... Si la longueur maximale de la colonne est plus petite que la longueur du nouveau nombre,
            sepMaxLength[col] = valueStringLength + 1; // Alors la nouvelle longueur maximale est celle du nouveau nombre, +1 (pour l'espace entre les colonnes)
    }

    /**
     * Permet d'échanger deux lignes
     *
     * @param first  Ligne 1
     * @param second Ligne 2
     */
    private void switchLines(int first, int second) {
        double[] switcher = copySimpleArray(data[first]);
        data[first] = data[second];
        data[second] = switcher;
    }

    /**
     * Ajoute une ligne à une autre, pouvant être complétée par un coeff. multiplicateur.
     *
     * @param subject       Ligne sujette à l'addition
     * @param lineToAdd     Ligne à ajouter
     * @param multiplicator Coefficient multiplicateur
     */
    private void addLineTo(double[] subject, double[] lineToAdd, BigDecimal multiplicator) {
        for (int i = 0; i < subject.length; i++) {
            BigDecimal d = new BigDecimal(Double.toString(lineToAdd[i]));
            BigDecimal e = new BigDecimal(Double.toString(subject[i]));
            subject[i] = d.multiply(multiplicator).add(e).doubleValue();
            if (Math.abs(subject[i]) < 1.0E-29) subject[i] = 0;
        }
    }

    /**
     * Vérifie si deux matrices sont égales.
     *
     * @param b Matrice à vérifier
     * @return TRUE si les matrices sont égales, FALSE sinon.
     */
    public boolean equals(Matrice b) {
        if (b.columnsCount() != columnsCount() || b.rowsCount() != rowsCount())
            return false;

        for (int i = 0; i < rowsCount(); i++) {
            for (int j = 0; j < columnsCount(); j++) {
                if (b.get(i + 1, j + 1) + 0.0 != data[i][j] + 0.0)
                    return false;
            }
        }

        return true;
    }

    /**
     * Effectue une jointure entre deux matrices. La jointure peut se faire en ligne ou en colonne.
     *
     * @param b      Matrice à joindre
     * @param rowAdd Si vrai, la jointure se fera en ligne. Sinon, elle se fera en colonne.
     * @return Matrice jointe
     */
    public Matrice join(Matrice b, boolean rowAdd) {
        double[][] newData;
        int firstLoop, secondLoop;
        if (rowAdd) {
            newData = new double[rowsCount() + b.rowsCount()][columnsCount()];
            firstLoop = rowsCount() + b.rowsCount();
            secondLoop = columnsCount();
        } else {
            newData = new double[rowsCount()][columnsCount() + b.columnsCount()];
            firstLoop = rowsCount();
            secondLoop = columnsCount() + b.columnsCount();
        }
        for (int i = 0; i < firstLoop; i++) {
            for (int k = 0; k < secondLoop; k++) {
                if (i > rowsCount() - 1) {
                    newData[i][k] = b.get(i - rowsCount() + 1, k + 1);
                } else if (k > columnsCount() - 1) {
                    newData[i][k] = b.get(i + 1, k - columnsCount() + 1);
                } else {
                    newData[i][k] = data[i][k];
                }
            }
        }
        return new Matrice(newData);
    }

    /**
     * Sépare une matrice en deux, suivant la position d'une ligne ou d'une colonne.
     *
     * @param pos Position de la ligne/colonne limite. cette ligne/colonne sera incluse dans la seconde matrice.
     * @param row Si vrai, la séparation se fera en ligne. Sinon, elle se fera en colonne.
     * @return Tableau contenant les deux matrices séparées.
     */
    public Matrice[] split(int pos, boolean row) {
        Matrice[] splitData = new Matrice[2];
        for (int i = 0; i < splitData.length; i++) {
            int firstLoop;
            int secondLoop;
            int start = (i == 0) ? 0 : pos;
            int extCount = 0;
            double[][] tab;
            if (row) {
                firstLoop = (i == 0) ? pos : rowsCount();
                secondLoop = columnsCount();
                if (i == 0)
                    tab = new double[pos][secondLoop];
                else
                    tab = new double[firstLoop - pos][secondLoop];

                for (int j = start; j < firstLoop; j++) {
                    System.arraycopy(data[j], 0, tab[extCount], 0, secondLoop);
                    extCount++;
                }
            } else {
                firstLoop = rowsCount();
                secondLoop = (i == 0) ? pos : columnsCount();
                if (i == 0)
                    tab = new double[firstLoop][pos];
                else
                    tab = new double[firstLoop][secondLoop - pos];

                for (int j = 0; j < firstLoop; j++) {
                    extCount = 0;
                    for (int k = start; k < secondLoop; k++) {
                        tab[j][extCount] = data[j][k];
                        extCount++;
                    }
                }
            }
            splitData[i] = new Matrice(tab);
        }
        return splitData;
    }

    protected void round(int scale) {
        for (int i = 0; i < rowsCount(); i++) {
            for (int j = 0; j < columnsCount(); j++) {
                data[i][j] = new BigDecimal(Double.toString(data[i][j])).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
        }
    }

    /**
     * Permet la conversion d'une matrice en matrice carrée si elle remplit les conditions.
     *
     * @return Matrice carrée.
     */
    public MatriceCarree toMatriceCarree() {
        if (rowsCount() != columnsCount()) {
            System.err.println("[ERREUR] La matrice n'est pas carrée. Conversion en matrice carrée impossible.");
            return MatriceCarree.getIdentity(1);
        }

        return new MatriceCarree(data);
    }

}
