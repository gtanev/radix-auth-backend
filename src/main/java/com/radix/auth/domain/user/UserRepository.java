package com.radix.auth.domain.user;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface UserRepository extends CrudRepository<User, Long> {

  boolean existsByEmail(@Param("email") String email);

  User findByEmail(@Param("email") String email);
}
