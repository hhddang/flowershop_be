package springboot.flowershop_be;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlowershopBeApplication {

	public static void main(String[] args) {
	SpringApplication.run(FlowershopBeApplication.class, args);
	}

	// public static void main(String[] args) {
	// 	String a = "12345";
	// 	String hashedA = BCrypt.hashpw(a, BCrypt.gensalt(10));
	// 	System.out.println(
	// 			BCrypt.checkpw(a, hashedA));

	// }

}
