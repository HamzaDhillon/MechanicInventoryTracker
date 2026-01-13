// VehicleRecall.java
package ca.hccis.mechanic.entity;

import javax.persistence.*;

@Entity
@Table(name = "vehicle_recalls")
public class VehicleRecall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "vin", length = 17)
    private String vin;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "nhtsa_campaign_number")
    private String nhtsaCampaignNumber;

    @Column(name = "report_received_date")
    private String reportReceivedDate;

    @Column(name = "component", length = 1000)
    private String component;

    @Column(name = "summary", length = 2000)
    private String summary;

    @Column(name = "consequence", length = 1000)
    private String consequence;

    @Column(name = "remedy", length = 1000)
    private String remedy;

    @Column(name = "notes", length = 1000)
    private String notes;

    @Column(name = "model_year")
    private Integer modelYear;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    // Constructors
    public VehicleRecall() {}

    public VehicleRecall(String vin, String manufacturer, String component, String summary) {
        this.vin = vin;
        this.manufacturer = manufacturer;
        this.component = component;
        this.summary = summary;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public String getNhtsaCampaignNumber() { return nhtsaCampaignNumber; }
    public void setNhtsaCampaignNumber(String nhtsaCampaignNumber) { this.nhtsaCampaignNumber = nhtsaCampaignNumber; }

    public String getReportReceivedDate() { return reportReceivedDate; }
    public void setReportReceivedDate(String reportReceivedDate) { this.reportReceivedDate = reportReceivedDate; }

    public String getComponent() { return component; }
    public void setComponent(String component) { this.component = component; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getConsequence() { return consequence; }
    public void setConsequence(String consequence) { this.consequence = consequence; }

    public String getRemedy() { return remedy; }
    public void setRemedy(String remedy) { this.remedy = remedy; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Integer getModelYear() { return modelYear; }
    public void setModelYear(Integer modelYear) { this.modelYear = modelYear; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    // Helper methods
    public String getDisplayTitle() {
        if (component != null && !component.isEmpty()) {
            return component;
        }
        return "Vehicle Recall";
    }

    public boolean isCritical() {
        return consequence != null &&
                (consequence.toLowerCase().contains("crash") ||
                        consequence.toLowerCase().contains("fire") ||
                        consequence.toLowerCase().contains("injury"));
    }

    @Override
    public String toString() {
        return "VehicleRecall{" +
                "manufacturer='" + manufacturer + '\'' +
                ", component='" + component + '\'' +
                ", modelYear=" + modelYear +
                '}';
    }
}