package br.com.fco_romario.repositories;

import br.com.fco_romario.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.userName = :userName") //JPQL
    User findByUsername(@Param("userName") String userName); //Neste caso o proprio JpaRepository disponibilizaria esta query nao precisaria deste método
}
