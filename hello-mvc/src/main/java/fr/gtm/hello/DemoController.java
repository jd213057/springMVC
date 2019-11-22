package fr.gtm.hello;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DemoController {
	@Autowired ClientDAO dao;
	
	@GetMapping(path="/")
	public String hello(@RequestParam (name="name", defaultValue="John", required=false) String name, Model model) {
		String message ="Bon anniversaire " +name;
		model.addAttribute("message",message);
		return "home";
	}
	
	@GetMapping("/signing")
	public String signing(Model model) {
		User user = new User();
		model.addAttribute("user",user);
		return "signing";
	}
	
//	@PostMapping("/connexion")
//	public String connexion(User user, Model model) {
//		List<User> users = dao.findAll();
//		for (User u : users) {
//			if (u.getNom().equals(user.getNom())&& u.getPassword().equals(user.getPassword())) {
//				model.addAttribute("user", user);
//				return "ok";
//			}
//		}
//		return "notok";
//	}
	
	@PostMapping("/connexion")
	public String connexionTo(@RequestParam(name="nom")String nom, @RequestParam (name="password") String password, Model model) throws NoSuchAlgorithmException {
		Optional<User> user = dao.findByNom(nom);
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		if(user.isPresent()) {
		byte[] hash = md.digest(password.getBytes());
		BigInteger number = new BigInteger(1,hash);
		StringBuilder hexString = new StringBuilder(number.toString(16));
		while (hexString.length() <64) {
			hexString.insert(0, '0');
		}
		String pwdFin = hexString.toString();

		String hashCheck= dao.getValues(nom);
		if (hashCheck.equals(pwdFin)) {
			model.addAttribute("user",user.get());
			return "ok";
		}
	   }
	   model.addAttribute("user",user);
	   return "signing";
	}
	
	@PostMapping("/signup")
	public String createUser() {
		return "signup";
	}
	@PostMapping("/signup2")
	public String createUser2(@RequestParam(name="nom")String nom,@RequestParam (name="password") String password) throws NoSuchAlgorithmException {
		Optional<User> user = dao.findByNom(nom);
		if(!user.isPresent()) {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(password.getBytes());
			BigInteger number = new BigInteger(1,hash);
			StringBuilder hexString = new StringBuilder(number.toString(16));
			while (hexString.length() <64) {
				hexString.insert(0, '0');
			}
			String pwdFin = hexString.toString();
        User userCreated = new User(nom);
        dao.save(userCreated);
        dao.updateDigest(password, pwdFin, userCreated.getId());
		return "signing";
	}
		return "signing";
	}

	
}
