package com.clientui.clientui.controller;

import com.clientui.clientui.beans.CommandeBean;
import com.clientui.clientui.beans.PaiementBean;
import com.clientui.clientui.beans.ProduitsBean;
import com.clientui.clientui.proxies.MicroServiceCommandeProxy;
import com.clientui.clientui.proxies.MicroServiceProduitsProxy;
import com.clientui.clientui.proxies.MicroServicesPaiementProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class ClientController {

    @Autowired
    private MicroServiceProduitsProxy produitsProxy;

    @Autowired
    private MicroServiceCommandeProxy commandeProxy;

    @Autowired
    private MicroServicesPaiementProxy paiementProxy;

    @GetMapping("/")
    public String accueil(Model model) {
        List<ProduitsBean> produitsBeans = produitsProxy.listeDesProduits();
        model.addAttribute("produits", produitsBeans);
        return "Accueil";
    }

    @GetMapping("/details-produit/{id}")
    public String trouverProduit(@PathVariable int id, Model model) {
        ProduitsBean produit = produitsProxy.recupererUnProduit(id);
        model.addAttribute("produit", produit);
        return "FicheProduit";
    }

    @GetMapping("/details-produit/commander-produit/{idProduit}/{montant}")
    public String passerUneCommande(@PathVariable int idProduit, @PathVariable Double montant, Model model) {
        CommandeBean commandeBean = new CommandeBean();
        commandeBean.setProductId(idProduit);
        commandeBean.setQuantite(1);
        commandeBean.setDateCommande(new Date());

        CommandeBean ajouterCommande = commandeProxy.ajouterCommande(commandeBean);
        model.addAttribute("commande", ajouterCommande);
        model.addAttribute("montant", montant);
        return "Paiement";
    }

    @GetMapping("/payer-commande/{idCommande}/{montantCommande}")
    public String payerUneCommance(@PathVariable int idCommande,
                                   @PathVariable Double montantCommande,
                                   Model model) {
        PaiementBean paiementAExecuter = new PaiementBean();
        paiementAExecuter.setIdCommande(idCommande);
        paiementAExecuter.setMontant(montantCommande);
        paiementAExecuter.setNumeroCarte(numeroDeCarte());

        ResponseEntity<PaiementBean> paiement = paiementProxy.payerUneCommande(paiementAExecuter);

        Boolean paiementAccepte = false;
        if(paiement.getStatusCode() == HttpStatus.CREATED) {
            paiementAccepte = true;
        }
        model.addAttribute("paiementOk", paiementAccepte);

        return "Confirmation";
    }

    private Long numeroDeCarte() {
        return ThreadLocalRandom.current().nextLong(1000000000000000L,9000000000000000L );
    }
}
