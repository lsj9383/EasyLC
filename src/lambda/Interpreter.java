package lambda;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import Programmer.Main;

public class Interpreter {
	private static String LogFile;
	private static String MainFile;
	static PrintStream LogOut;
	static{
		Properties properties = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("./conf/lc.conf");
			properties.load(fis);
			MainFile = properties.getProperty("MAIN");
			LogFile = properties.getProperty("LOG");
			LogOut = new PrintStream(LogFile);
			
		} 
		catch (IOException e) {
			System.out.println("initial error.");
		}
		finally {
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//主循环
	public static void Loop(){
		LoadModule(MainFile);
		System.out.println("===========================================================");
		
		while(true){
			Scanner stdin = new Scanner(System.in);
			System.out.print("Eval Input : ");
			ShowResult(Execute(stdin.nextLine()));
		}
	}
	
	//加载某个文件的内容
	public static void LoadModule(String fileName){		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(fileName)));
			String exp =  null;
			
			while((exp = br.readLine()) != null) {
				System.out.println(exp);
				LogOut.println(exp);
				ShowResult(Execute(exp));
			}
			
		} catch (Exception e) {System.out.println(fileName + "catch wrong!");}finally{
			if(br==null){
				try {br.close();} catch (IOException e) {}
			}
		}
	}
	
	private static void ShowResult(StmtResult stmtResult){
		String output = null;
		
		switch (stmtResult.Type) {
		case RIGHT: output = "->OK"; break;
		case WRONG: output = "->ERROR"; break;
		case AST:	output = "->"+stmtResult.Exp.Eval().toString(); break;
		case SKIP:	output = null; break;
		}
		
		if(output != null){
			System.out.println(output);	
			LogOut.println(output);
		}
	}
	
	private static StmtResult Execute(String origin){
		switch(Statement.Type(origin)){
		case ENV:		return EvalEnv(origin);
		case IMPORT:	return EvalImport(origin);
		case INT:		return EvalInt(origin);
		case REDUCE:	return EvalReduce(origin);
		case LOG:		return EvalTmp(origin);
		case BLIND:		return EvalBlind(origin);
		case EXPRESSION:return EvalExpression(origin);
		default: 		return new StmtResult(StmtResult.TYPE.SKIP);
		}
	}
	
	private static StmtResult EvalImport(String origin){
		String[] parts = origin.split(" ");
		Interpreter.LoadModule(parts[1].substring(1, parts[1].length()-1));
		return new StmtResult(StmtResult.TYPE.RIGHT);
	}
	
	private static StmtResult EvalEnv(String origin){
		System.out.println(Environment.EnvInstance());
		return new StmtResult(StmtResult.TYPE.RIGHT);
	}
	
	private static StmtResult EvalInt(String origin){
		ArrayList<String> parts = Analysis.Split(origin);
		Expression exp = Expression.AST(parts.get(1));
		Expression cal = new Call(new Call(exp, new INC()), new Number(0));
		Expression result =cal.Eval(); 
		System.out.println("->"+((Number)result).number);
		return new StmtResult(StmtResult.TYPE.RIGHT);
	}
	
	private static StmtResult EvalReduce(String origin){
		ArrayList<String> parts = Analysis.Split(origin);
		Expression exp = Expression.AST(parts.get(1));
		while(exp != null){
			String output = "->"+exp;
			System.out.println(output);
			LogOut.println(output);
			exp = exp.Reduce();			
		}
		return new StmtResult(StmtResult.TYPE.RIGHT);
	}
	
	private static StmtResult EvalTmp(String origin){
		LogOut.flush();
		return new StmtResult(StmtResult.TYPE.RIGHT);
	}
	
	private static StmtResult EvalBlind(String origin){
		ArrayList<String> parts = Analysis.Split(origin);
		Expression.env.Blind(parts.get(0), Expression.AST(parts.get(2)));
		return new StmtResult(StmtResult.TYPE.RIGHT);
	}
	
	private static StmtResult EvalExpression(String origin){
		return new StmtResult(Expression.AST(origin).Eval());
	}
}
