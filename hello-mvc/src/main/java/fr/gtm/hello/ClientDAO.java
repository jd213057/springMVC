package fr.gtm.hello;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClientDAO extends JpaRepository<User,Long>{

//	Optional<User> findByNomAndPassword(String nom, String password);
	Optional<User> findByNom(String nom);
	
	@Query(value = "SELECT digest from users WHERE user = ?1", nativeQuery = true)
	String getValues(String nom);
	
	@Transactional
	@Modifying
	@Query(value ="Update users u SET u.password=?1, u.digest=?2 WHERE u.id=?3", nativeQuery=true)
	void updateDigest(String password, String digest, long id);
	
}
