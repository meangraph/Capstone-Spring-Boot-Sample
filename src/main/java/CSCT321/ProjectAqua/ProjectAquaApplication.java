package CSCT321.ProjectAqua;

import CSCT321.ProjectAqua.Model.ScannableItem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.Scanner;

@SpringBootApplication(
		exclude = { SecurityAutoConfiguration.class,  ManagementWebSecurityAutoConfiguration.class })
public class ProjectAquaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectAquaApplication.class, args);

		Scanner scan = new Scanner(System.in);

		while(true) {

			System.out.println("Do you want to scan stuff? Y/N");

			String input = scan.nextLine();
			if (input.equalsIgnoreCase("y")) {

				ScannableItem item1 = new ScannableItem("Depressed Finelinker Pens","Dark Colours","9341109383986");
				ScannableItem item2 = new ScannableItem("Happy Finelinker Pens","Bright Colours","9341109383993");
				ScannableItem item3 = new ScannableItem("Chocolate","Orange Chocolate Box","8033286329802");

				System.out.println("Awaiting Scanning Fam");

				input = scan.nextLine();

				switch (input) {
					case "9341109383986" -> System.out.println("Item Name : " + item1.getItemName() + "\n"
							+ "Description :  " + item1.getDescription());
					case "9341109383993" -> System.out.println("Item Name : " + item2.getItemName() + "\n"
							+ "Description : " + item2.getDescription());
					case "8033286329802" -> System.out.println("Item Name : " + item3.getItemName() + "\n"
							+ "Description : " + item3.getDescription());
				}

			} else if (input.equalsIgnoreCase("n")) {
				break;
			}
		}
		scan.close();


	}

}
