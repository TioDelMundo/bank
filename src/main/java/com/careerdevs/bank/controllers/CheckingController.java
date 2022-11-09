package com.careerdevs.bank.controllers;

import com.careerdevs.bank.models.Checking;
import com.careerdevs.bank.repositories.CheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

//@CrossOrigin
@RestController
@RequestMapping("/api/checking")
public class CheckingController {

    @Autowired
    private CheckingRepository checkingRepository;

    @PostMapping
    public ResponseEntity<?> createOneCheckingAccount(@RequestBody Checking newCheckingAcct) {
        Checking addedCheckingAcct = checkingRepository.save(newCheckingAcct);
        return new ResponseEntity<>(addedCheckingAcct, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCheckingAccounts() {
        List<Checking> allCheckingAccounts = checkingRepository.findAll();
        return new ResponseEntity<>(allCheckingAccounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCheckingAccountById(@PathVariable Long id) {
        Checking foundCheckingAccount = checkingRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        return new ResponseEntity<>(foundCheckingAccount, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateCheckingAccount(@PathVariable Long id, @RequestBody Checking newCheckingData) {
        Checking requestedCheckingAccount = checkingRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        if (!newCheckingData.getAlias().equals("")) {
            requestedCheckingAccount.setAlias(newCheckingData.getAlias());
        }
        return new ResponseEntity<>(checkingRepository.save(requestedCheckingAccount), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCheckingAccount(@PathVariable Long id) {
        Checking requestedCheckingAccount = checkingRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        checkingRepository.delete(requestedCheckingAccount);
        return ResponseEntity.ok(requestedCheckingAccount);
    }
}
