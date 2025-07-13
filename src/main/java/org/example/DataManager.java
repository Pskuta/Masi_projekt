package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataManager {
    private static final Map<String, UnitermRecord> unitermy = new HashMap<>();

    public static void saveUniterm(String nazwa, String opis,
                                   UnitermZrownoleglenie zrownoleglenie,
                                   UnitermSekwencja sekwencja,
                                   int mergedChoice) {
        UnitermRecord record = new UnitermRecord(opis, zrownoleglenie, sekwencja, mergedChoice);
        unitermy.put(nazwa, record);
    }

    public static Set<String> getAllUnitermNames() {
        return unitermy.keySet();
    }

    public static String getUnitermDescription(String nazwa) {
        UnitermRecord record = unitermy.get(nazwa);
        return record != null ? record.opis : null;
    }

    public static UnitermData loadUniterm(String nazwa) {
        UnitermRecord record = unitermy.get(nazwa);
        if (record != null) {
            return new UnitermData(
                    record.zrownoleglenie.toMap(),
                    record.sekwencja.toMap(),
                    record.mergedChoice
            );
        }
        return null;
    }

    public static void deleteUniterm(String nazwa) {
        unitermy.remove(nazwa);
    }

    private static class UnitermRecord {
        public final String opis;
        public final UnitermZrownoleglenie zrownoleglenie;
        public final UnitermSekwencja sekwencja;
        public final int mergedChoice;

        public UnitermRecord(String opis, UnitermZrownoleglenie zrownoleglenie,
                             UnitermSekwencja sekwencja, int mergedChoice) {
            this.opis = opis;
            this.zrownoleglenie = zrownoleglenie;
            this.sekwencja = sekwencja;
            this.mergedChoice = mergedChoice;
        }
    }

    public static class UnitermData {
        public final Map<String, Object> zrownolegleniaData;
        public final Map<String, Object> sekwencjaData;
        public final int mergedChoice;

        public UnitermData(Map<String, Object> zrownolegleniaData,
                           Map<String, Object> sekwencjaData,
                           int mergedChoice) {
            this.zrownolegleniaData = zrownolegleniaData;
            this.sekwencjaData = sekwencjaData;
            this.mergedChoice = mergedChoice;
        }
    }
}