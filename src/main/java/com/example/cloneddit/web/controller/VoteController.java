package com.example.cloneddit.web.controller;

import com.example.cloneddit.clone.vote.VoteDTO;
import com.example.cloneddit.clone.vote.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cloneddit/votes")
@AllArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteDTO voteDTO){
        voteService.addVote(voteDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
