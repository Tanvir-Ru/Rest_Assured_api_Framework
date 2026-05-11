package com.tanvir.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Loan {

    private Integer id;

    @JsonProperty("user_id")
    private Integer userId;

    private Double principal;

    @JsonProperty("annual_rate")
    private Double annualRate;

    @JsonProperty("term_months")
    private Integer termMonths;

    @JsonProperty("monthly_payment")
    private Double monthlyPayment;

    @JsonProperty("total_repayable")
    private Double totalRepayable;

    @JsonProperty("total_interest")
    private Double totalInterest;

    private String status;

    @JsonProperty("created_at")
    private String createdAt;

    // ── Constructors ──────────────────────────────────────────────────────────
    public Loan() {}

    public Loan(Integer userId, Double principal, Double annualRate, Integer termMonths) {
        this.userId     = userId;
        this.principal  = principal;
        this.annualRate = annualRate;
        this.termMonths = termMonths;
        this.status     = "pending";
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────
    public Integer getId()                      { return id; }
    public void    setId(Integer id)            { this.id = id; }
    public Integer getUserId()                  { return userId; }
    public void    setUserId(Integer userId)    { this.userId = userId; }
    public Double  getPrincipal()               { return principal; }
    public void    setPrincipal(Double p)       { this.principal = p; }
    public Double  getAnnualRate()              { return annualRate; }
    public void    setAnnualRate(Double r)      { this.annualRate = r; }
    public Integer getTermMonths()              { return termMonths; }
    public void    setTermMonths(Integer t)     { this.termMonths = t; }
    public Double  getMonthlyPayment()          { return monthlyPayment; }
    public void    setMonthlyPayment(Double m)  { this.monthlyPayment = m; }
    public Double  getTotalRepayable()          { return totalRepayable; }
    public void    setTotalRepayable(Double t)  { this.totalRepayable = t; }
    public Double  getTotalInterest()           { return totalInterest; }
    public void    setTotalInterest(Double t)   { this.totalInterest = t; }
    public String  getStatus()                  { return status; }
    public void    setStatus(String status)     { this.status = status; }

    @Override
    public String toString() {
        return String.format("Loan{id=%d, userId=%d, principal=%.2f, rate=%.1f%%, term=%dm, status=%s}",
            id, userId, principal, annualRate, termMonths, status);
    }
}
