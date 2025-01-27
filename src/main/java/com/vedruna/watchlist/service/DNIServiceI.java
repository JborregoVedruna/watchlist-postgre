package com.vedruna.watchlist.service;

import com.vedruna.watchlist.dto.entitydto.DNICardDTO;
import com.vedruna.watchlist.persistance.model.DNICard;

public interface DNIServiceI {
    DNICard insertDNI(DNICardDTO document);
    void deleteDNI(Integer dniCardId);
}
