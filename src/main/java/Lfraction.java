import java.util.*;

/** This class represents fractions of form n/d where n and d are long integer
 * numbers. Basic operations and arithmetics for fractions are provided.
 */
public class Lfraction implements Comparable<Lfraction> {

   /** Main method. Different tests. */
   public static void main (String[] param) {
      Lfraction f1 = new Lfraction (2, 5);
      Lfraction f2 = new Lfraction (4, 15);
	      Lfraction sum = f1.plus(f2);
	   System.out.println(f1.getNumerator() + " " + f1.getDenominator());
	   System.out.println(f2.getNumerator() + " " + f2.getDenominator());
	   System.out.println(sum.getNumerator() + " " + sum.getDenominator());
   }

   // TODO!!! instance variables here
   private long numerator;
   private long denominator;

   /** Constructor.
    * @param a numerator
    * @param b denominator > 0
    */
   public Lfraction (long a, long b) {
      this.numerator = a;
      this.denominator = b;
      if(a == 0){
	this.numerator = 0;
	this.denominator = 1;
      }
      if (b == 0){
	throw new IllegalArgumentException("No fraction with denominator 0 exists!");
      }
      if(this.denominator < 0L) {
	      this.numerator = -this.numerator;
	      this.denominator = -this.denominator;
      }
     this.reduce(); 
   }

   /** Public method to access the numerator field.
    * @return numerator
    */
   public long getNumerator() {
      return this.numerator; // TODO!!!
   }

   /** Public method to access the denominator field.
    * @return denominator
    */
   public long getDenominator() {
      return this.denominator; // TODO!!!
   }

   /** Conversion to string.
    * @return string representation of the fraction
    */
   @Override
   public String toString() {
      return null; // TODO!!!
   }

   /** Equality test.
    * @param m second fraction
    * @return true if fractions this and m are equal
    */
   @Override
   public boolean equals (Object m) {
   if (this.numerator == ((Lfraction)m).numerator && this.denominator == ((Lfraction)m).denominator)return true;
      return false; 
   }

   /** Hashcode has to be the same for equal fractions and in general, different
    * for different fractions.
    * @return hashcode
    */
   @Override
   public int hashCode() {
      return 0; // TODO!!!
   }

   /** Sum of fractions.
    * @param m second addend
    * @return this+m
    */
   public Lfraction plus (Lfraction m) {
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
      if(this.numerator == 0 || m.numerator == 0) return new Lfraction(0, 1);
      return new Lfraction(this.numerator * m.numerator, this.denominator * m.denominator); // TODO!!!
   }

   /** Inverse of the fraction. n/d becomes d/n.
    * @return inverse of this fraction: 1/this
    */
   public Lfraction inverse() {
	if(this.denominator == 0) throw new RuntimeException("Error: Zero in denominator, no inverse exists!");
      return new Lfraction(denominator, numerator); // TODO!!!
   }

   /** Opposite of the fraction. n/d becomes -n/d.
    * @return opposite of this fraction: -this
    */
   public Lfraction opposite() {
      return new Lfraction(-numerator, denominator); // TODO!!!
   }

   /** Difference of fractions.
    * @param m subtrahend
    * @return this-m
    */
   public Lfraction minus (Lfraction m) {
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
	try{
	      return super.clone(); 
        }catch (CloneNotSupportedException e){
	      throw new AssertionError();
        }
   }

   /** Integer part of the (improper) fraction.
    * @return integer part of this fraction
    */
   public long integerPart() {
	   if( numerator >= denominator){
		return (numerator - numerator%denominator)/denominator;
	   }
	   return 0L;
   }

   /** Extract fraction part of the (improper) fraction
    * (a proper fraction without the integer part).
    * @return fraction part of this fraction
    */
   public Lfraction fractionPart() {
      return null; // TODO!!!
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
   public static Lfraction toLfraction (double f, long d) {
      return null; // TODO!!!
   }

   /** Conversion from string to the fraction. Accepts strings of form
    * that is defined by the toString method.
    * @param s string form (as produced by toString) of the fraction
    * @return fraction represented by s
    */
   public static Lfraction valueOf (String s) {
      return null; // TODO!!!
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
   public static long lcm(long a, long b) {
        return (a / gcd(a, b)) * b;
   }

  private void reduce(){
	long gcd = gcd(this.numerator, this.denominator);
	this.numerator /= gcd;
	this.denominator /= gcd;	
  }

}

