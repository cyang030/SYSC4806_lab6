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

    @OneToMany(cascade= CascadeType.ALL)
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

    public boolean addElement(BuddyInfo buddy) {
        return buddies.add(buddy);
    }

    public boolean removeElement(Long id) {
        buddies.removeIf(buddyInfo -> buddyInfo.getId() == id);
        return true;
    }

    public List<BuddyInfo> getBuddies() {
        return buddies;
    }
}
