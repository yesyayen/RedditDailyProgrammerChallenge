import java.text.DecimalFormat;
import java.util.Scanner; // This will import just the Scanner class.

public class TriangleSolver {


	static double a,b,c,A,B,C;
	static int angle,side;
	Scanner userInputScanner = new Scanner(System.in);
	public static void main(String[] args) 
	{   
		int inputCount;
		TriangleSolver TT=new TriangleSolver();

		inputCount=TT.userInput();
		if(angle+side<3)
		{
			System.out.println("Not enough info to continue calculation.");
			System.exit(0);
		}
		if(TT.isRightAngle() && side==2)
		{
			TT.pythagorasTheorm();
		}
		TT.findAngle();
		System.out.println("Input variable - A B C  a b c - "+A+" "+B+" "+C+" "+a+" "+b+" "+c);
		System.out.println("a = " + a+"\n"+"b = " + b+"\n"+"c = " + c+"\n"+"A = " + A+"\n"+"B = " + B+"\n"+"C = " + C);
	}

	Boolean isRightAngle()
	{
		if(A==90 || B==90 || C==90)
		{
			return true;
		}
		return false;
	}

	void thirdAngle()
	{
		if(A==0)
			A=twoDecimalPlace(180-B-C);
		else if(B==0)
			B=twoDecimalPlace(180-A-C);
		else if(C==0)
			C=twoDecimalPlace(180-A-B);
		angle++;
	}

	String triangleType()
	{
		if(angle==3 && side==0)
		{
			return "AAA";
		}
		else if(angle==0 && side==3)
		{
			return "SSS";
		}
		else if((A!=0 && c!=0 && B!=0) || (B!=0 && a!=0 && C!=0) || (C!=0 && b!=0 && A!=0))
		{
			return "ASA";
		}
		else if((a!=0 && B!=0 && c!=0) || (c!=0 && A!=0 && b!=0) || (b!=0 && C!=0 && a!=0))
		{
			return "SAS";
		}
		else if(angle==2 && side==1)
		{
			return "AAS";
		}
		else if(side==2 && angle==1)
		{
			return "SSA";
		}
		return "";
	}

	int userInput()
	{
		int inputCount;
		inputCount=userInputScanner.nextInt();

		for(int i=0;i<inputCount;i++)
		{
			assignVar(userInputScanner.next());
		}
		return inputCount;
	}

	void pythagorasTheorm()
	{
		if(a!=0 && b!=0 && c==0)
		{
			c=Math.sqrt((a*a)+(b*b));
		}
		else if(a==0 && b!=0 && c!=0)
		{
			a=Math.sqrt((c*c)-(b*b));
		}
		else if(a!=0 && b==0 && c!=0)
		{
			b=Math.sqrt((c*c)-(a*a));
		}
		else
		{
			System.out.println("Not enough info to use pythagorasTheorm");
			return;
		}
		side++;
	}

	void findAngle()
	{
		String triType="";
		triType=triangleType();

		switch(triType)
		{
			case "SAS":
				System.out.println("here");
				if(a!=0 && B!=0 && c!=0)
				{
					b=lawOfCosineGetSide(a,c,B);
				}
				else if(c!=0 && A!=0 && b!=0)
				{
					a=lawOfCosineGetSide(b,c,A);
				}
				else if(b!=0 && C!=0 && a!=0)
				{
					c=lawOfCosineGetSide(b,a,C);
				}
				if(A==0)
					A=lawOfCosineGetAngle(b, c, a);
				else if(B==0)
					B=lawOfCosineGetAngle(a, c, b);
				else if(C==0)
					C=lawOfCosineGetAngle(b, a, c);

				thirdAngle();
				break;

			case "SSS": 
				A=lawOfCosineGetAngle(b, c, a);
				B=lawOfCosineGetAngle(a, c, b);
				thirdAngle();
				break;

			case "ASA":
			case "AAS":
				thirdAngle();
				if(a==0)
				{
					if(c!=0)
						a=lawOfSines(c, A, C);
					else if(b!=0)
						a=lawOfSines(b, A, B);
				}
				if(b==0)
				{
					if(c!=0)
						b=lawOfSines(c, B, C);
					else if(a!=0)
						b=lawOfSines(a, B, A);
				}
				if(c==0)
				{
					if(b!=0)
						c=lawOfSines(b, C, B);
					else if(a!=0)
						c=lawOfSines(a, C, A);
				}
				break;

			case "SSA": //later
				break;
		}

		System.out.println(new DecimalFormat("#.##").format((Math.toDegrees(Math.acos(0.6)))));
	}

	Double twoDecimalPlace(Double val)
	{
		return Double.parseDouble(new DecimalFormat("#.##").format(val));
	}

	Double lawOfSines(Double side1,Double angle1,Double angle2)
	{
		side++;
		return twoDecimalPlace((side1*(Math.sin(Math.toRadians(angle1))))/(Math.sin(Math.toRadians(angle2))));
	}

	Double lawOfCosineGetAngle(Double side1,Double side2,Double side3)
	{
		angle++;
		return twoDecimalPlace((Math.toDegrees(Math.acos(((side1*side1)+(side2*side2)-(side3*side3))/(2*side1*side2)))));
	}

	Double lawOfCosineGetSide(Double side1,Double side2,Double angle1)
	{
		side++;
		return twoDecimalPlace(Math.sqrt((side1*side1)+(side2*side2)-(2*side1*side2*(Math.cos(Math.toRadians(angle1))))));
	}

	void assignVar(String input)
	{
		String var=input.split("=")[0];
		switch(var)
		{
		case "A":
			A=Double.parseDouble(input.split("=")[1]);
			angle++;
			break;
		case "B":
			B=Double.parseDouble(input.split("=")[1]);
			angle++;
			break;
		case "C":
			C=Double.parseDouble(input.split("=")[1]);
			angle++;
			break;
		case "a":
			a=Double.parseDouble(input.split("=")[1]);
			side++;
			break;
		case "b":
			b=Double.parseDouble(input.split("=")[1]);
			side++;
			break;
		case "c":
			c=Double.parseDouble(input.split("=")[1]);
			side++;
			break;
		}
	}
}