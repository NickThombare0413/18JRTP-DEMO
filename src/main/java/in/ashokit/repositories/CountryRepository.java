package in.ashokit.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.ashokit.entities.CountryEntity;
@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Serializable>{

}
