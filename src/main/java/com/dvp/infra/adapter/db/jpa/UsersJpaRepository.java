package com.dvp.infra.adapter.db.jpa;

import com.dvp.infra.adapter.db.entites.UsersDto;
import org.springframework.data.repository.CrudRepository;


public interface UsersJpaRepository extends CrudRepository<UsersDto, Long> {

     UsersDto findByFirstNameAndLastName(String firstName, String LastName);

}
