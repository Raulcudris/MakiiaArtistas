package com.makiia.crosscutting.persistence.repository;

import com.makiia.crosscutting.persistence.entity.EntyRecmaetarivalor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntyRecmaetarivalorRepository extends CrudRepository<EntyRecmaetarivalor,Integer>
{
        EntyRecmaetarivalor findById(int id);

}
