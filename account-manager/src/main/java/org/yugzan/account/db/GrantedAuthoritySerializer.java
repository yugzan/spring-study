package org.yugzan.account.db;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.security.core.GrantedAuthority;
import java.io.IOException;

/**
 * @author teddy
 *
 */

public class GrantedAuthoritySerializer extends JsonSerializer<GrantedAuthority> {
    @Override
    public void serialize(GrantedAuthority value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(value.getAuthority());
    }
}
