package callumboyd.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Data
@AllArgsConstructor
public class Journey {
    private Date started;
    private Date finished;
    private int durationSecs;
    private String fromStopId;
    private String toStopId;
    private String chargeAmount;
    private String companyId;
    private String busId;
    private String pan;
    private Status status;

    public Journey(Date started, Date finished, String fromStopId, String toStopId,
                        String companyId, String busId, String pan) {
        this.started = started;
        this.finished = finished;
        this.fromStopId = fromStopId;
        this.toStopId = toStopId;
        this.companyId = companyId;
        this.busId = busId;
        this.pan = pan;

        calculateDuration();
        calculateStatus();
        calculateFare();
    }

    public Journey(Date started, String fromStopId, String companyId,
                   String busId, String pan) {
        this.started = started;
        this.finished = null;
        this.fromStopId = fromStopId;
        this.toStopId = null;
        this.companyId = companyId;
        this.busId = busId;
        this.pan = pan;

        calculateDuration();
        calculateStatus();
        calculateFare();
    }

    private void calculateDuration() {
        if (this.finished == null) {
            this.durationSecs = 0;
        } else {
            this.durationSecs = Math.toIntExact((this.finished.getTime()-this.started.getTime())/1000);
        }
    }

    private void calculateFare() {
        //Hardcoding price for this exercise.
        switch (this.status) {
            case CANCELLED:
                this.chargeAmount = "$0";
                break;

            case COMPLETED:
                this.chargeAmount = calculateCost();

            case INCOMPLETE:
                this.chargeAmount = calculateCost();
        }
    }

    private String calculateCost() {
        String cost;
        switch (this.fromStopId) {
            case "Stop1":
                if ("Stop2".equals(this.toStopId)) {
                    cost = "$3.25";
                } else {
                    cost = "$7.30";
                }
                break;

            case "Stop2":
                if ("Stop1".equals(this.toStopId)) {
                    cost = "$3.25";
                } else {
                    cost = "$5.50";
                }
                break;
            case "Stop3":
                if ("Stop2".equals(this.toStopId)) {
                    cost = "$5.50";
                } else {
                    cost = "$7.30";
                }
                break;

            default:
                cost = "$0";
                break;
        }
        return cost;
    }

    private void calculateStatus() {
        if (this.toStopId == null) {
            this.status = Status.INCOMPLETE;
        } else {
            if (this.fromStopId != null) {
                if ( this.fromStopId.equals(this.toStopId)) {
                    status = Status.CANCELLED;
                } else {
                    status = Status.COMPLETED;
                }
            }
        }
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        StringBuilder sb = new StringBuilder();

        sb.append(formatter.format(this.started));
        sb.append(", ");
        if (this.finished != null) {
            sb.append(formatter.format(this.finished));
        }
        sb.append(", ");
        sb.append(this.durationSecs);
        sb.append(", ");
        sb.append(this.fromStopId);
        sb.append(", ");
        if (this.toStopId != null) {
            sb.append(this.toStopId);
        }
        sb.append(", ");
        sb.append(this.chargeAmount);
        sb.append(", ");
        sb.append(this.companyId);
        sb.append(", ");
        sb.append(this.busId);
        sb.append(", ");
        sb.append(this.pan);
        sb.append(", ");
        sb.append(this.status);

        return sb.toString();
    }

}
