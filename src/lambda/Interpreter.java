package lambda;

public class Interpreter {
	public static void Test(){
		
		Analysis.Execute("(lambda p (lambda x x))");
		
		Analysis.Execute("SLIDE		= (lambda p ((PAIR (RIGHT p)) (INC (RIGHT p))))");
		Analysis.Execute("ZERO		= (lambda p (lambda x x))");
		Analysis.Execute("ONE		= (lambda p (lambda x (p x)))");
		Analysis.Execute("TWO		= (lambda p (lambda x (p (p x))))");
		Analysis.Execute("THREE		= (lambda p (lambda x (p (p (p x)))))");
		Analysis.Execute("TRUE		= (lambda x (lambda y x))");
		Analysis.Execute("FALSE		= (lambda x (lambda y y))");
		
		
		Analysis.Execute("INC		= (lambda n (lambda p (lambda x (p ((n p) x)))))");
		Analysis.Execute("DEC		= (lambda n (LEFT ((n SLIDE) ((PAIR ZERO) ZERO))))");
		Analysis.Execute("ADD		= (lambda m (lambda n ((n INC) m)))");
		Analysis.Execute("SUB		= (lambda m (lambda n ((n DEC) m)))");
		Analysis.Execute("FIVE		= (INC (INC THREE))");
		Analysis.Execute("FOUR		= (DEC FIVE)");
		Analysis.Execute("NINE		= ((ADD FIVE) FOUR)");
		Analysis.Execute("SEVEN		= ((SUB NINE) TWO)");
		
		Analysis.Execute("PAIR		= (lambda x (lambda y (lambda f ((f x) y))))");
		Analysis.Execute("LEFT		= (lambda p (p (lambda x (lambda y x))))");
		Analysis.Execute("RIGHT		= (lambda p (p (lambda x (lambda y y))))");
		
		System.out.println(new Variable("ZERO").Eval().toString());
		System.out.println(new Variable("ONE").Eval().toString());
		System.out.println(new Variable("TWO").Eval().toString());
		System.out.println(new Variable("THREE").Eval().toString());
		System.out.println(new Variable("FOUR").Eval().toString());
		
		Analysis.Execute("MY_PAIR  = ((PAIR ONE) TWO)");
		Analysis.Execute("MY_SLIDE = (SLIDE MY_PAIR)");
		System.out.println(Analysis.Integer( new Call(new Variable("LEFT"), new Variable("MY_SLIDE"))) );
		System.out.println(Analysis.Integer( new Variable("FOUR")) );
		System.out.println(Analysis.Integer( new Variable("FIVE")) );
		System.out.println(Analysis.Integer( new Variable("NINE")) );
		System.out.println(Analysis.Integer( new Variable("SEVEN")) );
	}
}
