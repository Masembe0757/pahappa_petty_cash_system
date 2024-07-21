package org.pahappa.pettycashapp.systems.petty_cash_app.beans;

import org.pahappa.pettycashapp.systems.petty_cash_app.models.Accountability;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Requisition;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.User;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.AccountabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.primefaces.model.file.UploadedFile;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.*;
import java.util.List;

@Service
@SessionScope
public class AccountabilityBean implements Serializable {
    private Accountability accountability;

    @Autowired
    AccountabilityService accountabilityService;

    private int accountedAmount;
    private String description;
    private UploadedFile imageUploaded;
    private Requisition requisition;

    public Requisition getRequisition() {
        return requisition;
    }

    public void setRequisition(Requisition requisition) {
        this.requisition = requisition;
    }

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

    @PostConstruct
    public void init() {
        accountability = new Accountability();
    }

    private User getCurrentUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        return (User) externalContext.getSessionMap().get("currentUser");
    }

    public void addAccountability() {
        handleFileUpload();
        String message = accountabilityService.provideAccountability(accountability, accountedAmount, description, requisition);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    public List<Accountability> getAccountabilitiesOfUser() {
        return accountabilityService.getAccountabilitiesOfUser(getCurrentUser().getId());
    }

    public List<Accountability> getAllAccountabilities() {
        return accountabilityService.getAllAccountabilities();
    }

    public void deleteAccountability(int accId) {
    }

    public void handleFileUpload() {
        if (imageUploaded != null) {
            try {
                InputStream inputStream = imageUploaded.getInputStream();
                byte[] fileContent = new byte[(int) imageUploaded.getSize()];
                inputStream.read(fileContent);
                accountability.setImage(fileContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
            FacesMessage message = new FacesMessage("Successful", imageUploaded.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
}
