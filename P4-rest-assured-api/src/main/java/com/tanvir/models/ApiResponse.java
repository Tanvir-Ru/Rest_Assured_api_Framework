package com.tanvir.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse<T> {

    private Integer page;
    private Integer perPage;
    private Integer total;
    private Integer totalPages;
    private List<T> data;
    private String  error;
    private String  token;

    public ApiResponse() {}

    public Integer   getPage()           { return page; }
    public void      setPage(Integer p)  { this.page = p; }
    public Integer   getPerPage()        { return perPage; }
    public Integer   getTotal()          { return total; }
    public Integer   getTotalPages()     { return totalPages; }
    public List<T>   getData()           { return data; }
    public void      setData(List<T> d)  { this.data = d; }
    public String    getError()          { return error; }
    public String    getToken()          { return token; }

    public boolean hasData()  { return data != null && !data.isEmpty(); }
    public boolean hasError() { return error != null && !error.isEmpty(); }
}
