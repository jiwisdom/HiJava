import java.util.ArrayList;
import java.util.Scanner;

public class Game {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		String str = "Oasis";
		System.out.println("문장 맞추기 게임");
		ArrayList<String> wrong = new ArrayList<>();

		char[] cArray = str.toCharArray();
		// 반대로 문자를 문자열로 바꾸는 법: String str2= String.valueOf(cArray);

		for (int i = 0; i < cArray.length; i++) {

			if (str.charAt(i) != ' ') {
				cArray[i] = '*';
			}
		}
		while (true) {

			for (int i = 0; i < cArray.length; i++) {
				System.out.print(cArray[i]);
				
			}
		

			System.out.println(" ");
			System.out.println("알파벳을 입력하세요");
			String alpha = sc.next();
			

			for (int i = 0; i < cArray.length; i++) {

				if (str.toLowerCase().charAt(i) == alpha.charAt(0)||str.toUpperCase().charAt(i) == alpha.charAt(0)) {
					
					cArray[i] = str.charAt(i);
				}
				
					// System.out.print(cArray[i]); 위에 이미 있음
			}
						
			for(int i=0;i<cArray.length;i++) {
				 if (str.toLowerCase().contains(alpha)||str.toUpperCase().contains(alpha)) {
					 
				 }else {
					System.out.println("문장에 없는 알파벳입니다~");
					wrong.add(alpha);
					System.out.println(wrong);
					break;
				}
			}
				
			int flag = 0;
			for (int i = 0; i < cArray.length; i++) {

				if (cArray[i] == '*') {
					flag = 1;
					break;

				}
			} // for끝
			

			if (flag == 0) {
				System.out.println("정답입니다");
				System.out.println(str);
				break;
			}
			

		} // while끝

	}

}

