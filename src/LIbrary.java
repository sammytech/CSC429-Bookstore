import java.util.Scanner;

public class LIbrary {

	public static void main(String[] args) {
		System.out.println("What do you want to do? (Type Number) \n"
				+ "1. New Book, 2. New Patron, 3. List Books by Title, \n"
				+ "4. List Books by Year, 5. List Patrons by date, 6. List Patrons by zip."); 
		Scanner sc = new Scanner(System.in);
		int i = sc.nextInt();
	
		switch(i){
			case 1: 
				insertBook();
			case 2:
				insertPatron();
			case 3:
				sortTitle();
			case 4:
				sortYear();
			case 5:
				sortDate();
			case 6:
				sortZip();
		}
			

	}
	
	private static void insertBook(){
		System.out.print("Enter Author: ");
		Scanner sc = new Scanner(System.in);
		String author = sc.next();
		System.out.print("Enter Title: ");
		String title = sc.next();
		System.out.print("Enter Published Year: ");
		String pub = sc.next();
		System.out.print("Enter Status: ");
		String status = sc.next();
		
	}
	
	private static void insertPatron(){
		System.out.print("Enter Name: ");
		Scanner sc = new Scanner(System.in);
		String name = sc.next();
		System.out.print("Enter Address: ");
		String add = sc.next();
		System.out.print("Enter City: ");
		String city = sc.next();
		System.out.print("Enter State Code: ");
		String state = sc.next();
		System.out.print("Enter Zip: ");
		String zip = sc.next();
		System.out.print("Enter E-mail: ");
		String email = sc.next();
		System.out.print("Enter Date of Birth: ");
		String dob = sc.next();
		System.out.print("Enter Status: ");
		String status = sc.next();
		
	}
	
	private static void sortTitle(){
		System.out.print("Enter Title: ");
		Scanner sc = new Scanner(System.in);
		String title = sc.next();
	}
	
	private static void sortYear(){
		System.out.print("Enter Year: ");
		Scanner sc = new Scanner(System.in);
		String year = sc.next();
	}
	
	private static void sortDate(){
		System.out.print("Enter Date: ");
		Scanner sc = new Scanner(System.in);
		String date = sc.next();
	}
	
	private static void sortZip(){
		System.out.print("Enter Name: ");
		Scanner sc = new Scanner(System.in);
		String zip = sc.next();
	}

}
