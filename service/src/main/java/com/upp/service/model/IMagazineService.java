package com.upp.service.model;

import java.util.List;

public interface IMagazineService {

    List<Magazine> findAllMagazines();
    void saveMagazine(Magazine magazine);
    Magazine findMagazineById(String issn);

}
