package com.store.store.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "store", name = "products")
public class Product implements Serializable {
    private static final long serialVersionUID = -2402680795113876167L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "cost")
    private int cost;

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }


}
