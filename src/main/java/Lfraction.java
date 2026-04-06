import java.util.*;

/** This class represents fractions of form n/d where n and d are long integer
 * numbers. Basic operations and arithmetics for fractions are provided.
 */
public class Lfraction implements Comparable<Lfraction> {

	/** Main method. Different tests. */
	public static void main (String[] param) {
		Lfraction f2 = new Lfraction (13, 16);
		Lfraction f3 = Lfraction.valueOf("1/2/");
		System.out.println(f3);
	}

	private long numerator;
	private long denominator;

	/** Constructor.
	 * @param a numerator
	 * @param b denominator > 0
	 */
	public Lfraction (long a, long b) {
		this.numerator = a;
		this.denominator = b;

		if (b == 0){
			throw new IllegalArgumentException("No fraction with denominator 0 exists!");
		}
		else if(a == 0){
			this.numerator = 0;
			this.denominator = 1;
		}
		else if(this.denominator < 0L) {
			this.numerator = -this.numerator;
			this.denominator = -this.denominator;
		}
		this.reduce(); 
	}

	/** Public method to access the numerator field.
	 * @return numerator
	 */
	public long getNumerator() {
		return this.numerator; 
	}

	/** Public method to access the denominator field.
	 * @return denominator
	 */
	public long getDenominator() {
		return this.denominator; 
	}

	/** Conversion to string.
	 * @return string representation of the fraction
	 */
	@Override
	public String toString() {
		String ret = this.numerator  +  " / " + this.denominator;
		return ret;
	}

	/** Equality test.
	 * @param m second fraction
	 * @return true if fractions this and m are equal
	 */
	@Override
	public boolean equals (Object m) {
		if (this == m) return true;
		if(m == null) return false;
		if(!(m instanceof Lfraction)) return false;
		Lfraction other = (Lfraction)m;
		return this.numerator == other.numerator && this.denominator == other.denominator;

	}

	/** Hashcode has to be the same for equal fractions and in general, different
	 * for different fractions.
	 * @return hashcode
	 */
	@Override
	public int hashCode() {
		int h = (int)(this.numerator ^ (this.numerator >>> 32)); // combine top and bottom 32 bits of numerator
		h = (h << 5) - h + (int)(this.denominator ^ (this.denominator >>> 32)); // use classic Java implementation of hashCode using polynomial & combine top and bottom 32 bits of denominator
		return h;
	}

	/** Sum of fractions.
	 * @param m second addend
	 * @return this+m
	 */
	public Lfraction plus (Lfraction m) {
		if(m == null) throw new NullPointerException("Argument cannot be null!");
		long gcd = gcd(this.denominator, m.denominator);
		long mTerm = this.denominator / gcd;
		long thisTerm =	m.denominator / gcd; 
		return new Lfraction(this.numerator * thisTerm + m.numerator * mTerm, this.denominator * thisTerm);
	}

	/** Multiplication of fractions.
	 * @param m second factor
	 * @return this*m
	 */
	public Lfraction times (Lfraction m){
		if(m == null) throw new NullPointerException("Argument cannot be null!");
		return new Lfraction(this.numerator * m.numerator, this.denominator * m.denominator);
	}

	/** Inverse of the fraction. n/d becomes d/n.
	 * @return inverse of this fraction: 1/this
	 */
	public Lfraction inverse() {
		return new Lfraction(denominator, numerator);
	}

	/** Opposite of the fraction. n/d becomes -n/d.
	 * @return opposite of this fraction: -this
	 */
	public Lfraction opposite() {
		return new Lfraction(-numerator, denominator);
	}

	/** Difference of fractions.
	 * @param m subtrahend
	 * @return this-m
	 */
	public Lfraction minus (Lfraction m) {
		if(m == null) throw new NullPointerException("Argument cannot be null!");
		long gcd = gcd(this.denominator, m.denominator);
		long mTerm = this.denominator / gcd;
		long thisTerm =	m.denominator / gcd; 
		return new Lfraction(this.numerator * thisTerm - m.numerator * mTerm, this.denominator * thisTerm);

	}

	/** Quotient of fractions.
	 * @param m divisor
	 * @return this/m
	 */
	public Lfraction divideBy (Lfraction m) {
		if(m == null) throw new NullPointerException("Argument cannot be null!");
		if(m.numerator == 0) throw new RuntimeException("Cannot divide by zero");
		Lfraction ret = new Lfraction(this.numerator * m.denominator, this.denominator * m.numerator);  
		ret.reduce();
		return ret;
	}

	/** Comparision of fractions.
	 * @param m second fraction
	 * @return -1 if this < m; 0 if this==m; 1 if this > m
	 */
	@Override
	public int compareTo (Lfraction m) {

		if(m == null) throw new NullPointerException("Argument cannot be null!");
		long gcd = gcd(this.denominator, m.denominator);
		long mTerm = this.denominator / gcd;
		long thisTerm =	m.denominator / gcd; 

		long thisNum = thisTerm * this.numerator;
		long mNum = mTerm * m.numerator;

		if(thisNum < mNum)return -1;
		else if(thisNum == mNum)return 0;
		else return 1;
	}

	/** Clone of the fraction.
	 * @return new fraction equal to this
	 */
	@Override
	public Object clone() throws CloneNotSupportedException{
		Lfraction ret = new Lfraction(numerator, denominator);
		return ret;
	}

	/** Integer part of the (improper) fraction.
	 * @return integer part of this fraction
	 */
	public long integerPart() {
		if( numerator >= denominator){
			return (numerator - numerator%denominator)/denominator;
		}else if ( -numerator >= denominator){
			return -(-numerator - (-numerator)%denominator)/denominator;
		}
		return 0L;
	}

	/** Extract fraction part of the (improper) fraction
	 * (a proper fraction without the integer part).
	 * @return fraction part of this fraction
	 */
	public Lfraction fractionPart() {
		return this.minus(new Lfraction(this.integerPart(), 1));
	}

	/** Approximate value of the fraction.
	 * @return real value of this fraction
	 */
	public double toDouble() {
		return (double)this.numerator/(double)this.denominator;
	}

	/** Double value f presented as a fraction with denominator d > 0.
	 * @param f real number
	 * @param d positive denominator for the result
	 * @return f as an approximate fraction of form n/d
	 */
	public static Lfraction toLfraction(double f, long d) {

		long n = Math.round(f * d);

		return new Lfraction(n, d); 
	}

	/** Conversion from string to the fraction. Accepts strings of form
	 * that is defined by the toString method.
	 * @param s string form (as produced by toString) of the fraction
	 * @return fraction represented by s
	 */
	public static Lfraction valueOf(String s) {
		if (s == null) throw new NullPointerException("Argument cannot be null!");

		s = s.trim(); 

		String[] parts = s.split("/", -1);
		if (parts.length != 2) {
			throw new IllegalArgumentException("Invalid fraction format. Expected 'numerator / denominator'");
		}

		try {
			long numerator = Long.parseLong(parts[0].trim());
			long denominator = Long.parseLong(parts[1].trim());
			return new Lfraction(numerator, denominator);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Numerator or denominator is not a valid long integer", e);
		}
	}

	private static long gcd(long a, long b){
		a = Math.abs(a);
		b = Math.abs(b);
		while (b != 0){
			long temp = b;
			b = a % b;
			a = temp;
		}	
		return a;
	}

	private void reduce(){
		long gcd = gcd(this.numerator, this.denominator);
		this.numerator /= gcd;
		this.denominator /= gcd;	
	}
}

