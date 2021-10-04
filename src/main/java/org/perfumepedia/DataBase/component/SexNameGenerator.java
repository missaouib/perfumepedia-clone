package org.perfumepedia.DataBase.component;

import org.springframework.stereotype.Component;

@Component
public class SexNameGenerator {
    public String getSexName(int sex) {
        if (sex == 1) return "Damski";
        if (sex == 2) return "Męski";
        if (sex == 3) return "Unisex";
        return "NaN";
    }
}
