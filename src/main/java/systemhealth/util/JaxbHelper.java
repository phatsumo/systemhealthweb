/**
 *
 */
package systemhealth.util;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Utility class that uses Generics to marshall and unmarshall JAXB objects.
 * 
 * @author 1062992
 *
 */
public class JaxbHelper {

    /**
     * Unmarshalls an input stream that contains the XML content to a JAVA
     * object.
     *
     * @param inputStream
     * @param unmarshalClass
     * @return instance of T
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
    public static <T> T unmarshal(InputStream inputStream,
            Class<T> unmarshalClass) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(unmarshalClass);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        T object = (T) jaxbUnmarshaller.unmarshal(inputStream);
        return object;
    }

    /**
     * Marshalls the JAXB object to a String.
     *
     * @param object
     * @param marshalClass
     * @return String representation of marshalled object.
     * @throws JAXBException
     */
    public static <T> String marshal(T object, Class<T> marshalClass)
            throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(marshalClass);

        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter writer = new StringWriter();
        jaxbMarshaller.marshal(object, writer);
        return writer.toString();
    }

}
