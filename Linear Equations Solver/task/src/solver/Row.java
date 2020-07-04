package solver;

import java.util.ArrayList;
import java.util.List;

public class Row {
    protected List<Complex> coefficients;
    protected int size;
    public Row(List<Complex> equation){
        coefficients = equation;
        this.size = coefficients.size();
    }
    Row(LinearEquation e) {
        coefficients = e.coeficients;
        this.size = coefficients.size();
    }

    public int getSize() {
        return this.size;
    }

    public Complex getElement(int i) {
        return coefficients.get(i);
    }

    public void setElement(int index, Complex ele) {
        coefficients.set(index, ele);
    }

    public void setRow(Row added){
        this.coefficients = added.coefficients;
    }

    public Row add(Row added) {
        List<Complex> coef = new ArrayList<>();
        if (this.coefficients.size() == added.coefficients.size()) {
            for (int i = 0; i < this.coefficients.size(); i++) {
                coef.add(i,this.coefficients.get(i).add(added.coefficients.get(i)));
            }
        }

        return new Row(coef);
    }

    public Row substract(Row sub) {
        List<Complex> coef = new ArrayList<>();
        if (this.coefficients.size() == sub.coefficients.size()) {
            for (int i = 0; i < size; i++) {
                coef.add(i,this.coefficients.get(i).substract(sub.coefficients.get(i)));
            }
        }

        return new Row(coef);
    }

    public Row multiply(Complex cons) {
        List<Complex> coef = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            coef.add(i,cons.multiplyBy(coefficients.get(i)));
        }
        return new Row(coef);
    }

    public Row divide(Complex cons) {
        List<Complex> coef = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            coef.add(i,coefficients.get(i).divideBy(cons));
        }
        return new Row(coef);
    }

    public boolean isSignificant() {
        for (int i = 0; i < size - 1;i++) {
            if (!coefficients.get(i).isAllZero())
                return true;
        }
        return false;
    }

    public boolean isInconsist() {
        return !isSignificant() && !coefficients.get(size - 1).isAllZero();
    }

    public LinearEquation getLinearEquation(){
        return new LinearEquation(coefficients);
    }
}