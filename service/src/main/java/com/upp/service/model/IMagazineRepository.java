package com.upp.service.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface IMagazineRepository extends JpaRepository<MagazineEntity, BigInteger> {
}
