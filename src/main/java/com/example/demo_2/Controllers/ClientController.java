package com.example.demo_2.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.demo_2.Models.Entities.Client;
import com.example.demo_2.Models.Services.Client.IClientService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("clients", clientService.findAll());
        return "Clients/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("client", clientService.show(id));
        return "Clients/show";
    }
    
    @GetMapping("/create")
    public String create() {
        return "Clients/create";
    }

    @PostMapping("/store")
    public String store(@ModelAttribute Client client){
        clientService.store(client);
        return "redirect:/clients/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("client", clientService.show(id));
        return "Clients/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Client client) {
        clientService.update(id, client);
        return "redirect:/clients/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        clientService.delete(id);
        return "redirect:/clients/list";
    }
}
