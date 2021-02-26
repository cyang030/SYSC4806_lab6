package sysc4806lab;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sysc4806lab.model.AddressBook;

@RepositoryRestResource(collectionResourceRel = "addressbook", path = "addressbook")
public interface AddressBookRepository extends PagingAndSortingRepository<AddressBook, Long> {
}