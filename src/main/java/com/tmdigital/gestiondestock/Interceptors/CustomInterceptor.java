package com.tmdigital.gestiondestock.Interceptors;

import java.util.ArrayList;
import java.util.Arrays;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;



public class CustomInterceptor implements StatementInspector {

    private static final long serialVersionUID = 1L;

    @Override
    public String inspect(String sql) {

        final ArrayList<String> excludedEntities = new ArrayList<>(Arrays.asList("company", "roles", "roles_users", "users_roles"));
        
        if (sql.toLowerCase().startsWith("select")) {
            String idCompany = MDC.get("idCompany");
            // String entityName = sql.substring( "select ".length(), sql.indexOf(".")).toLowerCase();
            Integer i = sql.indexOf("from") + "from".length();
            String entityName = sql.substring( i+1, i+4).toLowerCase();

            
            // if (StringUtils.hasLength(entityName)
            // && !excludedEntities.contains(entityName)
            // && StringUtils.hasLength(idCompany)) 
            // {
            //     if (sql.contains("where")) {
            //         sql = sql + " and " + entityName + ".idcompany=" + idCompany;
            //     } else {
            //         sql = sql + " where " + entityName +".idcompany=" + idCompany; 
            //     }
            // }
            System.out.println("(CustomInterceptor) entityName : " + entityName);
            // System.out.println("(CustomInterceptor) idCompany  : " + idCompany);
            // System.out.println("(CustomInterceptor) sql : " + sql);
        }
            
        return sql;
    }  

}
