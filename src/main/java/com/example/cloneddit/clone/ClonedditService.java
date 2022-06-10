package com.example.cloneddit.clone;

import com.example.cloneddit.api.ApiService;
import com.example.cloneddit.clone.mapper.ClonedditMapper;
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
    private final ClonedditMapper clonedditMapper;

    @Transactional(readOnly = true)
    public List<ClonedditDTO> getAll(){
        return clonedditRepository.findAll()
                .stream()
                .map(clonedditMapper::mapCloneToDTO)
                .collect(toList());
    }

    public ClonedditDTO saveCloneddit(ClonedditDTO clonedditDTO){
        Cloneddit cloneddit = clonedditRepository.save(clonedditMapper.mapDTOToClone(clonedditDTO));
        clonedditDTO.setId(cloneddit.getCloneId());
        return clonedditDTO;
    }

    public ClonedditDTO getCloneddit(Long id){
        Cloneddit cloneddit = clonedditRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Cloneddit not found with id -" + id)
                );
        return clonedditMapper.mapCloneToDTO(cloneddit);
    }
}
