package sysc4806lab;

import org.junit.jupiter.api.BeforeEach;
import sysc4806lab.model.AddressBook;
import sysc4806lab.model.BuddyInfo;

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
