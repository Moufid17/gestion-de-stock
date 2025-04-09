package com.tmdigital.gestiondestock.Interceptors;

import java.util.ArrayList;
import java.util.Arrays;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CustomInterceptor implements StatementInspector {

    private static final long serialVersionUID = 1L;

    @Override
    public String inspect(String sql) {
        final ArrayList<String> excludedEntities = new ArrayList<>(Arrays.asList( "roles", "roles_users", "users_roles"));
            

        if (sql.toLowerCase().startsWith("select")) {

            if (sql.contains("nextval") || sql.contains("currval") || sql.contains("setval")) {
                return sql;
            }

            String companyId = MDC.get("companyId");
            if (companyId == null) return sql;         
            // Handle the case when the company id is a master company. Try to find a better way to handle this case.       
            if ("200".equals(companyId)) return sql;               
            
            Integer indexOfFromInSql = sql.indexOf("from") + "from".length();
            String entityName = sql.substring(indexOfFromInSql+1).toLowerCase().split(" ")[0];
            String sqlEntityName = sql.substring(indexOfFromInSql+1).toLowerCase().split(" ")[1];

            if (StringUtils.hasLength(entityName)
                && !excludedEntities.contains(entityName)
                && StringUtils.hasLength(companyId)) 
            {
                if (sql.contains("where")) {
                    sql = sql + " and " + sqlEntityName + ".company_id=" + companyId;
                } else {
                    sql = sql + " where " + sqlEntityName +".company_id=" + companyId; 
                }
            }
        }

        return sql;
    }  

}
