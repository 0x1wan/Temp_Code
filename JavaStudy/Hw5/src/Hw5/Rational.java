package Hw5;

public class Rational {
	private int nume;
	private int demo;
	
	public Rational() {
		this.nume = 0;
		this.demo = 1;
	}
	public Rational(int a, int b) {
		int x = gcd(a,b);
		
		this.nume =a/x;
		this.demo =b/x;
	}
	
	private int gcd(int a, int b) {
		int tempA = Math.abs(a);
        int tempB = Math.abs(b);
        
        while (tempB != 0) {
            int remainder = tempA % tempB;
            tempA = tempB;
            tempB = remainder;
        }
        return tempA;
	}
	
		public static Rational add(Rational r1, Rational r2) {
			int newNume = (r1.nume * r2.demo) + (r2.nume * r1.demo);
			int newDemo = r1.demo * r2.demo;
			return new Rational(newNume, newDemo); 
		}
		
		public static Rational subtract(Rational r1, Rational r2) {
			int newNume = (r1.nume * r2.demo) - (r2.nume * r1.demo);
			int newDemo = r1.demo * r2.demo;
			return new Rational(newNume, newDemo);
		}
		
		public static Rational multiply(Rational r1, Rational r2) {
			int newNume = r1.nume * r2.nume;
			int newDemo = r1.demo * r2.demo;
			return new Rational(newNume, newDemo);
		}
		

		public static Rational divide(Rational r1, Rational r2) {
			int newNume = r1.nume * r2.demo;
			int newDemo = r1.demo * r2.nume;
			return new Rational(newNume, newDemo);
		}
		

		public String toFractionString() {
			return this.nume + "/" + this.demo;
		}
		

		public String toFloatString(int precision) {
			double value = (double) this.nume / this.demo;
			
			// 소숫점 자리 제어하기.
			return String.format("%." + precision + "f", value);
		}
	
	
}
