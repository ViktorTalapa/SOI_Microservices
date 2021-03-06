package providers;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

@Provider
@Produces({"application/x-protobuf", "application/json"})
@Consumes({"application/x-protobuf", "application/json"})
public class ProtoBufProvider implements MessageBodyReader<Message>, MessageBodyWriter<Message> {

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Message.class.isAssignableFrom(type);
    }

    @Override
    public Message readFrom(Class<Message> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                            MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
            throws IOException, WebApplicationException {
        try {
            Method newBuilder = type.getMethod("newBuilder");
            AbstractMessage.Builder messageBuilder = (AbstractMessage.Builder) newBuilder.invoke(type);
            switch (mediaType.toString()) {
                case "application/x-protobuf":
                    messageBuilder.mergeFrom(entityStream).build();
                    break;
                case "application/json":
                    JsonFormat.parser().merge(new InputStreamReader(entityStream), messageBuilder);
            }
            return messageBuilder.build();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new WebApplicationException(e);
        }
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Message.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(Message message, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Message message, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
            throws IOException, WebApplicationException {
        switch (mediaType.toString()) {
            case "application/x-protobuf":
                entityStream.write(message.toByteArray());
                break;
            case "application/json":
                entityStream.write(JsonFormat.printer().print(message).getBytes());
        }
    }
}