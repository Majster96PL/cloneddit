package com.example.cloneddit.clone;

import com.example.cloneddit.api.ApiService;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class ClonedditService {

    private final ClonedditRepository clonedditRepository;
    private final ApiService apiService;

    @Transactional(readOnly = true)
    public List<ClonedditDTO> getAll(){
        return clonedditRepository.findAll()
                .stream()
                .map(this::mapDto)
                .collect(toList());
    }

    public ClonedditDTO saveCloneddit(ClonedditDTO clonedditDTO){
        Cloneddit cloneddit = clonedditRepository.save(mapToCloneddit(clonedditDTO));
        cloneddit.setCloneId(cloneddit.getCloneId());
        return clonedditDTO;
    }

    private ClonedditDTO mapDto(Cloneddit cloneddit) {
        return ClonedditDTO.builder().name(cloneddit.getName())
                .id(cloneddit.getCloneId())
                .postCount(cloneddit.getPosts().size())
                .build();
    }

    public Cloneddit mapToCloneddit(ClonedditDTO clonedditDTO){
        return Cloneddit.builder()
                .name("/clone/" + clonedditDTO.getName())
                .description(clonedditDTO.getDescription())
                .user(apiService.getEmailAsUser())
                .createDate(Instant.now())
                .build();
    }

    public ClonedditDTO getCloneddit(Long id){
        Cloneddit cloneddit = clonedditRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Cloneddit not found with id -" + id)
                );
        return mapDto(cloneddit);
    }
}
