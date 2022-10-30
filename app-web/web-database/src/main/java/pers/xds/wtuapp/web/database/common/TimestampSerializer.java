package pers.xds.wtuapp.web.database.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * TimeStamp序列化
 * @author DeSen Xu
 * @date 2022-09-21 22:23
 */
public class TimestampSerializer extends JsonSerializer<Timestamp> {

    @Override
    public void serialize(Timestamp value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.getTime());
    }

}
