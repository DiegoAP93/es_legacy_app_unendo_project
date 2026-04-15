package com.eslegacyapp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eslegacyapp.api.model.Objeto;

public interface ObjetoRepository extends JpaRepository<Objeto, Integer> {
}
