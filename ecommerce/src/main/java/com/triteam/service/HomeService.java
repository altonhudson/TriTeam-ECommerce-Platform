package com.triteam.service;

import org.springframework.stereotype.Service;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@Getter
public class HomeService {

    // Private JPA Repo Declaration

    // public HomeService(Repo repo){this.repo = repo;} <-- Valid constructor once
    // the repo is created and declared

    // public getCategory Method

    // public Page<Product> getProducts(Pageable pageable){return
    // repo.findAll(pageable);} <-- valid method
}
