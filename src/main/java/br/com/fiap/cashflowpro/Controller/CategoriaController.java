package br.com.fiap.cashflowpro.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller 
public class CategoriaController {

    @ResponseBody
    @RequestMapping( method = RequestMethod.GET,path= "/categoria", produces = "application/json")
    public String index(){  
        return "lista";
    }




}
