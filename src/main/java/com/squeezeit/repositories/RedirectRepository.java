package com.squeezeit.repositories;

import com.squeezeit.entities.RedirectData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedirectRepository extends JpaRepository<RedirectData, String> {

}
