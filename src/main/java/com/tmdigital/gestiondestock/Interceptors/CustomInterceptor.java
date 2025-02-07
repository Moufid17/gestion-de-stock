package com.tmdigital.gestiondestock.Interceptors;

import org.hibernate.resource.jdbc.spi.StatementInspector;


public class CustomInterceptor implements StatementInspector {

    private static final long serialVersionUID = 1L;

    @Override
    public String inspect(String sql) {
        // if (sql.startsWith("select")) {
        //     if (sql.contains("where")) {
        //         sql = sql + " and idCompany = 2";
        //     } else {
        //         sql = sql + " where idCompany = 2"; 
        //     }
        // }
        return sql;
    }  

}
