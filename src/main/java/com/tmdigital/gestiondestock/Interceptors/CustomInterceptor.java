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
            if (idCompany == null) return sql;                
            if ("200".equals(idCompany)) return sql;               
            
            Integer indexOfFromInSql = sql.indexOf("from") + "from".length();
            String entityName = sql.substring(indexOfFromInSql+1).toLowerCase().split(" ")[0];
            String sqlEntityName = sql.substring(indexOfFromInSql+1).toLowerCase().split(" ")[1];

            if (StringUtils.hasLength(entityName)
                && !excludedEntities.contains(entityName)
                && StringUtils.hasLength(idCompany)) 
            {
                if (sql.contains("where")) {
                    sql = sql + " and " + sqlEntityName + ".idcompany=" + idCompany;
                } else {
                    sql = sql + " where " + sqlEntityName +".idcompany=" + idCompany; 
                }
            }
        }
            
        return sql;
    }  

}
