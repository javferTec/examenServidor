package com.fpmislata.basespring.persistence.dao.db.jdbc.utils.text;

public class StringUtil {

    // Metodo para capitalizar la primera letra de una cadena
    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
