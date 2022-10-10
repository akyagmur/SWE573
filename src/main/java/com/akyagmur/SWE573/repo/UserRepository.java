package com.akyagmur.SWE573.repo;

import com.akyagmur.SWE573.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
    @Query("SELECT u FROM User u WHERE u.name = :xName and u.password = :xPassword")
    List<User> findAllByBirthDateBefore(@Param("xName") String name, @Param("xPassword") String password);
    List<User> findAllByNameAndBirthDateIsAfter(String name, LocalDate birthDate);

}
