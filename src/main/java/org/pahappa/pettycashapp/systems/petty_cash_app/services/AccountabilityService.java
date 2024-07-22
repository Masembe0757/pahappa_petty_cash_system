package org.pahappa.pettycashapp.systems.petty_cash_app.services;

import org.pahappa.pettycashapp.systems.petty_cash_app.dao.AccountabilityDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.dao.BudgetLineDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.dao.RequisitionDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Accountability;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Requisition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;
import java.util.Random;

@ApplicationScope
@Service
public class AccountabilityService {
    @Autowired
    AccountabilityDao accountabilityDao;
    @Autowired
    AccountabilityService accountabilityService;
    @Autowired
    RequisitionDao requisitionDao;
    @Autowired
    private BudgetLineDao budgetLineDao;

    public String generateReferenceNumber() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder referenceNumber = new StringBuilder("ACC-");

        for (int i = 0; i < 5; i++) {
            referenceNumber.append(characters.charAt(random.nextInt(characters.length())));
        }

        return referenceNumber.toString();
    }
    //ACCOUNTABILITY
    public String provideAccountability(Accountability accountability, int amountAccounted, String description, Requisition requisition){
        String error_message ="";

        //Excess returned and available on budget line
        if(amountAccounted < requisition.getAmount()){
            int excessReturned = requisition.getAmount()-amountAccounted;
            int balance    =  requisition.getBudgetLine().getBalance()+excessReturned;
            budgetLineDao.updateBudgetLIne(requisition.getBudgetLine().getId(),balance);

        }


        if(amountAccounted > requisition.getAmount()){
            error_message = "Amount accounted greater than amount requisitioned";
        } else if (description.length()<10) {
            error_message = "Please provide more description";
        }else if (requisition.getStatus().equals("pending")) {
            error_message ="Can not account for a drafted requisition";
        } else {

            accountability.setAmount(amountAccounted);
            accountability.setRequisition(requisition);
            accountability.setDateCreated();
            accountability.setDescription(description);
            accountability.setReferenceNumber(generateReferenceNumber());
            accountabilityDao.saveAccountability(accountability);
        }

        return error_message;
    }

    public List<Accountability> getAccountabilitiesOfUser(int id) {
        return  accountabilityDao.getAccountabilitiesOfUser(id);
    }
    public List<Accountability> getAllAccountabilities() {
        return  accountabilityDao.getAllAccountabilities();
    }
    public Accountability getAccountabilityOnRequisition(int requistionId){
        return accountabilityDao.getAccountabilityOnRequisition(requistionId);
    }
}
