
package fr.bpi.service;

import java.text.MessageFormat;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseMessageSource extends AbstractMessageSource {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String[] codes = code.split("\\.");
        if (codes.length != 4) {
            throw new IllegalArgumentException("we need a code with 4 fields " + code);
        }

        if (locale.getLanguage() == null) {
            throw new IllegalArgumentException("language is not set");
        }

        String tableName = codes[0];
        String translated_object_column_name = codes[1];
        String translated_object_id = codes[2];
        String codeToTranslate = codes[3];

        String query = "select " + codeToTranslate + " from " + tableName + " where " + translated_object_column_name + " = ? and language_code = ?";
        String translation = null;
        try {
            translation = jdbcTemplate.queryForObject(query,
                                                          String.class,
                                                          translated_object_id,
                                                          locale.getLanguage());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

        //optimise because of messageFormat is not well optimised
        return new MessageFormat(translation);
    }



}
