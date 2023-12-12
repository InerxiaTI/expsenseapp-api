package com.inerxia.expensemateapi.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GenerateRandomCodeService {

    public static String generateRandomCode() {
        // TODO: Parametrizar caracteres
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int cantidadCaracteres = 6;
        StringBuilder codigo = new StringBuilder();
        Set<Character> caracteresUtilizados = new HashSet<>();

        Random random = new Random();

        for (int i = 0; i < cantidadCaracteres; ) {
            int indiceAleatorio = random.nextInt(caracteres.length());
            char caracterAleatorio = caracteres.charAt(indiceAleatorio);

            if (!caracteresUtilizados.contains(caracterAleatorio)) {
                caracteresUtilizados.add(caracterAleatorio);
                codigo.append(caracterAleatorio);
                i++;
            }
        }
        return codigo.toString();
    }
}
