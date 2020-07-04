package solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A representation of a extended matrix that can
 * contain LinearEquations and can be solved with
 * GausJordan elimination
 */
public class Matrix {
    List<Row> rows;
    int numColums;
    int numRows;
    // Dictionary with al the column swaps
    Map<Integer, Integer> swapColumnIndexes;

    Matrix(List<Row> rows) {
        this.rows = rows;
        this.numColums = rows.get(0).getSize() - 1;
        this.numRows = rows.size();
        swapColumnIndexes = new HashMap<>();
        for (int i = 0; i <= numColums; i++) {
            swapColumnIndexes.put(i,i);
        }
    }

    public int getNumIncognites() {
        return numColums;
    }

    //This method have to be called after
    //the solveGausJordan method, and indicate
    //the type of solutions of the system
    public String solutions() {
        int numSignficative = 0;
        for (Row x: rows) {
            if (x.isSignificant()) {
                numSignficative++;
            }
            if (x.isInconsist()) {
                return "No solutions";
            }
        }

        if (numSignficative == numColums) {
            return "it is ok";
        }

        return "Infinitely many solutions";

    }

    public boolean isSquare() {
        return numColums == numRows;
    }

    public Row extendedValues() {
        List<Complex> cons = new ArrayList<>();
        for (Row x: rows) {
            cons.add(x.getElement(x.getSize() - 1));
        }
        return new Row(cons);
    }

    public Matrix solveGaussJordan() {
        makeUpperTriangleMatrix();
        makeLowerTriangleMatrix();
        makeSchelonForm();
        return new Matrix(rows);
    }

    private void makeSchelonForm() {
        swapOriginalColumnMatrix();
        for (int i = 0; i < numRows; i++) {
            int rowSwap = nextNotNullElementinRow(i);
            if (rowSwap == -1) {
                break;
            }
            swapRow(i,rowSwap);
        }
    }

    private void swapOriginalColumnMatrix() {
        for (Integer key : swapColumnIndexes.keySet()) {
            swapColumn(key,swapColumnIndexes.get(key));
        }
    }

    private void makeUpperTriangleMatrix() {
        for (int i = 0; i < numRows; i++) {
            if (makeSuitable(i)) {
                Row rowPiv = rows.get(i);
                Complex norm = rows.get(i).getElement(i);
                rowPiv = rowPiv.divide(norm);
                rows.set(i, rowPiv);
                System.out.printf("%s * R%d -> R%d\n", new Complex(1,0).divideBy(norm), i, i);
                for (int j = i + 1; j < numRows; j++) {
                    Row temp = rows.get(j);
                    Complex cons = temp.getElement(i);
                    temp = temp.substract(rowPiv.multiply(cons));
                    rows.set(j, temp);
                    System.out.printf("%s * R%d + R%d -> R%d\n", cons, i, j, j);
                }
            } else {
                break;
            }
        }
    }

    private void makeLowerTriangleMatrix() {
        for (int i = numRows - 1; i >= 0 ; i--) {
            Row rowPiv = rows.get(i);
            for (int j = i - 1; j >= 0 ; j--) {
                Row temp = rows.get(j);
                Complex cons = temp.getElement(i);
                temp = temp.substract(rowPiv.multiply(cons));
                rows.set(j,temp);
                System.out.printf("%s * R%d + R%d -> R%d\n",cons,i,j,j);
            }
        }
    }

    private int nextNotNullElementinColumn(int row, int nRow) {
        for (int i = nRow; i < numColums; i++) {
            if (!rows.get(row).getElement(i).isAllZero()) {
                return i;
            }
        }
        return -1;
    }

    private int nextNotNullElementinRow(int numRow) {
        for (int i = numRow; i < numRows; i++) {
            if (!rows.get(i).getElement(numRow).isAllZero()) {
                return i;
            }
        }
        return -1;
    }

    private boolean makeSuitable(int row) {
        int rowSwap = nextNotNullElementinRow(row);
        if (rowSwap == -1) {
            int columSwap = -1;
            for (int i = row; i < numRows; i++) {
                columSwap = nextNotNullElementinColumn(i,row);
                if (columSwap != -1) {
                    swapColumn(row,columSwap);
                    swapColumnIndexes.put(row,columSwap);
                    System.out.printf("C%d <-> C%d\n",row,columSwap);
                    return true;
                }
            }
            return false;
        } else {
            swapRow(row, rowSwap);
            System.out.printf("R%d <-> R%d\n",row,rowSwap);
        }
        return true;
    }
    private void normalizeMainDiagonal() {
        for (int i = 0; i < numRows; i++) {
            Complex cons = rows.get(i).getElement(i);
            Row newRow = rows.get(i).divide(cons);
            rows.set(i,newRow);
            System.out.printf("%s * R%d -> R%d\n",new Complex(1,0).divideBy(cons),i,i);
        }
    }

    private void swapRow(int indexRow1, int indexRow2) {
        Row r1 = rows.get(indexRow1);
        Row r2 = rows.get(indexRow2);

        rows.set(indexRow1,r2);
        rows.set(indexRow2,r1);
    }

    private void swapColumn(int indexCol1, int indexCol2) {
        for (Row x : rows) {
            Complex a = x.getElement(indexCol1);
            Complex b = x.getElement(indexCol2);
            x.setElement(indexCol1,b);
            x.setElement(indexCol2,a);
        }
    }


}
