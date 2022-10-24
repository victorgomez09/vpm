package com.vira.vpm.issuetrackerservice.util;

import org.springframework.stereotype.Component;

import com.vira.vpm.issuetrackerservice.enums.PriorityNameEnum;

@Component
public class EnumUtil {

    public PriorityNameEnum parsePriorityStringToEnum(String name) {
        switch (PriorityNameEnum.valueOf(name)) {
            case LOW:
                return PriorityNameEnum.LOW;
            case MEDIUM:
                return PriorityNameEnum.MEDIUM;
            case HIGH:
                return PriorityNameEnum.HIGH;
            default:
                return PriorityNameEnum.MEDIUM;
        }
    }
}
