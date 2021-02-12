package sysc4806lab;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ShowAddressBookController {
    @GetMapping("/showaddressbook")
    public String showaddressbook(@RequestParam(name="id", required=true, defaultValue="1") Long id, Model model) {

        Optional<Object> repository = AccessingDataRestApplication.getRepositories().getRepositoryFor(AddressBook.class);
        AddressBookRepository repo = (AddressBookRepository) repository.get();
        AddressBook addressbook = repo.findById(1L).get();

        model.addAttribute("id", id);
        model.addAttribute("addressbook", addressbook);
        return "showaddressbook";
    }
}
