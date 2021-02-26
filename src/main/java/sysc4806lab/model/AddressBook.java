package sysc4806lab.model;

import sysc4806lab.ctrl.StaticInputFormController;

import javax.persistence.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Entity
@Table(name = "addressbooks")
public class AddressBook {

    private final static Logger LOGGER = Logger.getLogger(AddressBook.class.getName());

    @Id
    @GeneratedValue
    private Long id = null;

    //@OneToMany(cascade= CascadeType.ALL)
    @OneToMany(cascade= CascadeType.ALL, mappedBy = "addressbook")
    private List<BuddyInfo> buddies = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void saveViaSerialization(String filename) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(filename)));

        objectOutputStream.writeObject(this);
        objectOutputStream.close();
    }

    public static AddressBook loadViaSerialization(String filename) throws IOException, ClassNotFoundException {
        AddressBook addressBook;
        ObjectInputStream objectInputStream = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(filename)));

        addressBook = (AddressBook) objectInputStream.readObject();

        objectInputStream.close();
        return addressBook;
    }

    public void addElement(BuddyInfo buddy) {
        LOGGER.info("addre book size old" + buddies.size());
        LOGGER.info("added" + buddies.add(buddy));
        LOGGER.info("addre book size new" + buddies.size());
    }

    public List<BuddyInfo> getBuddies() {
        return buddies;
    }
}
