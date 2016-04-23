package util;

/**
 * 
 * @author Peter Cappello
 */
public class Complex {
    private double real;
    private double imag;

    public Complex( double real, double imag ) {
        this.real = real;
        this.imag = imag;
    }
    
    public Complex( Complex c ) {
        this.real = c.real;
        this.imag = c.imag;
    }

    public Complex add( Complex operand ) {
        real += operand.real;
        imag += operand.imag;
        return this;
    }

    public Complex multiply( Complex operand ) {
        double temp = real * operand.real - imag * operand.imag;
        imag = imag * operand.real + real * operand.imag;
        real = temp;
        return this;
    }

    public double size() { return Math.sqrt( sizeSquared() ); }
    
    public double sizeSquared() { return real * real + imag * imag; }
    
    public Complex square() { 
        double temp = real * real - imag * imag;
        imag = 2 * real * imag;
        real = temp;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( " (").append( real ).append( ',' ).append( imag ).append( ") ");
        return new String( stringBuilder );
    }
}
