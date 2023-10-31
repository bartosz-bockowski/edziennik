package pl.edziennik.edziennik.address;

import jakarta.validation.Valid;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/address")
public class AddressAdminController {
    private final AddressRepository addressRepository;
    public AddressAdminController(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }
    @GetMapping("/{addressId}/change/{targetType}/{targetId}")
    public String change(@PathVariable Long addressId, @PathVariable String targetType, @PathVariable Long targetId, Model model){
        model.addAttribute("address",addressRepository.getReferenceById(addressId));
        return "address/change";
    }
    @PostMapping("/change")
    public String change(@Valid Address address, BindingResult result, Model model, @RequestParam String targetType, @RequestParam String targetId){
        if(result.hasErrors()){
            model.addAttribute("result",result);
            model.addAttribute("targetType",targetType);
            model.addAttribute("targetId",targetId);
            model.addAttribute("address",address);
            return "address/change";
        }
        addressRepository.save(address);
        return "redirect:/admin/" + targetType + "/" + targetId + "/adminDetails";
    }
}
