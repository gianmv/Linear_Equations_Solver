package solver;

/**
 * A high level representation of a complex
 * number. Because the complex set contains
 * the real number, I only work with complex
 * number to build matrixes
 */
public class Complex {
    /**
     * A complex number can be written in a
     * rectangular for like:
     * a * bi
     * With 'a' being the real part and 'b'
     * being the imaginary part
     */
    private double a;
    private double b;


    Complex(Complex c) {
        this.a = c.a;
        this.b = c.b;
    }

    Complex(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public Complex add(Complex added) {
        double real = this.a + added.a;
        double imaginary = this.b + added.b;
        return new Complex(real, imaginary);
    }

    public Complex substract(Complex added) {
        double real = this.a - added.a;
        double imaginary = this.b - added.b;
        return new Complex(real, imaginary);
    }

    public Complex divideBy(Complex divided) {
        Complex divCon = divided.conjugate();
        Complex a = multiplyBy(divCon);
        Complex b = divided.multiplyBy(divCon);

        a.a /= b.a;
        a.b /= b.a;

        return a;

    }



    public Complex multiplyBy(Complex multiplied) {
        double real = this.a * multiplied.a - this.b * multiplied.b;
        double imaginary = this.a * multiplied.b + this.b * multiplied.a;
        return new Complex(real, imaginary);
    }

    /**
     * This function tell us if the number is completely
     * zero (0 + 0i)
     * @return true o false
     */
    public boolean isAllZero(){
        return this.a == 0 && this.b == 0;
    }

    public Complex conjugate() {
        return new Complex(this.a, -this.b);
    }

    /**
     * This one is a tricky one, if the number is only
     * real, print the real number (variable a), if the number is
     * only imaginary print variable b and i (bi), is tested for the
     * cases where:
     * Both 'a' and 'b' are zero
     * 'a' or 'b' are zero
     * 'b' is one
     * @return String
     */
    @Override
    public String toString() {
        if (a == 0 && b == 0) {
            return "0";
        } else {
            if (b == 0) {
                return "" + a;
            } else if(a == 0){
                if (Math.abs(b) == 1) {
                    return b > 0 ? "i" : "-i";
                }
                return  b + "i";
            } else {
                return String.format("%f%+fi",a,b);
            }
        }
    }
}