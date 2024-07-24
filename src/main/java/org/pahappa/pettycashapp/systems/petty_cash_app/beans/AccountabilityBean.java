package org.pahappa.pettycashapp.systems.petty_cash_app.beans;

import org.pahappa.pettycashapp.systems.petty_cash_app.models.Accountability;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Requisition;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.User;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.AccountabilityService;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.primefaces.model.file.UploadedFile;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.*;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@SessionScope
public class AccountabilityBean implements Serializable {
    private Accountability accountability;

    @Autowired
    AccountabilityService accountabilityService;

    private static final long serialVersionUID = 4L;
    private int accountedAmount;
    private String description;
    private UploadedFile imageUploaded;
    private Requisition requisition;
    private Accountability selectedAccountability;

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

    public Accountability getSelectedAccountability() {
        return selectedAccountability;
    }

    public void setSelectedAccountability(Accountability selectedAccountability) {
        this.selectedAccountability = selectedAccountability;
    }

    public Accountability getAccountability() {
        return accountability;
    }

    public void setAccountability(Accountability accountability) {
        this.accountability = accountability;
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

    public StreamedContent getImageDownload() {
        if (selectedAccountability != null && selectedAccountability.getImage() != null) {
            byte[] imageData = selectedAccountability.getImage();
            return DefaultStreamedContent.builder()
                    .name(selectedAccountability.getRequisition().getUser().getUserName() + ": " + selectedAccountability.getRequisition().getDescription() + "accountability.png")
                    .contentType("image/jpeg")
                    .stream(() -> new ByteArrayInputStream(imageData))
                    .build();
        }
        return null;
    }


    public void onRowSelect(SelectEvent event) {
        selectedAccountability = (Accountability) event.getObject();
    }

    public boolean requisitionNotAccountedFor(int requisitionId) {
        boolean accounted = false;
        Accountability accountability = accountabilityService.getAccountabilityOnRequisition(requisitionId);
        if (accountability == null) {
            accounted = true;
        }
        return accounted;
    }

    public String getTimeSpent(Date approved,Date created) {
        LocalDateTime createdDateTime = created.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime approvedDateTime = approved.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        Duration duration = Duration.between(createdDateTime, approvedDateTime);

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;


        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
