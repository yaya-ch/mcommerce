package com.clientui.clientui.proxies;

import com.clientui.clientui.beans.ProduitsBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "microservice-produits")
public interface MicroServiceProduitsProxy {

    @GetMapping(value = "/microservice-produits/Produits")
    List<ProduitsBean> listeDesProduits();

    @GetMapping( value = "/microservice-produits/Produits/{id}")
    ProduitsBean recupererUnProduit(@PathVariable("id") int id);
}
