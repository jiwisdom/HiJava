import java.util.ArrayList;
import java.util.Scanner;

public class Game {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		String str = "Oasis";
		System.out.println("���� ���߱� ����");
		ArrayList<String> wrong = new ArrayList<>();

		char[] cArray = str.toCharArray();
		// �ݴ�� ���ڸ� ���ڿ��� �ٲٴ� ��: String str2= String.valueOf(cArray);

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
			System.out.println("���ĺ��� �Է��ϼ���");
			String alpha = sc.next();
			

			for (int i = 0; i < cArray.length; i++) {

				if (str.toLowerCase().charAt(i) == alpha.charAt(0)||str.toUpperCase().charAt(i) == alpha.charAt(0)) {
					
					cArray[i] = str.charAt(i);
				}
				
					// System.out.print(cArray[i]); ���� �̹� ����
			}
						
			for(int i=0;i<cArray.length;i++) {
				 if (str.toLowerCase().contains(alpha)||str.toUpperCase().contains(alpha)) {
					 
				 }else {
					System.out.println("���忡 ���� ���ĺ��Դϴ�~");
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
			} // for��
			

			if (flag == 0) {
				System.out.println("�����Դϴ�");
				System.out.println(str);
				break;
			}
			

		} // while��

	}

}

