package bdtc.lab4.controller.model;

import java.util.Objects;

public class Health {
    private String status;

    public Health() {
        this.status = "running";
    }

    public Health(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Health health = (Health) o;
        return Objects.equals(status, health.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }
}
