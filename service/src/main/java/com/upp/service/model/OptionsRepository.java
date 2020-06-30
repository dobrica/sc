package com.upp.service.model;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OptionsRepository {

    public static Map<String, String> timeOptions = new LinkedHashMap<>();

    static {
        timeOptions.put("PT1M", "1 minut");
        timeOptions.put("PT15M", "15 minuta");
        timeOptions.put("PT30M", "30 minuta");
        timeOptions.put("PT1H", "1 sat");
        timeOptions.put("P7D", "7 dana");
        timeOptions.put("P30D", "30 dana");
        timeOptions.put("P60D", "60 dana");
        timeOptions.put("P90D", "90 dana");
    }

    public static List<String> scienceFields = new ArrayList<>();

    static {
        scienceFields.add("Matematika");
        scienceFields.add("Fizika");
        scienceFields.add("Hemija");
        scienceFields.add("Biologija");
    }

    public static List<String> reviewOptions = new ArrayList<>();

    static {
        reviewOptions.add("Predlazem da rad bude prihvacen!");
        reviewOptions.add("Predlazem da rad bude odbijen!");
        reviewOptions.add("Predlazem da rad bude prihvacen uz manje izmene!");
        reviewOptions.add("Predlazem da rad bude prihvacen uz vece izmene!");

    }

}
