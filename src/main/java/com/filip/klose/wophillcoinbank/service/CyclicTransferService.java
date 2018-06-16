package com.filip.klose.wophillcoinbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filip.klose.wophillcoinbank.entity.CyclicTransfer;
import com.filip.klose.wophillcoinbank.repository.CyclicTransferRepository;

@Service
public class CyclicTransferService {

    @Autowired
    private CyclicTransferRepository cyclicTransferRepository;

    public void saveCyclicTransfer(CyclicTransfer cyclicTransfer) {
        cyclicTransferRepository.save(cyclicTransfer);
    }

    public List<CyclicTransfer> getAll() {
        return cyclicTransferRepository.findAll();
    }

}
