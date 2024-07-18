package org.pahappa.pettycashapp.systems.petty_cash_app.beans;

import org.pahappa.pettycashapp.systems.petty_cash_app.models.Accountability;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Requisition;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.AccountabilityService;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.primefaces.model.file.UploadedFile;

import javax.faces.bean.SessionScoped;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

@Service
@SessionScoped
public class AccountablityBean implements Serializable {
    @Autowired
    AccountabilityService accountabilityService;

    private int accountedAmount;
    private String description;
    private UploadedFile imageUploaded;

    public UploadedFile getImageUploaded() {
        return imageUploaded;
    }

    public void setImageUploaded(UploadedFile imageUploaded) {
        this.imageUploaded = imageUploaded;
    }

    public int getAccountedAmount() {
        return accountedAmount;
    }

    public void setAccountedAmount(int accountedAmount) {
        this.accountedAmount = accountedAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void addAccountability(UploadedFile imageUploaded, int amountAccounted, String description, Requisition requisition) {
      String message =  accountabilityService.provideAccountability(imageUploaded,amountAccounted,description,requisition);
    }

    public StreamedContent retrieveImage() {
        Accountability selectedAccountability =new Accountability();
        Requisition selectedRequisition = new Requisition();
        if (selectedAccountability != null && selectedAccountability.getImage() != null) {
            byte[] imageData = selectedAccountability.getImage();
            return DefaultStreamedContent.builder()
                    .name(selectedRequisition.getUser().getUserName() + "--"+ "accountability.jpeg")
                    .contentType("image/jpeg")
                    .stream(() -> new ByteArrayInputStream(imageData))
                    .build();
        }
        return null;
    }


}
