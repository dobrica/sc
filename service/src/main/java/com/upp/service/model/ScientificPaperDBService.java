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
                        spe.getTitle(),
                        spe.getAbstrct(),
                        spe.getKeywords(),
                        spe.getFee(),
                        spe.getPdfName(),
                        spe.getPdf(),
                        spe.getScientificField(),
                        spe.getMagazineId(),
                        spe.getCoauthors()
                )).collect(Collectors.toList());
    }

    @Override
    public void saveScientificPaper(ScientificPaper sp) {
        scientificPaperRepository.save(
                new ScientificPaperEntity(
                        sp.getId(),
                        sp.getTitle(),
                        sp.getAbstrct(),
                        sp.getKeywords(),
                        sp.getFee(),
                        sp.getPdfName(),
                        sp.getPdf(),
                        sp.getScientificField(),
                        sp.getMagazineId(),
                        sp.getCoauthors()));
    }

    @Override
    public void savePdf(String id, MultipartFile pdf) {
        ScientificPaper sp = findScientificPaperById(id);
        try {
            sp.setPdf(pdf.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveScientificPaper(sp);
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
