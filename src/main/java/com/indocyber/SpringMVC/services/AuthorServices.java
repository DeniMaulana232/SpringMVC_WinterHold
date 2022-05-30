package com.indocyber.SpringMVC.services;

import com.indocyber.SpringMVC.dtos.Author.AuthorBookGridDTO;
import com.indocyber.SpringMVC.dtos.Author.AuthorDTO;
import com.indocyber.SpringMVC.dtos.Author.UpsertAuthorDTO;
import com.indocyber.SpringMVC.models.Author;
import com.indocyber.SpringMVC.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServices {
    private AuthorRepository authorRepository;

    @Autowired
    public AuthorServices(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }



    private final int PAGE_LIMIT= 10;


    public Page<AuthorDTO> findAllAuthor(Integer page,String fullName) {
        Pageable pagination = PageRequest.of(page - 1, PAGE_LIMIT, Sort.by("id"));
        Page<AuthorDTO> allAuthor = authorRepository.findAll(fullName, pagination);
        return allAuthor;
    }

    public void saveDataAuthor (UpsertAuthorDTO authorDTO){
        Author author = new Author(
                authorDTO.getId(),
                authorDTO.getTitle(),
                authorDTO.getFirstName(),
                authorDTO.getLastName(),
                authorDTO.getBirthDate(),
                authorDTO.getDeceasedDate(),
                authorDTO.getEducation(),
                authorDTO.getSummary());
        authorRepository.save(author);
    }

    public UpsertAuthorDTO getAuthorById(Long id){
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return new UpsertAuthorDTO(
                author.getId(),
                author.getTitle(),
                author.getFirstName(),
                author.getLastName(),
                author.getBirthDate(),
                author.getDeceasedDate(),
                author.getEducation(),
                author.getSummary());
    }

    public void deleteAuthor(Long id){
        authorRepository.deleteById(id);
    }

    public List<AuthorBookGridDTO> findAllAuthorBookGridDTO(Long id){
        List<AuthorBookGridDTO> books = authorRepository.findAllAuthorBookGridDTO(id);
        return books;
    }
}
