package sysc4806lab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.persistence.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressBookTest {
    private BuddyInfo buddy;
    private static final String DEFAULT_NAME = "John Smith";
    private static final String TEST_FILENAME = "testfile";

    @BeforeEach
    void setUp() {
        buddy = new BuddyInfo(DEFAULT_NAME, "123 Meow Rd", "12345678");
    }

    //@Test
    public void TestAddressBookSizeOne() {
        AddressBook addressbook = new AddressBook();
        addressbook.addElement(buddy);

        assertEquals(1, addressbook.getBuddies().size());
    }

    //@Test
    public void TestAddressBookSizeClear() {
        AddressBook addressbook = new AddressBook();
        addressbook.addElement(buddy);

        addressbook.getBuddies().clear();
        assertEquals(0, addressbook.getBuddies().size());
    }
}
