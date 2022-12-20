package com.example.projectInternRampUp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "tb_timeperiod")
@Getter
@Setter
public class TimePeriod implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Instant startDateTime;
    private Instant endDateTime;
    private Instant retireDateTime;

    public TimePeriod() {

    }

    public TimePeriod(Integer id, Instant startDateTime, Instant endDateTime, Instant retireDateTime) {
        super();
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.retireDateTime = retireDateTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(retireDateTime, startDateTime);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TimePeriod other = (TimePeriod) obj;
        return Objects.equals(retireDateTime, other.retireDateTime)
                && Objects.equals(startDateTime, other.startDateTime);
    }


}