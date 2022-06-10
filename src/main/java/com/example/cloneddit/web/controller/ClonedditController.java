package com.example.cloneddit.web.controller;

import com.example.cloneddit.clone.ClonedditDTO;
import com.example.cloneddit.clone.ClonedditService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("cloneddit/api/clone")
@AllArgsConstructor
public class ClonedditController {

    private final ClonedditService clonedditService;

    @GetMapping
    public List<ClonedditDTO> getAllCloneddits(){
        return clonedditService.getAll();
    }

    @PostMapping
    public ClonedditDTO createCloneddit(@RequestBody @Valid ClonedditDTO clonedditDTO){
        return clonedditService.saveCloneddit(clonedditDTO);
    }

    @GetMapping("/{id}")
    public ClonedditDTO getCloneddit(@PathVariable Long id){
        return clonedditService.getCloneddit(id);
    }

}
