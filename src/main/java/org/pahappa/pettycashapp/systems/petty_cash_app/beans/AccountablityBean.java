package org.pahappa.pettycashapp.systems.petty_cash_app.beans;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Accountability;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Requisition;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.User;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.AccountabilityService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FilesUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.primefaces.model.file.UploadedFile;
import org.springframework.web.context.annotation.SessionScope;

import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@Service
@SessionScope
public class AccountablityBean implements Serializable {
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

    private User getCurrentUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        return (User) externalContext.getSessionMap().get("currentUser");
    }

    public void addAccountability() {
        FileUploadEvent event =  null;
        System.out.println("ADDING DETAILS .."+accountedAmount + description + requisition.getDateNeeded());
        System.out.println("IMAGE FILE \n    "+ imageUploaded +"    IMAGE");

      String message =  accountabilityService.provideAccountability(imageUploaded,accountedAmount,description,requisition);
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

//    public StreamedContent retrieveImage() {
//        Accountability selectedAccountability =new Accountability();
//        Requisition selectedRequisition = new Requisition();
//        if (selectedAccountability != null && selectedAccountability.getImage() != null) {
//            byte[] imageData = selectedAccountability.getImage();
//            return DefaultStreamedContent.builder()
//                    .name(selectedRequisition.getUser().getUserName() + "--"+ "accountability.jpeg")
//                    .contentType("image/jpeg")
//                    .stream(() -> new ByteArrayInputStream(imageData))
//                    .build();
//        }
//        return null;
//    }
//

}
