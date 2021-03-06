package com.clientui.clientui.proxies;

import com.clientui.clientui.beans.CommandeBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "zuul-server")
public interface MicroServiceCommandeProxy {

    @PostMapping(value = "/microservice-commandes/commandes")
    CommandeBean ajouterCommande(@RequestBody CommandeBean commande);

    @GetMapping(value = "/microservice-commandes/commandes/{id}")
    CommandeBean recupererUneCommande(@PathVariable("id") int id);
}
