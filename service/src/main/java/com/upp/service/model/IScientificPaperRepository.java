package com.upp.service.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface IScientificPaperRepository extends JpaRepository<ScientificPaperEntity, BigInteger> {
}
