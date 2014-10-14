/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

/**
 *
 * @author tulio.xcrtf
 */
public class Util {

    /**
     * @author paribe
     * @param strCpf
     * @return 
     */
    static public boolean CPF(String strCpf) {
        int d1, d2;
        int digito1, digito2, resto;
        int digitoCPF;

        d1 = d2 = 0;
        digito1 = digito2 = resto = 0;

        for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {
            digitoCPF = Integer.parseInt(strCpf.substring(nCount - 1, nCount));

            d1 = d1 + (11 - nCount) * digitoCPF;
            d2 = d2 + (12 - nCount) * digitoCPF;
        }

        resto = (d1 % 11);

        if (resto < 2) {
            digito1 = 0;
        } else {
            digito1 = 11 - resto;
        }

        d2 += 2 * digito1;
        resto = (d2 % 11);

        if (resto < 2) {
            digito2 = 0;
        } else {
            digito2 = 11 - resto;
        }

        String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf.length());
        String nDigResult = String.valueOf(digito1) + String.valueOf(digito2);
        return nDigVerific.equals(nDigResult);
    }

}