package com.epam.university.java.core.task034;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Task034Impl implements Task034 {
    @Override
    public Person readWithSaxParser(DefaultHandler handler, String filepath) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        try {
            saxParser = factory.newSAXParser();
            saxParser.parse("src/main/resources" + filepath, handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return ((SaxHandlerImpl) handler).getPerson();
    }

    @Override
    public Person readWithJaxbParser(String filepath) {
        JAXBContext jaxbContext;
        PersonImpl person = null;
        try {
            jaxbContext = JAXBContext.newInstance(PersonImpl.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            person = (PersonImpl) unmarshaller.unmarshal(new File("src/main/resources" + filepath));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public Person readWithStaxParser(XMLStreamReader streamReader) {
        PersonImpl person = null;
        Collection<PhoneNumber> phones = new ArrayList<>();

        while (true) {
            try {
                if (!streamReader.hasNext()) {
                    break;
                }
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
            try {
                streamReader.next();
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }

            if (streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                if (streamReader.getLocalName().equalsIgnoreCase("person")) {

                    person = new PersonImpl();

                    if (streamReader.getAttributeCount() > 0) {
                        String id = streamReader.getAttributeValue(null, "id");
                        person.setId(Integer.parseInt(id));
                    }
                }

                if (streamReader.getLocalName().equalsIgnoreCase("first-name")) {
                    try {
                        assert person != null;
                        person.setFirstName(streamReader.getElementText());
                    } catch (XMLStreamException e) {
                        e.printStackTrace();
                    }
                }

                if (streamReader.getLocalName().equalsIgnoreCase("last-name")) {
                    try {
                        assert person != null;
                        person.setLastName(streamReader.getElementText());
                    } catch (XMLStreamException e) {
                        e.printStackTrace();
                    }
                }

                if (streamReader.getLocalName().equalsIgnoreCase("person-phone")) {
                    try {
                        assert person != null;
                        PhoneNumberImpl phoneNumber = new PhoneNumberImpl();
                        phoneNumber.setPhoneNumber(streamReader.getElementText());
                        phones.add(phoneNumber);
                    } catch (XMLStreamException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (streamReader.getEventType() == XMLStreamReader.END_ELEMENT) {
                if (streamReader.getLocalName().equalsIgnoreCase("person")) {
                    assert person != null;
                    person.setPhoneNumbers(phones);
                    return person;
                }
            }
        }
        return null;
    }
}