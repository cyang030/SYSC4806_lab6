package sysc4806lab.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Scanner;

@Entity
@Table(name = "buddies")
public class BuddyInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id = null;
    private String name;
    private String address;
    private String phone_number;
    private int age;

    //@ManyToOne
    //@JoinColumn(name = "addressBook_id")
    //private AddressBook addressbook;

    public BuddyInfo() {
    }

    public BuddyInfo(String name, String address, String phone_number) {
        this.name = name;
        this.address = address;
        this.phone_number = phone_number;
    }

    public BuddyInfo(BuddyInfo obj) {
        this.name = obj.name;
        this.address = obj.address;
        this.phone_number = obj.phone_number;
    }

    public Long getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isOver18() {
        return age > 18;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String greetings()
    {
        return "Greetings, " + this.name;
    }

    public static BuddyInfo importFromString(String info) {
        Scanner scanner = new Scanner(info);
        scanner.useDelimiter("#");
        String name = scanner.next();
        String address = scanner.next();
        String phone = scanner.next();
        scanner.close();
        return new BuddyInfo(name, address, phone);
    }

    public String toString()
    {
        return String.format("ID:%d#Name:%s#Address:%s#Phone:%s",
                this.id, this.name, this.address, this.phone_number);
    }

    public boolean equals(Object obj) {
        // self check
        if (this == obj)
            return true;
        // null check
        if (obj == null)
            return false;
        // type check and cast
        if (getClass() != obj.getClass())
            return false;
        BuddyInfo buddy = (BuddyInfo) obj;
        // field comparison
        return Objects.equals(name, buddy.name)
                && Objects.equals(address, buddy.address)
                && Objects.equals(phone_number, buddy.phone_number);
    }

}