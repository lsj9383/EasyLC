package lambda;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Interpreter {
	public static void Loop(){
		
		
		//1).��ȡ�ļ�
		LoadDefinition("./src/Main.lc");
		System.out.println("======================================");
		
		//2).����̨ѭ��
		while(true){
			Scanner stdin = new Scanner(System.in);
			String exp = null;
			//1).����Ϸ��ִ�
			do{
				System.out.print("Eval Input : ");
				exp = stdin.nextLine();
			}while(exp.equals(""));
			
			//2.)ִ��
			Analysis.Execute(exp);
		}
	}
	
	public static void LoadDefinition(String fileName){		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
			String exp = null;
			
			do {
				exp = br.readLine();
				if(exp==null){
					break;
				}
				System.out.println(exp);
				Expression EXP = Analysis.Execute(exp);
				if(EXP != null){
					if(EXP.Eval() != null){
						System.out.println(EXP.Eval().toString());	
					}
				}
			} while (true);
			
		} catch (Exception e) {
			System.out.println(fileName + "catch wrong!");
		}
		return ;
	}
}
