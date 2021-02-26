package sysc4806lab.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sysc4806lab.AddressBookRepository;
import sysc4806lab.model.AddressBook;
import sysc4806lab.model.BuddyInfo;

import java.util.Optional;
import java.util.logging.Logger;



@Controller
public class StaticInputFormController {

    private final static Logger LOGGER = Logger.getLogger(StaticInputFormController.class.getName());

    @Autowired
    private AddressBookRepository addressBookRepo;

    @GetMapping("/")
    public String StaticInputForm(Model model) {
        return "addressBookCreation";
    }

    @PostMapping("/create")
    public String AddressBookCreation(@ModelAttribute BuddyInfo buddyinfo, Model model) {
        LOGGER.info("Requested to create addressbook");
        AddressBook addressBook = new AddressBook();
        addressBook.getBuddies().add(new BuddyInfo("John Smith","123 test rd","456789"));//debug
        addressBookRepo.save(addressBook);
        LOGGER.info("address book id:" + addressBook.getId());
        model.addAttribute("addressBookId", addressBook.getId());
        if (addressBook.getBuddies().isEmpty())
            model.addAttribute("content", "empty" );
        else
            model.addAttribute("content", addressBook.getBuddies() );

        //https://spring.io/guides/gs/validating-form-input/
        //not sure why this is not needed in that guide
        model.addAttribute("buddyinfo",new BuddyInfo());

        LOGGER.info("size" + addressBook.getBuddies().size());

        return "addressBookEdit";
    }

    @PostMapping("/addBuddy")
    public String AddBuddy(@RequestParam(name ="addressBookId") Long addressBookId, @ModelAttribute BuddyInfo buddyinfo, Model model, BindingResult bindingResult) {
        LOGGER.info("Requested to add an buddy, addr id" + addressBookId);
        LOGGER.info(buddyinfo.toString());

        Optional<AddressBook> addressBookOpt = addressBookRepo.findById(addressBookId);
        AddressBook addressBook;
        // TODO: add proper check for existence
        //if (addressBookOpt.isPresent())
            addressBook = addressBookOpt.get();
        LOGGER.info("address book size:" + addressBook.getBuddies().size());
        //else error...
        addressBook.addElement(buddyinfo);
        addressBookRepo.save(addressBook);

        //https://spring.io/guides/gs/validating-form-input/
        //not sure why this is not needed in that guide
        model.addAttribute("buddyinfo",new BuddyInfo());

        model.addAttribute("addressBookId", addressBook.getId());
        if (addressBook.getBuddies().isEmpty())
            model.addAttribute("content", "empty" );
        else
            model.addAttribute("content", addressBook.getBuddies() );

        LOGGER.info("size buddies in addr:" + addressBook.getBuddies().size());

        return "addressBookEdit";
    }

    @PostMapping("/delBuddy")
    public String DelBuddy(@RequestParam(name ="addressBookId") Long addressBookId, @RequestParam(name ="buddyid") Long buddyid,@ModelAttribute BuddyInfo buddyinfo, Model model, BindingResult bindingResult) {
        LOGGER.info("Requested to del an buddy, addr id" + addressBookId);

        Optional<AddressBook> addressBookOpt = addressBookRepo.findById(addressBookId);

        AddressBook addressBook;
        // TODO: add proper check for existence
        //if (addressBookOpt.isPresent())
        addressBook = addressBookOpt.get();
        //else error...

        addressBook.getBuddies().remove(buddyid);

        //https://spring.io/guides/gs/validating-form-input/
        //not sure why this is not needed in that guide
        model.addAttribute("buddyinfo",new BuddyInfo());

        model.addAttribute("addressBookId", addressBook.getId());
        if (addressBook.getBuddies().isEmpty())
            model.addAttribute("content", "empty" );
        else
            model.addAttribute("content", addressBook.getBuddies() );

        return "addressBookEdit";
    }
}
