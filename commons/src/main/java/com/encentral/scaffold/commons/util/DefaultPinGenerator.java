package com.encentral.scaffold.commons.util;

import java.util.Random;

public class DefaultPinGenerator {
    public static String generatePin() {
        Random random = new Random();
        int pin = 1000 + random.nextInt(9000);
        return String.valueOf(pin);
    }
}
