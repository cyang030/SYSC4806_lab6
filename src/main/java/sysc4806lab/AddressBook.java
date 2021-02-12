package sysc4806lab;

import javax.persistence.*;
import java.io.*;
import java.util.List;

@Entity
@Table(name = "addressbooks")
public class AddressBook {
    @Id
    @GeneratedValue
    private Long id = null;

    //@OneToMany(cascade= CascadeType.ALL)
    @OneToMany(cascade= CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "addressbook")
    private List<BuddyInfo> buddies;

    public void saveViaSerialization(String filename) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(filename)));

        objectOutputStream.writeObject(this);
        objectOutputStream.close();
    }

    public static AddressBook loadViaSerialization(String filename) throws IOException, ClassNotFoundException {
        AddressBook addressBook = new AddressBook();
        ObjectInputStream objectInputStream = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(filename)));

        addressBook = (AddressBook) objectInputStream.readObject();

        objectInputStream.close();
        return addressBook;
    }

    public void addElement(BuddyInfo buddy) {
        buddies.add(buddy);
    }

    public List<BuddyInfo> getBuddies() {
        return buddies;
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();
        addressBook.addElement(new BuddyInfo("John Smith", "101 Road", "123456789"));
        addressBook.addElement(new BuddyInfo("Jane  Doe", "102 Street", "987654321"));
        System.out.println(addressBook);
    }
}
