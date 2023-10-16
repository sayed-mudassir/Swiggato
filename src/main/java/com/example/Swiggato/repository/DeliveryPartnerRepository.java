package com.example.Swiggato.repository;

import com.example.Swiggato.model.DeliveryPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner,Integer> {
//    find random delivery partner hql query
    String findRandom = "Select q from DeliveryPartner q order by RAND() LIMIT 1";
    @Query(value = findRandom)
    DeliveryPartner findRandomDeliveryPartner();
}
