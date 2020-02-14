package com.upp.service.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MagazineDBService implements IMagazineService {

    private final IMagazineRepository magazineRepository;

    @Autowired
    public MagazineDBService(final IMagazineRepository magazineRepository) {
        this.magazineRepository = magazineRepository;
    }

    @Override
    public List<Magazine> findAllMagazines() {
        return magazineRepository.findAll()
                .stream()
                .map( m -> new Magazine(
                        m.getIssn(),
                        m.getTitle(),
                        m.getIsOpenAccess(),
                        m.getMainEditor()
                )).collect(Collectors.toList());
    }

    @Override
    public void saveMagazine(Magazine magazine) {
        magazineRepository.save(new MagazineEntity(
                magazine.getIssn(),
                magazine.getTitle(),
                magazine.getIsOpenAccess(),
                magazine.getMainEditor()));
    }

    @Override
    public Magazine findMagazineById(String issn) {
        var magazines = findAllMagazines();
        for (Magazine m: magazines) {
            if (m.getIssn().equals(issn)){
                return m;
            }
        }
        return null;
    }

}
