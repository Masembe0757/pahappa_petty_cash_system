package org.pahappa.pettycashapp.systems.petty_cash_app.services;

import org.pahappa.pettycashapp.systems.petty_cash_app.dao.UserDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Accountability;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Requisition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountabilityService {
    @Autowired
    UserDao userDao;
    @Autowired
    AccountabilityService accountabilityService;
    public  String generateReferenceNumber() {
        UUID uuid = UUID.randomUUID();
        String referenceNumber = uuid.toString().replace("-", "ACC").toUpperCase();
        return referenceNumber;
    }
    //ACCOUNTABILITY
    public String provideAccountability(String description,int amount,int requisitionId){
        String error_message ="";
        Requisition requisition = userDao.getRequisitionOfId(requisitionId);
        if(amount > requisition.getAmount()){
            error_message = "Amount accounted greater than amount requisitioned";
        } else if (description.length()<50) {
            error_message = "Please provide more description";
        }else if (requisition.getStatus().equals("draft")) {
            error_message ="Can not account for a drafted requisition";
        } else {
            Accountability accountability = new Accountability();
            accountability.setAmount(amount);
            accountability.setDescription(description);
            accountability.setRequisition(requisition);
            accountability.setReferenceNumber(accountabilityService.generateReferenceNumber());
            userDao.saveAccountability(accountability);
        }

        return error_message;
    }

}
