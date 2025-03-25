package spengergasse.at.sj2425scherzerrabar.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spengergasse.at.sj2425scherzerrabar.commands.BranchCommand;
import spengergasse.at.sj2425scherzerrabar.domain.Address;
import spengergasse.at.sj2425scherzerrabar.domain.Branch;
import spengergasse.at.sj2425scherzerrabar.dtos.BranchDto;
import spengergasse.at.sj2425scherzerrabar.persistence.BranchRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.LibraryRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BranchService {

    private final BranchRepository branchRepository;
    private final LibraryRepository libraryRepository;

    public BranchService(BranchRepository branchRepository, LibraryRepository libraryRepository) {
        this.branchRepository = branchRepository;
        this.libraryRepository = libraryRepository;
    }

    @Transactional
    public BranchDto createBranch(BranchCommand branchDto) {
        var library = libraryRepository.findLibraryByLibraryApiKey(branchDto.libraryApiKey());
        if(library.isEmpty()) {
            throw new NoSuchElementException("Library not found");
        }
        return BranchDto.branchDtoFromBranch(
                branchRepository.save(new Branch(library.get(), Address.addressFromString(branchDto.address()))));
    }

    @Transactional
    public BranchDto updateBranch(BranchCommand command) {
        Branch branch = branchRepository.findBranchByBranchApiKey(command.apiKey())
                .orElseThrow(() -> new NoSuchElementException("Branch not found"));

        var library = libraryRepository.findLibraryByLibraryApiKey(command.libraryApiKey())
                .orElseThrow(() -> new NoSuchElementException("Library not found"));

        branch.setLibrary(library);
        branch.setAddress(Address.addressFromString(command.address()));

        branch = branchRepository.save(branch);
        return BranchDto.branchDtoFromBranch(branch);
    }

    @Transactional
    public void deleteBranch(String branchApiKey) {
        Branch branch = branchRepository.findBranchByBranchApiKey(branchApiKey)
                .orElseThrow(() -> new NoSuchElementException("Branch not found"));

        branchRepository.delete(branch);
    }



    public List<BranchDto> getAllBranches() {
        return branchRepository.findAll().stream().map(BranchDto::branchDtoFromBranch).collect(Collectors.toList());
    }

    public BranchDto getBranchByApiKey(String branchApiKey) {
        return branchRepository.findBranchByBranchApiKey(branchApiKey)
                .map(BranchDto::branchDtoFromBranch)
                .orElseThrow(() -> new NoSuchElementException("Branch not found"));
    }

    public List<BranchDto> getBranchesByLibrary(String libraryApiKey) {
        var library = libraryRepository.findLibraryByLibraryApiKey(libraryApiKey)
                .orElseThrow(() -> new NoSuchElementException("Library not found"));

        return branchRepository.findBranchesByLibrary(library).stream().map(BranchDto::branchDtoFromBranch).collect(Collectors.toList());
    }

}
