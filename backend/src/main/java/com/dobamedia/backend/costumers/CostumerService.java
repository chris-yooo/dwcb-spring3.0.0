package com.dobamedia.backend.costumers;

import com.dobamedia.backend.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CostumerService {

    private final CostumerRepository costumerRepository;
    private final Utils utils;

    public Costumer addCostumer(DtoNewCostumer dtoNewCostumer) {
        Costumer costumer = new Costumer(
                utils.addUUIDasString(),
                dtoNewCostumer.name(),
                dtoNewCostumer.paket()
        );
        costumerRepository.save(costumer);
        return costumer;
    }

    public List<Costumer> getCostumers() {
        return costumerRepository.findAll();
    }

    public Costumer getCostumerById(String id) {
        return costumerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Costumer not found"));
    }

    public Costumer getCostumerByName(String name) {
        return (Costumer) costumerRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Costumer not found"));
    }

    public Costumer updateCostumer(String id, DtoNewCostumer dtoNewCostumer) {
        Costumer costumer = getCostumerById(id);
        costumer = new Costumer(
                costumer.id(),
                dtoNewCostumer.name(),
                dtoNewCostumer.paket()
        );
        costumerRepository.save(costumer);
        return costumer;
    }

    public void deleteCostumer(String id) {
        costumerRepository.deleteById(id);
    }
}
