package lambda;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Interpreter {
	//��ѭ��
	public static void Loop(){
		//1).��ȡ�ļ�
		LoadModule("./src/Main.lc");
		System.out.println("===========================================================");
		
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
			ShowExpression(Analysis.Execute(exp));
		}
	}
	
	//����ĳ���ļ�������
	public static void LoadModule(String fileName){		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
			
			String exp =  br.readLine();
			while(exp != null) {
				if(exp.replace(" ", "").replace("\t", "").replaceAll("\n", "").length()==0){
					exp = br.readLine();
					continue;
				}
				System.out.println(exp);
				ShowExpression(Analysis.Execute(exp));
				exp = br.readLine();
			}
		} catch (Exception e) {System.out.println(fileName + "catch wrong!");}
		return ;
	}
	
	private static void ShowExpression(Expression EXP){
		if(EXP != null){
			if(EXP.Eval() != null){
				System.out.println("->"+EXP.Eval().toString());	
			}
		}
	}
}
