package com.upp.service.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScientificPaperDBService implements IScientificPaperService {

    private final IScientificPaperRepository scientificPaperRepository;

    @Autowired
    public ScientificPaperDBService(final IScientificPaperRepository scientificPaperRepository) {
        this.scientificPaperRepository = scientificPaperRepository;
    }

    @Override
    public List<ScientificPaper> findAllScientificPapers() {
        return scientificPaperRepository.findAll()
                .stream()
                .map(spe -> new ScientificPaper(
                        spe.getId(),
                        spe.getDoi(),
                        spe.getTitle(),
                        spe.getAbstrct(),
                        spe.getKeywords(),
                        spe.getFee(),
                        spe.getPdfName(),
                        spe.getPdf(),
                        spe.getScientificField(),
                        spe.getMagazineId(),
                        mapCoauthors(spe)
                )).collect(Collectors.toList());
    }

    @Override
    public void saveScientificPaper(ScientificPaper sp) {
        scientificPaperRepository.save(new ScientificPaperEntity(
                sp.getId(),
                sp.getDOI(),
                sp.getTitle(),
                sp.getAbstrct(),
                sp.getKeywords(),
                sp.getFee(),
                sp.getPdfName(),
                sp.getPdf(),
                sp.getScientificField(),
                sp.getMagazineId(),
                mapCoauthorEntities(sp)));
    }

    @Override
    public void savePdf(String id, MultipartFile pdf) {
        ScientificPaper sp = findScientificPaperById(id);

        if (sp == null) {
            sp = new ScientificPaper();
            sp.setId(id);
        }

        try {
            sp.setPdf(pdf.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveScientificPaper(sp);
    }

    @Override
    public void deleteScientificPaper(String id) {
        ScientificPaper sp = findScientificPaperById(id);
        scientificPaperRepository.delete(new ScientificPaperEntity(
                sp.getId(),
                sp.getDOI(),
                sp.getTitle(),
                sp.getAbstrct(),
                sp.getKeywords(),
                sp.getFee(),
                sp.getPdfName(),
                sp.getPdf(),
                sp.getScientificField(),
                sp.getMagazineId(),
                mapCoauthorEntities(sp)));
    }

    private List<User> mapCoauthors(ScientificPaperEntity entity) {
        return entity.getCoauthors()
                .stream()
                .map(ca -> new User(
                        ca.getId(),
                        ca.getFistname(),
                        ca.getLastname(),
                        ca.getEmail(),
                        ca.getUsername(),
                        ca.getPassword(),
                        ca.getCity(),
                        ca.getState(),
                        ca.getIsReviewer(),
                        ca.getIsEditor(),
                        ca.getSubscriptionStatus()))
                .collect(Collectors.toList());
    }

    private List<UserEntity> mapCoauthorEntities(ScientificPaper scientificPaper) {
        return scientificPaper.getCoauthors()
                .stream()
                .map(ca -> new UserEntity(
                        ca.getId(),
                        ca.getFistname(),
                        ca.getLastname(),
                        ca.getEmail(),
                        ca.getUsername(),
                        ca.getPassword(),
                        ca.getCity(),
                        ca.getState(),
                        ca.getIsReviewer(),
                        ca.getIsEditor(),
                        ca.getSubscriptionStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public ScientificPaper findScientificPaperById(String id) {
        var allPapers = findAllScientificPapers();
        for (ScientificPaper sp : allPapers) {
            if (sp.getId().equals(id)) {
                return sp;
            }
        }
        return null;
    }
}
