package providers;

import com.google.protobuf.GeneratedMessage;
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
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

@Provider
@Consumes("application/json")
@Produces("application/json")
public class JsonProvider implements MessageBodyReader<Message>, MessageBodyWriter<Message> {
    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public Message readFrom(Class<Message> aClass, Type type, Annotation[] annotations, MediaType mediaType,
                            MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
            throws IOException, WebApplicationException {
        try {
            Method newBuilder = aClass.getMethod("newBuilder");
            GeneratedMessage.Builder messageBuilder = (GeneratedMessage.Builder) newBuilder.invoke(aClass);
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(entityStream));
            String line = reader.readLine();
            String newLine = System.getProperty("line.separator");
            while (line != null) {
                buffer.append(line).append(newLine);
                line = reader.readLine();
            }
            JsonFormat.parser().merge(buffer.toString(), messageBuilder);
            return messageBuilder.build();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new WebApplicationException(e);
        }
    }

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public long getSize(Message message, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Message message, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream)
            throws IOException, WebApplicationException {
        outputStream.write(JsonFormat.printer().print(message).getBytes());
    }
}
