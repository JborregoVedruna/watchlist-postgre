package com.vedruna.watchlist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedruna.watchlist.dto.entitydto.DNICardDTO;
import com.vedruna.watchlist.exception.UserNotFoundException;
import com.vedruna.watchlist.persistance.model.DNICard;
import com.vedruna.watchlist.persistance.model.User;
import com.vedruna.watchlist.persistance.repository.DNIRepositoryI;
import com.vedruna.watchlist.persistance.repository.UserRepositoryI;
import com.vedruna.watchlist.service.DNIServiceI;

@Service
public class DNIServiceImpl implements DNIServiceI {

    @Autowired
    private DNIRepositoryI dniRepositoryI;

    @Autowired
    private UserRepositoryI userRepositoryI;

    @Override
    public DNICard insertDNI(DNICardDTO document) {
        User user = userRepositoryI.findById(document.getDniOwnerId())
                        .orElseThrow(() -> new UserNotFoundException());
        DNICard dni = new DNICard();
        dni.setDniOwner(user);
        dni.setNumber(document.getNumber());
        dni.setFrontImg(document.getFrontImg());
        dni.setBackImg(document.getBackImg());
        return dniRepositoryI.save(dni);
    }

    @Override
    public void deleteDNI(Integer dniCardId) {
        dniRepositoryI.deleteById(dniCardId);
    }
    
}
