package sysc4806lab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BuddyInfoTest {
	private BuddyInfo buddy;
	private static final String DEFAULT_NAME = "John Smith";

	@BeforeEach
	void setUp() {
		buddy = new BuddyInfo(DEFAULT_NAME, "123 Meow Rd", "12345678");
	}
	
	@Test
	void TestBuddyInfoConstructor() {
		BuddyInfo buddyCopy = new BuddyInfo(buddy);
		
		assertEquals(buddy, buddyCopy);
	}
	
	@Test
	void TestBuddyInfoGreeting() {
		assertEquals("Greetings, " + DEFAULT_NAME, buddy.greetings());
	}
	
	@Test
	void TestBuddyInfoIsAgeOver18() {
		buddy.setAge(10);
		assertFalse(buddy.isOver18());
	}
	
	@Test
	void TestBuddyInfoAge() {
		buddy.setAge(123);
		assertEquals(123, buddy.getAge());
	}
	
	@Test
	void TestBuddyInfoName() {
		assertEquals("John Smith", buddy.getName());
	}
	
	@Test
	void TestBuddyInfoAddress() {
		buddy.setAddress("123 Deerfield Rd");
		assertEquals("123 Deerfield Rd", buddy.getAddress());
	}
	
	@Test
	void TestBuddyInfoPhone() {
		buddy.setPhone_number("789127389");
		assertEquals("789127389", buddy.getPhone_number());
	}

	//@Test
	void TestBuddyInfoJPA() {
		// Connecting to the database through EntityManagerFactory
		// connection details loaded from persistence.xml
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-lab2");

		EntityManager em = emf.createEntityManager();

		// Creating a new transaction
		EntityTransaction tx = em.getTransaction();

		tx.begin();

		// Persisting the BuddyInfo entity objects
		em.persist(buddy);

		tx.commit();

		// Querying the contents of the database using JPQL query
		Query q = em.createQuery("SELECT p FROM BuddyInfo p");

		@SuppressWarnings("unchecked")
		List<BuddyInfo> results = q.getResultList();

		BuddyInfo b = results.get(0);
		assertEquals(buddy.getName(), b.getName());
		assertEquals(buddy.getAddress(), b.getAddress());
		assertEquals(buddy.getPhone_number(), b.getPhone_number());

		// Closing connection
		em.close();

		emf.close();
	}

}
