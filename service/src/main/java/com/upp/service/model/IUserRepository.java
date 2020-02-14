package com.upp.service.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface IUserRepository extends JpaRepository<UserEntity, BigInteger> {
}
