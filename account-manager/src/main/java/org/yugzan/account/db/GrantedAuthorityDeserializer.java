package org.yugzan.account.db;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;

/**
 * @author teddy
 *
 */

public class GrantedAuthorityDeserializer extends JsonDeserializer<SimpleGrantedAuthority> {
	private static final Logger logger = LoggerFactory.getLogger(GrantedAuthoritySerializer.class);
    @Override
    public SimpleGrantedAuthority deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    	logger.debug(jp.getValueAsString());
        return new SimpleGrantedAuthority(jp.getValueAsString());
    }
}
