package br.com.fiap.cashflowpro.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.cashflowpro.Models.Categoria;
import br.com.fiap.cashflowpro.Repository.CategoriaRepository;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/categoria")
@Slf4j
public class CategoriaController {

    @Autowired // CDI - Injeção de Dependência
    CategoriaRepository repository;

    @GetMapping
    public List<Categoria> index() {
        return repository.findAll();
    }

    @PostMapping()
    @ResponseStatus(CREATED)
    public Categoria create(@RequestBody Categoria categoria){
        // categoria.setId(new Random().nextLong()); //esse codigo é responsabilidade da Categoria, e n deve estar aqui
        log.info("cadastrando categoria: {}", categoria);
        return repository.save(categoria); 
    }

     @GetMapping("/{id}")
    public ResponseEntity<Categoria> get (@PathVariable() Long id) {
        log.info("buscando categoria com id {}", id);
        
        return repository
            .findById(id)
            .map((c) -> {return ResponseEntity.ok(c);})   
            .orElse(ResponseEntity.notFound().build());

        // var categoria = repository.findById(id);
        
        // if (categoria.isEmpty()){
        //         return ResponseEntity.notFound().build();
        //     }
        //         return ResponseEntity.ok(categoria.get());
    }

    // private Optional<Categoria> getCategoriaById(Long id) {
    //     var categoria = repository
    //             .stream()
    //             .filter(c -> c.id().equals(id))
    //             .findFirst();
    //     return categoria;
    // }



    //Delete
    @DeleteMapping("{id}")
    @ResponseStatus()
    public ResponseEntity<Object> destroy(@PathVariable Long id){
        log.info("apagando categoria {}", id);

        verificaSeExisteCategoria(id);

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //PUT
    @PutMapping("{id}")
    @ResponseStatus(OK)
    public Categoria update(@PathVariable Long id, @RequestBody Categoria categoria){
        log.info("atualizando categoria com id {} para {}", id, categoria);

        verificaSeExisteCategoria(id);
        
        categoria.setId(id);
        return repository.save(categoria);

    }

    private void verificaSeExisteCategoria(Long id) {
        repository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                NOT_FOUND,
                 "id da categoria não encontrado"
                 ));
    }    
}